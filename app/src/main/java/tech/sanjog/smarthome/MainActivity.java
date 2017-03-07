package tech.sanjog.smarthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.view.View;
import android.widget.AdapterView;
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

    Button onButton, offButton, fwdButton, revButton, stpButton, mesButton, resButton, calButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.devices_array, android.R.layout.simple_spinner_item);
        onButton = (Button) findViewById(R.id.onButton);
        offButton = (Button) findViewById(R.id.offButton);
        fwdButton = (Button) findViewById(R.id.fwdButton);
        revButton = (Button) findViewById(R.id.revButton);
        stpButton = (Button) findViewById(R.id.stpButton);
        mesButton = (Button) findViewById(R.id.mesButton);
        resButton = (Button) findViewById(R.id.resButton);
        calButton = (Button) findViewById(R.id.calButton);
        Spinner spinner = (Spinner) findViewById(R.id.DevicesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    onButton.setVisibility(View.GONE);
                    offButton.setVisibility(View.GONE);
                    fwdButton.setVisibility(View.VISIBLE);
                    stpButton.setVisibility(View.VISIBLE);
                    revButton.setVisibility(View.VISIBLE);
                    mesButton.setVisibility(View.GONE);
                    calButton.setVisibility(View.GONE);
                    resButton.setVisibility(View.GONE);
                } else if (position == 2) {
                    onButton.setVisibility(View.GONE);
                    offButton.setVisibility(View.GONE);
                    fwdButton.setVisibility(View.GONE);
                    stpButton.setVisibility(View.GONE);
                    revButton.setVisibility(View.GONE);
                    mesButton.setVisibility(View.VISIBLE);
                    calButton.setVisibility(View.GONE);
                    resButton.setVisibility(View.GONE);
                } else if (position == 0) {
                    onButton.setVisibility(View.VISIBLE);
                    offButton.setVisibility(View.VISIBLE);
                    fwdButton.setVisibility(View.GONE);
                    stpButton.setVisibility(View.GONE);
                    revButton.setVisibility(View.GONE);
                    mesButton.setVisibility(View.GONE);
                    calButton.setVisibility(View.GONE);
                    resButton.setVisibility(View.GONE);
                } else if (position == 3) {
                    onButton.setVisibility(View.GONE);
                    offButton.setVisibility(View.GONE);
                    fwdButton.setVisibility(View.GONE);
                    stpButton.setVisibility(View.GONE);
                    revButton.setVisibility(View.GONE);
                    mesButton.setVisibility(View.GONE);
                    calButton.setVisibility(View.VISIBLE);
                    resButton.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                onButton.setVisibility(View.GONE);
                offButton.setVisibility(View.GONE);
                fwdButton.setVisibility(View.GONE);
                stpButton.setVisibility(View.GONE);
                revButton.setVisibility(View.GONE);
                mesButton.setVisibility(View.GONE);
                calButton.setVisibility(View.GONE);
                resButton.setVisibility(View.GONE);
            }
        });
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

    public void sendOff(View V) {
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

    public void sendFwd(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Garage Opening";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL gg = null;
                try {
                    gg = new URL("http://10.42.0.232:8004");
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

    public void sendRev(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Garage Closing";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL gg = null;
                try {
                    gg = new URL("http://10.42.0.232:8002");
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

    public void sendStp(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Door Stooped. Door Ajar!!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL gg = null;
                try {
                    gg = new URL("http://10.42.0.232:8003");
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

    void measure() {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                MessageQueue queue = Looper.myQueue();
                queue.addIdleHandler(new MessageQueue.IdleHandler() {
                    int mReqCount = 0;

                    @Override
                    public boolean queueIdle() {
                        if (++mReqCount == 2) {
                            Looper.myLooper().quit();
                            return false;
                        } else
                            return true;
                    }
                });

                int distance = -1;
                URL gg = null;
                BufferedReader reader = null;
                try {
                    gg = new URL("http://10.42.0.232:8005");
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    reader = new BufferedReader(new InputStreamReader(gg.openStream()));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        int start = line.indexOf("<p>");
                        int end = line.indexOf("</p>");

                        if (start != -1) {
                            distance = Integer.parseInt(line.substring(start + "<p>".length(), end));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (distance != -1) {
                    String txt = "Distance is: ";
                    txt = txt + distance + "cm.";
                    Toast toast1 = Toast.makeText(MainActivity.this, txt, Toast.LENGTH_SHORT);
                    toast1.show();
                    Looper.loop();
                }
            }
        });
        thread.start();
    }

    public void sendMes(View view) {
        final Context context = getApplicationContext();
        CharSequence text = "Measuring...";
        final int duration = Toast.LENGTH_SHORT;
        final Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        measure();
    }

    public void sendCal(View view) {
        Context context = getApplicationContext();
        CharSequence text = "Calibrating Sensor...";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                URL gg = null;
                try {
                    gg = new URL("http://10.42.0.232:8006");
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

    public void sendRes(View view) {
        Intent intent = new Intent(MainActivity.this, SetGeoFence.class);
        startActivity(intent);
    }
}
