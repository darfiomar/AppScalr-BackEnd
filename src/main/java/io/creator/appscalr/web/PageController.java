package io.creator.appscalr.web;

import io.creator.appscalr.dao.AppRepository;
import io.creator.appscalr.dao.PageRepository;
import io.creator.appscalr.dto.CreatePageRequest;
import io.creator.appscalr.dto.SavePageRequest;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Pagescreen;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.serviceimpl.Appserviceimpl;
import io.creator.appscalr.service.serviceimpl.Pageserviceimpl;
import io.creator.appscalr.service.serviceimpl.Userserviceimpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/app")
public class PageController {

    private final AppRepository appdao;
    private final Userserviceimpl userserviceimpl;
    private final Pageserviceimpl pageserviceimpl;
    private final PageRepository pagedao;
    private final Appserviceimpl appserviceimpl;

    @GetMapping("/{appid}/page")
    public ResponseEntity<Set<Pagescreen>> getAppPages(@PathVariable("appid") long id){
        User currentUser = userserviceimpl.getCurrentUser();
        App app = appdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (currentUser.getApps().contains(app))
            return ResponseEntity.status(HttpStatus.OK)
                    .body(app.getPages());
        else return null;
    }

    @PostMapping("/{appid}/page/create")
    public ResponseEntity<Pagescreen> createPage(@PathVariable("appid") long id, @RequestBody CreatePageRequest createPageRequest) {
        User currentUser = userserviceimpl.getCurrentUser();
        App app = appdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid app Id:" + id));
        if (currentUser.getApps().contains(app)) {
            appserviceimpl.appModified(app);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(pageserviceimpl.newPage(createPageRequest, app, false));
        }
        else return null;
    }

    @PutMapping("/{appid}/page/{pageid}/save")
    public ResponseEntity<Pagescreen> savePage(@PathVariable("appid") long appid,@PathVariable("pageid") long pageid, @RequestBody SavePageRequest savePageRequest) {
        User currentUser = userserviceimpl.getCurrentUser();
        Pagescreen pagetosave = pagedao.findById(pageid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid page Id:" + pageid));
        App app = appdao.findById(appid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid app Id:" + appid));
        if (currentUser.getApps().contains(app) && app.getPages().contains(pagetosave)) {
            appserviceimpl.appModified(app);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(pageserviceimpl.savePage(pagetosave, savePageRequest, app));
        }
        else return null;
    }

    @DeleteMapping("/{appid}/page/{pageid}/delete")
    public ResponseEntity<String> deletePage(@PathVariable("appid") long appid,@PathVariable("pageid") long pageid) {
        User currentUser = userserviceimpl.getCurrentUser();
        Pagescreen pagetodelete = pagedao.findById(pageid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid page Id:" + pageid));
        App app = appdao.findById(appid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid app Id:" + appid));
        if (currentUser.getApps().contains(app) && app.getPages().contains(pagetodelete) && !pagetodelete.isIshomepage()) {
            appserviceimpl.appModified(app);
            pageserviceimpl.deletePage(pagetodelete,app);
            return new ResponseEntity<>("Page successfully deleted!", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Something went wrong!", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/{appid}/page/{pageid}")
    public ResponseEntity<Pagescreen> getAppPage(@PathVariable("appid") long appid,@PathVariable("pageid") long pageid) {
        User currentUser = userserviceimpl.getCurrentUser();
        Pagescreen requestedpage = pagedao.findById(pageid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid page Id:" + pageid));
        App app = appdao.findById(appid)
                .orElseThrow(() -> new IllegalArgumentException("Invalid app Id:" + appid));
        if (currentUser.getApps().contains(app) && app.getPages().contains(requestedpage)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(requestedpage);
        }
        else return null;
    }
}
