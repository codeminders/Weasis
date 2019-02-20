package org.weasis.dicom.google.explorer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.weasis.core.api.gui.util.JMVUtils;
import org.weasis.core.api.media.data.ImageElement;
import org.weasis.core.ui.util.WtoolBar;

public class GoogleHealthcareToolBar<E extends ImageElement> extends WtoolBar {

    protected GoogleHealthcareToolBar() {
        super("Google Healthcare Toolbar", 500);

        final JButton helpButton = new JButton();
        helpButton.setToolTipText("Cloud Healthcare API documentation");
        helpButton.setIcon(new ImageIcon(GoogleHealthcareToolBar.class.getResource("/icon/32x32/help-browser.png"))); //$NON-NLS-1$
        helpButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() instanceof Component) {
                    URL url;
                    try {
                        url = new URL("https://cloud.google.com/healthcare/docs/"); //$NON-NLS-1$
                        JMVUtils.openInDefaultBrowser((Component) e.getSource(), url);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(helpButton);

    }

}
