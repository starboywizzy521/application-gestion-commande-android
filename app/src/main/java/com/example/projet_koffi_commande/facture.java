package com.example.projet_koffi_commande;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class facture extends AppCompatActivity {

    private Double total=0.0;
    TextView totaltv;
    LinearLayout rowlinearlayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facture);

        rowlinearlayout= findViewById(R.id.rowlinearlayout);
        totaltv=findViewById(R.id.totaltv);

       // try{

            ArrayList<ArrayList<Object>> listDesCompositions = (ArrayList<ArrayList<Object>>) getIntent().getExtras().get("listDesCompositions");
            System.out.println(((Article)listDesCompositions.get(0).get(0)).getLibelle());

            int vertexCount = listDesCompositions.size();
            for (int i = 0; i < vertexCount; i++) {

                /*System.out.printf("Code: %d%n",((Article)listDesCompositions.get(i).get(0)).getCode());
                System.out.println("Libelle: "+((Article)listDesCompositions.get(i).get(0)).getLibelle());
                System.out.printf("Quantité: %d%n",listDesCompositions.get(i).get(1));*/

                LinearLayout Verticalinearlayout = new LinearLayout(this);
                Verticalinearlayout.setOrientation(LinearLayout.HORIZONTAL);
                Verticalinearlayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                LinearLayout bottomline = new LinearLayout(this);
                bottomline.setOrientation(LinearLayout.VERTICAL);
                bottomline.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,1*3));
                bottomline.setBackgroundColor(Color.BLACK);

                Verticalinearlayout.addView(kjk(String.valueOf(((Article)listDesCompositions.get(i).get(0)).getCode())));
                Verticalinearlayout.addView(fed());
                Verticalinearlayout.addView(kjk(((Article)listDesCompositions.get(i).get(0)).getLibelle()));

                Verticalinearlayout.addView(fed());
                Verticalinearlayout.addView(kjk(String.valueOf(((Article)listDesCompositions.get(i).get(0)).getPu())+"€"));
                Verticalinearlayout.addView(fed());
                Verticalinearlayout.addView(kjk(String.valueOf(listDesCompositions.get(i).get(1))));

                rowlinearlayout.addView(Verticalinearlayout);
                rowlinearlayout.addView(bottomline);


                total+=((Article)listDesCompositions.get(i).get(0)).getPu()*Double.parseDouble(String.valueOf(listDesCompositions.get(i).get(1)));
            }

            totaltv.setText(String.valueOf(Math.ceil(total))+"€");

        /*}catch(Exception ex){
            Log.d("bonjour",ex.getMessage());
        }*/

    }


    public TextView kjk(String valuetv){

        TextView tv=new TextView(this);
        ViewGroup.LayoutParams param=tv.getLayoutParams();

        if(param!=null){
            param.height = getResources().getDimensionPixelSize(R.dimen.text_view_height);
            param.width = getResources().getDimensionPixelSize(R.dimen.text_view_width);
        }
        else{
            param = new ViewGroup.LayoutParams(getResources().getDimensionPixelSize(R.dimen.text_view_width),getResources().getDimensionPixelSize(R.dimen.text_view_height));
        }

        tv.setLayoutParams(param);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(16);
        tv.setText(valuetv);

        return tv;
    }

    public LinearLayout fed(){

        LinearLayout cellseparate = new LinearLayout(this);
        cellseparate.setOrientation(LinearLayout.HORIZONTAL);
        cellseparate.setLayoutParams(new LinearLayout.LayoutParams(1,LinearLayout.LayoutParams.MATCH_PARENT));
        cellseparate.setBackgroundColor(Color.BLACK);

        return cellseparate;
    }

}
