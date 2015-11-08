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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import entity.ReportData;
import helper.API;

public class ReportActivity extends UrbanRootActivity{
    private LocationManager locationManager;
    private String selectedImagePath;
    private ImageView ivImage;
    private String lat = "39.920770", lon = "39.920770";
    private String provider;
    private String encoded;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        setTitle("Report Vandalism");
        initView();

//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        // Define the criteria how to select the locatioin provider -> use
//        // default
//        Criteria criteria = new Criteria();
//        provider = locationManager.getBestProvider(criteria, false);
//
//        try {
//            Location location = locationManager.getLastKnownLocation(provider);
//
//            // Initialize the location fields
//            if (location != null) {
//                System.out.println("Provider " + provider + " has been selected.");
//                onLocationChanged(location);
//            } else {
//                Toast.makeText(this, "Location unavailable", Toast.LENGTH_SHORT).show();
//            }
//        }
//        catch(SecurityException e){
//            e.printStackTrace();
//        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        try {
//            locationManager.requestLocationUpdates(provider, 400, 1, this);
//        } catch(SecurityException e){
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

        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = ((EditText) findViewById(R.id.description)).getText().toString();
                ReportData data = new ReportData(encoded, description, lat, lon, "vandalism");

                API.report(ReportActivity.this, data);
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

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream .toByteArray();
                        encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    } catch (Exception e) {
                        Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                                .show();
                        Log.e("Camera", e.toString());
                    }
                }
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        try {
//            locationManager.removeUpdates(this);
//        } catch(SecurityException e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void onLocationChanged(Location location) {
//        int lat = (int) (location.getLatitude());
//        int lng = (int) (location.getLongitude());
//        lon = lng + "";
//        this.lat = lat + "";
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        // TODO Auto-generated method stub
//
//    }
//
//    @Override
//    public void onProviderEnabled(String provider) {
//        Toast.makeText(this, "Enabled new provider " + provider,
//                Toast.LENGTH_SHORT).show();
//
//    }
//
//    @Override
//    public void onProviderDisabled(String provider) {
//        Toast.makeText(this, "Disabled provider " + provider,
//                Toast.LENGTH_SHORT).show();
//    }
}
