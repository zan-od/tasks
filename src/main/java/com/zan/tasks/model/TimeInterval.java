package com.zan.tasks.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TIME_INTERVALS")
public class TimeInterval {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false, cascade = CascadeType.DETACH)
	//@JoinColumn(name = "TASK_ID")
	private Task task;
	
	@ManyToOne(optional = false, cascade = CascadeType.DETACH)
	@JoinColumn(name = "PERFORMER_ID")
	private User performer;
	
	@Column(name = "START_TIME")
	private Date startTime;
	
	@Column(name = "STOP_TIME")
	private Date stopTime;
	
	@Column(name = "DURATION")
	private Integer duration = 0;

	public static Integer getIntervalDuration(Date intervalStartTime, Date intervalStopTime){
		if ((intervalStartTime != null) && (intervalStopTime != null)){
			return (int) ((intervalStopTime.getTime()-intervalStartTime.getTime())/1000);
		} else {
			return 0;
		}
	}
	
	public void updateDuration(){
		duration = getIntervalDuration(startTime, stopTime);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getPerformer() {
		return performer;
	}

	public void setPerformer(User performer) {
		this.performer = performer;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
		updateDuration();
	}

	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
		updateDuration();
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	public boolean isStarted(){
		return ((startTime!=null) && (stopTime==null));
	}
}
