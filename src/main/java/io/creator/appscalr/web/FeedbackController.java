package io.creator.appscalr.web;

import io.creator.appscalr.dto.DtoFeedback;
import io.creator.appscalr.entities.Feedback;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.serviceimpl.Feedbackserviceimpl;
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
@RequestMapping("/api")
public class FeedbackController {

    private final Feedbackserviceimpl feedbackserviceimpl;
    private final Userserviceimpl userserviceimpl;

    @GetMapping("/feedbacks")
    public ResponseEntity<Set<Feedback>> getFeedbacks(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(feedbackserviceimpl.getFeedbacks());
    }

    @PostMapping("/feedback/create")
    public ResponseEntity<Feedback> leaveFeedback(@RequestBody DtoFeedback dtoFeedback){
        User currentUser = userserviceimpl.getCurrentUser();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackserviceimpl.newfeedback(currentUser, dtoFeedback));
    }
}
