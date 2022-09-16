package io.creator.appscalr.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "changlogs")
public class Changelog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "created_at")
	private Instant created_at;

	@Transient
	private String createdat;

	@Column(name = "log")
	private String log;

	@JsonIgnore
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
}
