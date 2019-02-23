package org.weasis.dicom.google.api.ui.dicomstore;

import org.weasis.dicom.google.api.GoogleAPIClient;
import org.weasis.dicom.google.api.model.Dataset;
import org.weasis.dicom.google.api.model.DicomStore;

import java.util.Comparator;
import java.util.List;

public class LoadDicomStoresTask extends AbstractDicomSelectorTask<List<DicomStore>> {

    private final Dataset dataset;

    public LoadDicomStoresTask(Dataset dataset,
                               GoogleAPIClient api,
                               DicomStoreSelector view) {
        super(api, view);
        this.dataset = dataset;
    }

    @Override
    protected List<DicomStore> doInBackground() throws Exception {
        List<DicomStore> locations = api.fetchDicomstores(dataset);
        locations.sort(Comparator.comparing(DicomStore::getName));
        return locations;
    }

    @Override
    protected void onCompleted(List<DicomStore> result) {
        view.updateDicomStores(result);
    }
}
