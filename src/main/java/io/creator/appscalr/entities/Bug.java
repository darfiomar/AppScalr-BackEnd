package io.creator.appscalr.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "bugs")
public class Bug {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false)
	private long bug_id;

	@Column(name = "Title")
	private String Title;

	@Column(name = "description", length = 512)
	private String description;

	@Column(name = "type")
	private String type;

	@Column(name = "status")
	private String status;

	@Column(name = "screenshot")
	private String screenshot;

	@Column(name = "created_at")
	private Instant created_at;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
}
