package io.creator.appscalr.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "apps")
public class App implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false)
	private long app_id;

	@Column(name = "appname")
	private String app_name;

	@Column(name = "appdesc", length = 512)
	@Lob
	private String app_desc;

	@Column(name = "created_at")
	private Instant created_at;

	@Column(name = "modifications")
	private long modifications;

	@Column(name = "last_modified")
	private Instant last_modified;

	@Transient
	private String lastmodified;

	@Column(name = "app_icon_url")
	private String app_icon_url;

	@JsonIgnore
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "app", orphanRemoval = true)
	private Set<Pagescreen> pages;
}
