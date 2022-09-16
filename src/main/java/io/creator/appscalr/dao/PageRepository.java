package io.creator.appscalr.dao;

import io.creator.appscalr.entities.App;
import io.creator.appscalr.entities.Pagescreen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface PageRepository extends JpaRepository<Pagescreen, Long> {
    Set<Pagescreen> findByApp(App app);
}
