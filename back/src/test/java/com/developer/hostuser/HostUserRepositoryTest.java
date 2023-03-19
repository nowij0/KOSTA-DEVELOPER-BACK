//package com.developer.hostuser;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.Optional;
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.developer.hostuser.entity.HostUser;
//import com.developer.hostuser.repository.HostUserRepository;
//
//@SpringBootTest
//class HostUserRepositoryTest {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private HostUserRepository hur;
//
//	@Test
//	@DisplayName("hostUser insert() 테스트")
//	void testSave() {
//
//<<<<<<< HEAD
//		for (int i = 1; i <= 5; i++) {
//=======
//		for (int i = 10; i <= 20; i++) {
//>>>>>>> a17bbc4b15abb5fd1a82dbbe90805ae698acaa4f
//			HostUser h = new HostUser();
//			h.setHostId("아이디" + i);
//			h.setPwd("114");
//			h.setNum("211-21-111");
//			h.setName("홍길동");
//			h.setTel("010-1111-8951");
//			h.setEmail("1est@test.com");
//			hur.save(h);
//		}
//	}
//
//	@Test
//	@DisplayName("hostUser findById() 테스트")
//	void testfindById() {
//
//		HostUser h = new HostUser();
//		Optional<HostUser> optA = hur.findById("아이디2");
//		assertTrue(optA.isPresent());
//		String expectedId = "아이디2";
//		assertEquals(expectedId, optA.get().getHostId());
//		logger.info("아이디: "+optA.get().getHostId());
//
//	}
//
//	@Test
//	@DisplayName("hostUser findAll() 테스트")
//	void testfindAll() {
//
//		HostUser h = new HostUser();
//		Iterable<HostUser> IterA = hur.findAll();
//
//		IterA.forEach(iterA -> {
//			logger.info("------------------");
//			logger.info("아이디:" + iterA.getHostId());
//			logger.info("비밀번호:" + iterA.getPwd());
//			logger.info("사업자번호:" + iterA.getNum());
//			logger.info("이름:" + iterA.getName());
//			logger.info("전화번호:" + iterA.getTel());
//			logger.info("이메일:" + iterA.getEmail());
//			logger.info("승인대기여부:" + iterA.getReady());
//			logger.info("------------------");
//		});
//	}
//	
//	@Test
//	@DisplayName("hostUser update() 테스트")
//	void testUpdate() {
//
//		Optional<HostUser> optA = hur.findById("아이디4");
//		assertTrue(optA.isPresent());
//		HostUser h = optA.get();
//		h.setPwd("1212");
//		h.setEmail("7734@naver.com");
//		h.setTel("111-111-1111");
//		hur.save(h); 	
//	}
//	
//	@Test
//	@DisplayName("hostUser delete() 테스트")
//	void testDelete() {
//
//		Optional<HostUser> optA = hur.findById("아이디2");
//		assertTrue(optA.isPresent());
//		HostUser h = optA.get();
//		hur.delete(h);
//	}
//}
package com.developer.hostuser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.hostuser.entity.HostUser;
import com.developer.hostuser.repository.HostUserRepository;

@SpringBootTest
class HostUserRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private HostUserRepository hur;

	@Test
	@DisplayName("hostUser insert() 테스트")
	void testSave() {

		for (int i = 1; i <= 5; i++) {
			HostUser h = new HostUser();
			h.setHostId("아이디" + i);
			h.setPwd("114");
			h.setNum("211-21-111");
			h.setName("홍길동");
			h.setTel("010-1111-8951");
			h.setEmail("1est@test.com");
			hur.save(h);
		}
	}

	@Test
	@DisplayName("hostUser findById() 테스트")
	void testfindById() {

		HostUser h = new HostUser();
		Optional<HostUser> optA = hur.findById("아이디2");
		assertTrue(optA.isPresent());
		String expectedId = "아이디2";
		assertEquals(expectedId, optA.get().getHostId());
		logger.info("아이디: "+optA.get().getHostId());

	}

	@Test
	@DisplayName("hostUser findAll() 테스트")
	void testfindAll() {

		HostUser h = new HostUser();
		Iterable<HostUser> IterA = hur.findAll();

		IterA.forEach(iterA -> {
			logger.info("------------------");
			logger.info("아이디:" + iterA.getHostId());
			logger.info("비밀번호:" + iterA.getPwd());
			logger.info("사업자번호:" + iterA.getNum());
			logger.info("이름:" + iterA.getName());
			logger.info("전화번호:" + iterA.getTel());
			logger.info("이메일:" + iterA.getEmail());
			logger.info("승인대기여부:" + iterA.getReady());
			logger.info("------------------");
		});
	}
	
	@Test
	@DisplayName("hostUser update() 테스트")
	void testUpdate() {

		Optional<HostUser> optA = hur.findById("아이디4");
		assertTrue(optA.isPresent());
		HostUser h = optA.get();
		h.setPwd("1212");
		h.setEmail("7734@naver.com");
		h.setTel("111-111-1111");
		hur.save(h); 	
	}
	
	@Test
	@DisplayName("hostUser delete() 테스트")
	void testDelete() {

		Optional<HostUser> optA = hur.findById("아이디2");
		assertTrue(optA.isPresent());
		HostUser h = optA.get();
		hur.delete(h);
	}
}
