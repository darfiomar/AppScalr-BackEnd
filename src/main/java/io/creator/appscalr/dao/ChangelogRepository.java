package io.creator.appscalr.dao;

import io.creator.appscalr.entities.Changelog;
import io.creator.appscalr.entities.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Set;

@RepositoryRestResource
public interface ChangelogRepository extends JpaRepository<Changelog, Long> {
    Set<Changelog> findByUserOrderByIdDesc(User user);
}
