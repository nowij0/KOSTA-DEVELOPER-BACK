//package com.developer.roominfo;
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
//import com.developer.roominfo.entity.RoomInfo;
//import com.developer.roominfo.repository.RoomInfoRepository;
//import com.developer.studyroom.entity.Studyroom;
//import com.developer.studyroom.repository.StudyroomRepository;
//
//@SpringBootTest
//class RoomInfoRepositoryTest {
//	private Logger logger = LoggerFactory.getLogger(getClass());
//
//	@Autowired
//	private RoomInfoRepository rir;
//	
//	@Autowired
//	private StudyroomRepository srr;
//	
//	@Test
//	@DisplayName("roominfo insert() 테스트")
//	void testSave() {
//<<<<<<< HEAD
//		Optional<Studyroom>optS =srr.findById(1L);
//=======
//		Optional<Studyroom>optS =srr.findById(24L);
//>>>>>>> a17bbc4b15abb5fd1a82dbbe90805ae698acaa4f
//		assertTrue(optS.isPresent());
//		Studyroom s = optS.get();
//		RoomInfo r = new RoomInfo();
//		r.setName("2인실");
//		r.setInfo("깨끗해요");
//		r.setImgPath("2.jpg");
//		r.setPerson(10);
//		r.setPrice(3000);
//		r.setStudyroom(s); //**필수설정!
//			rir.save(r);
//	}
//
//	@Test
//	@DisplayName("roominfo findById() 테스트")
//	void testfindById() {
//
//		RoomInfo r = new RoomInfo();
//		Optional<RoomInfo> optA = rir.findById(2L);
//		assertTrue(optA.isPresent());
//		Long expectedId = 2L;
//		assertEquals(expectedId, optA.get().getRoomSeq());
//		logger.info("방번호: "+optA.get().getRoomSeq());
//		logger.info("방가격: "+optA.get().getPrice());
//		
//	}
//
//	@Test
//	@DisplayName("roominfo findAll() 테스트")
//	void testfindAll() {
//
//		RoomInfo r = new RoomInfo();
//		Iterable<RoomInfo> IterA = rir.findAll();
//
//		IterA.forEach(iterA -> {
//			logger.info("------------------");
//			logger.info("방번호: "+iterA.getRoomSeq());
//			logger.info("방가격: "+iterA.getPrice());
//			logger.info("방이름:" + iterA.getName());
//			logger.info("인원:" + iterA.getPerson());
//			logger.info("------------------");
//		});
//	}
//	
//	@Test
//	@DisplayName("roominfo update() 테스트")
//	void testUpdate() {
//
//		Optional<RoomInfo> optA = rir.findById(2L);
//		assertTrue(optA.isPresent());
//		RoomInfo r = optA.get();
//		r.setInfo("옹오웅");
//		r.setPrice(1000);
//		r.setPerson(5);
//		rir.save(r); 	
//	}
//	
//	@Test
//	@DisplayName("roominfo delete() 테스트")
//	void testDelete() {
//
//		Optional<RoomInfo> optA = rir.findById(3L);
//		assertTrue(optA.isPresent());
//		RoomInfo r = optA.get();
//		rir.delete(r);
//	}
//
//}=======
package com.developer.roominfo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.roominfo.entity.RoomInfo;
import com.developer.roominfo.repository.RoomInfoRepository;
import com.developer.studyroom.entity.Studyroom;
import com.developer.studyroom.repository.StudyroomRepository;

@SpringBootTest
class RoomInfoRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RoomInfoRepository rir;
	
	@Autowired
	private StudycafeRepository srr;
	
	@Test
	@DisplayName("roominfo insert() 테스트")
	void testSave() {

		Optional<Studycafe>optS =srr.findById(1L);

		assertTrue(optS.isPresent());
		Studycafe s = optS.get();
		RoomInfo r = new RoomInfo();
		r.setName("2인실");
		r.setInfo("깨끗해요");
		r.setImgPath("2.jpg");
		r.setPerson(10);
		r.setPrice(3000);
		r.setStudyroom(s); //**필수설정!
			rir.save(r);
	}

	@Test
	@DisplayName("roominfo findById() 테스트")
	void testfindById() {

		RoomInfo r = new RoomInfo();
		Optional<RoomInfo> optA = rir.findById(2L);
		assertTrue(optA.isPresent());
		Long expectedId = 2L;
		assertEquals(expectedId, optA.get().getRoomSeq());
		logger.info("방번호: "+optA.get().getRoomSeq());
		logger.info("방가격: "+optA.get().getPrice());
		
	}

	@Test
	@DisplayName("roominfo findAll() 테스트")
	void testfindAll() {

		RoomInfo r = new RoomInfo();
		Iterable<RoomInfo> IterA = rir.findAll();

		IterA.forEach(iterA -> {
			logger.info("------------------");
			logger.info("방번호: "+iterA.getRoomSeq());
			logger.info("방가격: "+iterA.getPrice());
			logger.info("방이름:" + iterA.getName());
			logger.info("인원:" + iterA.getPerson());
			logger.info("------------------");
		});
	}
	
	@Test
	@DisplayName("roominfo update() 테스트")
	void testUpdate() {

		Optional<RoomInfo> optA = rir.findById(2L);
		assertTrue(optA.isPresent());
		RoomInfo r = optA.get();
		r.setInfo("옹오웅");
		r.setPrice(1000);
		r.setPerson(5);
		rir.save(r); 	
	}
	
	@Test
	@DisplayName("roominfo delete() 테스트")
	void testDelete() {

		Optional<RoomInfo> optA = rir.findById(3L);
		assertTrue(optA.isPresent());
		RoomInfo r = optA.get();
		rir.delete(r);
	}

}
