package roots.urban.com.urbanroots;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReportActivity extends UrbanRootActivity {

    private String selectedImagePath;
    private ImageView ivImage;
    private String lat, lon;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle("Report Vandalism");
        initView();
        //getLocation();
    }

//    public void getLocation()
//    {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        // Define the criteria how to select the locatioin provider -> use
//        // default
//        Criteria criteria = new Criteria();
//
//        try {
//            Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
//            Toast.makeText(this, location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_LONG).show();
//        }
//        catch(SecurityException e){
//            e.printStackTrace();
//        }
//    }

    private void initView(){
        ivImage = (ImageView) findViewById(R.id.image);
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File photo = new File(Environment.getExternalStorageDirectory(),  "Pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = imageUri;
                    getContentResolver().notifyChange(selectedImage, null);
                    ContentResolver cr = getContentResolver();
                    Bitmap bitmap;
                    try {
                        bitmap = android.provider.MediaStore.Images.Media
                                .getBitmap(cr, selectedImage);

                        int newWidth = bitmap.getWidth() / 2;
                        int newHeight = bitmap.getHeight() / 2;

                        Matrix matrix = new Matrix();

                        matrix.postRotate(90);

                        Bitmap newImage = Bitmap.createScaledBitmap(bitmap,
                                newWidth,
                                newHeight,
                                false);

                        Bitmap rotatedBitmap = Bitmap.createBitmap(newImage, 0, 0, newImage.getWidth(), newImage.getHeight(), matrix, true);
                        ivImage.setImageBitmap(rotatedBitmap);
                        Toast.makeText(this, selectedImage.toString(),
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }
}
