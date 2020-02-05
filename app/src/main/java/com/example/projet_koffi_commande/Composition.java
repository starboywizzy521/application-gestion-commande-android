package com.example.projet_koffi_commande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class Composition extends AppCompatActivity {

    private Spinner Libellespinner;
    private TextView codeArtV;
    private TextView numcommandetv;
    private TextView putv;
    private EditText qteditext;
    private Button facturerbtn;
    private Button Ajouterbtn;
    private ImageView imglogo;

    private List<Article> listDesArticles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_composition);

        numcommandetv = findViewById(R.id.numcommandetv);
        codeArtV = findViewById(R.id.codeArtV);
        Libellespinner = findViewById(R.id.libellespinner);
        qteditext = findViewById(R.id.qteditext);
        facturerbtn =  findViewById(R.id.facturerbtn);
        Ajouterbtn = findViewById(R.id.Ajouterbtn);
        putv= findViewById(R.id.putv);
        imglogo = findViewById(R.id.imglogo);
        numcommandetv.setText(numcommandetv.getText().toString()+getIntent().getStringExtra("num_commande"));

        final ArrayList<ArrayList<Object>> listDesCompositions = new ArrayList<>();


        try{

            listDesArticles = ((List<Article>)getIntent().getExtras().getSerializable("listArticle"));

            ListIterator<Article> iterator = listDesArticles.listIterator();

            Vector<Integer> codesarticle=new Vector<>(); Vector<String> libellesarticle=new Vector<>();

            while (iterator.hasNext()) {
                Article temporary=iterator.next();
                codesarticle.add(temporary.getCode());
                libellesarticle.add(temporary.getLibelle());
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Composition.this, android.R.layout.simple_spinner_item,libellesarticle);
            Libellespinner.setAdapter(adapter);
        }
        catch(Exception ex){
            Log.d("exercice",ex.getMessage());
        }

        Libellespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{
                    codeArtV.setText(String.valueOf(Libellespinner.getSelectedItemPosition()+1));
                    ListIterator<Article> iterator = listDesArticles.listIterator();

                    while (iterator.hasNext()){
                        Article temporary=iterator.next();
                        if(temporary.getLibelle().equals(Libellespinner.getSelectedItem().toString())){
                            putv.setText(String.valueOf(temporary.getPu()));
                        }
                    }
                    int id1 = getResources().getIdentifier("com.example.projet_koffi_commande:drawable/" +Libellespinner.getSelectedItem().toString(), null, null);
                    imglogo.setImageDrawable(getResources().getDrawable(id1));

                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }
               // Log.d("item","item sélectionné: "+Libellespinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Ajouterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Article temporary = new Article(Integer.parseInt(codeArtV.getText().toString()),Libellespinner.getSelectedItem().toString(),Double.parseDouble(putv.getText().toString()));
                    int qte=Integer.parseInt(qteditext.getText().toString());
                    listDesCompositions.add(new ArrayList<Object>(Arrays.asList(temporary,qte)));

                }catch(Exception ex){
                    System.out.println(ex.getMessage());
                }

            }
        });
        facturerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent factureIntent = new Intent(Composition.this,facture.class);
                factureIntent.putExtra("listDesCompositions",listDesCompositions);
                startActivity(factureIntent);
            }
        });
    }
}
