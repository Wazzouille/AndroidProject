package com.example.etu26132.e_night;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import Model.Soiree;

public class description extends AppCompatActivity {

    private Soiree soireeSelected;
    private String adr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_layout);

        Bundle bundle = this.getIntent().getExtras();
        soireeSelected = (Soiree) bundle.getSerializable("Soiree");
        ((TextView)findViewById(R.id.lieu)).setText(soireeSelected.getVille() + " " + soireeSelected.getCodePostal() + "\n" + soireeSelected.getRue());
        ((TextView)findViewById(R.id.nom)).setText(soireeSelected.getNom());
        ((TextView)findViewById(R.id.date)).setText(soireeSelected.getDate().toString());

        if(soireeSelected.getPrixPrevente() != 0.00) {
            ((TextView) findViewById(R.id.prixPrevente)).setText(Double.toString(soireeSelected.getPrixPrevente()) + " €");
        } else {
            ((TextView) findViewById(R.id.prixPrevente)).setText("n.c.");
        }

        if(soireeSelected.getPrixSurPlace() != 0.00) {
            ((TextView) findViewById(R.id.prixSurPlace)).setText(Double.toString(soireeSelected.getPrixSurPlace()) + " €");
        } else {
            ((TextView) findViewById(R.id.prixSurPlace)).setText("n.c.");
        }
        if(soireeSelected.getDescription() != null) {
            ((TextView) findViewById(R.id.description)).setText(soireeSelected.getDescription());
        } else {
            ((TextView) findViewById(R.id.description)).setText("Pas de description disponible");
        }

        TextView adresse = (TextView)findViewById(R.id.lieu);
        adr = adresse.getText().toString();
        adr = adr.replaceAll("\\s", "+");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.addParticipation) {
            /*preferences = PreferenceManager.getDefaultSharedPreferences(this);
            Editor editor = preferences.edit();
            Gson gson = Gson();
            String json = gson.toJson(soireeSelected);
            editor.putString("Soiree", json);
            editor.commit();
            return true;*/
        } else {

            if (id == R.id.Maps) {

                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + adr + "+Belgium");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }


        }
        return super.onOptionsItemSelected(item);
    }
}