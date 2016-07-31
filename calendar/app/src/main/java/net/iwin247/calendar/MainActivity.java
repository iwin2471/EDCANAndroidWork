package net.iwin247.calendar;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button sTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sTS = (Button) findViewById(R.id.SendToServer);
        sTS.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this, TokenToServer.class));
    }
}