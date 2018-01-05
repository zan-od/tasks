package com.zan.tasks.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zan.tasks.dao.ClientDAO;
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
	public List<Client> listClients() {
		return clientDAO.findAll();
	}

	@Override
	public void deleteClient(Long id) {
		clientDAO.delete(id);		
	}

	@Override
	public List<Client> findClientsByName(String name) {
		return clientDAO.findByNameContainingIgnoreCase(name);
	}

	@Override
	public Client getClient(Long id) {
		if ((id==null) || (id==0)) {
			return null;
		}
		
		return clientDAO.getOne(id);
	}

}
