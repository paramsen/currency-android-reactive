package com.amsen.par.cewrncyconv.base.util;

import android.util.Pair;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Wrapper for doing all Http stuff, without 3rd party
 * libs for a change.
 *
 * @author Pär Amsen 2016
 */
public class Http {
    public static Pair<Reader, HttpURLConnection> get(String rawUrl) {
        try {
            URL url = new URL(rawUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int code = conn.getResponseCode();
            String message = conn.getResponseMessage();

            if (code == HttpURLConnection.HTTP_OK) {
                return new Pair<>(new InputStreamReader(new BufferedInputStream(conn.getInputStream()), "UTF-8"), conn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null; //if network err, die
    }

    /**
     * Reader from get -> get response as String
     */
    public static String toString(Reader reader) {
        try {
            StringBuilder builder = new StringBuilder();
            int c = -1;

            while ((c = reader.read()) != -1) {
                builder.append((char) c);
            }

            return builder.toString();
        } catch (Exception e) {
            try {
                reader.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }

        return null;
    }

}
