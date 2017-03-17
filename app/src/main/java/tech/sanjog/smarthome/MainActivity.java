package tech.sanjog.smarthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static android.location.LocationManager.NETWORK_PROVIDER;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    Button onButton, offButton, fwdButton, revButton, stpButton, mesButton, resButton, calButton, updloc1, updloc2;
    GoogleApiClient mGoogleApiClient = null;
    TextView mLongitudeText, mLatitudeText;
    MockLocationProvider mock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onButton = (Button) findViewById(R.id.onButton);
        offButton = (Button) findViewById(R.id.offButton);
        fwdButton = (Button) findViewById(R.id.fwdButton);
        revButton = (Button) findViewById(R.id.revButton);
        stpButton = (Button) findViewById(R.id.stpButton);
        mesButton = (Button) findViewById(R.id.mesButton);
        resButton = (Button) findViewById(R.id.resButton);
        calButton = (Button) findViewById(R.id.calButton);
        updloc1 = (Button) findViewById(R.id.updloc1Button);
        updloc2 = (Button) findViewById(R.id.updloc2Button);
        mLatitudeText = (TextView) findViewById(R.id.mLatitudeText);
        mLongitudeText = (TextView) findViewById(R.id.mLongitudeText);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.devices_array, android.R.layout.simple_spinner_item);
        Spinner spinner = (Spinner) findViewById(R.id.DevicesList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setVisibility(View.VISIBLE);
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
                    updloc1.setVisibility(View.GONE);
                    updloc2.setVisibility(View.GONE);
                } else if (position == 2) {
                    onButton.setVisibility(View.GONE);
                    offButton.setVisibility(View.GONE);
                    fwdButton.setVisibility(View.GONE);
                    stpButton.setVisibility(View.GONE);
                    revButton.setVisibility(View.GONE);
                    mesButton.setVisibility(View.VISIBLE);
                    calButton.setVisibility(View.GONE);
                    resButton.setVisibility(View.GONE);
                    updloc1.setVisibility(View.GONE);
                    updloc2.setVisibility(View.GONE);
                } else if (position == 0) {
                    onButton.setVisibility(View.VISIBLE);
                    offButton.setVisibility(View.VISIBLE);
                    fwdButton.setVisibility(View.GONE);
                    stpButton.setVisibility(View.GONE);
                    revButton.setVisibility(View.GONE);
                    mesButton.setVisibility(View.GONE);
                    calButton.setVisibility(View.GONE);
                    resButton.setVisibility(View.GONE);
                    updloc1.setVisibility(View.GONE);
                    updloc2.setVisibility(View.GONE);
                } else if (position == 3) {
                    onButton.setVisibility(View.GONE);
                    offButton.setVisibility(View.GONE);
                    fwdButton.setVisibility(View.GONE);
                    stpButton.setVisibility(View.GONE);
                    revButton.setVisibility(View.GONE);
                    mesButton.setVisibility(View.GONE);
                    calButton.setVisibility(View.VISIBLE);
                    resButton.setVisibility(View.VISIBLE);
                    updloc1.setVisibility(View.GONE);
                    updloc2.setVisibility(View.GONE);
                } else if (position == 4) {
                    onButton.setVisibility(View.GONE);
                    offButton.setVisibility(View.GONE);
                    fwdButton.setVisibility(View.GONE);
                    stpButton.setVisibility(View.GONE);
                    revButton.setVisibility(View.GONE);
                    mesButton.setVisibility(View.GONE);
                    calButton.setVisibility(View.GONE);
                    resButton.setVisibility(View.GONE);
                    updloc1.setVisibility(View.VISIBLE);
                    updloc2.setVisibility(View.VISIBLE);
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
                updloc1.setVisibility(View.GONE);
                updloc2.setVisibility(View.GONE);
            }
        });
        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        mock = new MockLocationProvider(NETWORK_PROVIDER, this);
        //Set test location

        LocationManager locMgr = (LocationManager)
                getSystemService(LOCATION_SERVICE);
        LocationListener lis = new LocationListener() {
            public void onLocationChanged(Location location) {
                //You will get the mock location
                mLatitudeText.setText(String.valueOf(location.getLatitude()));
                mLongitudeText.setText(String.valueOf(location.getLongitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
            //...
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locMgr.requestLocationUpdates(
                NETWORK_PROVIDER, 0, 0, lis);

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
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

    @Override
    public void onConnected(@Nullable Bundle connectionHint) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            //here to request the missing permissions, and then overriding
            //public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                    int[] grantResults);
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


    protected void onDestroy() {
        mock.shutdown();
        super.onDestroy();
    }

    public void updloc1(View view) {
        mock.pushLocation(12.8597375, 77.4392705);
        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            //here to request the missing permissions, and then overriding
            //public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                    int[] grantResults);
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }*/
    }

    public void updloc2(View view) {
        mock.pushLocation(12.3456789, 77.8901234);
        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            //here to request the missing permissions, and then overriding
            //public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                    int[] grantResults);
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
        }*/
    }

}
