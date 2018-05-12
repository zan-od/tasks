package com.zan.tasks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.InvoiceItem;

public interface InvoiceItemDAO extends JpaRepository<InvoiceItem, Long> {

}
