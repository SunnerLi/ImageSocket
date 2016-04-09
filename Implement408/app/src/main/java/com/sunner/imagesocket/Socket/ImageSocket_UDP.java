package com.sunner.imagesocket.Socket;


import android.graphics.Bitmap;
import android.util.Base64;

import com.sunner.imagesocket.Log.Log;
import com.sunner.imagesocket.RTP.RTPPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by sunner on 2016/4/8.
 */
public class ImageSocket_UDP {
    String TAG = "資訊";
    private static int imageLength = 60000;                                                         // the max length of payload

    DatagramSocket datagramSocket;
    public InetAddress inetAddress;
    public int oppoPort = -1;

    public ImageSocket_UDP(String host, int port) throws UnknownHostException, SocketException {
        // remember to add check if the port number is valid
        datagramSocket = new DatagramSocket(port);
        datagramSocket.setReuseAddress(true);
        inetAddress = InetAddress.getByName(host);
        if (!datagramSocket.isClosed())
            Log.v(TAG, "socket開啟中");
        else
            Log.v(TAG, "socket已關閉");
    }

    // Set the opposite port number
    public ImageSocket_UDP setOppoPort(int port){
        if (port < 10000 || port > 60000)
            Log.e(TAG, "Invalid opposite port number");
        else
            oppoPort = port;
        return this;
    }

    // Set the max length of payload (Default is 60000)
    public ImageSocket_UDP setImageLength(int length){
        if (length>60001 || length < 10)                                                            // length should in range 10-60000
            Log.e(TAG, "Invalid image length");
        else
            imageLength = length;
        return this;
    }

    // Send the image
    public void send(Bitmap bitmap) throws IOException{
        int imageIndex = 0;
        String bitmapString = bitMap2String(bitmap);
        String smallString = "";

        do {
            // Get the piece payload of image first and remove it
            if (bitmapString.length() > imageLength) {
                smallString = bitmapString.substring(0, imageLength);
                bitmapString = bitmapString.substring(imageLength, bitmapString.length());
            } else {
                smallString = bitmapString;
                bitmapString = "";
            }

            // Encode the RTP
            RTPPacket rtpPacket = new RTPPacket();
            byte[] _package = rtpPacket.encode(smallString, imageIndex++);

            // Send the pachage
            DatagramPacket packet = new DatagramPacket(_package, _package.length, inetAddress, 12345);
            android.util.Log.v(TAG, "封包長度: " + packet.getLength());
            datagramSocket.send(packet);


        }while (bitmapString.length() > 0);
    }

    // Bitmap change to String
    public String bitMap2String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        android.util.Log.v("資訊", "bitmap[0]:" + (bitmap.getPixel(0, 0)));
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
