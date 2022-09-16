package io.creator.appscalr.service;

import io.creator.appscalr.entities.Template;

import java.util.Set;

public interface Templateservice {
    public Template newtemplate(String title, String thumbnailurl, String templatedom);
    public Template updatetemplate(long templateid, String title, String thumbnailurl, String templatedom);
    public void deletetemplate(long templateid);
    public Set<Template> getTemplates();
}
