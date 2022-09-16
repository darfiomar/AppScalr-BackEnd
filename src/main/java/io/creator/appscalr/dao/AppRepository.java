package io.creator.appscalr.dao;

import io.creator.appscalr.entities.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


public interface AppRepository extends JpaRepository<App, Long> {
}
