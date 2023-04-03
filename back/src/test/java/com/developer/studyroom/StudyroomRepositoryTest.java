//package com.developer.studyroom;
//
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
//import com.developer.studyroom.entity.Studyroom;
//import com.developer.studyroom.repository.StudyroomRepository;
//
//@SpringBootTest
//class StudyroomRepositoryTest {
//	
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private StudyroomRepository srr;
//
//	@Autowired
//	private HostUserRepository hur;
//
//	@Test
//	@DisplayName("Studyroom insert() 테스트")
//	void testSave() {
//<<<<<<< HEAD
//		Optional<HostUser>optH =hur.findById("아이디1");
//		assertTrue(optH.isPresent());
//		HostUser h = optH.get();
//		Studyroom s = new Studyroom();
//		s.setAddr("주소테스트");
//		s.setEndTime("10:00");
//		s.setOpenTime("01:00");
//		s.setName("스터디카페");
//=======
//		Optional<HostUser>optH =hur.findById("아이디10");
//		assertTrue(optH.isPresent());
//		HostUser h = optH.get();
//		Studyroom s = new Studyroom();
//		s.setAddr("경기 수원시 영통구 권선로908번길 50");
//		s.setEndTime("10:00");
//		s.setOpenTime("01:00");
//		s.setName("독고스터디카페");
//>>>>>>> a17bbc4b15abb5fd1a82dbbe90805ae698acaa4f
//		s.setImgPath("3.png");
//		s.setHostUser(h); //**필수설정!
//		srr.save(s);
//	}
//
//	@Test
//	@DisplayName("Studyroom findById() 테스트")
//	void testfindById() {
//
//		Studyroom s = new Studyroom();
//		Optional<Studyroom> optA = srr.findById(3L);
//		assertTrue(optA.isPresent());
//		Long expectedId = 3L;
//		assertEquals(expectedId, optA.get().getSrSeq());
//		logger.info("카페시퀀스: "+optA.get().getSrSeq());
//		logger.info("주소: "+optA.get().getAddr());
//		
//	}
//
//	@Test
//	@DisplayName("Studyroom findAll() 테스트")
//	void testfindAll() {
//
//		Studyroom s = new Studyroom();
//		Iterable<Studyroom> IterA = srr.findAll();
//
//		IterA.forEach(iterA -> {
//			logger.info("------------------");
//			logger.info("카페번호: "+iterA.getSrSeq());
//			logger.info("주소: "+iterA.getAddr());
//			logger.info("소개글:" + iterA.getInfo());
//			logger.info("호스트Id:" + iterA.getHostUser().getHostId());
//			logger.info("카페명:" + iterA.getName());
//			logger.info("------------------");
//		});
//	}
//	
//	@Test
//	@DisplayName("Studyroom update() 테스트")
//	void testUpdate() {
//
//		Optional<Studyroom> optA = srr.findById(2L);
//		assertTrue(optA.isPresent());
//		Studyroom s = optA.get();
//		s.setInfo("옹오웅");
//		s.setImgPath("4.png");
//		s.setOpenTime("07:10");
//		s.setEndTime("23:10");
//		srr.save(s); 	
//	}
//	
//	@Test
//	@DisplayName("roominfo delete() 테스트")
//	void testDelete() {
//
//		Optional<Studyroom> optA = srr.findById(1L);
//		assertTrue(optA.isPresent());
//		Studyroom s = optA.get();
//		srr.delete(s);
//	}
//
//}
package com.developer.studyroom;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.hostuser.entity.Host;
import com.developer.hostuser.repository.HostUserRepository;
import com.developer.studyroom.entity.Studyroom;
import com.developer.studyroom.repository.StudyroomRepository;

@SpringBootTest
class StudyroomRepositoryTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private StudycafeRepository srr;

	@Autowired
	private HostRepository hur;

	@Test
	@DisplayName("Studyroom insert() 테스트")
	void testSave() {
		Optional<Host>optH =hur.findById("아이디1");
		assertTrue(optH.isPresent());
		Host h = optH.get();
		Studycafe s = new Studycafe();
		s.setAddr("주소테스트");
		s.setEndTime("10:00");
		s.setOpenTime("01:00");
		s.setName("스터디카페");
		s.setImgPath("3.png");
		s.setHostUser(h); //**필수설정!
		srr.save(s);
	}

	@Test
	@DisplayName("Studyroom findById() 테스트")
	void testfindById() {

		Studycafe s = new Studycafe();
		Optional<Studycafe> optA = srr.findById(3L);
		assertTrue(optA.isPresent());
		Long expectedId = 3L;
		assertEquals(expectedId, optA.get().getSrSeq());
		logger.info("카페시퀀스: "+optA.get().getSrSeq());
		logger.info("주소: "+optA.get().getAddr());
		
	}

	@Test
	@DisplayName("Studyroom findAll() 테스트")
	void testfindAll() {

		Studycafe s = new Studycafe();
		Iterable<Studycafe> IterA = srr.findAll();

		IterA.forEach(iterA -> {
			logger.info("------------------");
			logger.info("카페번호: "+iterA.getSrSeq());
			logger.info("주소: "+iterA.getAddr());
			logger.info("소개글:" + iterA.getInfo());
			logger.info("호스트Id:" + iterA.getHostUser().getHostId());
			logger.info("카페명:" + iterA.getName());
			logger.info("------------------");
		});
	}
	
	@Test
	@DisplayName("Studyroom update() 테스트")
	void testUpdate() {

		Optional<Studycafe> optA = srr.findById(2L);
		assertTrue(optA.isPresent());
		Studycafe s = optA.get();
		s.setInfo("옹오웅");
		s.setImgPath("4.png");
		s.setOpenTime("07:10");
		s.setEndTime("23:10");
		srr.save(s); 	
	}
	
	@Test
	@DisplayName("roominfo delete() 테스트")
	void testDelete() {

		Optional<Studycafe> optA = srr.findById(1L);
		assertTrue(optA.isPresent());
		Studycafe s = optA.get();
		srr.delete(s);
	}

}
