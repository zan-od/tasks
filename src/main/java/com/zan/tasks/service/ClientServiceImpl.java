package com.zan.tasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zan.tasks.dao.ClientDAO;
import com.zan.tasks.model.Board;
import com.zan.tasks.model.Client;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	ClientDAO clientDAO;
	
	@Override
	public void addClient(Client client) {
		clientDAO.save(client);
	}

	@Override
	public List<Client> listBoardClients(Board board) {
		return clientDAO.findByBoard(board);
	}

	@Override
	public void deleteClient(Long id) {
		clientDAO.delete(id);		
	}

	@Override
	public List<Client> findBoardClientsByName(Board board, String name) {
		return clientDAO.findByBoardAndNameContainingIgnoreCase(board, name);
	}

	@Override
	public Client getClient(Long id) {
		if ((id==null) || (id==0)) {
			return null;
		}
		
		return clientDAO.getOne(id);
	}

}
