package example.android.one.installationanduninstallation;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + "test.apk";

    public static final String ACTION_INSTALL_COMPLETE
            = "com.facebook.mlite.INSTALL_COMPLETE";

    int currentapiVersion = android.os.Build.VERSION.SDK_INT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                if(currentapiVersion < 23) {
                    install(path);
                }else{
//                    DownloadManager mDownloadManager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
//                    ParcelFileDescriptor pfd = mDownloadManager.openDownloadedFile(id);
                    try{
                        InputStream in = new FileInputStream(new File(Environment.getExternalStorageDirectory() + "/Download/" + "test.apk"));
                        installPackage(getApplicationContext(), in, "com.facebook.mlite");
                    }catch(IOException ex){
                        Log.e("INSTALLATION", "Silent shiiiiiiiieeeet");
                        ex.printStackTrace();
                    }
                }
            }
        });

        Button b2 = (Button) findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Do something in response to button click
                uninstall();
            }
        });
    }

    private void install(String path){

        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File apkFile = new File(Environment.getExternalStorageDirectory() + "/Download/" + "test.apk");
        Uri apkUrl = Uri.fromFile(apkFile);

        Intent promptInstall = new Intent(Intent.ACTION_VIEW)
                .setDataAndType(apkUrl,
                        "application/vnd.android.package-archive");
        startActivity(promptInstall);
    }


    private static boolean installPackage(Context context, InputStream in, String packageName) throws IOException {
        PackageInstaller packageInstaller = context.getPackageManager().getPackageInstaller();
        PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                PackageInstaller.SessionParams.MODE_FULL_INSTALL);
        params.setAppPackageName(packageName);
        // set params
        int sessionId = packageInstaller.createSession(params);
        PackageInstaller.Session session = packageInstaller.openSession(sessionId);
        OutputStream out = session.openWrite("com.facebook.mlite", 0, -1);
        byte[] buffer = new byte[65536];
        int c;
        while ((c = in.read(buffer)) != -1) {
            out.write(buffer, 0, c);
        }
        session.fsync(out);
        in.close();
        out.close();

        session.commit(createIntentSender(context, sessionId));
        return true;
    }

    private static IntentSender createIntentSender(Context context, int sessionId) {
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                sessionId,
                new Intent(ACTION_INSTALL_COMPLETE),
                0);
        return pendingIntent.getIntentSender();
    }

    private void uninstall(){
//        File apkFile = new File(Environment.getExternalStorageDirectory() + "/Download/" + "test.apk");
//        Uri apkUrl = Uri.fromFile(apkFile);

        Intent intent = new Intent(Intent.ACTION_DELETE, Uri.fromParts("package",
                "com.facebook.mlite",null));
        startActivity(intent);
    }

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
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
