package com.developer.host.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.developer.host.entity.Host;

public interface HostRepository extends CrudRepository<Host, String> {

	// [JH] 호스트유저 전체 목록
	@Query(value = " SELECT *" 
			+ " FROM HOST" 
			+ " order by host_name", nativeQuery = true)
	public List<Host> selectAll();
	
	//[JH]
	public boolean existsByHostId(String hostId);
	
	//[JH]
	public boolean existsByHostEmail(String email);
	
	//[JH]
	public boolean existsByBusinessNum(String num);

	// [SR] 미승인 호스트유저 목록
	@Query(value = "SELECT host_id, business_num, host_name, host_tel, host_email " 
			+ "FROM host " 
			+ "WHERE status = 0", nativeQuery = true)
	public List<Object[]> selectAllUnapproveHost();


	
	//[SR] 호스트 비밀번호찾기용
    @Query(value="select * from host where host_email = :email",nativeQuery= true)
    public Host hostEmailCheck(@Param("email") String email);

	// [GH] 호스트 아이디찾기
	public Optional<Host> findByBusinessNum(String num);
}
