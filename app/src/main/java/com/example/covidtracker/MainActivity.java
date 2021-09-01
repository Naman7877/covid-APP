package com.example.covidtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ScrollView scroll;
    PieChart piechart;
    SimpleArcLoader loader;
    TextView caseid ,recoveredid ,death,active,today;
    Button track;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for hide action bar
        // getSupportActionBar().hide();

        loader=findViewById(R.id.loader);
        piechart=findViewById(R.id.piechart);
        scroll=findViewById(R.id.scroll);

        caseid=findViewById(R.id.caseid);
        recoveredid=findViewById(R.id.recoveredid);
        active=findViewById(R.id.active);
        death=findViewById(R.id.death);
        today=findViewById(R.id.today);
        track=findViewById(R.id.track_btn);


        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,contries.class);
                startActivity(intent);

            }
        });




        fetch();



    }
    private  void fetch()
    {
        String url ="https://corona.lmao.ninja/v2/all";
        loader.start();
        // here request for data from api
        StringRequest reuest= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // create json object that handle json data
                        try {
                            JSONObject jsonobject=new JSONObject(response.toString());
                                                                // name featch from post man using get method
                            // another simple from api


                            caseid.setText(jsonobject.getString("cases"));
                            recoveredid.setText(jsonobject.getString("recovered"));
                            active.setText(jsonobject.getString("active"));
                            death.setText(jsonobject.getString("deaths"));
                            today.setText(jsonobject.getString("todayCases"));

                             // by the help of pie chart set the value of pie chart
                            //                                    slice name like case recovered    here parse the value of case form textview    and the set colour


                            piechart.addPieSlice(new PieModel("cases",Integer.parseInt(caseid.getText().toString()), Color.parseColor("#FFA726")));
                            piechart.addPieSlice(new PieModel("recovered",Integer.parseInt(recoveredid.getText().toString()), Color.parseColor("#66BB6A")));
                            piechart.addPieSlice(new PieModel("active",Integer.parseInt(active.getText().toString()), Color.parseColor("#EF5350")));
                            piechart.addPieSlice(new PieModel("deaths",Integer.parseInt(death.getText().toString()), Color.parseColor("#29B6F6")));

                            // here stop show loder and show scroll view where Api information visiable
                            loader.stop();
                            // loader set invisiable because now scroll view visiable
                            loader.setVisibility(View.GONE );
                            scroll.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            loader.stop();
                            loader.setVisibility(View.GONE );
                            scroll.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE );
                scroll.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        // for handle request
        RequestQueue queue= Volley.newRequestQueue(this);
        //here add string Request name in add method
        queue.add(reuest);
    }
}