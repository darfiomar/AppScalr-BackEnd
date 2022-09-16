package io.creator.appscalr.service;

import io.creator.appscalr.dto.DtoBug;
import io.creator.appscalr.entities.Bug;
import io.creator.appscalr.entities.User;

import java.util.Set;

public interface Bugservice {
    public Bug newBug(DtoBug dtoBug, User user);
    public Set<Bug> getAllBugs();
    public Set<Bug> getUserBugs(User user);
}
