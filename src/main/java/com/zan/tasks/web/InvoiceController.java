package com.zan.tasks.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Client;
import com.zan.tasks.model.Invoice;
import com.zan.tasks.model.InvoiceItem;
import com.zan.tasks.model.User;
import com.zan.tasks.service.BoardService;
import com.zan.tasks.service.ClientService;
import com.zan.tasks.service.InvoiceService;
import com.zan.tasks.service.TaskService;
import com.zan.tasks.service.UserService;

@Controller
public class InvoiceController {
	
	private BoardService boardService;
	private UserService userService;
	private ClientService clientService;
	private TaskService taskService;
	private InvoiceService invoiceService;
	
	@Autowired
	public void setBoardService(BoardService boardService){
		this.boardService = boardService;
	}
	
	@Autowired
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	
	@Autowired
	public void setClientService(ClientService clientService){
		this.clientService = clientService;
	}
	
	@Autowired
	public void setTaskService(TaskService taskService){
		this.taskService = taskService;
	}
	
	@Autowired
	public void setInvoiceService(InvoiceService invoiceService){
		this.invoiceService = invoiceService;
	}
	
	private Invoice getInvoiceSample(){
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
		
		return invoice;
	}
	
	private void fillModel(Model model){
		User currentUser = userService.getCurrentUser();
		Board currentBoard = currentUser.getCurrentBoard();
		
		model.addAttribute("boards", boardService.getBoards(currentUser));
		model.addAttribute("currentBoard", currentBoard);
		model.addAttribute("clients", clientService.listBoardClients(currentBoard));
		model.addAttribute("tasks", taskService.listBoardTasks(currentBoard));		
	}
	
	@GetMapping("/invoices")
	public String invoices(Model model){
		
		fillModel(model);
		model.addAttribute("invoices", invoiceService.listBoardInvoices(userService.getCurrentBoard()));
		
		return "invoices";
	}

	@GetMapping("/invoice/add")
	public String addInvoice(Model model){
		
		Invoice invoice = new Invoice();
		invoice.setDate(new Date());
		
		fillModel(model);
		model.addAttribute("invoice", invoice);
		
		return "invoice";
	}
	
	@GetMapping("/invoice/edit/{invoiceId}")
	public String editInvoice(Model model, @ModelAttribute("invoiceId") Long invoiceId){
		
		Invoice invoice = invoiceService.getInvoice(invoiceId);
		
		fillModel(model);
		model.addAttribute("invoice", invoice);
		
		return "invoice";
	}
	
	@PostMapping({"/invoice/add", "/invoice/edit/{invoiceId}"})
	public String saveInvoice(final Invoice invoice, final BindingResult bindingResult){
		
		invoice.setBoard(userService.getCurrentBoard());
		invoiceService.saveInvoice(invoice);
		
		return "redirect:/invoices";
	}
	
	@RequestMapping(value={"/invoice/add", "/invoice/edit/{invoiceId}"}, params={"addRow"})
	public String addRow(Model model, final Invoice invoice, final BindingResult bindingResult) {
		
		if (invoice.getItems()==null){
			invoice.setItems(new ArrayList<>());
		}
		
		invoice.getItems().add(new InvoiceItem());
	    
		fillModel(model);
		
		return "invoice";
	}

	@RequestMapping(value={"/invoice/add", "/invoice/edit/{invoiceId}"}, params={"removeRow"})
	public String removeRow(Model model, final Invoice invoice, final BindingResult bindingResult, final HttpServletRequest req) {
		
		final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
		invoice.getItems().remove(rowId.intValue());
	    
		fillModel(model);
		
		return "invoice";
	}
}
