package tswaterking.waterking.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tswaterking.waterking.entity.Board;
import tswaterking.waterking.service.impl.BoardService;


public class BoardServiceTest {

	private ApplicationContext applicationContext = null;


	@Before
	public void setUp() {
		applicationContext = new ClassPathXmlApplicationContext("tswaterking/waterking/test/applicationContext.xml");
	}


	@Test
	public void testSaveOrUpdate() {
		BoardService service = 
			(BoardService)applicationContext.getBean("boardService");
		Board b = new Board();
		System.out.println(service.saveOrUpdate(b));

	}
}
