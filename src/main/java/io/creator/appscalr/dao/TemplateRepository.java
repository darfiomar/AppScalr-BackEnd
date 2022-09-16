package io.creator.appscalr.dao;

import io.creator.appscalr.entities.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface TemplateRepository extends JpaRepository<Template, Long> {
}
