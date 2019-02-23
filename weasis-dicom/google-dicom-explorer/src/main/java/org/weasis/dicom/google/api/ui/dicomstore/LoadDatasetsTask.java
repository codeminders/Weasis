package org.weasis.dicom.google.api.ui.dicomstore;

import org.weasis.dicom.google.api.GoogleAPIClient;
import org.weasis.dicom.google.api.model.Dataset;
import org.weasis.dicom.google.api.model.Location;

import java.util.Comparator;
import java.util.List;

public class LoadDatasetsTask extends AbstractDicomSelectorTask<List<Dataset>> {

    private final Location location;

    public LoadDatasetsTask(Location location,
                            GoogleAPIClient api,
                            DicomStoreSelector view) {
        super(api, view);
        this.location = location;
    }

    @Override
    protected List<Dataset> doInBackground() throws Exception {
        List<Dataset> locations = api.fetchDatasets(location);
        locations.sort(Comparator.comparing(Dataset::getName));
        return locations;
    }

    @Override
    protected void onCompleted(List<Dataset> result) {
        view.updateDatasets(result);
    }
}
