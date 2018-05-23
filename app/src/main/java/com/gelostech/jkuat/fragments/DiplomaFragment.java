package com.gelostech.jkuat.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gelostech.jkuat.R;
import com.gelostech.jkuat.activities.MainActivity;
import com.gelostech.jkuat.activities.ViewPdfActivity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiplomaFragment extends Fragment implements View.OnClickListener{
    private TextView diplomaLevel;
    private ImageView feesIcon, registrationIcon, brochureIcon, rulesIcon, applicationIcon, ttIcon;
    private View view;
    private LinearLayout brochureItem, applicationItem, ttItem, rulesItem, regItem, feesItem;
    private int year;

    public DiplomaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null){
            view =  inflater.inflate(R.layout.fragment_diploma, container, false);

            MainActivity mainActivity = (MainActivity) getActivity();
            year = mainActivity.getYear();

            initViews(view);
            diplomaLevel.setText(" Diploma - Year " + (year+1));
        }

        return view;
    }

    private void initViews(View view){
        diplomaLevel = view.findViewById(R.id.diploma_level);
        feesIcon = view.findViewById(R.id.diploma_icon_fees);
        registrationIcon = view.findViewById(R.id.diploma_icon_registration);
        brochureIcon = view.findViewById(R.id.diploma_icon_brochure);
        rulesIcon = view.findViewById(R.id.diploma_icon_rules);
        applicationIcon = view.findViewById(R.id.diploma_icon_application);
        ttIcon = view.findViewById(R.id.diploma_icon_timetable);

        brochureItem = view.findViewById(R.id.diploma_brochure_item);
        applicationItem = view.findViewById(R.id.diploma_application_item);
        ttItem = view.findViewById(R.id.diploma_tt_item);
        rulesItem = view.findViewById(R.id.diploma_rules_item);
        regItem = view.findViewById(R.id.diploma_reg_item);
        feesItem = view.findViewById(R.id.diploma_fees_item);

        setIcons();
        brochureItem.setOnClickListener(this);
        applicationItem.setOnClickListener(this);
        ttItem.setOnClickListener(this);
        rulesItem.setOnClickListener(this);
        regItem.setOnClickListener(this);
        feesItem.setOnClickListener(this);
    }

    private void setIcons(){
        feesIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_money).sizeDp(24).color(Color.GRAY));
        registrationIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_pencil_square_o).sizeDp(24).color(Color.GRAY));
        brochureIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_newspaper_o).sizeDp(24).color(Color.GRAY));
        rulesIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_list_alt).sizeDp(24).color(Color.GRAY));
        applicationIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_file_text_o).sizeDp(24).color(Color.GRAY));
        ttIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_table).sizeDp(24).color(Color.GRAY));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.diploma_brochure_item:
                viewPdf("Brochure", getString(R.string.url_brochure));
                break;

            case R.id.diploma_application_item:
                viewPdf("Application", getString(R.string.url_application));
                break;

            case R.id.diploma_tt_item:
                viewPdf("Timetable", getString(R.string.url_tt));
                break;

            case R.id.diploma_rules_item:
                viewPdf("Rules & regulations", getString(R.string.url_rules));
                break;

            case R.id.diploma_reg_item:
                viewPdf("Units registration", getString(R.string.url_reg_diploma));
                break;

            case R.id.diploma_fees_item:
                if (year == 0)
                    viewPdf("Fees statement", getString(R.string.url_fees_diploma_1));
                else
                    viewPdf("Fees statement", getString(R.string.url_fees_diploma_2));
                break;

            default:
                break;

        }
    }

    private void viewPdf(String title, String url){
        Intent intent = new Intent(getActivity(), ViewPdfActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.enter_main, R.anim.exit_splash);
    }
}
