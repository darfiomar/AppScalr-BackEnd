package io.creator.appscalr.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "pages")
public class Pagescreen implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	@Column(name = "id", updatable = false, nullable = false)
	private long page_id;

	@Column(name = "title")
	private String title;

	@Column(name = "route_url")
	private String route_url;

	@Column(name = "page_dom", columnDefinition="TEXT")
	//@Lob
	private String page_dom;

	@Column(name = "ishomepage")
	private boolean ishomepage;

	@Column(name = "created_at")
	private Instant created_at;

	@Column(name = "last_modified")
	private Instant last_modified;

	@Transient
	private String lastmodified;

	@JsonIgnore
	@ManyToOne
	@ToString.Exclude
	@JoinColumn(name = "app_id")
	private App app;
}
