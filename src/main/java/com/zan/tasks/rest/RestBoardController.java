package com.zan.tasks.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.zan.tasks.model.Board;
import com.zan.tasks.service.BoardService;

@RestController
public class RestBoardController {

	private BoardService boardService;
	
	@Autowired
	public void getBoardService(BoardService boardService){
		this.boardService = boardService;
	}
	
	@GetMapping("api/board/{boardId}")
	public @ResponseBody Board getBoard(@ModelAttribute("boardId") Long boardId){
		
		Board board = boardService.getBoard(boardId);
		
		if (board==null){
			throw new ResourceNotFoundException(); 
		}
		
		return board;
	}
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public class ResourceNotFoundException extends RuntimeException {
		private static final long serialVersionUID = 1L;
	}
	
}
