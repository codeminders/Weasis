package org.weasis.dicom.explorer.google;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class GoogleAuthStub {

    // Stub for google auth token TODO implement auth
    public static String getAuthToken() {
        return "";
    }

    public static URLConnection googleApiConnection(String url) {
        try {
            return googleApiConnection(new URL(url));
        } catch (MalformedURLException ex) {
            throw new IllegalStateException("Failed parse url " + url, ex);
        }
    }

    public static URLConnection googleApiConnection(URL url) {
        try {
            URLConnection connection = url.openConnection();
            connection.addRequestProperty("Authorization", "Bearer " + GoogleAuthStub.getAuthToken());
            return connection;
        } catch (IOException ex) {
            throw new IllegalStateException("Failed to create connection to " + url, ex);
        }
    }

}
