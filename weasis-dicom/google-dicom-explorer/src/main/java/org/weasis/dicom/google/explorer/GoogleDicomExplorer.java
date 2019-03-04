package org.weasis.dicom.google.explorer;

import bibliothek.gui.dock.common.CLocation;
import bibliothek.gui.dock.common.mode.ExtendedMode;
import org.weasis.core.api.explorer.DataExplorerView;
import org.weasis.core.api.explorer.model.DataExplorerModel;
import org.weasis.core.ui.docking.PluginTool;
import org.weasis.core.ui.editor.SeriesViewerEvent;
import org.weasis.core.ui.editor.SeriesViewerListener;
import org.weasis.dicom.google.api.GoogleAPIClient;
import org.weasis.dicom.google.api.GoogleAPIClientFactory;
import org.weasis.dicom.google.api.ui.GoogleExplorer;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.List;

public class GoogleDicomExplorer extends PluginTool implements DataExplorerView, SeriesViewerListener {

    public static final String NAME = Messages.getString("GoogleDicomExplorer.title"); //$NON-NLS-1$
    public static final String BUTTON_NAME = Messages.getString("GoogleDicomExplorer.btn_title"); //$NON-NLS-1$
    public static final String DESCRIPTION = Messages.getString("GoogleDicomExplorer.desc"); //$NON-NLS-1$
    
    private final GoogleExplorer explorer;

    private final GoogleAPIClient googleAPIClient = GoogleAPIClientFactory.getInstance().createGoogleClient();

    public GoogleDicomExplorer() {
        super(NAME, BUTTON_NAME, POSITION.WEST, null,//ExtendedMode.NORMALIZED,
                PluginTool.Type.EXPLORER, 120);
        setLayout(new BorderLayout());
        
        explorer = new GoogleExplorer(googleAPIClient);
        add(explorer);
        setDockableWidth(500);
        dockable.setMaximizable(true);
        dockable.setMinimizable(true);
    }


    @Override
    public void dispose() {
        super.closeDockable();
    }

    @Override
    public DataExplorerModel getDataExplorerModel() {
        return null;
    }

    @Override
    public List<Action> getOpenImportDialogAction() {
        return null;
    }

    @Override
    public List<Action> getOpenExportDialogAction() {
        return null;
    }

    @Override
    public void importFiles(File[] files, boolean recursive) {
    }

    @Override
    public boolean canImportFiles() {
        return false;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    @Override
    public String getUIName() {
        return NAME;
    }

    @Override
    public String toString() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    protected void changeToolWindowAnchor(CLocation clocation) {
    }

    @Override
    public void changingViewContentEvent(SeriesViewerEvent event) {
    }
}
