package io.creator.appscalr.service;

import io.creator.appscalr.dto.DtoAppRequest;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Pagescreen;
import io.creator.appscalr.entities.User;

import java.util.Set;

public interface Appservice {
    public App createApp(DtoAppRequest createappdto, User user);
    public App updateApp(App apptoupdate,DtoAppRequest dtoAppRequest);
    public void deleteApp(App apptodelete);
    public Set<App> getUserApps(User user);
    public Pagescreen getHomePage(App app);
}
