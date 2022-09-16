package io.creator.appscalr.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false)
	private long userid;

	@Column(name = "fullname")
	private String fullname;

	@Column(name = "email", unique = true)
	private String email;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password")
	private String password;

	@Column(name = "specialite")
	private String specialite;

	@Column(name = "created_at")
	private Instant created_at;

	@Column(name = "hasfeedback")
	private boolean hasfeedback;

	@Column(name = "enabled")
	private boolean enabled;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Bug> bugs;

	@JsonIgnore
	@OneToOne(mappedBy = "user")
	private Feedback feedback;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<App> apps;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<Changelog> logs;
}
