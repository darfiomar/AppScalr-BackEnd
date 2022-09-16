package io.creator.appscalr.web;

import io.creator.appscalr.entities.Changelog;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.serviceimpl.Changelogserviceimpl;
import io.creator.appscalr.service.serviceimpl.Userserviceimpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/userlog")
public class UserlogController {

    private final Userserviceimpl userserviceimpl;
    private final Changelogserviceimpl changelogserviceimpl;

    @GetMapping
    public ResponseEntity<Set<Changelog>> getUserlog(){
        User currentUser = userserviceimpl.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(changelogserviceimpl.getUserlogs(currentUser));
    }
}
