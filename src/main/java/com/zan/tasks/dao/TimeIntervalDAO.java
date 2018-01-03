package com.zan.tasks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.TimeInterval;

public interface TimeIntervalDAO extends JpaRepository<TimeInterval, Long> {

}
