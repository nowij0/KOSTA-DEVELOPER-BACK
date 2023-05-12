package com.developer.host.service;

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
import com.developer.host.dto.HostDTO;
import com.developer.host.entity.Host;
import com.developer.host.repository.HostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HostService {

	private final HostRepository hRepository;
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
	public HostDTO selectHost(String hostId) throws FindException {
		Optional<Host> optHost = hRepository.findById(hostId);
		if (optHost.isPresent()) {
			Host hostEntity = optHost.get();
			HostDTO hostDTO = modelMapper.map(hostEntity, HostDTO.class);
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
	public void addHost(HostDTO hostUserDTO) throws AddException {
		Host hostUserEntity = modelMapper.map(hostUserDTO, Host.class);
		hRepository.save(hostUserEntity);
	}

	/**
	 * 호스트유저 전체목록 불러오기
	 * 
	 * @author Jin
	 * @return
	 * @throws FindException
	 */
	public List<Host> selectAll() throws FindException {
		List<Host> list = hRepository.selectAll();
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
		return hRepository.existsByHostEmail(email);
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
		return hRepository.existsByBusinessNum(num);
	}

	/**
	 * HostUser 상태값을 변환한다(탈퇴기능 ready=2).
	 * 
	 * @author SR
	 * @param hostId
	 * @throws FindException
	 */
	public void outHost(String hostId) throws FindException {

		Optional<Host> optHost = hRepository.findById(hostId);
		if (optHost.isPresent()) {
			Host hostEntity = optHost.get();
			hostEntity.setStatus(2);
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
	public void updateHost(String hostId, HostDTO hostuserDTO) throws FindException {

		Optional<Host> optHost = hRepository.findById(hostId);
		if (optHost.isPresent()) {
			Host hostEntity = optHost.get();

			// logger.error("값:" + hostuserDTO.toString());
			hostEntity.setPwd(hostuserDTO.getPwd());
			hostEntity.setHostTel(hostuserDTO.getHostTel());
			hostEntity.setHostEmail(hostuserDTO.getHostEmail());
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
	public List<HostDTO.unApproveHostDTO> hostUnapproveList() throws FindException {
		List<Object[]> hList = hRepository.selectAllUnapproveHost();
		List<HostDTO.unApproveHostDTO> hListDto = new ArrayList<>();

		for (int i = 0; i < hList.size(); i++) {
			HostDTO.unApproveHostDTO hDto = new HostDTO.unApproveHostDTO();
			hDto.setHostId((String) hList.get(i)[0]);
			hDto.setBusinessNum((String) hList.get(i)[1]);
			hDto.setHostName((String) hList.get(i)[2]);
			hDto.setHostEmail((String) hList.get(i)[4]);	
			hDto.setHostTel((String) hList.get(i)[3]);
			
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
		Optional<Host> optHost = hRepository.findById(hostId);
		Host hostEntity = optHost.get();
		hostEntity.setStatus(1);
		emailService.hostOk(hostEntity.getHostEmail());
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
		Optional<Host> optH = hRepository.findById(hostId);
		if (optH.isPresent()) {
			Host entityH = optH.get();
			emailService.hostReject(entityH.getHostEmail());
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
	public HostDTO.HostLoginDTO HostLogin(String HostId, String pwd) throws FindException {

		Optional<Host> optH = hRepository.findById(HostId);
		if (optH.isPresent()) {
			Host hostuser = optH.get();
			HostDTO.HostLoginDTO hostLoginDTO = modelMapper.map(hostuser, HostDTO.HostLoginDTO.class);
			System.out.println("호스트아이디는 " + HostId + "비밀번호는" + pwd);
			if (hostLoginDTO.getPwd().equals(pwd) && !hostLoginDTO.getStatus().equals(2)) {
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
	public Host findById(String hostId) throws FindException {
		Optional<Host> optH = hRepository.findById(hostId);
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
		Host host = hRepository.hostEmailCheck(email);
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
	public HostDTO findHostId(String num) throws FindException {
		Optional<Host> optH = hRepository.findByBusinessNum(num);
		if (optH.isPresent()) {
			Host host = optH.get();
			HostDTO hostDTO = modelMapper.map(host, HostDTO.class);
			if (hostDTO.getBusinessNum().equals(num)) {
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
		Host host = hRepository.hostEmailCheck(email);
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