package com.codeminders.demo.ui;

import com.codeminders.demo.GoogleAPIClient;
import com.codeminders.demo.ui.dicomstore.DicomStoreSelector;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class GoogleExplorer extends JPanel {

    private final StudiesTable table;

    private final GoogleAPIClient googleAPIClient;
    private final List<BiConsumer<String, String>> studySelectedListener = new ArrayList<>();
    private final DicomStoreSelector storeSelector;

    public GoogleExplorer(GoogleAPIClient googleAPIClient) {
        this.googleAPIClient = googleAPIClient;

        BorderLayout layout = new BorderLayout();

        layout.setVgap(20);
        setLayout(layout);

        table = new StudiesTable(this);
        storeSelector = new DicomStoreSelector(googleAPIClient, table);

        add(table, BorderLayout.CENTER);
        add(storeSelector, BorderLayout.NORTH);
    }

    public void fireStudySelected(String studyId) {
        storeSelector.getCurrentStore()
                .map(store -> GoogleAPIClient.getImageUrl(store, studyId))
                .ifPresent(image -> studySelectedListener.forEach(listener -> listener.accept(image, googleAPIClient.getAccessToken())));
    }

    public void subscribeStudySelected(BiConsumer<String, String> consumer) {
        studySelectedListener.add(consumer);
    }
}
