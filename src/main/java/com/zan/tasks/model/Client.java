package com.zan.tasks.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CLIENTS")
public class Client {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;
	
	@Column(name = "NAME")
	private String name = "";

	@OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private Collection<Task> tasks;
	
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
}
