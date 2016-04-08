package com.sunner.imagesocket.Socket;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by sunner on 2016/4/8.
 */
class ImageSocket_UDP {
    DatagramSocket datagramSocket;
    public InetAddress inetAddress;

    public ImageSocket_UDP(String host, int port) throws UnknownHostException, SocketException {
        inetAddress = InetAddress.getByName(host);
        datagramSocket = new DatagramSocket(port);
    }
}
