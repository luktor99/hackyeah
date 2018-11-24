package com.example.huber.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements RESTCallback {

    private TextView startLabel;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.activity_main);


        startLabel = (TextView)findViewById(R.id.startLabel);
        startButton = (Button) findViewById(R.id.startButton);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }


    @Override
    public void postResponse(JSONObject response) {
        System.out.print(response);
    }

    @Override
    public void postError(VolleyError error) {

    }


}
