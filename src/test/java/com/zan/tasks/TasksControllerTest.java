package com.zan.tasks;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.zan.tasks.model.Board;
import com.zan.tasks.model.Task;
import com.zan.tasks.model.User;
import com.zan.tasks.service.TaskService;
import com.zan.tasks.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TasksControllerTest {
    
	private MockMvc mockMvc;
    
    @Autowired
    @Mock
    private TaskService taskServiceMock;
    
    @Autowired
    @Mock
    private UserService userServiceMock;
    
    @Autowired
    WebApplicationContext wContext;
    
    @Before
    public void init() throws Exception {
    	mockMvc = MockMvcBuilders.webAppContextSetup(wContext).build();
    	MockitoAnnotations.initMocks(taskServiceMock);
    	MockitoAnnotations.initMocks(userServiceMock);
    }
    
    @Test
    public void showAllTasks_ShouldAddTaskEntriesToModelAndRenderTaskListView() throws Exception {
    	Task taskFirst = new Task();
    	taskFirst.setId(1L);
    	
    	Task taskSecond = new Task();
    	taskSecond.setId(2L);
    	
    	Board board = new Board();
    	board.setId(1L);
    	
    	User user = new User();
    	user.setId(1L);
    	user.setUsername("test");
    	user.setCurrentBoard(board);
    	
    	//when(userServiceMock.getCurrentUser()).thenReturn(user);
    	doReturn(user).when(userServiceMock).getCurrentUser();
    	when(taskServiceMock.listBoardTasks(board)).thenReturn(Arrays.asList(taskFirst, taskSecond));
    	
    	mockMvc.perform(get("/tasks"))
    		.andExpect(status().isOk())
    		//.andExpect(view().name("tasks"))
    		//.andExpect(model().attribute("todos", hasSize(2)))
    	;
    	verify(taskServiceMock, times(1)).listBoardTasks(board);
        verifyNoMoreInteractions(taskServiceMock);
    }

}
