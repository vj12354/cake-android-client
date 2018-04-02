package com.waracle.androidtest;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Riad on 20/05/2015.
 */
public class StreamUtils {
    private static final String TAG = StreamUtils.class.getSimpleName();

    // Can you see what's wrong with this???
    public static byte[] readUnknownFully(InputStream stream) throws IOException {
        // Read in stream of bytes
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        while (true) {
            byte[] bytes = new byte[1024];
            int count = stream.read(bytes, 0, 1024);
            if (count == -1) {
                break;
            } else {
                byteArrayOS.write(bytes, 0, count);
            }
        }

        byteArrayOS.flush();
        byteArrayOS.close();
        return byteArrayOS.toByteArray();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }
}
