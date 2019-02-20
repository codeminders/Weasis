package org.weasis.dicom.google.explorer;

import java.util.Hashtable;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Deactivate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.weasis.core.api.media.data.ImageElement;
import org.weasis.core.api.gui.Insertable;
import org.weasis.core.api.gui.Insertable.Type;
import org.weasis.core.api.gui.InsertableFactory;

@org.osgi.service.component.annotations.Component(service = InsertableFactory.class, immediate = false, property = {
"org.weasis.dicom.viewer2d.View2dContainer=true"  })
public class GoogleHealthcareToolBarFactory implements InsertableFactory {
    private final Logger LOGGER = LoggerFactory.getLogger(GoogleHealthcareToolBarFactory.class);

    @Override
    public Type getType() {
        return Type.TOOLBAR;
    }

    @Override
    public Insertable createInstance(Hashtable<String, Object> properties) {
        return new GoogleHealthcareToolBar<ImageElement>();
    }

    @Override
    public boolean isComponentCreatedByThisFactory(Insertable component) {
        return component instanceof GoogleHealthcareToolBar;
    }

    @Override
    public void dispose(Insertable bar) {
        if (bar != null) {
            // Remove all the registered listeners or other behaviors links with other existing components if exists.
        }
    }

    @Activate
    protected void activate(ComponentContext context) throws Exception {
        LOGGER.info("Activate the Google Healthcare help tool bar");
    }

    @Deactivate
    protected void deactivate(ComponentContext context) {
        LOGGER.info("Deactivate the Google Healthcare help tool bar");
    }

}

