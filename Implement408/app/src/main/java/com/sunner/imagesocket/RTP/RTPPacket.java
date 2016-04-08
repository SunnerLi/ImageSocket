package com.sunner.imagesocket.RTP;

import android.util.Base64;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Calendar;

/**
 * Created by sunner on 2016/4/8.
 */
public class RTPPacket {
    String TAG = "資訊";

    /*----------------------------------------------------------------------------------------------
     *                                      Header Variable
     *----------------------------------------------------------------------------------------------*/
    //  version 為版號，目前RTP版號為2
    public static int version = 2;

    //  padding 為加密演算法，目前不使用
    //  1為使用，0為不使用
    public static int padding = 0;

    // extension 為頭部延長(預設不延長)
    // 1為延長，0為不延長
    public static int extension = 0;

    //  cc 為csrc個數，這裡只有手機(1)
    public static int cc = 1;

    //  pt 為type，jpeg設定為1(?)
    public static int pt = 1;

    //  ssrc為ss個數，本插件目前僅支援單播
    public static int ssrc = 1;

    /*----------------------------------------------------------------------------------------------
     *                                      Rest Variable
     *----------------------------------------------------------------------------------------------*/
    int minute = -1, second = -1, millisecond = -1;


    // 建構式
    public RTPPacket() {

    }

    // Get the current time
    public void getCurrentTime() {
        // 獲取時間資訊
        Calendar calendar = Calendar.getInstance();
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        millisecond = calendar.get(Calendar.MILLISECOND);
    }

    // 編碼(default)
    public byte[] encode(String payload, int imageIndex) {
        // 第 1 個參數為image之index
        // 第 3 個參數表示是否為結尾   ( 0 為結尾 )
        return encode(payload, imageIndex, (payload.length() > 60000 ? 1 : 0));
    }

    // 編碼實作
    // 0000 0010
    // 1000 0000 000
    public byte[] encode(String payload, int imageIndex, int marker) {
        int[] header = new int[12];

        header[0] = (header[0] | version << 6) & 0x40;
        header[0] = (header[0] | padding << 5);
        header[0] = (header[0] | extension << 4);                                                   // 是否頭部延長
        header[0] = (header[0] | (cc & 0x0F));                                                      // cc用4個bit
        header[1] = (header[1] | marker << 7);                                                      // 第2個byte的第1個bit
        header[1] = (header[1] | (pt & 0x7F));                                                      // 第2個byte的第2-8個bit
        header[2] = (imageIndex & 0xFF00) >> 8;                                                     // 第3個byte
        header[3] = (imageIndex & 0xFF);                                                            // 第4個
        getCurrentTime();
        header[4] = minute & 0xFF;                                                                  // 32 bit timestamp
        header[5] = second & 0xFF;
        header[6] = (millisecond >> 3) & 0xFF;
        header[7] = (millisecond & 0x07) << 5;
        header[7] = header[7] | (ssrc & 0x0F);                                                      // ssrc用4個bit

        return Base64Encode(header, payload);
    }

    public byte[] Base64Encode(int[] header, String payload) {
        byte[] headerBytes = intArr2ByteArr(header);
        Log.v(TAG, "header長度：" + header.length);

        return (new String(Base64.encode(headerBytes, Base64.DEFAULT))
                + payload.getBytes()).getBytes();
    }

    public byte[] intArr2ByteArr(int[] ints) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(ints.length * 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(ints);
        return byteBuffer.array();
    }

    public int[] intArr2ByteArr(byte[] bytes) {
        IntBuffer intBuf = ByteBuffer.wrap(bytes)
                .order(ByteOrder.BIG_ENDIAN)
                .asIntBuffer();
        int[] array = new int[intBuf.remaining()];
        intBuf.get(array);
        return array;
    }

    public void decode(byte[] packet) {
        String wholeString = new String(packet);
        String headerString = wholeString.substring(0, 65);
        String payload = wholeString.substring(65);
        int[] header = intArr2ByteArr(headerString.getBytes());
        Log.v(TAG, "header長度：" + header.length);

    }
}
