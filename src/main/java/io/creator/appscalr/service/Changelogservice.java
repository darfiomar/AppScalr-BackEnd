package io.creator.appscalr.service;

import io.creator.appscalr.entities.Changelog;
import io.creator.appscalr.entities.User;

import java.util.Set;

public interface Changelogservice {
    public Set<Changelog> getUserlogs(User user);
    public void newUserLog(User user, String log);
}
