package com.fire.android.scopedstoragewithsavefile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.fire.android.saveimage.SaveImage;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;

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

                    /*BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                    Bitmap bitmap = bitmapDrawable.getBitmap();
                    saveImageAboveQ(bitmap);*/

                new SaveImage(imageView,
                        "HD IMAGE","saved!",
                        "not saved!",getApplicationContext());

            }
        });

    }





/*    private void saveImageAboveQ(Bitmap bitmap) {
        OutputStream outputStream;
        try {

            ContentResolver contentResolver = getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                       Environment.DIRECTORY_PICTURES + File.separator + "HD IMAGES");
            }
            Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            outputStream = (FileOutputStream) contentResolver.openOutputStream(Objects.requireNonNull(imageUri));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            Objects.requireNonNull(outputStream);
            Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();


        } catch (Exception e) {
            Toast.makeText(this, "Not Saved!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }*/
}