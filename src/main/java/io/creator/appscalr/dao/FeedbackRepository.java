package io.creator.appscalr.dao;

import io.creator.appscalr.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Set;

@RepositoryRestResource
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
