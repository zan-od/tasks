package com.zan.tasks.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.zan.tasks.model.Board;
import com.zan.tasks.service.BoardService;
import com.zan.tasks.service.UserService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/board/add")
    public String addBoard(Model model) {
		
		model.addAttribute("boardForm", new Board());
        
		return "new_board";
    }
	
	@PostMapping("/board/add")
    public String addBoard(@ModelAttribute("boardForm") Board board) {
		
		boardService.addBoard(board, userService.getCurrentUser());
        
		return "redirect:/tasks";
    }
	
	@GetMapping("/board/{boardId}")
    public String deleteTask(@PathVariable("boardId") Long boardId) {
		
		userService.setCurrentBoard(userService.getCurrentUser(), boardService.getBoard(boardId));
        
		return "redirect:/tasks";
    }
}
