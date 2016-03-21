package com.example.ginrex.nytimessearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

public class FIlterActivity extends AppCompatActivity {

    String rdate = null;
    int order = 0;
    int check1 = 0, check2 = 0, check3 = 0;
    String rquery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent r = getIntent();
        rquery = r.getStringExtra("query");
        Button bSave = (Button) findViewById(R.id.btnSave);
        EditText date = (EditText) findViewById(R.id.etDate);
        if (date != null) {rdate = date.getText().toString();}
        else {rdate = "";}

        RadioGroup rgOrder = (RadioGroup) findViewById(R.id.rgOrder);
        rgOrder.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbNew) {order = 1;}
                else if (checkedId == R.id.rbOld) {order = 2;};
            }
        });

        CheckBox c1 = (CheckBox) findViewById(R.id.cbArt);
        CheckBox c2 = (CheckBox) findViewById(R.id.cbFS);
        CheckBox c3 = (CheckBox) findViewById(R.id.cbSport);

        if (c1.isChecked()) {check1 = 1;}
        if (c2.isChecked()) {check2 = 1;}
        if (c3.isChecked()) {check3 = 1;}


        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FIlterActivity.this, SearchActivity.class);
                i.putExtra("order", order);
                i.putExtra("date", rdate);
                i.putExtra("c1", check1);
                i.putExtra("c2", check2);
                i.putExtra("c3", check3);
                i.putExtra("rquery", rquery);
                startActivity(i);
            }
        });


    }

}
