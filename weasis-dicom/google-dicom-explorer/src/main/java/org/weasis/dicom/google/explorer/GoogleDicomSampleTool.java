package org.weasis.dicom.google.explorer;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.weasis.core.ui.docking.PluginTool;


public class GoogleDicomSampleTool extends PluginTool {

    public static final String BUTTON_NAME = "Google Dicom Tool";

    private final JScrollPane rootPane = new JScrollPane();

    public GoogleDicomSampleTool(Type type) {
        super(BUTTON_NAME, "Google Dicom Tool", type, 120);
        dockable.setTitleIcon(new ImageIcon(GoogleDicomSampleTool.class.getResource("/icon/22x22/text-html.png"))); //$NON-NLS-1$
        setDockableWidth(290);
    }

    @Override
    protected void changeToolWindowAnchor(bibliothek.gui.dock.common.CLocation clocation) {

    }

    @Override
    public Component getToolComponent() {
        JViewport viewPort = rootPane.getViewport();
        if (viewPort == null) {
            viewPort = new JViewport();
            rootPane.setViewport(viewPort);
        }
        if (viewPort.getView() != this) {
            viewPort.setView(this);
        }
        return rootPane;
    }


}
