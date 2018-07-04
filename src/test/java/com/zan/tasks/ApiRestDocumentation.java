package com.zan.tasks;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.zan.tasks.model.Board;
import com.zan.tasks.service.BoardService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ApiRestDocumentation {
	
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
    public void shouldReturnBoard() throws Exception {
        Board board = new Board();
        board.setId(1L);
        board.setName("Test");
 
        when(boardServiceMock.getBoard(1L)).thenReturn(board);
       
        mockMvc.perform(get("/api/board/1"))
        //.andDo(print())
        .andExpect(status().isOk())
                //.andExpect(content().string(containsString("Hello World")))
        .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8));
        //.andDo(document("board"));
   }

}
