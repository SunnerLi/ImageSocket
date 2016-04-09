package com.example.sunner.implement408;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunner.imagesocket.Socket.ImageSocket;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String TAG = "資訊";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSocket imageSocket = new ImageSocket("", 12345);
        try {
            imageSocket.setProtocol(ImageSocket.TCP)
                    .getSocket(10)
                    .connect()
                    .getInputStream()
                    .send(getImage());
            imageSocket.setProtocol(ImageSocket.UDP)
                    .getSocket(true)
                    .setOppoPort(12345)
                    .send(getImage());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            imageSocket.close();
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
