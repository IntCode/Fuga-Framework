package com.bunjlabs.fugaframework.foundation.controllers;

import com.bunjlabs.fugaframework.foundation.Controller;
import com.bunjlabs.fugaframework.foundation.Response;
import com.bunjlabs.fugaframework.templates.TemplateNotFoundException;
import com.bunjlabs.fugaframework.templates.TemplateRenderException;
import com.bunjlabs.fugaframework.utils.MimeTypeUtils;
import java.io.InputStream;

public class DefaultController extends Controller {

    public Response generateNotFound() {
        String path = ctx.getApp().getConfiguration().get("fuga.404.redirect", null);
        if (path != null && !path.isEmpty()) {
            return temporaryRedirect(urls.that(path));
        }
        return notFound("404 Not Found");
    }

    public Response generateAsset(String path) {
        InputStream asset = ctx.getApp().getResourceManager().load("assets/" + path);

        if (asset == null) {
            return notFound();
        }

        String mime = MimeTypeUtils.getMimeTypeByExt(path.substring(path.lastIndexOf('.') + 1));

        return ok(asset).setContentType(mime != null ? mime : "application/octet-stream");
    }

    public Response generateAssetView(String name) throws TemplateNotFoundException, TemplateRenderException {
        return ok(view(name));
    }

    public Response generateOk(String data) {

        return ok(data);
    }
}
