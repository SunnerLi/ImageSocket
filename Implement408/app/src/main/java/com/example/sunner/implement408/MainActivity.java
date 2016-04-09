package com.example.sunner.implement408;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.sunner.imagesocket.RTP.RTPPacket;
import com.sunner.imagesocket.Socket.ImageSocket;
import com.sunner.imagesocket.Socket.ImageSocket_UDP;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    String TAG = "資訊";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            new ImageSocket_UDP("", 12345)
                    .getSocketWithCheck()
                    .setOppoPort(12345)
                    .send(getImage());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getImage() {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        //Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.screen1, opts);
        return null;
    }
}
