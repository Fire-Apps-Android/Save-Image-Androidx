package com.fire.android.scopedstoragewithsavefile;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fire.android.saveimage.DownloadImage;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    ImageView imageView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);
        Picasso.get().load("https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg").into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DownloadImage("Test"
                        , "https://images.pexels.com/photos/2014422/pexels-photo-2014422.jpeg",
                        getApplicationContext(),
                        MainActivity.this);


            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 30:
                if (grantResults.length > 0) {
                    boolean write = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (write) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "You Denied Permission", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}