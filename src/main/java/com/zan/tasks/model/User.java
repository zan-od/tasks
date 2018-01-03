package com.zan.tasks.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "USERS")
public class User {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "NAME")
	private String name;

	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "PASSWORD")
	private String password;
	
	@ManyToOne(optional = true, cascade = CascadeType.DETACH)
	@JoinColumn(name = "BOARD_ID")
	private Board currentBoard;
	
	@Transient
	private String confirmPassword;
	
	@ManyToMany
	@JoinTable(name = "user_roles", joinColumns= @JoinColumn(name = "user_id"), 
		inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@ManyToMany
	@JoinTable(name = "BOARD_USERS", joinColumns= @JoinColumn(name = "user_id", referencedColumnName="ID"), 
		inverseJoinColumns = @JoinColumn(name = "board_id", referencedColumnName="ID"))
	private Set<Board> boards;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Board getCurrentBoard() {
		return currentBoard;
	}

	public void setCurrentBoard(Board currentBoard) {
		this.currentBoard = currentBoard;
	}

	public Set<Board> getBoards() {
		return boards;
	}

	public void setBoards(Set<Board> boards) {
		this.boards = boards;
	}
	
}
