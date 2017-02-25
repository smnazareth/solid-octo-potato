package tech.sanjog.smarthome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends Activity {

    Button onButton, offButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.devices_array, android.R.layout.simple_spinner_item);
        onButton = (Button) findViewById(R.id.onButton);
        offButton = (Button) findViewById(R.id.offButton);
        Spinner spinner = (Spinner) findViewById(R.id.DevicesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void sendOn(View V) {
        Context context = getApplicationContext();
        CharSequence text = "LED Coming On!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL gg = null;
                try {
                    gg = new URL("http://10.42.0.232:8000");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                URLConnection yc = null;
                try {
                    yc = gg.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*System.out.println(gg.getProtocol());
                System.out.println(gg.getHost());
                System.out.println(gg.getPort());
                System.out.println(gg.getPath());
                System.out.println(gg.getQuery());
                System.out.println(gg.getRef());*/
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String inputLine;
                try {
                    while ((inputLine = in.readLine()) != null)
                        System.out.println(inputLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void sendOff(View V) throws IOException {
        Context context = getApplicationContext();
        CharSequence text = "LED Going Off!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL gg = null;
                try {
                    gg = new URL("http://10.42.0.232:8001");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                URLConnection yc = null;
                try {
                    yc = gg.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*System.out.println(gg.getProtocol());
                System.out.println(gg.getHost());
                System.out.println(gg.getPort());
                System.out.println(gg.getPath());
                System.out.println(gg.getQuery());
                System.out.println(gg.getRef());*/
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*String inputLine;
                try {
                    while ((inputLine = in.readLine()) != null)
                        System.out.println(inputLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}