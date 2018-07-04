package com.zan.tasks;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.User;
import com.zan.tasks.service.BoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestBoardControllerTest {
	
	private MockMvc mockMvc;
	 
	@MockBean
    private BoardService boardServiceMock;
    
    @Autowired
    WebApplicationContext wContext;
    
    @Before
    public void init() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(wContext).build();
    }
    
    
    @Test
    public void getBoard_ShouldReturnNotFound() throws Exception {
        when(boardServiceMock.getBoard(1L)).thenReturn(null);
 
        mockMvc.perform(get("/api/board/1"))
                .andExpect(status().isNotFound())
                ;
 
        verify(boardServiceMock, times(1)).getBoard(1L);
        verifyNoMoreInteractions(boardServiceMock);
    }
    
    @Test
    public void getBoard_ShouldReturnBoard() throws Exception {
        Board board = new Board();
        board.setId(1L);
        board.setName("Test");
 
        when(boardServiceMock.getBoard(1L)).thenReturn(board);
 
        mockMvc.perform(get("/api/board/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test")));
 
        verify(boardServiceMock, times(1)).getBoard(1L);
        verifyNoMoreInteractions(boardServiceMock);
    }

    @Test
    public void getBoard_ShouldReturnBoardWithUserLink() throws Exception {
        Board board = new Board();
        board.setId(1L);
        board.setName("Test");
 
        Board board2 = new Board();
        board2.setId(2L);
        board2.setName("Test 2");
        
        User user = new User();
        user.setUsername("test");
        user.setCurrentBoard(board);
        
        Set<User> users = new HashSet<>();
        users.add(user);
        
        board.setUsers(users);
        
        Set<Board> boards = new HashSet<>();
        boards.add(board);
        boards.add(board2);
        
        user.setBoards(boards);
        
        when(boardServiceMock.getBoard(1L)).thenReturn(board);
 
        MvcResult result = 
        mockMvc.perform(get("/api/board/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.users", hasSize(1)))
                .andExpect(jsonPath("$.users[0].username", is("test")))
                .andReturn()
                ;
        
        System.out.println(result.getResponse().getContentAsString());
 
        verify(boardServiceMock, times(1)).getBoard(1L);
        verifyNoMoreInteractions(boardServiceMock);
    }
}
