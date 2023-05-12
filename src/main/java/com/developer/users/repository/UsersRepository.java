package com.developer.users.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.developer.users.entity.Users;

public interface UsersRepository extends JpaRepository<Users, String> {
	
	//[JW]
	@EntityGraph(attributePaths = "tutor") //TUTOR + USERS 동시 출력 
	public Optional<Users> findByUserId(String userId);
	
	//[JW]
	@Query(nativeQuery = true,
					value = "SELECT u.name, t.tutor_id FROM Users u\n"
							+ "INNER JOIN tutor t\n"
							+ "ON u.user_id = t.tutor_id\n"
							+ "INNER JOIN lesson l\n"
							+ "ON t.tutor_id = l.tutor_id\n"
							+ "INNER JOIN applied_lesson al\n"
							+ "ON al.al_lesson_seq = l.lesson_seq "
							+ "WHERE l.lesson_seq = :lessonSeq "
							+"AND al.apply_ok = 0")
	public List<Object[]> applyTuteeList(@Param("lessonSeq") Long lessonSeq);
	
	//근형 (사용안할수도)
		@Query(value="SELECT *"
				+ "	FROM users"
				+ "	WHERE"
				+ "	user_id= :userId and pwd= :pwd", nativeQuery = true)
		public Users usersLogin(@Param("userId")String userId, @Param("pwd") String pwd);
		
		//근형
		public Optional<Users> findByEmail(String email);


		// [SR] 미승인 튜터 목록
		@Query(value = "SELECT u.user_id, u.name, u.nickname, u.email, u.tel "
				+ "FROM users u, tutor t "
				+ "WHERE u.user_id = t.tutor_id "
				+ "AND apply_ok = 0 ", nativeQuery = true)
		public List<Object[]> selectAllUnapproveTutor();
		
		//ds
		@Query(value="SELECT user_id, role, name, nickname, tel \r\n"
					+ "FROM users \r\n"
					+ "ORDER BY name", nativeQuery = true)
		public List<Object[]> selectALLUsers();
			
		//ds
		@Query(value= "SELECT user_id, role, name, nickname, tel   \r\n"
				+ "FROM  users   \r\n"
				+ "where user_id Like '%t%'  \r\n"
				+ "ORDER BY name", nativeQuery = true)
		public List<Object[]> selectUserById(@Param("userId") String userId);
			
		@Query(value="select * from users where user_id = :userId",nativeQuery= true)
		public Users getUserdetail(@Param("userId") String userId);
		
		//[JH]
		public boolean existsByUserId(String userId);
		
		//[JH]
		public boolean existsByEmail(String email);
		
		//[SR] 비밀번호찾기용
		@Query(value="select * from users where email = :email",nativeQuery= true)
		public Users userEmailCheck(@Param("email") String email);
}
