package com.sunner.imagesocket.Socket;

import android.graphics.Bitmap;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by sunner on 2016/4/8.
 */
class ImageSocket_TCP {
    Socket socket = null;
    SocketAddress socketAddress;

    public ImageSocket_TCP(String host, int port) throws IOException {
        socket = new Socket(host, port);
        socketAddress = socket.getRemoteSocketAddress();
    }

    // The Image socket can set the time to keep connecting if it fail at first
    public ImageSocket_TCP keepConnect(int timeRepeatConnect) {
        // skip implementation
        return null;
    }

    public ImageSocket_TCP getInputStream() {
        return this;
    }

    // Close the image socket
    public void close() throws IOException {
        if (socket != null)
            socket.close();
    }

    // Connect to the server
    public void connect() throws IOException {
        socket.connect(socketAddress);
    }

    public void send(Bitmap bitmap){
        // skip implementation
    }
}
