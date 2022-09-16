package io.creator.appscalr.web;

import io.creator.appscalr.dto.DtoBug;
import io.creator.appscalr.dto.DtoFeedback;
import io.creator.appscalr.entities.Bug;
import io.creator.appscalr.entities.Feedback;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.serviceimpl.Bugserviceimpl;
import io.creator.appscalr.service.serviceimpl.Userserviceimpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/bug")
public class BugController {

    private final Bugserviceimpl bugserviceimpl;
    private final Userserviceimpl userserviceimpl;

    @PostMapping("/create")
    public ResponseEntity<Bug> declareBug(@RequestBody DtoBug dtoBug){
        User currentUser = userserviceimpl.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bugserviceimpl.newBug(dtoBug,currentUser));
    }
}
