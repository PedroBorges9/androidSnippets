package example.android.one.cpuusage;

import android.app.ActivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.RandomAccessFile;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView cpuText = (TextView) findViewById(R.id.CPUText);
        final TextView memText = (TextView) findViewById(R.id.MEMText);

        final Handler handler = new Handler();

        final Runnable r = new Runnable() {
            public void run() {
                cpuText.setText(String.valueOf(readCPUUsage()));
                readMEMUsage(memText);
                handler.postDelayed(this, 1000);
            }
        };

        handler.postDelayed(r, 1000);
    }

    private void readMEMUsage(TextView memText){

        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager)getSystemService(ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(mi);
        long availableMegs = mi.availMem / 1048576L;
//        totalMegs = mi.totalMem / 1048576L;
        memText.setText(mi.totalMem / 1048576L + " " + mi.availMem / 1048576L + " " + (mi.totalMem - mi.availMem) * 100 / mi.totalMem);
//        return (mi.totalMem - mi.availMem) * 100 / mi.totalMem;

    }

    private float readCPUUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" +");  // Split on one or more spaces

            long idle1 = Long.parseLong(toks[4]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {

            }

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" +");

            long idle2 = Long.parseLong(toks[4]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[5])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            return (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1)) * 100;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0;
    }

}
