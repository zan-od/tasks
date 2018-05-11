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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zan.tasks.model.Client;
import com.zan.tasks.service.ClientService;
import com.zan.tasks.service.UserService;

@Controller
public class ClientController {
	
	private ClientService clientService;
	private UserService userService;

	@Autowired
	public void setClientService(ClientService clientService){
		this.clientService = clientService;
	}
	
	@Autowired
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	
	@GetMapping("/clients")
    public String tasks(Model model) {
        
		Map<String, String> columns = new HashMap<String, String>();
		columns.put("id", "ID");
		columns.put("name", "NAME");
		model.addAttribute("columns", columns);
		model.addAttribute("new_client", new Client());
		
		List<Client> clients = (List<Client>) clientService.listBoardClients(userService.getCurrentBoard());
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
	
	@RequestMapping(value = "/searchClient", method = RequestMethod.POST)
	public @ResponseBody
	List<Client> searchClient(@RequestParam String query) {

		//System.out.println(query);
		List<Client> clients = clientService.findBoardClientsByName(userService.getCurrentBoard(), query);
		
		return clients;
	}
}
