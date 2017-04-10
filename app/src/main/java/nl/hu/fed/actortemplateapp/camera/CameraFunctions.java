package nl.hu.fed.actortemplateapp.camera;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jonah-pc on 31-Mar-17.
 */

public class CameraFunctions {
    //permissions zijn nodig voor het opslaan van foto's en het ophalen van foto's uit de gallerij
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    //convert een drawable (vanuit een imageview) naar een string zodat het in de db kan worden opgeslagen
    public String fromImageToString(BitmapDrawable personPhoto){
        Bitmap photoBitmap = personPhoto.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] photoByteFormat = stream.toByteArray();
        String encodedImage = Base64.encodeToString(photoByteFormat, Base64.NO_WRAP);
        return encodedImage;
    }
    //convert de string (vanuit de db) naar een bitmap zodat deze in een imageview geplaatst kan worden
    public Bitmap fromStringToImage(String photo){
        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    //als met de camera een foto is gemaakt, process deze en sla hem op in het device.
    public Bitmap processCapturedImage(Intent intentData){
        Bitmap processedImage = (Bitmap) intentData.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        processedImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return processedImage;
    }
    //verifeer of de app permissions heeft om bestanden te lezen en op te slaan
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
