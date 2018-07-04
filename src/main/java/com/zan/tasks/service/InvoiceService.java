package com.zan.tasks.service;

import java.util.List;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Invoice;

public interface InvoiceService {
	
	void saveInvoice(Invoice invoice);
	
	List<Invoice> listBoardInvoices(Board board);
	
	Invoice getInvoice(Long id);
}
