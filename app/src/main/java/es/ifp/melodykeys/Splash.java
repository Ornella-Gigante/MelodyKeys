package es.ifp.melodykeys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.Manifest;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Splash extends AppCompatActivity {

    private static final int PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;

    String[] permissionsRequired = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    private SharedPreferences permissionStatus;
    private boolean sentToSettings = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        permissionStatus = getSharedPreferences("permissionStatus", MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(Splash.this, permissionsRequired[0]) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(Splash.this, permissionsRequired[1]) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(Splash.this, permissionsRequired[1])) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Storage and Record Audio Permissions");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();

            } else {
                ActivityCompat.requestPermissions(Splash.this, permissionsRequired, PERMISSION_CONSTANT);
            }
        }else{

            proceedAfterPermission();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull
                                           int[] grantResults){

        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        if(requestCode == PERMISSION_CONSTANT){

            boolean allgranted = true;

            for(int i =0; i<grantResults.length; i++){

                if(grantResults[i] == PackageManager.PERMISSION_GRANTED){

                    allgranted = true;
                }else{
                    allgranted = false;
                }

            }if(allgranted){
                proceedAfterPermission();
            }else if(ActivityCompat.shouldShowRequestPermissionRationale(Splash.this,permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(Splash.this,permissionsRequired[1])
            ){

                AlertDialog.Builder builder = new AlertDialog.Builder(Splash.this);
                builder.setTitle("Need Multiple Permissions");
                builder.setMessage("This app needs Storage and Record Audio Permissions");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        sentToSettings = true;
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
        }else{
                Toast.makeText(this, "Unable to Get Permissions", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "App will not work properly", Toast.LENGTH_SHORT).show();
            }
    }


   private void proceedAfterPermission() {

        Toast.makeText(this, "Got All Permissions", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
