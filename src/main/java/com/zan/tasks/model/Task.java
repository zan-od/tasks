package com.zan.tasks.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TASKS")
public class Task {
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;
	
	@Column(name = "NAME")
	private String name = "";
	
	@ManyToOne(optional = true, cascade = CascadeType.DETACH)
	@JoinColumn(name = "CLIENT_ID")
	private Client client;

	@ManyToOne
	@JoinColumn(name = "BOARD_ID")
	private Board board;
	
	@OneToMany(mappedBy = "task", fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = TimeInterval.class)
	private List<TimeInterval> timeIntervals;
	
	@Transient
	private Integer duration = 0;
	
	@Transient
	private boolean isStarted = false;
	
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

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<TimeInterval> getTimeIntervals() {
		return timeIntervals;
	}

	public void setTimeIntervals(List<TimeInterval> timeIntervals) {
		this.timeIntervals = timeIntervals;
	}

	public boolean isStarted() {
		return isStarted;
	}

	public void setStarted(boolean isStarted) {
		this.isStarted = isStarted;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
}
