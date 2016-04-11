package com.example.sunner.sendimage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public final String ipTAG = "ip";                                                               // Used to carried ip string
    public final String bundleTAG = "bundle";                                                       // Used to carried bundle
    Button tcpBtn, udpBtn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();

        tcpBtn = (Button)findViewById(R.id.tcp_btn);
        udpBtn = (Button)findViewById(R.id.udp_btn);
        editText = (EditText)findViewById(R.id.ip);

        tcpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editText.getText().toString();
                if (ip == null || ip.length() < 7) {
                    Log.e("Send Image Ex =>>", "IP is invalid");
                    Toast.makeText(MainActivity.this, "IP is invalid", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString(ipTAG, ip);
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, TCPSend.class);
                    intent.putExtra(bundleTAG, bundle);
                    startActivity(intent);
                }
            }
        });
        udpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = editText.getText().toString();
                if (ip == null || ip.length() < 7) {
                    Log.e("Send Image Ex =>>", "IP is invalid");
                    Toast.makeText(MainActivity.this, "IP is invalid", Toast.LENGTH_SHORT).show();
                }
                else {
                    Bundle bundle = new Bundle();
                    bundle.putString(ipTAG, ip);
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, UDPSend.class);
                    intent.putExtra(bundleTAG, bundle);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
