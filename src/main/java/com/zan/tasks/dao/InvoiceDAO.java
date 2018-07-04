package com.zan.tasks.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Invoice;

public interface InvoiceDAO  extends JpaRepository<Invoice, Long>{
	
	List<Invoice> findByBoard(Board board);
	
}
