package com.gelostech.jkuat.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.gelostech.jkuat.R;
import com.gelostech.jkuat.fragments.DiplomaFragment;
import com.gelostech.jkuat.fragments.MastersFragment;
import com.gelostech.jkuat.fragments.UnderGraduateFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int id = getIntent().getIntExtra("id", 0);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (id){
            case 0:
                ft.add(R.id.main_activity_frame, new DiplomaFragment());
                ft.commit();
                break;

            case 1:
                ft.add(R.id.main_activity_frame, new UnderGraduateFragment());
                ft.commit();
                break;

            case 2:
                ft.add(R.id.main_activity_frame, new MastersFragment());
                ft.commit();
                break;

            default:
                break;
        }

        checkPermission();

    }

    public int getYear() {
        return getIntent().getIntExtra("year", -1);
    }

    private void checkPermission(){
        Dexter.withActivity(this).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                showPermissionRationale(token);
            }
        }).check();
    }

    private void showPermissionRationale(final PermissionToken token) {
        new AlertDialog.Builder(this).setTitle("Storage permission")
                .setMessage("Storage permission is required to download files")
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.cancelPermissionRequest();
                    }
                })
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        token.continuePermissionRequest();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override public void onDismiss(DialogInterface dialog) {
                        token.cancelPermissionRequest();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_signin, R.anim.exit_main);
    }
}
