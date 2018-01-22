package com.zan.tasks.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import com.zan.tasks.model.Client;
import com.zan.tasks.model.Task;

public class TasksView {
	private List<ClientData> clients = new ArrayList<>();
	private HashMap<Client, ClientData> clientsData = new HashMap<Client, ClientData>();
	
	private class ClientData{
		public Client client;
		public List<Task> tasks = new ArrayList<>();
		
		public Integer duration = 0;
		
		public ClientData(Client client){
			this.client = client;
		}
		
		public void addTask(Task task){
			this.tasks.add(task);
			this.duration += task.getDuration();
		}
	}
	
	public void addTask(Task task){
		Client client = task.getClient();
		
		ClientData clientData;
		if (!clientsData.containsKey(client)) {
			clientData = new ClientData(client);
			clientsData.put(client, clientData);
		    clients.add(clientData);
		} else {
			clientData = clientsData.get(client);
		}
		
		clientData.addTask(task);
	}
	
	public void sort(){
		Collections.sort(clients, new Comparator<ClientData>() {
	        public int compare(ClientData clientData1, ClientData clientData2) {
	        		Client client1 = clientData1.client;
	        		Client client2 = clientData2.client;
	        		if ((client1==null)||(client2==null)){
	        			if (client1==null){
	        				return -1;
	        			} else if (client2==null){
	        				return 1;
	        			} else {
	        				return 0;
	        			}
	        		}
	        	
	                return client1.getName().compareTo(client2.getName());
	        }});

		for (ClientData client : clients) {
			if (!clientsData.containsKey(client))
				continue;
			
			ClientData clientData = clientsData.get(client);
			Collections.sort(clientData.tasks, new Comparator<Task>() {
		        public int compare(Task task1, Task task2) {
		                return task1.getId().compareTo(task2.getId());
		        }});
		}
	}
	
	public List<ClientData> getClients(){
		return clients;
	}
	
	public List<Task> getClientTasks(Client client){
		return clientsData.get(client).tasks;
	}	
}
