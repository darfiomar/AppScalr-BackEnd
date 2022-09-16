package io.creator.appscalr.web;

import io.creator.appscalr.dao.AppRepository;
import io.creator.appscalr.dto.DtoAppRequest;
import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.serviceimpl.Appserviceimpl;
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
public class AppController {

    private final Userserviceimpl userserviceimpl;
    private final Appserviceimpl appserviceimpl;
    private final AppRepository appdao;

    @PostMapping("/create")
    public ResponseEntity<App> createApp(@RequestBody DtoAppRequest createAppRequest){
        User currentUser = userserviceimpl.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appserviceimpl.createApp(createAppRequest, currentUser));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<App> editApp(@PathVariable("id") long id,@RequestBody DtoAppRequest dtoAppRequest){
        User currentUser = userserviceimpl.getCurrentUser();
        App apptoedit = appdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        if (currentUser.getApps().contains(apptoedit))
             return ResponseEntity.status(HttpStatus.OK)
                    .body(appserviceimpl.updateApp(apptoedit, dtoAppRequest));
        else return null;
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteApp(@PathVariable("id") long id){
        User currentUser = userserviceimpl.getCurrentUser();
        App apptodelete = appdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        System.out.println(apptodelete.toString());
        if (currentUser.getApps().contains(apptodelete)) {
            appserviceimpl.deleteApp(apptodelete);
            return new ResponseEntity<>("App successfully deleted!", HttpStatus.OK);
        }
        else return new ResponseEntity<>("Something went wrong!", HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<Set<App>> getUserApps() {
        User currentUser = userserviceimpl.getCurrentUser();
        return ResponseEntity.status(HttpStatus.OK)
                .body(appserviceimpl.getUserApps(currentUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<App> getUserApp(@PathVariable("id") long id) {
        User currentUser = userserviceimpl.getCurrentUser();
        App requestedApp = appdao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        return ResponseEntity.status(HttpStatus.OK)
                .body(requestedApp);
    }
}
