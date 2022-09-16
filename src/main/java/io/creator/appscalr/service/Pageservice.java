package io.creator.appscalr.service;

import io.creator.appscalr.dto.CreatePageRequest;
import io.creator.appscalr.dto.SavePageRequest;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Pagescreen;

import java.util.Set;

public interface Pageservice {
    public Pagescreen newPage(CreatePageRequest createPageRequest, App app, boolean ishomepage);
    public Pagescreen savePage(Pagescreen pagetosave, SavePageRequest savePageRequest, App app);
    public void deletePage(Pagescreen page, App app);
}
