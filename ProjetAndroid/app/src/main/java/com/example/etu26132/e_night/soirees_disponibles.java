package com.example.etu26132.e_night;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import DataAcces.RequestQueueSingleton;
import Model.CPException;
import Model.RueException;
import Model.Soiree;
import Model.NomException;
import Model.VilleException;

public class soirees_disponibles extends Fragment {

    private ListView soireesList;
    private ArrayList<Soiree> allSoirees;
    private Gson gson;
    private ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.soirees_disponible_layout, container,false);

        soireesList = (ListView)rootView.findViewById(R.id.list);
        ConnectionDetector cd = new ConnectionDetector(getActivity());
        Boolean isInternetPresent = cd.isConnectingToInternet();


        if (isInternetPresent) {

            super.onCreate(savedInstanceState);
            gson = new Gson();
            allSoirees = new ArrayList<Soiree>();

            progressBar = new ProgressDialog(this.getContext());
            progressBar.setTitle("Chargement des données");
            progressBar.setMessage("Patience");
            progressBar.setCancelable(true);
            progressBar.show();

            RequestQueue requestQueue = RequestQueueSingleton.getInstance().getRequestQueue();
            StringRequest request = new StringRequest(Request.Method.GET, "http://enightapp.azurewebsites.net/api/soirees", new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try{

                        JSONArray jsonArray = new JSONArray(response);
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject mSoiree = jsonArray.getJSONObject(i);
                            Soiree s = new Soiree();
                            s.setIdSoiree(mSoiree.getInt("idsoiree"));
                            s.setNom(mSoiree.getString("nom"));
                            String dateStr = mSoiree.getString("date");
                            s.setDate(sdf.parse(dateStr));
                            s.setVille(mSoiree.getString("ville"));
                            s.setCodePostal(mSoiree.getInt("codePostale"));
                            s.setRue(mSoiree.getString("rue"));

                            if(mSoiree.getDouble("prixPrevente") != 0.00){
                                s.setPrixPrevente(mSoiree.getDouble("prixPrevente"));
                            } else {
                                s.setPrixPrevente(0.00);
                            }

                            if(mSoiree.getDouble("prixSurPlace") != 0.00){
                                s.setPrixSurPlace(mSoiree.getDouble("prixSurPlace"));
                            } else {
                                s.setPrixSurPlace(0.00);
                            }
                            if(mSoiree.getString("description") != null){
                                s.setDescription(mSoiree.getString("description"));
                            } else {
                                s.setDescription(null);
                            }

                            s.setFk_utilisateur(mSoiree.getInt("fk_utilisateur"));
                            allSoirees.add(s);
                        }
                            if(!allSoirees.isEmpty()){
                                SoireeAdapter adapter = new SoireeAdapter(getActivity(), allSoirees);
                                soireesList.setAdapter(adapter);
                                progressBar.dismiss();
                            } else {
                                progressBar.setMessage("Pas de Soiree");
                            }



                    }catch(JSONException e){
                        e.printStackTrace();
                    }catch(ParseException e){
                        e.printStackTrace();
                    }catch(NomException e){
                        e.printStackTrace();
                    }catch(VilleException e){
                        e.printStackTrace();
                    }catch(RueException e){
                        e.printStackTrace();
                    }catch(CPException e){
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setMessage(error.getMessage());
                }

            });

            request.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(request);

        } else {
            super.onCreate(savedInstanceState);

            ArrayList<String> allSoirees = new ArrayList<String>();
            allSoirees.add("b");
            allSoirees.add("a");
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, allSoirees);

            soireesList.setAdapter(adapter);
        }

        soireesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent intent = new Intent(getActivity().getApplication(), description.class);
                Soiree soireeSelected = (Soiree) soireesList.getItemAtPosition(position);
                intent.putExtra("Soiree", soireeSelected);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void parseJSONResponse(JSONObject response){
        progressBar.setMessage("Oui c'est une error");
        if(response == null || response.length()==0){
            return;
        }
        try{
            if(response.has("soirees")){

                JSONArray arraySoiree = response.getJSONArray("soirees");
                Type listType = new TypeToken<List<Soiree>>(){}.getType();
                allSoirees = gson.fromJson(arraySoiree.toString(),listType);
                if(!allSoirees.isEmpty()){
                    SoireeAdapter adapter = new SoireeAdapter(getActivity(), allSoirees);
                    soireesList.setAdapter(adapter);
                } else {
                    progressBar.setMessage("Pas de Soiree");
                }
            }
        } catch(JSONException e){
            e.printStackTrace();
        }
    }


}


