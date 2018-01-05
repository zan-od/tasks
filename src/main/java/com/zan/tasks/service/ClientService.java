package com.zan.tasks.service;

import java.util.List;

import com.zan.tasks.model.Client;

public interface ClientService {
	public void addClient(Client client);
	
	public List<Client> listClients();
	
	public void deleteClient(Long id);
	
	public List<Client> findClientsByName(String name);
	
	public Client getClient(Long id);
}
