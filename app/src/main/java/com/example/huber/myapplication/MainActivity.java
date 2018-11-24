package com.example.huber.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView startLabel;
    private Button startButton;
    private RESTClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

//Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

//set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.activity_main);


        startLabel = (TextView)findViewById(R.id.startLabel);
        startButton = (Button) findViewById(R.id.startButton);
        client = new RESTClient();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String name = client.getData();
//
//                System.out.print(name);
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);

                startActivity(intent);
                finish();
            }
        });

    }
}
