package org.weasis.dicom.google.explorer;

import bibliothek.gui.dock.common.CLocation;
import bibliothek.gui.dock.common.mode.ExtendedMode;
import org.weasis.core.api.explorer.DataExplorerView;
import org.weasis.core.api.explorer.model.DataExplorerModel;
import org.weasis.core.ui.docking.PluginTool;
import org.weasis.core.ui.editor.SeriesViewerEvent;
import org.weasis.core.ui.editor.SeriesViewerListener;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.io.File;
import java.util.List;

public class GoogleDicomExplorer extends PluginTool implements DataExplorerView, SeriesViewerListener {

    public GoogleDicomExplorer(String id, String toolName, Type type, int position) {
        super(id, toolName, type, position);
    }

    public GoogleDicomExplorer() {
        super("GOOGLEDICOM",
                "Google Healthcare Explorer",
                POSITION.WEST,
                null,//ExtendedMode.NORMALIZED,
                PluginTool.Type.EXPLORER,
                120);
        setLayout(new BorderLayout());
        setDockableWidth(500);
        dockable.setMaximizable(true);
        dockable.setMinimizable(true);

        changeToolWindowAnchor(getDockable().getBaseLocation());
    }


    @Override
    public void dispose() {

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
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    protected void changeToolWindowAnchor(CLocation clocation) {
//        removeAll();
//        add(getMainPanel(), verticalLayout ? BorderLayout.NORTH : BorderLayout.WEST);
//        patientContainer.refreshLayout();
//        add(thumnailView, BorderLayout.CENTER);
//        add(loadingPanel, verticalLayout ? BorderLayout.SOUTH : BorderLayout.EAST);

    }

    @Override
    public void changingViewContentEvent(SeriesViewerEvent event) {

    }
}
