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

import com.gelostech.jkuat.R;
import com.gelostech.jkuat.activities.MastersActivity;
import com.gelostech.jkuat.activities.ViewPdfActivity;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;

/**
 * A simple {@link Fragment} subclass.
 */
public class MastersFragment extends Fragment implements View.OnClickListener{
    private ImageView brochureIcon, rulesIcon, applicationIcon;
    private View view;
    private LinearLayout brochureItem, applicationItem, rulesItem;


    public MastersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null){
            view =  inflater.inflate(R.layout.fragment_masters, container, false);

            initViews(view);

        }

        return view;
    }

    private void initViews(View view){
        brochureIcon = view.findViewById(R.id.masters_icon_brochure);
        rulesIcon = view.findViewById(R.id.masters_icon_rules);
        applicationIcon = view.findViewById(R.id.masters_icon_application);

        brochureItem = view.findViewById(R.id.masters_brochure_item);
        applicationItem = view.findViewById(R.id.masters_application_item);
        rulesItem = view.findViewById(R.id.masters_rules_item);

        setIcons();
        brochureItem.setOnClickListener(this);
        applicationItem.setOnClickListener(this);
        rulesItem.setOnClickListener(this);
    }

    private void setIcons(){
        brochureIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_newspaper_o).sizeDp(24).color(Color.GRAY));
        rulesIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_list_alt).sizeDp(24).color(Color.GRAY));
        applicationIcon.setImageDrawable(new IconicsDrawable(getActivity()).icon(FontAwesome.Icon.faw_file_text_o).sizeDp(24).color(Color.GRAY));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.masters_brochure_item:
                viewPdf("Brochure", getString(R.string.url_brochure));
                break;

            case R.id.masters_application_item:
                loadMastersInfo("Masters application", getString(R.string.url_masters_info));
                break;

            case R.id.masters_rules_item:
                viewPdf("Rules & regulations", getString(R.string.url_rules));
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

    private void loadMastersInfo(String title, String url){
        Intent intent = new Intent(getActivity(), MastersActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.enter_main, R.anim.exit_splash);
    }
}
