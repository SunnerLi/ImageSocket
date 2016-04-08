package com.example.sunner.implement408;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sunner.imagesocket.RTP.RTPPacket;

public class MainActivity extends AppCompatActivity {
    String TAG = "資訊";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RTPPacket rtpPacket = new RTPPacket();
        Log.v(TAG, "長度："+rtpPacket.encode("Adsfdfs", 255).length);
        rtpPacket.decode(rtpPacket.encode("Adsfdfs", 2));

    }
}
