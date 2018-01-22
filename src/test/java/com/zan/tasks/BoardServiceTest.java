package com.zan.tasks;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zan.tasks.dao.BoardDAO;
import com.zan.tasks.dao.UserDAO;
import com.zan.tasks.model.Board;
import com.zan.tasks.model.User;
import com.zan.tasks.service.BoardService;
import com.zan.tasks.service.BoardServiceImpl;
import com.zan.tasks.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

//    @Mock
//    private BoardDAO boardDaoMock;
//     
//    @InjectMocks
    @Autowired
    private BoardService boardService;
//     
//     
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
	
	@Test
	public void testAddBoard(){
		//when(boardDaoMock.save(any(Board.class))).thenReturn(Long.valueOf(1));
		
		BoardDAO mockedBoardDAO = mock(BoardDAO.class);
		UserService mockedUserService = mock(UserService.class);
		Board mockedBoard = mock(Board.class);
		User mockedUser = mock(User.class);
		
		//BoardService mockedBoardService = mock(BoardService.class);
		BoardService boardService = new BoardServiceImpl(mockedBoardDAO, mockedUserService);
		
		boardService.addBoard(mockedBoard, mockedUser);
		verify(mockedBoardDAO, times(1)).save(mockedBoard);
		verify(mockedUserService, times(1)).setCurrentBoard(mockedUser, mockedBoard);
	}

}
