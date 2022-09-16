package io.creator.appscalr.service.serviceimpl;

import io.creator.appscalr.dao.BugRepository;
import io.creator.appscalr.dto.DtoBug;
import io.creator.appscalr.entities.Bug;
import io.creator.appscalr.entities.User;
import io.creator.appscalr.service.Bugservice;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Set;
@AllArgsConstructor
@Service
@Transactional
public class Bugserviceimpl implements Bugservice {

    private final BugRepository bugdao;
    private final Changelogserviceimpl userlog;

    @Override
    public Bug newBug(DtoBug dtoBug, User user) {
        Bug bug = new Bug();
        bug.setTitle(dtoBug.getTitle());
        bug.setDescription(dtoBug.getDesc());
        bug.setType(dtoBug.getBugtype());
        bug.setStatus("Pending");
        bug.setScreenshot(dtoBug.getScreenshoturl());
        bug.setUser(user);
        bug.setCreated_at(Instant.now());
        bugdao.save(bug);
        userlog.newUserLog(user,"Bug declared, we will take care of it and get back to you ASAP.");
        return bug;
    }

    @Override
    public Set<Bug> getAllBugs() {
        return null;
    }

    @Override
    public Set<Bug> getUserBugs(User user) {
        return null;
    }
}
