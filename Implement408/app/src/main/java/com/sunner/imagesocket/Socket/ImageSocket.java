package com.sunner.imagesocket.Socket;

import android.graphics.Bitmap;

import com.sunner.imagesocket.Log.Log;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by sunner on 2016/4/8.
 */
public class ImageSocket {
    String TAG = "資訊";

    // The Transfer Protocol
    public final static int Def = -1;
    public final static int TCP = 0;
    public final static int UDP = 1;
    public static int mode = Def;

    // Log
    public static boolean enableLog = true;


    public ImageSocket_TCP socket_tcp = null;
    public ImageSocket_UDP socket_udp = null;
    public String host = null;
    public int port = -1;

    public ImageSocket(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public ImageSocket setProtocol(int mode) {
        if (this.mode != Def) {
            Log.e(TAG, "Mode cannot change unless create a new one");
            return this;
        } else {
            this.mode = mode;
            switch (mode) {
                case TCP:
                    try {
                        socket_tcp = new ImageSocket_TCP(host, port);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return this;
                case UDP:
                    socket_udp = new ImageSocket_UDP(host, port);
                    return this;
                default:
                    Log.e(TAG, "Wrong Mode Number");
                    return null;
            }
        }
    }

    // The Image socket can set the time to keep connecting if it fail at first
    public ImageSocket keepConnect(int timeRepeatConnect) {
        // skip implementation
        // Notice: the UDP should forbid the query
        return null;
    }

    // Inherit the usage of the socket(didn't return the real inputStream)
    public ImageSocket getInputStream() {
        // skip implementation
        // Notice: the UDP should forbid the query
        return null;
    }

    // Close the image socket
    public void close() throws IOException {
        // skip implementation
    }

    // Connect to the server
    public void connect() throws IOException {
        // skip implementation
        // Notice: the UDP should forbid the query
    }

    // Send the Image
    public void send(Bitmap bitmap) {
        // skip implementation
    }

}

