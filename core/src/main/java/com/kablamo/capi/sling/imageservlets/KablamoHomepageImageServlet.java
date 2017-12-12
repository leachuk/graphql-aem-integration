package com.kablamo.capi.sling.imageservlets;

import com.day.cq.commons.ImageHelper;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.Rendition;
import com.day.cq.dam.api.RenditionPicker;
import com.day.cq.wcm.commons.AbstractImageServlet;
import com.day.cq.wcm.foundation.WCMRenditionPicker;
import com.day.image.Layer;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.resource.Resource;

import javax.jcr.RepositoryException;
import java.awt.Dimension;
import java.io.IOException;

@SlingServlet(resourceTypes = { "capi-aem-poc-project/components/content/kablamo/homepage" }, selectors = "img", extensions = { "jpeg", "jpg", "png" })
public class KablamoHomepageImageServlet extends AbstractImageServlet {

    private static final RenditionPicker RENDITION_PICKER = new WCMRenditionPicker();
    private static final int MAX_HEIGHT = 768;
    private static final int MAX_WIDTH = 768;
    private static final double QUALITY = 0.75d;

    @Override
    protected Layer createLayer(ImageContext imageContext) throws RepositoryException, IOException {
        String imageReference = imageContext.properties.get("fileReference", String.class);
        if (imageReference == null) {
            return null;
        }
        Resource image = imageContext.resolver.getResource(imageReference);
        if (image == null) {
            return null;
        }
        Asset asset = image.adaptTo(Asset.class);
        if (asset == null) {
            return null;
        }
        Rendition rendition = RENDITION_PICKER.getRendition(asset);
        Layer layer = new Layer(rendition.getStream());
        if (layer.getHeight() > MAX_HEIGHT || layer.getWidth() > MAX_WIDTH) {
            Layer resized = ImageHelper.resize(layer, new Dimension(), new Dimension(0, 0), new Dimension(MAX_WIDTH, MAX_HEIGHT));
            if (resized != null) {
                layer = resized;
            }
        }
        return layer;

    }

    @Override
    protected double getImageQuality() {
        return QUALITY;
    }
}
