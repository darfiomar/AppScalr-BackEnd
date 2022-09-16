package io.creator.appscalr.dao;

import io.creator.appscalr.entities.Bug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BugRepository extends JpaRepository<Bug, Long> {
}
