package com.developer.hostuser.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.email.EmailService;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.hostuser.dto.HostUserDTO;
import com.developer.hostuser.entity.HostUser;
import com.developer.hostuser.repository.HostUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostUserService {

	private final HostUserRepository hRepository;
	private final EmailService emailService;

	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * HostUser 객체 1개를 가져온다.
	 * 
	 * @author SR
	 * @param hostId
	 * @return HostUser
	 * @throws FindException
	 */
	public HostUserDTO selectHost(String hostId) throws FindException {
		Optional<HostUser> optHost = hRepository.findById(hostId);
		if (optHost.isPresent()) {
			HostUser hostEntity = optHost.get();
			HostUserDTO hostDTO = modelMapper.map(hostEntity, HostUserDTO.class);
			return hostDTO;
		} else {
			throw new FindException("해당 HOST_ID가 존재하지 않습니다.");
		}
	}

	/**
	 * 호스트유저 추가하기
	 * 
	 * @author Jin
	 * @param hostUserDTO
	 * @throws AddException
	 */
	public void addHost(HostUserDTO hostUserDTO) throws AddException {
		HostUser hostUserEntity = modelMapper.map(hostUserDTO, HostUser.class);
		hRepository.save(hostUserEntity);
	}

	/**
	 * 호스트유저 전체목록 불러오기
	 * 
	 * @author Jin
	 * @return
	 * @throws FindException
	 */
	public List<HostUser> selectAll() throws FindException {
		List<HostUser> list = hRepository.selectAll();
		return list;
	}

	/**
	 * 호스트유저 아이디 중복체크
	 * 
	 * @author Jin
	 * @param hostId
	 * @return
	 * @throws FindException
	 */
	public boolean existsByHostId(String hostId) throws FindException {
		return hRepository.existsByHostId(hostId);
	}

	/**
	 * 호스트유저 이메일 중복체크
	 * 
	 * @author Jin
	 * @param email
	 * @return
	 * @throws FindException
	 */
	public boolean existsByHostEmail(String email) throws FindException {
		return hRepository.existsByEmail(email);
	}

	/**
	 * 호스트유저 사업자번호 중복체크
	 * 
	 * @author Jin
	 * @param num
	 * @return
	 * @throws FindException
	 */
	public boolean existsByNum(String num) throws FindException {
		return hRepository.existsByNum(num);
	}

	/**
	 * HostUser 상태값을 변환한다(탈퇴기능 ready=2).
	 * 
	 * @author SR
	 * @param hostId
	 * @throws FindException
	 */
	public void outHost(String hostId) throws FindException {

		Optional<HostUser> optHost = hRepository.findById(hostId);
		if (optHost.isPresent()) {
			HostUser hostEntity = optHost.get();
			hostEntity.setReady(2);
			hRepository.save(hostEntity);
		}
	}

	/**
	 * 호스트정보를 수정한다.
	 * 
	 * @author SR
	 * @param hostId
	 * @param hostUserDTO
	 * @throws FindException
	 */
	public void updateHost(String hostId, HostUserDTO hostuserDTO) throws FindException {

		Optional<HostUser> optHost = hRepository.findById(hostId);
		if (optHost.isPresent()) {
			HostUser hostEntity = optHost.get();

			// logger.error("값:" + hostuserDTO.toString());
			hostEntity.setPwd(hostuserDTO.getPwd());
			hostEntity.setTel(hostuserDTO.getTel());
			hostEntity.setEmail(hostuserDTO.getEmail());
			hRepository.save(hostEntity);

		} else {
			throw new FindException("호스트회원정보 수정 오류");
		}
	}

	/**
	 * 미승인 호스트 유저 목록 출력한다.
	 * 
	 * @author SR
	 * @return
	 * @throws FindException
	 */
	public List<HostUserDTO.unApproveHostDTO> hostUnapproveList() throws FindException {
		List<Object[]> hList = hRepository.selectAllUnapproveHost();
		List<HostUserDTO.unApproveHostDTO> hListDto = new ArrayList<>();

		for (int i = 0; i < hList.size(); i++) {
			HostUserDTO.unApproveHostDTO hDto = new HostUserDTO.unApproveHostDTO();
			hDto.setHostId((String) hList.get(i)[0]);
			hDto.setNum((String) hList.get(i)[1]);
			hDto.setName((String) hList.get(i)[2]);
			hDto.setTel((String) hList.get(i)[3]);
			hDto.setEmail((String) hList.get(i)[4]);

			hListDto.add(hDto);
		}
		return hListDto;
	}

	/**
	 * 호스트 가입을 승인한다. (메일 포함)
	 * 
	 * @author SR
	 * @param hostId
	 * @throws FindException, Exception
	 */
	public void readyOk(String hostId) throws FindException, Exception {
		Optional<HostUser> optHost = hRepository.findById(hostId);
		HostUser hostEntity = optHost.get();
		hostEntity.setReady(1);
		emailService.hostOk(hostEntity.getEmail());
		hRepository.save(hostEntity);
	}

	/**
	 * 호스트 승인을 거절한다(삭제)
	 * 
	 * @author SR
	 * @param hostId
	 * @throws Exception
	 * @throws FindException
	 */
	public void deleteHost(String hostId) throws FindException, Exception {
		Optional<HostUser> optH = hRepository.findById(hostId);
		if (optH.isPresent()) {
			HostUser entityH = optH.get();
			emailService.hostReject(entityH.getEmail());
			hRepository.delete(entityH);
		} else {
			throw new FindException("존재하지 않는 호스트 유저입니다.");
		}
	}

	/**
	 * 호스트로그인
	 * 
	 * @author choigeunhyeong
	 * @param userId
	 * @param pwd
	 * @return
	 * @throws FindException
	 */
	public HostUserDTO.HostLoginDTO HostLogin(String HostId, String pwd) throws FindException {

		Optional<HostUser> optH = hRepository.findById(HostId);
		if (optH.isPresent()) {
			HostUser hostuser = optH.get();
			HostUserDTO.HostLoginDTO hostLoginDTO = modelMapper.map(hostuser, HostUserDTO.HostLoginDTO.class);
			System.out.println("호스트아이디는 " + HostId + "비밀번호는" + pwd);
			if (hostLoginDTO.getPwd().equals(pwd) && !hostLoginDTO.getReady().equals(2)) {
				hostLoginDTO.setPwd("");
				return hostLoginDTO;
			} else {
				throw new FindException("실패");
			}
		} else {
			throw new FindException("로그인 실패");
		}
	}

	/**
	 * 호스트 회원정보 찾기
	 * 
	 * @author choigeunhyeong
	 * @param id
	 * @return
	 * @throws FindException
	 */
	public HostUser findById(String hostId) throws FindException {
		Optional<HostUser> optH = hRepository.findById(hostId);
		if (optH.isPresent()) {
			return optH.get();
		}
		throw new FindException("아이디에 해당하는 고객이 없습니다");
	}

	/**
	 * 본인인증 이메일 체크(가입여부확인)
	 * 
	 * @author SR
	 * @param email
	 * @return true: 신규가입가능 false: 신규가입불가
	 */
	public boolean hostEmailCheck(String email) {
		HostUser host = hRepository.hostEmailCheck(email);
		if (host == null) {
			return true; // 가입된 정보가 없음
		} else {
			return false; // 가입된 정보가 있음
		}
	}

	/**
	 * 호스트 아이디 찾기
	 * 
	 * @author choigeunhyeong
	 * @param Num 사업자 번호
	 * @return
	 * @throws FindException
	 */
	public HostUserDTO findHostId(String num) throws FindException {
		Optional<HostUser> optH = hRepository.findByNum(num);
		if (optH.isPresent()) {
			HostUser host = optH.get();
			HostUserDTO hostDTO = modelMapper.map(host, HostUserDTO.class);
			if (hostDTO.getNum().equals(num)) {
				return hostDTO;
			}
		}
		throw new FindException("사업자번호가 일치하는 회원이 없습니다.");
	}

	/**
	 * Email을 통해 해당 email로 가입된 정보가 있는지 확인. 가입된 정보가 있다면 입력받은 id와 email이 서로 일치한지 여부를
	 * 리턴하면서 임시비밀번호로 변경 및 메일발송
	 * 
	 * @param email
	 * @param hostId
	 * @return
	 * @throws Exception
	 */
	public boolean hostPwdAndEmailCheck(String email, String hostId) throws Exception {
		HostUser host = hRepository.hostEmailCheck(email);
		if (host != null && host.getHostId().equals(hostId)) {
			String temporaryPwd = emailService.updatePwd(email);
			host.setPwd(temporaryPwd);
			hRepository.save(host);
			return true;
		} else {
			return false;
		}
	}

}