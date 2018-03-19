package com.zan.tasks.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Task;
import com.zan.tasks.model.TaskStatus;
import com.zan.tasks.model.User;
import com.zan.tasks.service.BoardService;
import com.zan.tasks.service.ClientService;
import com.zan.tasks.service.DurationFormatterService;
import com.zan.tasks.service.TaskService;
import com.zan.tasks.service.UserService;

@Controller
public class TaskController {

	@Autowired
	private TaskService taskService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	DurationFormatterService durationFormatter;
	
	private Board getCurrentBoard(){
		return userService.getCurrentUser().getCurrentBoard();
 	}
	
	@GetMapping("/tasks")
    public String tasks(Model model) {
		return tasksByStatus(model, TaskStatus.IN_PROGRESS);
    }
	
	@GetMapping(value = "/tasks", params = {"status"})
    public String tasksByStatus(Model model, @RequestParam(value = "status") TaskStatus status) {
        
		User currentUser = userService.getCurrentUser();
		Board currentBoard = currentUser.getCurrentBoard();
		TasksView tasksView = new TasksView();
		
		List<Task> tasks = (List<Task>) taskService.listBoardTasks(currentBoard, status);
		for (Task task : tasks) {
			task.setStarted(taskService.isTaskStarted(task, currentUser));
			task.setDuration(taskService.getTaskDuration(task, currentUser));
			
			tasksView.addTask(task);
		}
		
		model.addAttribute("currentStatus", status);
		model.addAttribute("taskStatuses", taskService.getAllTaskStatuses());
		model.addAttribute("nextTaskStatuses", taskService.getTaskStatusesToChange(status));
		model.addAttribute("boards", boardService.getBoards(currentUser));
		model.addAttribute("currentBoard", currentBoard);
		
		tasksView.sort();
		model.addAttribute("tasksView", tasksView);
		model.addAttribute("durationFormatter", durationFormatter);
		
		return "tasks";
    }
	
	@GetMapping("/task/add")
    public String addTask(Model model) {
		Task newTask = new Task();
		model.addAttribute("task", newTask);
		model.addAttribute("currentBoard", getCurrentBoard());
		
		return "task";
	}
    
	@GetMapping("/task/edit/{taskId}")
	public String editTask(Model model, @PathVariable("taskId") Long taskId) {
		Task newTask = taskService.getTask(taskId);
		model.addAttribute("task", newTask);
		model.addAttribute("currentBoard", getCurrentBoard());
		model.addAttribute("client_id", newTask.getClient() == null ? 0 : newTask.getClient().getId());
		
		return "task";
	}
	
	@PostMapping({"/task/add", "/task/edit/{taskId}"})
    public String saveTask(@ModelAttribute("task") Task task, @ModelAttribute("client_id") Long clientId) {
		
		//System.out.println("task id: " + task.getId());
		task.setBoard(getCurrentBoard());
		task.setClient(clientService.getClient(clientId));
		if (task.getId() == null){
			task.setStatus(TaskStatus.NEW);
		}
		taskService.saveTask(task);
        
		return "redirect:/tasks";
    }
	
	@GetMapping("/task/delete/{taskId}")
    public String deleteTask(@PathVariable("taskId") Long taskId) {
		
		taskService.deleteTask(taskId);
        
		return "redirect:/tasks";
    }
	
	@GetMapping("/task/start/{taskId}")
    public String startTask(@PathVariable("taskId") Long taskId) {
		
		Task task = taskService.getTask(taskId);
		taskService.startTask(task, userService.getCurrentUser());
        
		return "redirect:/tasks";
    }
	
	@GetMapping("/task/stop/{taskId}")
    public String stopTask(@PathVariable("taskId") Long taskId) {
		
		Task task = taskService.getTask(taskId);
		taskService.stopTask(task, userService.getCurrentUser());
        
		return "redirect:/tasks";
    }
	
	@PostMapping("/task/set_status/")
    public String setTasksStatus(@ModelAttribute("status") TaskStatus status, @ModelAttribute("selectedTasks") String tasksIdsString /*Long[] tasksIds*/) {
		
		//System.out.println(status);
		//System.out.println(tasksIdsString);
        
		String[] tasksIds = tasksIdsString.split(",");
		List<Task> tasks = new ArrayList<>();
		for (int i = 0; i < tasksIds.length; i++) {
			Task task = taskService.getTask(Long.parseLong(tasksIds[i]));
			tasks.add(task);
		}
		
		taskService.SetTasksStatus(tasks, status);
		
		return "redirect:/tasks";
    }
}
