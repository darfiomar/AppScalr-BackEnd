package io.creator.appscalr.web;

import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Template;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.serviceimpl.Templateserviceimpl;
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
@RequestMapping("/api/templates")
public class TemplateController {

    private final Templateserviceimpl templateserviceimpl;

    @GetMapping
    public ResponseEntity<Set<Template>> getUserApps() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(templateserviceimpl.getTemplates());
    }
}
