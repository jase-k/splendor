package com.jasekraft.splendor.mvc.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique=true)
	@NotEmpty(message="Email is required!")
	@Email(message="Please enter a valid email!")
	private String email;
	
	@NotEmpty(message="Username is required!")
	@Column(unique = true)
	@Size(min=3, max=30, message="Username must be between 3 and 30 characters")
	private String username;
	
	@JsonIgnore
	@NotEmpty(message="Password is required!")
	@Size(min=8, max=128, message="Password must be between 8 and 128 characters")
	private String password;
	
	@JsonIgnore
	@Transient
	@NotEmpty(message="Confirm Password is required!")
	@Size(min=8, max=128, message="Confirm Password must be between 8 and 128 characters")
	private String confirm;
	
	@JsonIgnore
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date createdAt;
	
	@JsonIgnore
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date updatedAt;
	
	//@JsonManagedReference
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY)
    private List<Player> players;
	
	public User() {}

	public User(String username, String email, String password, String confirm) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.confirm = confirm;
	}
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


}
