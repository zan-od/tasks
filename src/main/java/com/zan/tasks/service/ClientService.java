package com.zan.tasks.service;

import java.util.List;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Client;

public interface ClientService {
	public void addClient(Client client);
	
	public List<Client> listBoardClients(Board board);
	
	public void deleteClient(Long id);
	
	public List<Client> findBoardClientsByName(Board board, String name);
	
	public Client getClient(Long id);
}
