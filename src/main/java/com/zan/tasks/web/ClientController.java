package com.zan.tasks.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.zan.tasks.model.Client;
import com.zan.tasks.service.ClientService;

@Controller
public class ClientController {
	
	@Autowired
	private ClientService clientService;

	@GetMapping("/clients")
    public String tasks(Model model) {
        
		Map<String, String> columns = new HashMap<String, String>();
		columns.put("id", "ID");
		columns.put("name", "NAME");
		model.addAttribute("columns", columns);
		model.addAttribute("new_client", new Client());
		
		List<Client> clients = (List<Client>) clientService.listClients();
		model.addAttribute("clients", clients);
        
		return "clients";
    }
	
	@PostMapping("/client/add")
    public String addClient(@ModelAttribute("new_client") Client client) {
		
		clientService.addClient(client);
        
		return "redirect:/clients";
    }
	
	@GetMapping("/client/delete/{clientId}")
    public String deleteClient(@PathVariable("clientId") Long clientId) {
		
		clientService.deleteClient(clientId);
        
		return "redirect:/clients";
    }
}
