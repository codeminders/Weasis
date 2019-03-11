package org.weasis.dicom.google.api.ui;

import org.weasis.dicom.google.api.GoogleAPIClient;
import org.weasis.dicom.google.api.ui.dicomstore.DicomStoreSelector;
import org.weasis.dicom.google.explorer.DownloadManager;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import java.awt.*;

import static javax.swing.BoxLayout.PAGE_AXIS;

public class GoogleExplorer extends JPanel {

    private final StudiesTable table;

    private final GoogleAPIClient googleAPIClient;
    private final DicomStoreSelector storeSelector;

    private final SearchPanel searchPanel;

    public GoogleExplorer(GoogleAPIClient googleAPIClient) {
        this.googleAPIClient = googleAPIClient;

        BorderLayout layout = new BorderLayout();

        layout.setHgap(15);
        setLayout(layout);

        table = new StudiesTable(this);
        storeSelector = new DicomStoreSelector(googleAPIClient, table);
        searchPanel = new SearchPanel(googleAPIClient, storeSelector);

        add(centralComponent(), BorderLayout.CENTER);
        add(searchPanel, BorderLayout.WEST);
    }

    public Component centralComponent() {
        JPanel panel = new JPanel();
        BoxLayout layout = new BoxLayout(panel, PAGE_AXIS);
        panel.setLayout(layout);

        panel.add(storeSelector);
        panel.add(Box.createVerticalStrut(10));
        panel.add(table);

        return panel;
    }
    
    public void fireStudySelected(String studyId) {
        storeSelector.getCurrentStore()
                .map(store -> GoogleAPIClient.getImageUrl(store, studyId))
                .ifPresent(image -> {
                	DownloadManager.getLoadingExecutor().submit(
                        new DownloadManager.LoadGoogleDicom(image,  null, googleAPIClient.getAccessToken(), new DownloadManager.DownloadListener() {
							@Override
							public void downloadFinished() {
								table.hideLoadIcon(studyId);
							}
						}));
                	table.showLoadIcon(studyId);
                });
    }

}
