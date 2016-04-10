package com.example.sunner.implement408;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sunner.imagesocket.RTP.RTPPacket;
import com.sunner.imagesocket.Socket.ImageSocket;

import java.io.IOException;

/*
imageSocket.setProtocol(ImageSocket.UDP)
                            .getSocket(true)
                            .setOppoPort(12345)
                            .send(getImage());
                    imageSocket.close();

try {
                    imageSocket.setProtocol(ImageSocket.TCP)
                            .getSocket(10)
                            .connect();
                    imageSocket.getInputStream();
                    imageSocket.send(getImage());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

 */
public class MainActivity extends AppCompatActivity {
    String TAG = "資訊";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new Thread() {
            @Override
            public void run() {
                super.run();

                ImageSocket imageSocket = new ImageSocket("192.168.0.101", 12345);
                try {
                    imageSocket.setProtocol(ImageSocket.UDP)
                            .getSocket(true)
                            .setOppoPort(12345)
                            .send(getImage());
                    imageSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        }.start();
    }

    public Bitmap getImage() {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inScaled = false;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.dog1, opts);
        return bitmap;
    }
}
