package com.gelostech.jkuat.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gelostech.jkuat.R;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title;
    private TextView yearTitle;
    private TextView dipYearTitle;
    private static final String titleText = "JOMO KENYATTA UNIVERSITY OF \n AGRICULTURE & TECHNOLOGY";
    private Button select;
    private boolean isDoubleTap = false;
    private Spinner spinner;
    private Spinner yearSpinner;
    private Spinner dipYearSpinner;
    private int levelPos;
    private int yearPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        initViews();
    }

    private void initViews(){
        title = findViewById(R.id.start_title);
        select = findViewById(R.id.button_select);
        spinner = findViewById(R.id.select_level);
        yearSpinner = findViewById(R.id.select_year);
        yearTitle = findViewById(R.id.select_year_title);
        dipYearTitle = findViewById(R.id.select_dip_year_title);
        dipYearSpinner = findViewById(R.id.select_dip_year);

        title.setText(titleText);
        select.setOnClickListener(this);

        initLevelSpinner();
        initYearSpinner();
        initDipYearSpinner();
    }

    private void initLevelSpinner() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                levelPos = position;

                yearSpinner.setVisibility(spinner.getSelectedItemId() == 1 ? View.VISIBLE : View.GONE);
                yearTitle.setVisibility(spinner.getSelectedItemId() == 1 ? View.VISIBLE : View.GONE);

                dipYearSpinner.setVisibility(spinner.getSelectedItemId() == 0 ? View.VISIBLE : View.GONE);
                dipYearTitle.setVisibility(spinner.getSelectedItemId() == 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initYearSpinner() {
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearPos = yearSpinner.isShown() ? position : -1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initDipYearSpinner() {
        dipYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearPos = dipYearSpinner.isShown() ? position : -1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_select:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("id", levelPos);

                if (yearSpinner.isShown()) intent.putExtra("year", yearPos);
                if (dipYearSpinner.isShown()) intent.putExtra("year", yearPos);

                startActivity(intent);
                overridePendingTransition(R.anim.enter_main, R.anim.exit_splash);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if(isDoubleTap){
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Tap back again to exit", Toast.LENGTH_SHORT).show();

            isDoubleTap = true;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isDoubleTap = false;
                }
            }, 1500);
        }


    }


}
