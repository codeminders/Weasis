package org.weasis.dicom.google.explorer;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.api.explorer.DataExplorerView;
import org.weasis.core.api.explorer.DataExplorerViewFactory;
import org.weasis.core.api.explorer.ObservableEvent;
import org.weasis.core.ui.editor.ViewerPluginBuilder;

import java.util.Hashtable;

@org.osgi.service.component.annotations.Component(service = DataExplorerViewFactory.class, immediate = false)
public class GoogleDicomExplorerFactory implements DataExplorerViewFactory {

    private GoogleDicomExplorer explorer = null;
    private final Logger LOGGER = LoggerFactory.getLogger(GoogleDicomExplorerFactory.class);

    @Override
    public DataExplorerView createDataExplorerView(Hashtable<String, Object> properties) {
        if (explorer == null) {
            explorer = new GoogleDicomExplorer();
            ViewerPluginBuilder.DefaultDataModel.firePropertyChange(
                    new ObservableEvent(ObservableEvent.BasicAction.NULL_SELECTION, explorer, null, null));
        }
        return explorer;
    }

    // ================================================================================
    // OSGI service implementation
    // ================================================================================

    @Activate
    protected void activate(ComponentContext context) {

        LOGGER.info("Activate the Google Healthcare DataExplorerView");
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        LOGGER.info("Deactivate the Google Healthcare DataExplorerView");
    }
}