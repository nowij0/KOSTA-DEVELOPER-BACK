package com.example.demo;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.board.entity.Board;

@SpringBootTest
class BoardRepositoryTest {

	@Autowired
	private Board b;
	
//	@Test
//	void testFind() {
//		for(int i=1; i<=5; i++) {
//			Board b = new Board();
//			b.setPNo("C"+i);
//			b.setPName("상품명" + i);
//			pr.save(p);
//		}
//	}

}
