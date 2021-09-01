package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covidtracker.contrie.ApiUtility;
import com.example.covidtracker.contrie.apiinterface;
import com.example.covidtracker.contrie.count;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class contries extends AppCompatActivity {

    ScrollView scrolli;
    PieChart piechart1;
    TextView caseidi ,recoveredidi ,deathi,activei,todayi;
    List<count> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contries);

        list=new ArrayList<>();
        scrolli=findViewById(R.id.scrolli);
        piechart1=findViewById(R.id.piechart1);
        caseidi=findViewById(R.id.caseidi);
        recoveredidi=findViewById(R.id.recoveredidi);
        deathi=findViewById(R.id.deathi);
        activei=findViewById(R.id.activei);


        ApiUtility.getapiinterface().getcount().enqueue(new Callback<List<count>>() {
            @Override
            public void onResponse(Call<List<count>> call, Response<List<count>> response) {
                list.addAll(response.body());

                for(int i=0; i<list.size();i++)
                {
                    if(list.get(i).getCountry().equals("India"))
                    {
                        int confirm=Integer.parseInt(list.get(i).getCases());
                        int active=Integer.parseInt((list.get(i).getActive()));
                        int death=Integer.parseInt(list.get(i).getDeaths());

                        caseidi.setText(confirm);
                        activei.setText(active);
                        deathi.setText(death);
                    }

                }

            }

            @Override
            public void onFailure(Call<List<count>> call, Throwable t) {
                Toast.makeText(contries.this, "error", Toast.LENGTH_SHORT).show();

            }
        });


    }

}