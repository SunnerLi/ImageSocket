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
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by sunner on 2016/4/8.
 */
public class ImageSocket_UDP {
    String TAG = "資訊";
    private static int imageLength = 60000;                                                         // the max length of payload

    DatagramSocket datagramSocket[];
    private InetAddress inetAddress;

    private int localPort = -1;
    private String oppoHost = "";
    private int oppoPort = -1;

    public ImageSocket_UDP(String host, int port) {
        // remember to add check if the port number is valid
        localPort = port;
        oppoHost = host;
    }

    public ImageSocket_UDP getSocketWithoutCheck() throws UnknownHostException, SocketException {
        for (int i = 0; i < 5; i++) {
            datagramSocket[i] = new DatagramSocket(localPort + i);
            datagramSocket[i].setReuseAddress(true);
            inetAddress = InetAddress.getByName(oppoHost);
            if (!datagramSocket[i].isClosed())
                Log.v(TAG, "socket [" + localPort + i + "] 開啟中");
            else
                Log.v(TAG, "socket [" + localPort + i + "] 已關閉");
        }
        return this;
    }

    public ImageSocket_UDP getSocketWithCheck() throws UnknownHostException, SocketException {
        while (!portsAvaliable(localPort)) {
            Log.v(TAG, "本地連接阜" + localPort + "被佔用，自動產生新連接阜號碼");
            determineNewPort();
        }
        Log.v(TAG, "本地連接阜號碼為" + localPort);
        return getSocketWithoutCheck();
    }

    // 檢查後5個阜號碼是否閒置
    private boolean portsAvaliable(int port) {
        for (int i = port; i < port + 5; i++) {
            if (!portAvaliable(i))
                return false;
        }
        return true;
    }

    // 檢查特定1個阜號碼是否閒置
    private static boolean portAvaliable(int port) {
        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                }
            }
        }
        return false;
    }

    // 產生新的阜號碼並連接模式代碼
    private void determineNewPort() {
        // 休息一會兒讓系統釋放阜
        int port = -1;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        do {
            port = (int) (Math.random() * 10000 + 40000);
        } while (!portsAvaliable(port));
        localPort = port;
    }

    // Set the opposite port number
    public ImageSocket_UDP setOppoPort(int port) {
        if (port < 10000 || port > 60000)
            Log.e(TAG, "Invalid opposite port number");
        else
            oppoPort = port;
        return this;
    }

    // Set the max length of payload (Default is 60000)
    public ImageSocket_UDP setImageLength(int length) {
        if (length > 60001 || length < 10)                                                            // length should in range 10-60000
            Log.e(TAG, "Invalid image length");
        else
            imageLength = length;
        return this;
    }

    // Send the image
    public ImageSocket_UDP send(Bitmap bitmap) throws IOException {
        int imageIndex = 0;
        String bitmapString = bitMap2String(bitmap);
        String smallString = "";

        try {
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
                RTPPacket rtpPacket = new RTPPacket().setMaxLengthPayload(imageLength);
                byte[] _package = rtpPacket.encode(smallString, imageIndex++);

                // Send the pachage
                DatagramPacket packet = new DatagramPacket(_package, _package.length, inetAddress, oppoPort);
                Log.v(TAG, "封包長度: " + packet.getLength());
                datagramSocket[imageIndex % 5].send(packet);
            } while (bitmapString.length() > 0);
        } finally {
            for (int i = 0; i < 5; i++)
                datagramSocket[i].close();
        }
        return this;
    }

    // Bitmap change to String
    private String bitMap2String(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        Log.v("資訊", "bitmap[0]:" + (bitmap.getPixel(0, 0)));
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
