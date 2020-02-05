package com.example.projet_koffi_commande;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView create;
    private TextView dateTimeDisplay;
    private EditText codePT;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;

    public List<Article> listArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateTimeDisplay = (TextView)findViewById(R.id.dateTimeDisplay);
        codePT= findViewById(R.id.codePT);
        create= findViewById(R.id.createbtn);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CompositionIntent = new Intent(MainActivity.this,Composition.class);
                CompositionIntent.putExtra("num_commande",codePT.getText().toString());
                CompositionIntent.putExtra("date_commande",dateTimeDisplay.getText().toString());
                CompositionIntent.putExtra("listArticle",(Serializable)listArticle);
                startActivity(CompositionIntent);

            }
        });

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        //connexion à la base de données
        try
        {
            SQLiteDatabase db = this.openOrCreateDatabase("projet_commande", Context.MODE_PRIVATE, null);

            db.execSQL("create table if not exists commande(code_commande integer primary key autoincrement,date text not null)");
            db.execSQL("create table if not exists article(code_article integer primary key autoincrement,libelle text)");
            db.execSQL("create table if not exists facture(fcode_commande integer,fcode_article integer,quantite integer not null,foreign key(fcode_commande) references commande(code_commande),foreign key(fcode_article) references article(code_article),primary key(fcode_commande,fcode_article))");

            /*
            db.execSQL("insert into article(libelle,pu) values('chaussure',99.99),('montre',20.55),('chapeau',10.05)");
            db.execSQL("alter table article add pu double default 2.0 not null;");
            db.execSQL("insert into commande(date) values(datetime('now'))");
            db.execSQL("insert into article(libelle) values('pain'),('riz'),('biscuit')");*/
            /*db.execSQL("insert into etudiant values (3,'Bachir',2)");
            db.execSQL("insert into etudiant values (4,'Omar',2)");
            db.execSQL("insert into etudiant values (5,'Rachida',3)");
            db.execSQL("insert into etudiant values (6,'Allal',1)");*/

            Cursor cur = db.rawQuery("select * from commande order by date desc limit 1",null);
            cur.moveToFirst();
            codePT.setText(String.valueOf(cur.getInt(0)+1));
            cur.close();

            listArticle = new ArrayList<>();

            cur = db.rawQuery("select * from article",null);

            while (cur.moveToNext()){
                System.out.println(cur.getInt(0)+" "+cur.getString(1)+" "+cur.getDouble(2));
                listArticle.add(new Article(cur.getInt(0),cur.getString(1),cur.getDouble(2)));

            }
            cur.close();
        }
        catch (Exception ex)
        {
            Log.d("exercice", ex.getMessage());

        }
    }
}
