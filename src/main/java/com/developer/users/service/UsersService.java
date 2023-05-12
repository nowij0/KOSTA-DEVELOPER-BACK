package com.developer.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.appliedlesson.repository.AppliedLessonRepository;
import com.developer.email.EmailService;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.users.dto.UsersDTO;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersService {

	private final UsersRepository uRepository;
	private final AppliedLessonRepository alRepository;
	private final EmailService emailService;

	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 사용자 추가.
	 * 
	 * @author Jin
	 * @param usersDTO
	 * @throws AddException
	 */
	public void addUsers(UsersDTO usersDTO) throws AddException {
//		Optional<Users> u = uRepository.findById(usersDTO.getUserId());
		Users usersEntity = modelMapper.map(usersDTO, Users.class);
		uRepository.save(usersEntity);
	}

	/**
	 * 사용자 정보 수정(변경 예정..)
	 * 
	 * @author Jin
	 * @param users
	 * @throws AddException
	 */		
	public void editUser(String userId, UsersDTO usersDTO) throws FindException {
	
		Optional<Users> optUsers = uRepository.findById(userId);
		if(optUsers.isPresent()) {
			Users usersEntity = optUsers.get();
			
			usersEntity.setAddr(usersDTO.getAddr());
			usersEntity.setTel(usersDTO.getTel());
			usersEntity.setPwd(usersDTO.getPwd());
			usersEntity.setNickname(usersDTO.getNickname());
			usersEntity.setName(usersDTO.getName());
			uRepository.save(usersEntity);
			
		} else {
			throw new FindException("회원 정보 수정 맨");
		}
	}


	/**
	 * 사용자 상세정보 조회.
	 * 
	 * @author Jin
	 * @param userId
	 * @return usersDTO
	 * @throws FindException
	 */
	public UsersDTO.UsersDetailDTO getUser(String userId) throws FindException {
		Optional<Users> optU = uRepository.findById(userId);
		if (optU.isPresent()) {
			Users users = optU.get();
			UsersDTO.UsersDetailDTO usersDTO = modelMapper.map(users, UsersDTO.UsersDetailDTO.class);

			return usersDTO;
		} else {
			throw new FindException("존재하지 않는 유저입니다.");
		}
	}

	/**
	 * 사용자 아이디 중복체크
	 * 
	 * @author Jin
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public boolean existsByUserId(String userId) throws FindException {
		return uRepository.existsByUserId(userId);
	}

	/**
	 * 사용자 이메일 중복체크
	 * 
	 * @author Jin
	 * @param email
	 * @return
	 * @throws FindException
	 */
	public boolean existsByEmail(String email) throws FindException {
		return uRepository.existsByEmail(email);
	}

	/**
	 * 유저 로그인
	 * 
	 * @author choigeunhyeong
	 * @param userId
	 * @param pwd
	 * @return
	 * @throws FindException
	 */
	public UsersDTO.uDTO userLogin(String userId, String pwd) throws FindException {

		Optional<Users> optU = uRepository.findById(userId);
		if (optU.isPresent()) {
			Users users = optU.get();
			UsersDTO.uDTO usersDTO = modelMapper.map(users, UsersDTO.uDTO.class);
			if (usersDTO.getPwd().equals(pwd) && !usersDTO.getRole().equals(3)) {
				usersDTO.setPwd("");
				return usersDTO;
			} else {
				throw new FindException("실패");
			}
		} else {
			throw new FindException("로그인 실패");
		}
	}

	/**
	 * 유저 아이디 찾기 !
	 * 
	 * @author choigeunhyeong
	 * @param email
	 * @return
	 * @throws FindException
	 */
	public UsersDTO.uDTO findId(String email) throws FindException {
		Optional<Users> optU = uRepository.findByEmail(email);
		if (optU.isPresent()) {
			Users users = optU.get();
			UsersDTO.uDTO usersDTO = modelMapper.map(users, UsersDTO.uDTO.class);
			if (usersDTO.getEmail().equals(email)) {
				return usersDTO;
			}
		}
		throw new FindException("이메일에 해당하는 회원이 없습니다");
	}

	/**
	 * 회원 탈퇴(Role 값이 3인 유저는 탈퇴한 것으로 간주)
	 * 
	 * @author Jin
	 * @param userId
	 * @throws FindException
	 */
	public void deleteUser(String userId) throws FindException {
		Optional<Users> optUsers = uRepository.findById(userId);
		if(optUsers.isPresent()) {
			Users usersEntity = optUsers.get();
			usersEntity.setRole(3);
			uRepository.save(usersEntity);
		}
	
	}

	/**
	 * 회원정보 찾기
	 * 
	 * @author choigeunhyeong
	 * @param id
	 * @return
	 * @throws FindException
	 */
	public Users findById(String userId) throws FindException {
		Optional<Users> optU = uRepository.findById(userId);
		if (optU.isPresent()) {
			return optU.get();
		}
		throw new FindException("아이디에 해당하는 고객이 없습니다");
	}

	/**
	 * user 객체 1개를 출력한다
	 * 
	 * @author SR
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public UsersDTO selectUsers(String userId) throws FindException {
		Optional<Users> optU = uRepository.findById(userId);
		if (optU.isPresent()) {
			Users entityU = optU.get();
			UsersDTO usersDTO = modelMapper.map(entityU, UsersDTO.class);
			return usersDTO;
		} else {
			throw new FindException("해당 ID가 존재하지 않습니다.");
		}
	}

	/**
	 * [관리자] 수업을 예약한 튜티 목록
	 * 
	 * @author moonone
	 * @param lessonSeq 수업번호
	 * @return 튜티목록
	 */
	public List<UsersDTO.uNameDTO> applyTuteeList(Long lessonSeq) {
		List<Object[]> list = uRepository.applyTuteeList(lessonSeq);
		List<UsersDTO.uNameDTO> uDTO = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			UsersDTO.uNameDTO dto = new UsersDTO.uNameDTO();
			dto.setTuteeName((String) list.get(i)[0]);
			dto.setTuteeId((String) list.get(i)[1]);
			uDTO.add(dto);
		}
		return uDTO;
	}

	/**
	 * [관리자] 수업을 예약한 튜티 삭제
	 * 
	 * @author moonone
	 * @param tuteeId 튜티아이디
	 * @throws RemoveException
	 */
	public void deleteTutee(String tuteeId, Long lessonSeq) throws RemoveException {
		Long applySeq = alRepository.delAppliedTutee(tuteeId, lessonSeq);
		alRepository.deleteById(applySeq);
	}

	/**
	 * 튜터 미승인 목록을 출력한다.
	 * 
	 * @author SR
	 * @return
	 * @throws FindException
	 */
	public List<UsersDTO.unapproveTutorDTO> selectAllUnapproveTutor() throws FindException {
		List<Object[]> uList = uRepository.selectAllUnapproveTutor();

		List<UsersDTO.unapproveTutorDTO> uListDto = new ArrayList<>();
		for (int i = 0; i < uList.size(); i++) {
			UsersDTO.unapproveTutorDTO uDto = new UsersDTO.unapproveTutorDTO();
			uDto.setUserId((String) uList.get(i)[0]);
			uDto.setName((String) uList.get(i)[1]);
			uDto.setNickname((String) uList.get(i)[2]);
			uDto.setEmail((String) uList.get(i)[3]);
			uDto.setTel((String) uList.get(i)[4]);

			uListDto.add(uDto);
		}
		return uListDto;
	}

	/**
	 * 관리자페이지에서 회원리스트 전체출력
	 * 
	 * @author DS
	 * @return
	 * @throws FindException
	 */
	public List<UsersDTO> getALLUsers() throws FindException {
		List<Object[]> list = uRepository.selectALLUsers();
		List<UsersDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			uDTO.setUserId((String) list.get(i)[0]);
			uDTO.setRole(Integer.parseInt(String.valueOf(list.get(i)[1])));
			uDTO.setName((String) list.get(i)[2]);
			uDTO.setNickname((String) list.get(i)[3]);
			uDTO.setTel((String) list.get(i)[4]);
			dto.add(uDTO);

		}
		return dto;
	}

	/**
	 * 관리자페이지 회원리스트에서 검색하기
	 * 
	 * @author DS
	 * @param userId
	 * @return List<UsersDTO>
	 * @throws FindException
	 */
	public List<UsersDTO> getUserById(String userId) throws FindException {
		List<Object[]> list = uRepository.selectUserById(userId);
		List<UsersDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			UsersDTO uDTO = new UsersDTO();
			uDTO.setUserId((String) list.get(i)[0]);
			uDTO.setRole(Integer.parseInt(String.valueOf(list.get(i)[1])));
			uDTO.setName((String) list.get(i)[2]);
			uDTO.setNickname((String) list.get(i)[3]);
			uDTO.setTel((String) list.get(i)[4]);
			dto.add(uDTO);

		}
		return dto;
	}

	/**
	 * 회원 1명의 상세정보를 출력한다
	 * 
	 * @author DS
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public UsersDTO selectUserDetail(String userId) throws FindException {
		Users u = uRepository.getUserdetail(userId);
		UsersDTO dto = new UsersDTO();
		dto.setUserId(u.getUserId());
		dto.setAddr(u.getAddr());
		dto.setEmail(u.getEmail());
		dto.setName(u.getName());
		dto.setNickname(u.getNickname());
		dto.setPwd(u.getPwd());
		dto.setTel(u.getTel());
		return dto;
	}

	/**
	 * 본인인증 이메일 체크(가입여부확인)
	 * 
	 * @author SR
	 * @param email
	 * @return true: 신규가입가능 false: 신규가입불가
	 */
	public boolean userEmailCheck(String email) {
		Users users = uRepository.userEmailCheck(email);
		if (users == null) {
			return true; // 가입된 정보가 없음
		} else {
			return false; // 가입된 정보가 있음
		}
	}

	/**
	 * Email을 통해 해당 email로 가입된 정보가 있는지 확인. 가입된 정보가 있다면 입력받은 id와 email이 서로 일치한지 여부를
	 * 리턴하면서 임시비밀번호로 변경 및 메일발송
	 * 
	 * @author SR
	 * @param userEmail
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public boolean userPwdAndEmailCheck(String email, String userId) throws Exception {
		Users users = uRepository.userEmailCheck(email);
		if (users != null && users.getUserId().equals(userId)) {
			String temporaryPwd = emailService.updatePwd(email);
			users.setPwd(temporaryPwd);
			uRepository.save(users);
			return true;
		} else {
			return false;
		}
	}

}
