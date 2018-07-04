package com.zan.tasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zan.tasks.dao.InvoiceDAO;
import com.zan.tasks.model.Board;
import com.zan.tasks.model.Invoice;
import com.zan.tasks.model.InvoiceItem;

@Service
public class InvoiceServiceImpl implements InvoiceService {

	private InvoiceDAO invoiceDAO;
	
	@Autowired
	public void setInvoiceDAO(InvoiceDAO invoiceDAO){
		this.invoiceDAO = invoiceDAO;
	}
	
	@Override
	public void saveInvoice(Invoice invoice) {
		List<InvoiceItem> items = invoice.getItems();
		if(items!=null){
			items.stream().forEach(item -> item.setInvoice(invoice));
			invoice.setItems(items);
		}
		
		invoiceDAO.save(invoice);
	}

	@Override
	public List<Invoice> listBoardInvoices(Board board) {
		return invoiceDAO.findByBoard(board);
	}

	@Override
	public Invoice getInvoice(Long id) {
		return invoiceDAO.getOne(id);
	}

}
