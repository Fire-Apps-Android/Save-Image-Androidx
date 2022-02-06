package com.fire.android.saveimage;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DownloadImage extends AppCompatActivity {
    String folderName, url;
    Context context;
    Activity activity;
    String[] permission={WRITE_EXTERNAL_STORAGE};
    ActivityResultLauncher<Intent> activityResultLauncher;

    public DownloadImage( String folderName, String url, Context context,Activity activity) {
        this.folderName = folderName;
        this.url = url;
        this.context = context;
        this.activity=activity;


        if (checkPermission()){
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri uri = Uri.parse(url);
            DownloadManager.Request request = new DownloadManager.Request(uri);
            String subPath = "/"+folderName+"/" + System.currentTimeMillis() + ".jpg";
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, subPath);
            //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            downloadManager.enqueue(request);
            Toast.makeText(context, "Downloading Start", Toast.LENGTH_SHORT).show();
        }else{
            requestPermission();
        }

        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode()== Activity.RESULT_OK){
                    if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
            }
        });






    }

    void requestPermission(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            try{
                Intent intent=new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", context.getPackageName())));
                activityResultLauncher.launch(intent);

            } catch (Exception e) {
                Intent intent=new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activityResultLauncher.launch(intent);
            }
        }else{
            ActivityCompat.requestPermissions(activity,permission,30);
        }
    }

    boolean checkPermission() {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            return Environment.isExternalStorageManager();

        }else{
            int writeCheck= ContextCompat.checkSelfPermission(context,WRITE_EXTERNAL_STORAGE);
            return writeCheck== PackageManager.PERMISSION_GRANTED;
        }
    }


}




