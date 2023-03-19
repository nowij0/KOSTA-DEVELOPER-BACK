package com.developer.favoritesStudyroom;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.developer.favoritesstudyroom.entity.FavoritesStudyroom;
import com.developer.favoritesstudyroom.repository.FavoritesStudyroomRepository;
import com.developer.studyroom.entity.Studyroom;
import com.developer.studyroom.repository.StudyroomRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;
@SpringBootTest
class FavoritesStudyroomRepositoryTest {
	private Logger logger = LoggerFactory.getLogger(getClass()); 
	
	@Autowired 
	private FavoritesStudyroomRepository fsr;
	
	@Autowired
	private StudyroomRepository sr;
	
	@Autowired
	private UsersRepository user;

//	@DisplayName("즐겨찾기 추가 기능 테스트")
//	@Test
//	void testFavStudyroomAdd() {
//		FavoritesStudyroom fs = new FavoritesStudyroom();
//		Optional<Studyroom> optS = sr.findById(1L);
//		Studyroom s=optS.get();
//		fs.setStudyroom(s);
//		//fs.setStudyroomFav(s);
//		Optional<Users> optU = user.findById("abc");
//		Users u=optU.get();
//		fs.setUserId(u);
//		fsr.save(fs);
//	}
	
	@DisplayName("즐겨찾기 삭제 기능 테스트")
	@Test
	void testFavStudyroomDelete() {
		fsr.deleteById(2L);
	}

}
