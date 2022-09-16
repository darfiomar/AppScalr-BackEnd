package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.AppRepository;
import io.creator.appscalr.dao.PageRepository;
import io.creator.appscalr.dao.TemplateRepository;
import io.creator.appscalr.dto.CreatePageRequest;
import io.creator.appscalr.dto.SavePageRequest;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Pagescreen;
import io.creator.appscalr.service.Pageservice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class Pageserviceimpl implements Pageservice {

    private final PageRepository pagedao;
    private final TemplateRepository templatedao;
    private final AppRepository appdao;
    private final Changelogserviceimpl userlog;

    @Override
    public Pagescreen newPage(CreatePageRequest createPageRequest, App app, boolean ishomepage) {
        Pagescreen page = new Pagescreen();
        page.setApp(app);
        page.setTitle(createPageRequest.getPagetitle());
        page.setRoute_url(createPageRequest.getRouteurl());
        page.setCreated_at(Instant.now());
        page.setIshomepage(ishomepage);
        page.setLast_modified(Instant.now());
        if (templatedao.findById(createPageRequest.getTemplateid()).isPresent()) {
            page.setPage_dom(templatedao.findById(createPageRequest.getTemplateid()).get().getDom());
        }else page.setPage_dom(templatedao.findById(1L).get().getDom());
        pagedao.save(page);
        userlog.newUserLog(app.getUser(),"App '"+app.getApp_name()+"': New Page created '"+page.getTitle()+"'.");
        //appservice.appModified(app);
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        page.setLastmodified(formatter.format(page.getLast_modified()));
        return page;
    }

    @Override
    public Pagescreen savePage(Pagescreen pagetosave, SavePageRequest savePageRequest, App app) {
        Pagescreen page = null;
        if (app.getPages().contains(pagetosave)){
            page = pagedao.findById(pagetosave.getPage_id()).get();
            page.setApp(app);
            page.setTitle(savePageRequest.getPagetitle());
            page.setRoute_url(savePageRequest.getRouteurl());
            page.setLast_modified(Instant.now());
            page.setPage_dom(savePageRequest.getDom());
            pagedao.save(page);
            userlog.newUserLog(app.getUser(),"App '"+app.getApp_name()+"': Page saved '"+page.getTitle()+"'.");
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                            .withLocale( Locale.UK )
                            .withZone( ZoneId.systemDefault() );
            page.setLastmodified(formatter.format(page.getLast_modified()));
        }
        return page;
    }

    @Override
    public void deletePage(Pagescreen page, App app) {
        if (app.getPages().contains(page) && !page.isIshomepage()){
            String pagetittle = page.getTitle();
            pagedao.delete(page);
            userlog.newUserLog(app.getUser(),"App '"+app.getApp_name()+"': Page deleted '"+pagetittle+"'.");
        }
    }

}
