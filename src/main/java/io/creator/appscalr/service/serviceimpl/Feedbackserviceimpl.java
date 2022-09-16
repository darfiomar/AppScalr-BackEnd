package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.FeedbackRepository;
import io.creator.appscalr.dao.UserRepository;
import io.creator.appscalr.dto.DtoFeedback;
import io.creator.appscalr.entities.Changelog;
import io.creator.appscalr.entities.Feedback;
import io.creator.appscalr.entities.Template;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.Feedbackservice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@AllArgsConstructor
@Service
@Transactional
public class Feedbackserviceimpl implements Feedbackservice {
     private final UserRepository userdao;
     private final FeedbackRepository fbdao;
     private final Changelogserviceimpl userlog;

    @Override
    public Feedback newfeedback(User user, DtoFeedback dtoFeedback) {
        Feedback fb = new Feedback();
        if (!user.isHasfeedback()) {
            fb.setUser(user);
            fb.setRate(dtoFeedback.getRate());
            fb.setFeedback(dtoFeedback.getFeedback());
            fb.setCreated_at(Instant.now());
            fbdao.save(fb);
            user.setHasfeedback(true);
            userdao.save(user);
            userlog.newUserLog(user,"You have just rated our service and left a feedback. Thank you!");
        }
        return fb;
    }

    @Override
    public Set<Feedback> getFeedbacks() {
        Set<Feedback> feedbacks = new HashSet(fbdao.findAll());
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.UK )
                        .withZone( ZoneId.systemDefault() );
        for(Feedback feedback : feedbacks) {
            feedback.setCreatedat(formatter.format(feedback.getCreated_at()));
        }
        return feedbacks;
    }
}
