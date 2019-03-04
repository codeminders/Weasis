package org.weasis.dicom.google.api;

public class GoogleAPIClientFactory {

    private static GoogleAPIClientFactory instance;

    private GoogleAPIClient googleAPIClient;

    public static GoogleAPIClientFactory getInstance() {
        if (instance == null) {
            instance = new GoogleAPIClientFactory();
        }
        return instance;
    }

    public GoogleAPIClient createGoogleClient() {
        if (googleAPIClient == null) {
            googleAPIClient = new GoogleAPIClient();
        }
        return googleAPIClient;
    }

}
