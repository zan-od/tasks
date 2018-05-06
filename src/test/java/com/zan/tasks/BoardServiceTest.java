package com.zan.tasks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zan.tasks.dao.BoardDAO;
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
    
    @Mock
    BoardDAO mockedBoardDAO;
    
//     
//     
//    @Before
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
	
	@Test
	public void testAddBoard(){
		//when(boardDaoMock.save(any(Board.class))).thenReturn(Long.valueOf(1));
		
		//BoardDAO mockedBoardDAO = mock(BoardDAO.class);
		UserService mockedUserService = mock(UserService.class);
		Board mockedBoard = mock(Board.class);
		User mockedUser = mock(User.class);
		
		//BoardService mockedBoardService = mock(BoardService.class);
		BoardService boardService = new BoardServiceImpl(mockedBoardDAO, mockedUserService);
		
		boardService.addBoard(mockedBoard, mockedUser);
		verify(mockedBoardDAO, times(1)).save(mockedBoard);
		verify(mockedUserService, times(1)).setCurrentBoard(mockedUser, mockedBoard);
	}

	@Test
	public void testGetBoard(){
		//when(boardDaoMock.save(any(Board.class))).thenReturn(Long.valueOf(1));
		
		BoardDAO mockedBoardDAO = mock(BoardDAO.class);
		UserService mockedUserService = mock(UserService.class);
		
		BoardService boardService = new BoardServiceImpl(mockedBoardDAO, mockedUserService);
		
		//Board board = boardService.addBoard(mockedBoard, mockedUser);
		//verify(mockedBoardDAO, times(1)).save(mockedBoard);
		//verify(mockedUserService, times(1)).setCurrentBoard(mockedUser, mockedBoard);
	}
}
