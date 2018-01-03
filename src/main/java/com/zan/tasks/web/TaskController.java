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

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Task;
import com.zan.tasks.model.User;
import com.zan.tasks.service.BoardService;
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
	
	private Board getCurrentBoard(){
		return userService.getCurrentUser().getCurrentBoard();
 	}
	
	@GetMapping("/tasks")
    public String tasks(Model model) {
        
		User currentUser = userService.getCurrentUser();
		Board currentBoard = currentUser.getCurrentBoard();
		Task newTask = new Task();
		newTask.setBoard(currentBoard);
		
		Map<String, String> columns = new HashMap<String, String>();
		columns.put("id", "ID");
		columns.put("name", "NAME");
		columns.put("duration", "DURATION");
		
		model.addAttribute("columns", columns);
		model.addAttribute("new_task", newTask);
		
		List<Task> tasks = (List<Task>) taskService.listBoardTasks(currentBoard);
		model.addAttribute("tasks", tasks);
		
		for (Task task : tasks) {
			task.setStarted(taskService.isTaskStarted(task, currentUser));
			task.setDuration(taskService.getTaskDuration(task, currentUser));
		}
		
		model.addAttribute("boards", boardService.getBoards(currentUser));
		model.addAttribute("currentBoard", currentBoard);
		
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
		
		return "task";
	}
	
	@PostMapping({"/task/add", "/task/edit/{taskId}"})
    public String saveTask(@ModelAttribute("task") Task task) {
		
		task.setBoard(getCurrentBoard());
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
}
