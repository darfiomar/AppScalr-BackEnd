package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.AppRepository;
import io.creator.appscalr.dao.PageRepository;
import io.creator.appscalr.dao.UserRepository;
import io.creator.appscalr.dto.CreatePageRequest;
import io.creator.appscalr.dto.DtoAppRequest;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Pagescreen;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.Appservice;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import javax.persistence.PreRemove;
import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class Appserviceimpl implements Appservice {

    private final AppRepository appdao;
    private final UserRepository userdao;
    private final PageRepository pagedao;
    private final Pageserviceimpl pageservice;
    private final Changelogserviceimpl userlog;


    @Override
    public App createApp(DtoAppRequest createappdto, User user) {
        App app = new App();
        app.setUser(user);
        app.setApp_name(createappdto.getAppname());
        app.setApp_desc(createappdto.getAppdesc());
        app.setApp_icon_url(createappdto.getAppicon());
        app.setModifications(0);
        app.setCreated_at(Instant.now());
        appdao.save(app);
        userlog.newUserLog(user,"App created '"+app.getApp_name()+"'.");
        CreatePageRequest createPageRequest = new CreatePageRequest("Home","/page",createappdto.getTemplateid());
        pageservice.newPage(createPageRequest,app,true);
        this.appModified(app);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        app.setLastmodified(formatter.format(app.getLast_modified()));
        return app;
    }

    @Override
    public App updateApp(App apptoupdate, DtoAppRequest dtoAppRequest) {
        App app = new App();
        if(appdao.findById(apptoupdate.getApp_id()).isPresent()) {
            app = appdao.findById(apptoupdate.getApp_id()).get();
            app.setApp_name(dtoAppRequest.getAppname());
            app.setApp_desc(dtoAppRequest.getAppdesc());
            app.setApp_icon_url(dtoAppRequest.getAppicon());
            if(pagedao.findById(dtoAppRequest.getTemplateid()).isPresent() && app.getPages().contains(pagedao.findById(dtoAppRequest.getTemplateid()).get())) {
                Pagescreen homepage = pagedao.findById(dtoAppRequest.getTemplateid()).get();
                Pagescreen currenthomepage = getHomePage(app);
                currenthomepage.setIshomepage(false);
                homepage.setIshomepage(true);
                pagedao.save(homepage);
                pagedao.save(currenthomepage);
            }
            appdao.save(app);
            userlog.newUserLog(app.getUser(),"App updated '"+app.getApp_name()+"'.");
            appModified(app);
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                            .withLocale( Locale.UK )
                            .withZone( ZoneId.systemDefault() );
            app.setLastmodified(formatter.format(app.getLast_modified()));
        }
        return app;
    }

    @Override
    public void deleteApp(App app) {
        String appname = app.getApp_name();
        for(Pagescreen page : app.getPages()){
            pagedao.delete(page);
        }
        appdao.delete(app);
        userlog.newUserLog(app.getUser(),"App deleted '"+appname+"'.");
    }

    @Override
    public Set<App> getUserApps(User user) {
        Set<App> apps = user.getApps();
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        for(App app : apps) {
            app.setLastmodified(formatter.format(app.getLast_modified()));
        }
        return apps;
    }

    @Override
    public Pagescreen getHomePage(App app) {
        Pagescreen homepage = null;
        for (Iterator<Pagescreen> it = app.getPages().iterator(); it.hasNext(); ) {
            Pagescreen p = it.next();
            if (p.isIshomepage()) {
                homepage = p;
                break;
            }
        }
        return homepage;
    }

    public void appModified(App app) {
        app.setLast_modified(Instant.now());
        app.setModifications(app.getModifications()+1);
        appdao.save(app);
    }
}
