package com.developer.reservation.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.hostuser.entity.HostUser;
import com.developer.hostuser.repository.HostUserRepository;
import com.developer.reservation.dto.ReservationDTO;
import com.developer.reservation.entity.Reservation;
import com.developer.reservation.repository.ReservationRepository;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roominfo.entity.RoomInfo;
import com.developer.roominfo.repository.RoomInfoRepository;
import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.users.dto.UsersDTO;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository rRepository;
	private final UsersRepository uRepository;
	private final RoomInfoRepository riRepository;
	private final HostUserRepository hRepository;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 해당 스터디카페의 모든 예약내역을 출력한다(목록)
	 * 
	 * @author SR
	 * @param hostId
	 * @return
	 * @throws FindException
	 */
	public List<ReservationDTO.selectAllReservationDTO> selectAllReservation(String hostId) throws FindException {
		List<Object[]> rList = rRepository.selectAllReservation(hostId);

		List<ReservationDTO.selectAllReservationDTO> rListDto = new ArrayList<>();
        System.out.println("사이즈:" + rList.size());

			for (int i = 0; i < rList.size(); i++) {
				ReservationDTO.selectAllReservationDTO rDto = new ReservationDTO.selectAllReservationDTO();
				BigDecimal resSeq = (BigDecimal) rList.get(i)[0];
				Long convertResSeq = resSeq.longValue();
				rDto.setResSeq(convertResSeq);
				rDto.setUserId((String) rList.get(i)[2]);
				rDto.setHostId((String) rList.get(i)[4]);

				// logger.error("값"+rList.get(i)[5].getClass().getName());
				rDto.setUsingDate((Date) rList.get(i)[5]);
				rDto.setStartTime((String) rList.get(i)[6]);
				rDto.setEndTime((String) rList.get(i)[7]);
				RoomInfoDTO.selectAllReservationDTO roomDto = new RoomInfoDTO.selectAllReservationDTO();
				roomDto.setName((String) rList.get(i)[1]);

				UsersDTO.selectAllReservationDTO uDto = new UsersDTO.selectAllReservationDTO();
				uDto.setName((String) rList.get(i)[3]);

				rDto.setUsersDTO(uDto);
				rDto.setRoomInfoDTO(roomDto);

				rListDto.add(rDto);

			}
			System.out.println("dto사이즈 : " +rListDto.size());
			return rListDto;
		}
	

	/**
	 * 예약내역 1건을 출력한다.
	 * 
	 * @author SR
	 * @param resSeq
	 * @return
	 * @throws FindException
	 */
	public List<ReservationDTO.selectAllReservationDTO> infoReservation(Long resSeq) throws FindException {

		List<Object[]> rList = rRepository.selectReservation(resSeq);
		List<ReservationDTO.selectAllReservationDTO> rListDto = new ArrayList<>();

		for (int i = 0; i < rList.size(); i++) {
			ReservationDTO.selectAllReservationDTO rDto = new ReservationDTO.selectAllReservationDTO();
			BigDecimal resSeq1 = (BigDecimal) rList.get(i)[0];
			Long convertSeq = resSeq1.longValue();
			rDto.setResSeq(convertSeq);
			rDto.setUserId((String) rList.get(i)[1]);
			rDto.setUsingDate((Date) rList.get(i)[5]);
			rDto.setStartTime((String) rList.get(i)[6]);
			rDto.setEndTime((String) rList.get(i)[7]);
			rDto.setHostId((String) rList.get(i)[8]);

			UsersDTO.selectAllReservationDTO uDto = new UsersDTO.selectAllReservationDTO();
			uDto.setName((String) rList.get(i)[2]);
			uDto.setTel((String) rList.get(i)[3]);

			RoomInfoDTO.selectAllReservationDTO riDto = new RoomInfoDTO.selectAllReservationDTO();
			riDto.setName((String) rList.get(i)[4]);

			rDto.setUsersDTO(uDto);
			rDto.setRoomInfoDTO(riDto);

			rListDto.add(rDto);
		}
		return rListDto;
	}

	/**
	 * 예약내역 1건을 삭제한다.
	 * 
	 * @author SR
	 * @param resSeq
	 * @throws RemoveException
	 */
	public void deleteReservation(Long resSeq) throws RemoveException {
		Optional<Reservation> optR = rRepository.findById(resSeq);
		if (optR.isPresent()) {
			Reservation entityR = optR.get();
			rRepository.delete(entityR);
		} else {
			throw new RemoveException("잘못된 예약번호입니다.");
		}
	}

	/**
	 * [스터디카페 예약페이지] 예약정보를 예약테이블에 넣어 예약내역에 insert
	 * 
	 * @author ds
	 * @throws 전체정보 출력시 FindException예외발생한다
	 */
	public void insertRv(ReservationDTO.insertRvDTO rvDTO, String logined) throws AddException {

		Reservation r = new Reservation();
		Optional<Users> optU = uRepository.findById(logined);
		Users u = optU.get();
		r.setUsers(u);
		Optional<HostUser> optH = hRepository.findById(rvDTO.getHostId());
		// r.setUserId(u);

		HostUser hu = optH.get();
		r.setHostUser(hu);
		Optional<RoomInfo> optR = riRepository.findById(rvDTO.getRoomSeq());
		RoomInfo ri = optR.get();

		r.setUsingDate(rvDTO.getUsingDate());
		r.setRoominfo(ri);
		r.setStartTime(rvDTO.getStartTime());
		r.setEndTime(rvDTO.getEndTime());
		rRepository.save(r);
	}

	/**
	 * [호스트마이페이지] 호스트가 예약하는 기능(예약막기용)
	 * 
	 * @author SR
	 * @param rvDTO
	 * @param hostId
	 * @throws AddException
	 */
	public void insertHostRv(ReservationDTO.insertRvDTO rvDTO, String hostId) throws AddException {

		Reservation r = new Reservation();
		Optional<HostUser> optH = hRepository.findById(hostId);
		HostUser h = optH.get();
		r.setHostUser(h);

		Optional<RoomInfo> optR = riRepository.findById(rvDTO.getRoomSeq());
		RoomInfo ri = optR.get();
		r.setRoominfo(ri);

		r.setUsingDate(rvDTO.getUsingDate());
		r.setStartTime(rvDTO.getStartTime());
		r.setEndTime(rvDTO.getEndTime());
		// rvDTO.setHostId(h.getHostId());
		rRepository.save(r);

	}

	/**
	 * [스터디카페 예약페이지] 룸 시퀀스와 예약일을 받아 이미 예약된 예약정보에 대한 리스트를 출력한다
	 * 
	 * @author ds
	 * @param roomSeq   스터디룸 시퀀스
	 * @param usingDate 예약일
	 * @return List<ReservationDTO.selectAllByUsingDateDTO> 특정일자의 해당 스터디룸 예약정보
	 * @throws 전체정보 출력시 FindException, ParseException예외발생한다
	 */
	public List<ReservationDTO.selectAllByUsingDateDTO> selectAllByUsingDate(Long roomSeq, String usingDate)
			throws FindException, ParseException {

		List<Object[]> list = rRepository.findAllByUsingDate(roomSeq, usingDate);
		List<ReservationDTO.selectAllByUsingDateDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ReservationDTO.selectAllByUsingDateDTO sDTO = new ReservationDTO.selectAllByUsingDateDTO();
			BigDecimal room_seq = (BigDecimal) list.get(i)[0];
			Long resultRoom_seq = room_seq.longValue();
			sDTO.setRoomSeq(resultRoom_seq);
			sDTO.setStartTime((String) list.get(i)[1]);
			sDTO.setEndTime((String) list.get(i)[2]);
			sDTO.setUsingDate((Date) list.get(i)[3]);

			RoomInfoDTO.RoomInfoPriceDTO riDTO = new RoomInfoDTO.RoomInfoPriceDTO();
			StudyroomDTO.StudyroomTimeDTO srDTO = new StudyroomDTO.StudyroomTimeDTO();
			srDTO.setOpenTime((String) list.get(i)[5]);
			srDTO.setEndTime((String) list.get(i)[6]);
			riDTO.setPrice(Integer.parseInt(String.valueOf(list.get(i)[4])));
			riDTO.setStudyroomTimeDTO(srDTO);

			sDTO.setRoomInfoPriceDTO(riDTO);
			dto.add(sDTO);

		}
		return dto;
	}

	/**
	 * [마이페이지 - 스터디카페 예약내역 페이지] 아이디값을 받아와 전체 예약내역을 최신순으로 출력한다
	 * 
	 * @author ds
	 * @param userId 유저 아이디
	 * @return List<ReservationDTO.selectMyResHistoryDTO> 유저의 전체 예약 내역(최신순)
	 * @throws 전체정보 출력시 FindException예외발생한다
	 */
	public List<ReservationDTO.selectMyResHistoryDTO> selectMyResHistory(String logined) throws FindException {
		List<Object[]> list = rRepository.findByUserId(logined);
		List<ReservationDTO.selectMyResHistoryDTO> rDTO = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ReservationDTO.selectMyResHistoryDTO dto = new ReservationDTO.selectMyResHistoryDTO();
			BigDecimal res_seq = (BigDecimal) list.get(i)[0];
			Long resultRes_seq = res_seq.longValue();
			dto.setResSeq(resultRes_seq);
			dto.setStartTime((String) list.get(i)[4]);
			dto.setEndTime((String) list.get(i)[5]);
			dto.setUsingDate((Date) list.get(i)[3]);
			RoomInfoDTO.RoomInfoNameDTO riDTO = new RoomInfoDTO.RoomInfoNameDTO();
			StudyroomDTO.StudyroomNameDTO srDTO = new StudyroomDTO.StudyroomNameDTO();
			srDTO.setName((String) list.get(i)[1]);

			riDTO.setName((String) list.get(i)[2]);
			riDTO.setStudyroomNameDTO(srDTO);
			dto.setRoomInfoNameDTO(riDTO);
			rDTO.add(dto);
		}
		return rDTO;
	}

	/**
	 * [마이페이지 스터디카페 후기페이지] 아이디값으로 후기를 작성하지 않은 예약리스트를 출력한다
	 * 
	 * @author ds
	 * @param userId
	 * @return List<ReservationDTO.selectRmRvDTO> 유저의 작성한 이용후기 리스트
	 */
	public List<ReservationDTO.selectRmRvDTO> selectMyReqRmRv(String logined) throws FindException {
		List<Object[]> rlist = rRepository.selectReqRmRv(logined);
		List<ReservationDTO.selectRmRvDTO> dto = new ArrayList<>();
		for (int i = 0; i < rlist.size(); i++) {

			// 기본 reservation
			ReservationDTO.selectRmRvDTO rDTO = new ReservationDTO.selectRmRvDTO();
			BigDecimal res_seq = (BigDecimal) rlist.get(i)[0];
			Long resultRes_seq = res_seq.longValue();
			rDTO.setResSeq(resultRes_seq);
			rDTO.setStartTime((String) rlist.get(i)[4]);
			rDTO.setEndTime((String) rlist.get(i)[5]);
			rDTO.setUsingDate((Date) rlist.get(i)[3]);

			RoomInfoDTO.RoomInfoNameDTO riDTO = new RoomInfoDTO.RoomInfoNameDTO();
			StudyroomDTO.StudyroomNameDTO sDTO = new StudyroomDTO.StudyroomNameDTO();
			sDTO.setName((String) rlist.get(i)[1]);
			riDTO.setName((String) rlist.get(i)[2]);
			riDTO.setStudyroomNameDTO(sDTO);
			rDTO.setRoomInfoNameDTO(riDTO);
			dto.add(rDTO);
		}
		return dto;
	}
}