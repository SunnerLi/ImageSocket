package com.example.sunner.sendimage;

import android.util.Log;

/**
 * Created by sunner on 2016/7/29.
 * Check if the input address is valid
 */
public final class Examiner {

    // Check if the address is the valid internet address
    public boolean isValidInternetAddr(String addr) {
        int dotCount = 0;
        for (int i = 0; i < addr.length(); i++) {
            if (addr.charAt(i) == '.')
                dotCount++;
        }
        return (dotCount == 4 ? true : false);
    }

    // Check if the address is the valid bluetooth address
    public boolean isValidBTAddr(String addr) {
        if (addr.length() != 17)
            return false;
        else {
            int dotCount = 0;
            for (int i = 0; i < addr.length(); i++) {
                if (addr.charAt(i) == ':')
                    dotCount++;
            }
            return (dotCount == 5 ? true : false);
        }
    }
}
