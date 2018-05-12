package com.zan.tasks.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Client;
import com.zan.tasks.model.Invoice;
import com.zan.tasks.model.InvoiceItem;
import com.zan.tasks.model.User;
import com.zan.tasks.service.BoardService;
import com.zan.tasks.service.UserService;

@Controller
public class InvoiceController {
	
	private BoardService boardService;
	private UserService userService;
	
	@Autowired
	public void setBoardService(BoardService boardService){
		this.boardService = boardService;
	}
	
	@Autowired
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/invoices")
	public String invoices(Model model){
		
		User currentUser = userService.getCurrentUser();
		Board currentBoard = currentUser.getCurrentBoard();
		
		List<Invoice> invoices = new ArrayList<>();
		
		Client client = new Client();
		client.setName("test");
		
		Invoice invoice = new Invoice();
		invoice.setId(1);
		invoice.setNumber("1-a");
		invoice.setDate(new Date());
		invoice.setClient(client);
		
		List<InvoiceItem> items = new ArrayList<>();
		
		InvoiceItem item = new InvoiceItem();
		item.setAmount(10.75);
		items.add(item);
		
		item = new InvoiceItem();
		item.setAmount(1.33);
		items.add(item);
		
		invoice.setItems(items);
		
		invoices.add(invoice);
		
		model.addAttribute("boards", boardService.getBoards(currentUser));
		model.addAttribute("currentBoard", currentBoard);
		model.addAttribute("invoices", invoices);
		
		return "invoices";
	}

}
