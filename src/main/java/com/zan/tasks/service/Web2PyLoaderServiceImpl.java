package com.zan.tasks.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zan.tasks.model.Board;
import com.zan.tasks.model.Client;
import com.zan.tasks.model.Task;
import com.zan.tasks.model.TaskStatus;
import com.zan.tasks.model.TimeInterval;
import com.zan.tasks.model.User;

@Service
public class Web2PyLoaderServiceImpl implements Web2PyLoaderService {

	private class TaskData{
		private String name;
		private int duration;
		private boolean isStarted;
		private boolean isDone;
		private boolean isPaid;
		private int clientId;
		private int id;
	}
	
	private class ClientData{
		private String name;
		private int id;
	}
	
	private class TimesliceData{
		private int id;
		private int taskId;
		private Date starttime;
		private Date endtime;
		private int duration;
	}
	
	private String baseUrl;
	private String username;
	private String password;
	
	private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private UserService userService;
	private TaskService taskService;
	private ClientService clientService;
	
	@Autowired
	private void setUserService(UserService userService){
		this.userService = userService;
	}
	
	@Autowired
	private void setTaskService(TaskService taskService){
		this.taskService = taskService;
	}
	
	@Autowired
	private void setClientService(ClientService clientService){
		this.clientService = clientService;
	}
	
	
	@Override
	@Transactional
	public void load(String baseUrl, String username, String password, Board board) throws IOException, ParseException {
		this.baseUrl = baseUrl;
		this.username = username;
		this.password = password;
		
		// data loading
		List<TaskData> tasksList = loadTasksData();
		Map<Integer, ClientData> clientsMap = loadClientsData();
		Map<Integer, List<TimesliceData>> timeslicesByTask = loadTimeslicesData();
		
		User currentUser = userService.getCurrentUser();
		Map<Integer, Client> clients = new HashMap<>();

		TaskStatus status;
		
		for (TaskData taskData: tasksList) {
			Client client = clients.get(taskData.clientId);
			if (client==null) {
				client = new Client();
				client.setName(clientsMap.get(taskData.clientId).name);
				
				clientService.addClient(client);
				
				clients.put(taskData.clientId, client);
			}
			
			List<TimeInterval> timeIntervals = new ArrayList<>();
			List<TimesliceData> taskTimeslices = timeslicesByTask.get(taskData.id);
			
			if (taskData.isDone){
				status = TaskStatus.COMPLETED;
			} else {
				if (taskTimeslices==null || taskTimeslices.isEmpty()) {
					status = TaskStatus.NEW;
				} else {
					status = TaskStatus.IN_PROGRESS;
				}
			}
			
			Task task = new Task();
			task.setClient(client);
			task.setBoard(board);
			task.setName(taskData.name);
			task.setStatus(status);
			
			if (taskTimeslices!=null) {
				for (TimesliceData timeslice : taskTimeslices) {
					TimeInterval timeInterval = new TimeInterval();
					timeInterval.setTask(task);
					timeInterval.setPerformer(currentUser);
					timeInterval.setStartTime(timeslice.starttime);
					timeInterval.setStopTime(timeslice.endtime);
					
					timeIntervals.add(timeInterval);
				}
			}
			task.setTimeIntervals(timeIntervals);
			
			taskService.saveTask(task);
		}
	}
	
	
	private InputStream getData(String urlString) throws IOException{
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		String userpass = username + ":" + password;
		String basicAuth = "Basic " + new String(Base64.encode(userpass.getBytes()));
		connection.setRequestProperty ("Authorization", basicAuth);
		
		connection.connect();
		
		return connection.getInputStream();
	}
	
	private List<TaskData> loadTasksData() throws IOException{
		List<TaskData> list = new ArrayList<>();
		
		InputStream data = getData(baseUrl+"tasks.json?limit=10000");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(data).get("content");
		
		if (root.isArray()) {
		    for (final JsonNode itemNode : root) {
		        TaskData taskData = new TaskData();
		        taskData.name      = itemNode.get("name").asText();
		        taskData.isStarted = itemNode.get("started").asBoolean();
		        taskData.isPaid    = itemNode.get("paid").asBoolean();
		        taskData.isDone    = itemNode.get("done").asBoolean();
		        taskData.duration  = itemNode.get("duration").asInt();
		        taskData.clientId  = itemNode.get("client_id").asInt();
		        taskData.id        = itemNode.get("id").asInt();
		    	
		        list.add(taskData);
		    }
		}
		
		return list;
	}
	
	private Map<Integer,ClientData>  loadClientsData() throws IOException{
		Map<Integer,ClientData>  map = new HashMap<>();
		
		InputStream data = getData(baseUrl+"clients.json?limit=10000");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(data).get("content");
		
		if (root.isArray()) {
		    for (final JsonNode itemNode : root) {
		    	ClientData clientData = new ClientData();
		        clientData.name = itemNode.get("name").asText();
		        clientData.id   = itemNode.get("id").asInt();
		    	
		    	map.put(clientData.id, clientData);
		    }
		}
		
		return map;
	}
	
	private Map<Integer, List<TimesliceData>>  loadTimeslicesData() throws IOException, ParseException{
		Map<Integer, List<TimesliceData>>  map = new HashMap<>();
		
		InputStream data = getData(baseUrl+"timeslices.json?limit=10000");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(data).get("content");
		
		if (root.isArray()) {
		    for (final JsonNode itemNode : root) {
		    	TimesliceData timesliceData = new TimesliceData();
		        timesliceData.id        = itemNode.get("id").asInt();
		        timesliceData.taskId    = itemNode.get("task_id").asInt();
		        timesliceData.duration  = itemNode.get("duration").asInt();
		        timesliceData.starttime = sdf.parse(itemNode.get("starttime").asText());
		        timesliceData.endtime   = sdf.parse(itemNode.get("endtime").asText());
		    	
		        List<TimesliceData> list = map.get(timesliceData.taskId);
		        if (list==null){
		        	list = new ArrayList<>();
		        	map.put(timesliceData.taskId, list);
		        }
		        
		        
		    	list.add(timesliceData);
		    }
		}
		
		return map;
	}

}
