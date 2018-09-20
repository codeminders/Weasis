package com.codeminders.demo.ui.dicomstore;

import com.codeminders.demo.*;
import com.codeminders.demo.model.Dataset;
import com.codeminders.demo.model.DicomStore;
import com.codeminders.demo.model.Location;
import com.codeminders.demo.model.ProjectDescriptor;
import com.codeminders.demo.ui.StudiesTable;
import com.codeminders.demo.ui.StudyView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.util.Collections.emptyList;

public class DicomStoreSelector extends JPanel {

    private final GoogleAPIClient googleAPIClient;

    private final DefaultComboBoxModel<Optional<ProjectDescriptor>> modelProject = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<Optional<Location>> modelLocation = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<Optional<Dataset>> modelDataset = new DefaultComboBoxModel<>();
    private final DefaultComboBoxModel<Optional<DicomStore>> modelDicomstore = new DefaultComboBoxModel<>();

    private final StudiesTable table;

    public DicomStoreSelector(GoogleAPIClient googleAPIClient, StudiesTable table) {
        this.googleAPIClient = googleAPIClient;
        this.table = table;
        BoxLayout layout = new BoxLayout(this, BoxLayout.X_AXIS);
        setLayout(layout);

        JComboBox<Optional<ProjectDescriptor>> googleProjectCombobox = new JComboBox<>(modelProject);
        JComboBox<Optional<Location>> googleLocationCombobox = new JComboBox<>(modelLocation);
        JComboBox<Optional<Dataset>> googleDatasetCombobox = new JComboBox<>(modelDataset);
        JComboBox<Optional<DicomStore>> googleDicomstoreCombobox = new JComboBox<>(modelDicomstore);

        googleProjectCombobox.setPrototypeDisplayValue(Optional.empty());
        googleLocationCombobox.setPrototypeDisplayValue(Optional.empty());
        googleDatasetCombobox.setPrototypeDisplayValue(Optional.empty());
        googleDicomstoreCombobox.setPrototypeDisplayValue(Optional.empty());

        add(googleProjectCombobox);
        add(Box.createHorizontalStrut(10));
        add(googleLocationCombobox);
        add(Box.createHorizontalStrut(10));
        add(googleDatasetCombobox);
        add(Box.createHorizontalStrut(10));
        add(googleDicomstoreCombobox);
        add(Box.createHorizontalGlue());

        googleProjectCombobox.setRenderer(new ListRenderer<>(ProjectDescriptor::getName, "-- Choose project --"));
        googleLocationCombobox.setRenderer(new ListRenderer<>(Location::getId, "-- Choose location --"));
        googleDatasetCombobox.setRenderer(new ListRenderer<>(Dataset::getName, "-- Choose dataset --"));
        googleDicomstoreCombobox.setRenderer(new ListRenderer<>(DicomStore::getName, "-- Choose store --"));

        googleProjectCombobox.addItemListener(this.<ProjectDescriptor>selectedListener(
                project -> new LoadLocationsTask(project, googleAPIClient, this),
                nothing -> updateLocations(emptyList())
        ));

        googleLocationCombobox.addItemListener(this.<Location>selectedListener(
                location -> new LoadDatasetsTask(location, googleAPIClient, this),
                nothing  -> updateDatasets(emptyList())
        ));

        googleDatasetCombobox.addItemListener(this.<Dataset>selectedListener(
                dataset -> new LoadDicomStoresTask(dataset, googleAPIClient, this),
                nothing -> updateDicomStores(emptyList())
        ));

        googleDicomstoreCombobox.addItemListener(this.<DicomStore>selectedListener(
                store -> new LoadStudiesTask(store, googleAPIClient, this),
                nothing -> updateTable(emptyList())
        ));

        new LoadProjectsTask(googleAPIClient, this).execute();
    }

    public void updateProjects(List<ProjectDescriptor> result) {
        updateModel(result, modelProject);
        updateLocations(emptyList());
    }

    public void updateLocations(List<Location> result) {
        updateModel(result, modelLocation);
        updateDatasets(emptyList());
    }

    public void updateDatasets(List<Dataset> result) {
        updateModel(result, modelDataset);
        updateDicomStores(emptyList());
    }

    public void updateDicomStores(List<DicomStore> result) {
        updateModel(result, modelDicomstore);
        updateTable(emptyList());
    }

    public void updateTable(List<StudyView> studies) {
        table.clearTable();
        studies.forEach(table::addStudy);
    }

    public Optional<DicomStore> getCurrentStore() {
        return (Optional<DicomStore>) modelDicomstore.getSelectedItem();
    }

    private <T>void updateModel(List<T> list, DefaultComboBoxModel<Optional<T>> model) {
        model.removeAllElements();
        if (!list.isEmpty()) {
            model.addElement(Optional.empty());
            list.stream().map(Optional::of).forEach(model::addElement);
            model.setSelectedItem(Optional.empty());
        }
    }

    private <T>ItemListener selectedListener(Function<T, SwingWorker<?, ?>> taskFactory, Consumer<Void> onEmpty) {
        return e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                Optional<T> item = (Optional<T>) e.getItem();

                if (!item.isPresent()) {
                    onEmpty.accept(null);
                }

                item.map(taskFactory).ifPresent(SwingWorker::execute);
            }
        };
    }

    private class ListRenderer<T> implements ListCellRenderer<Optional<T>> {

        private final DefaultListCellRenderer renderer = new DefaultListCellRenderer();
        private final String defaultLabel;
        private final Function<T, String> textExtractor;

        public ListRenderer(Function<T, String> textExtractor, String defaultLabel) {
            this.textExtractor = textExtractor;
            this.defaultLabel = defaultLabel;
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Optional<T>> list,
                                                      Optional<T> value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            renderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                String label = value.map(textExtractor).orElse(defaultLabel);
                renderer.setText(label);
            }
            return renderer;
        }
    }
}
