package com.developer.roominfo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.host.dto.HostDTO;
import com.developer.reservation.dto.ReservationDTO;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.roominfo.entity.RoomInfo;
import com.developer.roominfo.repository.RoomInfoRepository;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;
import com.developer.studycafe.repository.StudycafeRepository;
import com.developer.users.dto.UsersDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomInfoService {

	private final RoomInfoRepository riRepository;
	private final StudycafeRepository sRepository;

	private Logger logger = LoggerFactory.getLogger(getClass());
	ModelMapper modelMapper = new ModelMapper();

	/**
	 * 룸 1개정보를 출력한다.
	 * 
	 * @author SR
	 * @param roomSeq
	 * @return
	 * @throws FindException
	 */
	public RoomInfoDTO selectRoom(Long roomSeq) throws FindException {
		Optional<RoomInfo> optRoom = riRepository.findById(roomSeq);
		if (optRoom.isPresent()) {
			RoomInfo roomEntity = optRoom.get();
			RoomInfoDTO roomDTO = modelMapper.map(roomEntity, RoomInfoDTO.class);
			return roomDTO;
		} else {
			throw new FindException("해당 방이 존재하지 않습니다.");
		}
	}

	/**
	 * 룸 1개정보를 출력한다(엔티티로)
	 * 
	 * @author SR
	 * @param roomSeq
	 * @return
	 * @throws FindException
	 */
	public RoomInfo selectRoomEntity(Long roomSeq) throws FindException {
		Optional<RoomInfo> optRi = riRepository.findById(roomSeq);
		if (optRi.isPresent()) {
			RoomInfo ri = optRi.get();
			return ri;
		} else {
			throw new FindException("해당 방이 존재하지 않습니다.");
		}
	}

	/**
	 * 룸을 추가한다.
	 * 
	 * @author SR
	 * @param roomInfoDTO
	 * @param srSeq
	 * @throws AddException
	 */
	public void insertRoom(RoomInfoDTO roomInfoDTO, Long srSeq) {
		Optional<Studycafe> optCafe = sRepository.findById(srSeq);
		Studycafe cafeEntity = optCafe.get();
		roomInfoDTO.setStudycafe(cafeEntity);
		RoomInfo roomEntity = modelMapper.map(roomInfoDTO, RoomInfo.class);
		riRepository.save(roomEntity);
	}

	/**
	 * 룸정보를 수정한다.
	 * 
	 * @author SR
	 * @param roomSeq
	 * @param roomInfoDTO
	 * @return
	 * @throws FindException
	 */
	public void updateRoom(Long roomSeq, RoomInfoDTO roomInfoDTO) throws FindException {
		Optional<RoomInfo> optRoom = riRepository.findById(roomSeq);
		if (optRoom.isPresent()) {
			RoomInfo roomEntity = optRoom.get();
			roomEntity.setRoomName(roomInfoDTO.getRoomName());
			roomEntity.setInfo(roomInfoDTO.getInfo());
			roomEntity.setRoomImg(roomInfoDTO.getRoomImg());
			roomEntity.setPerson(roomInfoDTO.getPerson());
			roomEntity.setPrice(roomInfoDTO.getPrice());
			riRepository.save(roomEntity);
		} else {
			throw new FindException("해당 방이 존재하지 않습니다.");
		}
	}

	/**
	 * 룸 1개를 삭제한다(status = 1)
	 * 
	 * @author SR
	 * @param roomSeq
	 * @return
	 * @throws FindException
	 */
	public void deleteRoom(Long roomSeq) throws FindException {
		RoomInfoDTO roomInfoDTO = this.selectRoom(roomSeq);
		roomInfoDTO.setRoomStatus(1);
		RoomInfo roomEntity = modelMapper.map(roomInfoDTO, RoomInfo.class);
		riRepository.save(roomEntity);
	}

	/**
	 * 방 목록을 출력한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @return
	 * @throws FindException
	 */
	public List<RoomInfoDTO.selectAllRoomDTO> selectAllRoom(String hostId) throws FindException {
		List<Object[]> rList = riRepository.selectAllRoom(hostId);

		List<RoomInfoDTO.selectAllRoomDTO> rListDto = new ArrayList<>();
		for (int i = 0; i < rList.size(); i++) {
			RoomInfoDTO.selectAllRoomDTO rDto = new RoomInfoDTO.selectAllRoomDTO();
			BigDecimal roomSeq = (BigDecimal) rList.get(i)[0];
			Long convertRoomSeq = roomSeq.longValue();
			rDto.setRoomSeq(convertRoomSeq);
			rDto.setRoomName((String) rList.get(i)[1]);
			rDto.setInfo((String) rList.get(i)[2]);
			rDto.setRoomImg((String) rList.get(i)[3]);
			BigDecimal person = (BigDecimal) rList.get(i)[4];
			int convertPerson = person.intValue();
			rDto.setPerson(convertPerson);
			BigDecimal price = (BigDecimal) rList.get(i)[5];
			int convertPrice = price.intValue();
			rDto.setPrice(convertPrice);
			StudycafeDTO.StudycafeTimeDTO sDTO = new StudycafeDTO.StudycafeTimeDTO();
			sDTO.setOpenTime((String) rList.get(i)[6]);
			sDTO.setEndTime((String) rList.get(i)[7]);
			rDto.setStudycafeTimeDTO(sDTO);

			rListDto.add(rDto);
		}
		return rListDto;

	}

	/**
	 * 스터디룸번호로 해당하는 예약내역 출력
	 * 
	 * @author choigeunhyeong
	 * @param srSeq
	 * @return
	 * @throws FindException
	 */
	public List<RoomInfoDTO.getReservationDTO> getReservation(Long srSeq) throws FindException {
		List<Object[]> RList = riRepository.getReservation(srSeq);
		List<RoomInfoDTO.getReservationDTO> dto = new ArrayList<>();
		for (int i = 0; i < RList.size(); i++) {
			RoomInfoDTO.getReservationDTO roomInfoDTO = new RoomInfoDTO.getReservationDTO();
			ReservationDTO.getReservationDTO resDTO = new ReservationDTO.getReservationDTO();
			BigDecimal res_seq = (BigDecimal) RList.get(i)[0];
			Long resultres_seq = res_seq.longValue();
			resDTO.setResSeq(resultres_seq);
			resDTO.setUsingDate((Date) RList.get(i)[4]);
			resDTO.setStartTime((String) RList.get(i)[5]);
			resDTO.setEndTime((String) RList.get(i)[6]);
			UsersDTO.UsersNameDTO uDTO = new UsersDTO.UsersNameDTO();
			uDTO.setUserId((String) RList.get(i)[2]);
			uDTO.setNickname((String) RList.get(i)[3]);
			roomInfoDTO.setRoomName((String) RList.get(i)[1]);
			roomInfoDTO.setReservation(resDTO);
			roomInfoDTO.setUsers(uDTO);
			dto.add(roomInfoDTO);
		}
		return dto;
	}

	/**
	 * [스터디카페 정보 출력페이지] 스터디룸 시퀀스를 받아 스터디룸의 전체정보를 출력한다
	 * 
	 * @author ds
	 * @param srSeq 스터디카페 시퀀스(장소번호)
	 * @return 특정스터디카페 전체정보들(방여러개)
	 * @throws 전체정보 출력시 FindException예외발생한다
	 */
	public List<RoomInfoDTO.RoomInfoRoomDetailListDTO> selectAll(Long srSeq) throws FindException {
		List<Object[]> list = riRepository.selectAll(srSeq);
		List<RoomInfoDTO.RoomInfoRoomDetailListDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			RoomInfoDTO.RoomInfoRoomDetailListDTO riDTO = new RoomInfoDTO.RoomInfoRoomDetailListDTO();
			StudycafeDTO.StudycafeHostIdDTO shDTO = new StudycafeDTO.StudycafeHostIdDTO();
			HostDTO.HostIdDTO hhDTO = new HostDTO.HostIdDTO();
			BigDecimal room_seq = (BigDecimal) list.get(i)[0];
			Long resultRoomSeq = room_seq.longValue();
			riDTO.setRoomSeq(resultRoomSeq);
			riDTO.setRoomStatus(Integer.parseInt(String.valueOf(list.get(i)[6])));
			riDTO.setRoomImg((String) list.get(i)[1]);
			riDTO.setInfo((String) list.get(i)[2]);
			riDTO.setRoomName((String) list.get(i)[3]);
			riDTO.setPerson(Integer.parseInt(String.valueOf(list.get(i)[4])));
			riDTO.setPrice(Integer.parseInt(String.valueOf(list.get(i)[5])));

			riDTO.setRoomStatus(Integer.parseInt(String.valueOf(list.get(i)[6])));
			hhDTO.setHostId((String) list.get(i)[8]);

			shDTO.setHostIdDTO(hhDTO);
			riDTO.setStudycafeDTO(shDTO);
			dto.add(riDTO);
		}
		return dto;
	}
}