package io.creator.appscalr.service;

import io.creator.appscalr.dto.DtoFeedback;
import io.creator.appscalr.entities.Feedback;
import io.creator.appscalr.entities.User;

import java.util.Set;

public interface Feedbackservice {
    public Feedback newfeedback(User user, DtoFeedback dtoFeedback);
    public Set<Feedback> getFeedbacks();
}
