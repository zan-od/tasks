package com.zan.tasks.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.Invoice;

public interface InvoiceDAO  extends JpaRepository<Invoice, Long>{

}
