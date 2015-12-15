package com.example.etu26132.e_night;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import DataAcces.RequestQueueSingleton;
import Model.*;

/**
 * Created by etu26132 on 23/10/2015.
 */
public class ajout_soiree extends Fragment{

    private EditText nom;
    private EditText ville;
    private EditText codePostal;
    private EditText rue;
    private EditText prixSurPlace;
    private EditText prixPrevente;
    private DatePicker date;
    private EditText description;
    private Button bAjouter;
    private Soiree newSoiree;
    private GregorianCalendar cal;
    RequestQueue requestQueue = RequestQueueSingleton.getInstance().getRequestQueue();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        rootView = inflater.inflate(R.layout.ajout_soiree_layout, container,false);

         nom = ((EditText)rootView.findViewById(R.id.editNameParty));
         ville = ((EditText)rootView.findViewById(R.id.editVille));
         codePostal = ((EditText)rootView.findViewById(R.id.editCP));
         rue = ((EditText)rootView.findViewById(R.id.editRue));
         prixSurPlace = ((EditText)rootView.findViewById(R.id.editPricePlace));
         prixPrevente = ((EditText)rootView.findViewById(R.id.editPricePrev));
         date = ((DatePicker)rootView.findViewById(R.id.datePicker2));
         date.setMinDate(System.currentTimeMillis() - 1000);
         description = ((EditText)rootView.findViewById(R.id.editDescrip));
         bAjouter = (Button)rootView.findViewById(R.id.buttonAjout);

         bAjouter.setOnClickListener(new View.OnClickListener(){
             public void onClick(View view){
             if(view.getId() == R.id.buttonAjout){
                 try {
                     newSoiree = new Soiree();
                     newSoiree.setNom(nom.getText().toString());
                     newSoiree.setVille(ville.getText().toString());
                     newSoiree.setCodePostal(Integer.parseInt(codePostal.getText().toString()));
                     newSoiree.setRue(rue.getText().toString());
                     if(!prixSurPlace.getText().toString().isEmpty()) {
                         newSoiree.setPrixSurPlace(Double.parseDouble(prixSurPlace.getText().toString()));
                     }
                     if(!prixPrevente.getText().toString().isEmpty()) {
                         newSoiree.setPrixPrevente(Double.parseDouble(prixPrevente.getText().toString()));
                     }
                     int day = date.getDayOfMonth();
                     int month = date.getMonth();
                     int year = date.getYear();
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                     String formatedDate = sdf.format(new Date(year, month, day));
                     newSoiree.setDate(sdf.parse(formatedDate));

                     if(!description.getText().toString().isEmpty()) {
                         newSoiree.setDescription(description.getText().toString());
                     }
                     newSoiree.setFk_utilisateur(1); //TODO
                     addSoiree();

                 }catch(NomException nE){
                    Toast.makeText(getContext(), "Veuillez entre un nom valide", Toast.LENGTH_LONG).show();
                 }catch(VilleException vE){
                     Toast.makeText(getContext(), "Veuillez entre une ville valide", Toast.LENGTH_LONG).show();
                 }catch(RueException rE){
                     Toast.makeText(getContext(), "Veuillez entre une rue valide", Toast.LENGTH_LONG).show();
                 }catch(CPException cpE){
                     Toast.makeText(getContext(), "Veuillez entre un code postal valide", Toast.LENGTH_LONG).show();
                 } catch (ParseException e) {
                     e.printStackTrace();
                 }
             }
            }
         });
        return rootView;
    }

    public void addSoiree() {
        JSONObject params = new JSONObject();
        try {
            params.put("nom", newSoiree.getNom());
            params.put("date", String.valueOf(cal));
            params.put("ville", newSoiree.getVille());
            params.put("codePostale", String.valueOf(newSoiree.getCodePostal()));
            params.put("rue", newSoiree.getRue());
            params.put("prixPrevente", String.valueOf(newSoiree.getPrixPrevente()));
            params.put("prixSurPlace", String.valueOf(newSoiree.getPrixSurPlace()));
            params.put("description", newSoiree.getDescription());
            params.put("fk_utilisateur", String.valueOf(newSoiree.getFk_utilisateur()));
        }catch(JSONException e){
            e.printStackTrace();
        }
        JsonObjectRequest requestAddSoiree = new JsonObjectRequest(Request.Method.POST, "http://enightapp.azurewebsites.net/api/soirees/", params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(), "Ajout bien effectué", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

        };

        requestAddSoiree.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(requestAddSoiree);
    }
}
