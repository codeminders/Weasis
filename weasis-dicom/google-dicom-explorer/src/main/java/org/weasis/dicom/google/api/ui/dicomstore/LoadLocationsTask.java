package org.weasis.dicom.google.api.ui.dicomstore;

import org.weasis.dicom.google.api.GoogleAPIClient;
import org.weasis.dicom.google.api.model.Location;
import org.weasis.dicom.google.api.model.ProjectDescriptor;

import java.util.Comparator;
import java.util.List;

public class LoadLocationsTask extends AbstractDicomSelectorTask<List<Location>> {

    private final ProjectDescriptor project;

    public LoadLocationsTask(ProjectDescriptor project,
                             GoogleAPIClient api,
                             DicomStoreSelector view) {
        super(api, view);
        this.project = project;
    }

    @Override
    protected List<Location> doInBackground() throws Exception {
        List<Location> locations = api.fetchLocations(project);
        locations.sort(Comparator.comparing(Location::getName));
        return locations;
    }

    @Override
    protected void onCompleted(List<Location> result) {
        view.updateLocations(result);
    }
}
