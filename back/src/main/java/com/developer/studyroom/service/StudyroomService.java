package com.developer.studyroom.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.developer.admin.AdminDTO;
import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.favoritesstudyroom.dto.FavoritesStudyroomDTO;
import com.developer.favoritesstudyroom.entity.FavoritesStudyroom;
import com.developer.hostuser.dto.HostUserDTO;
import com.developer.hostuser.entity.HostUser;
import com.developer.hostuser.repository.HostUserRepository;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.studyroom.entity.Studyroom;
import com.developer.studyroom.repository.StudyroomRepository;
import com.developer.users.dto.UsersDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyroomService {

	private final StudyroomRepository sRepository;
	private final HostUserRepository hRepository;
	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 관리자 스터디카페 목록 출력
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 */
	public List<StudyroomDTO.getAllStudyroomDTO> getAllStudyroom() throws FindException {
		List<Object[]> Slist = sRepository.getAllStudyroom();
		List<StudyroomDTO.getAllStudyroomDTO> dtoList = new ArrayList<>();
		for (int i = 0; i < Slist.size(); i++) {
			StudyroomDTO.getAllStudyroomDTO srDTO = new StudyroomDTO.getAllStudyroomDTO();
			BigDecimal sr_seq = (BigDecimal) Slist.get(i)[0];
			Long resultsr_seq = sr_seq.longValue();
			srDTO.setSrSeq(resultsr_seq);
			srDTO.setEndTime((String) Slist.get(i)[5]);
			srDTO.setOpenTime((String) Slist.get(i)[4]);
			srDTO.setName((String) Slist.get(i)[1]);
			srDTO.setAddr((String) Slist.get(i)[2]);
			HostUserDTO.getAllHostUserDTO hDTO = new HostUserDTO.getAllHostUserDTO();
			hDTO.setHostId((String) Slist.get(i)[8]);
			srDTO.setHostUser(hDTO);
			dtoList.add(srDTO);
		}
		return dtoList;
	}

	/**
	 * 관리자 스터디카페 상세페이지 -> 오류발생시 엔티티 @JsonIgnore 지우고 반환타입 dto로 만들어서 ..!
	 * 
	 * @author choigeunhyeong
	 * @param srSeq
	 * @return
	 * @throws FindException
	 */
	public Studyroom detailStudyroom(Long srSeq) throws FindException {
		Optional<Studyroom> optS = sRepository.findById(srSeq);
		Studyroom s = optS.get();
		return s;
	}

	/**
	 * Studyroom 객체 1개를 출력한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @return StudyroomDTO
	 * @throws FindException
	 */
	public StudyroomDTO selectStudyroom(long srSeq) throws FindException {
		Optional<Studyroom> optStudyroom = sRepository.findById(srSeq);
		if (optStudyroom.isPresent()) {
			Studyroom StudyroomEntity = optStudyroom.get();
			StudyroomDTO studyroomDTO = modelMapper.map(StudyroomEntity, StudyroomDTO.class);
			return studyroomDTO;
		} else {
			throw new FindException("해당 스터디카페가 존재하지 않습니다.");
		}
	}

	/**
	 * 스터디카페의 영업을 시작한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @throws FindException
	 */
	public void openOc(Long srSeq) throws FindException {
		StudyroomDTO studyroomDTO = this.selectStudyroom(srSeq);
		studyroomDTO.setOc(0);
		Studyroom studyroomEntity = modelMapper.map(studyroomDTO, Studyroom.class);
		sRepository.save(studyroomEntity);
	}

	/**
	 * 스터디카페의 영업을 마감한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @throws FindException
	 */
	public void closeOc(Long srSeq) throws FindException {
		StudyroomDTO studyroomDTO = this.selectStudyroom(srSeq);
		studyroomDTO.setOc(1);
		Studyroom studyroomEntity = modelMapper.map(studyroomDTO, Studyroom.class);
		sRepository.save(studyroomEntity);
	}

	/**
	 * [호스트메인페이지] 스터디룸 + 호스트정보 출력
	 * 
	 * @author SR
	 * @param hostId
	 * @return StudyroomDTO.getHostAndStudyroomDTO
	 * @throws FindException
	 */
	public StudyroomDTO.getHostAndStudyroomDTO getHostAndStudyroom(String hostId) throws FindException {
		Optional<Studyroom> optS = sRepository.getHostAndStudyroom(hostId);

		StudyroomDTO.getHostAndStudyroomDTO sDto = new StudyroomDTO.getHostAndStudyroomDTO();
		sDto.setSrSeq(optS.get().getSrSeq());
		sDto.setName(optS.get().getName());
		sDto.setAddr(optS.get().getAddr());
		sDto.setInfo(optS.get().getInfo());
		sDto.setOpenTime(optS.get().getOpenTime());
		sDto.setEndTime(optS.get().getEndTime());
		sDto.setImgPath(optS.get().getImgPath());
		sDto.setOc(optS.get().getOc());

		HostUserDTO.getHostDTO hDto = new HostUserDTO.getHostDTO();
		hDto.setHostId(optS.get().getHostUser().getHostId());
		hDto.setPwd(optS.get().getHostUser().getPwd());
		hDto.setNum(optS.get().getHostUser().getNum());
		hDto.setReady(optS.get().getHostUser().getReady());
		hDto.setName(optS.get().getHostUser().getName());
		hDto.setTel(optS.get().getHostUser().getTel());
		hDto.setEmail(optS.get().getHostUser().getEmail());

		sDto.setHostUserDTO(hDto);
		return sDto;

	}

	/**
	 * 스터디카페를 추가한다.
	 * 
	 * @author SR
	 * @param studyroomDTO
	 * @throws AddException
	 */
	public void insertCafe(StudyroomDTO studyroomDTO, String hostId) throws AddException {
		Optional<HostUser> hostUser = hRepository.findById(hostId);
		HostUser hostUserEntity = hostUser.get();
		studyroomDTO.setHostUser(hostUserEntity);
		Studyroom studyroomEntity = modelMapper.map(studyroomDTO, Studyroom.class);
		sRepository.save(studyroomEntity);
	}

	/**
	 * 스터디카페 정보를 수정한다.
	 * 
	 * @author SR
	 * @param srSeq
	 * @param studyroomDTO
	 * @throws FindException
	 */
	public void updateCafe(long srSeq, StudyroomDTO studyroomDTO) throws FindException {

		Optional<Studyroom> optCafe = sRepository.findById(srSeq);
		if (optCafe.isPresent()) {
			Studyroom cafeEntity = optCafe.get();
			cafeEntity.setInfo(studyroomDTO.getInfo());
			cafeEntity.setImgPath(studyroomDTO.getImgPath());
			cafeEntity.setOpenTime(studyroomDTO.getOpenTime());
			cafeEntity.setEndTime(studyroomDTO.getEndTime());
			sRepository.save(cafeEntity);
		} else {
			throw new FindException("해당 스터디카페가 존재하지 않습니다.");
		}
	}

	/**
	 * [스터디카페 메인] 주소(1) 또는 스터디카페명(2) 및 인원 수와 정렬로 스터디카페리스트를 검색한다
	 * 
	 * @author ds
	 * @param srNameAddrName, searchBy, person, orderBy
	 * @throws 전체정보 출력시 FindException예외발생한다
	 */
	@PersistenceContext
	private EntityManager entityManager;

	public List<StudyroomDTO.StudyroomSelectBySearchDTO> selectBySearch(String srNameAddrName, Integer searchBy,
			Integer person, Integer orderBy) throws FindException {
		String jpql1 = "	SELECT S.NAME, S.ADDR, S.IMG_PATH, MAX(R.PERSON) AS PERSON,\r\n"
				+ "		MIN(R.PRICE) AS PRICE, COUNT(distinct(F.USER_ID)) AS FAV_CNT, S.SR_SEQ\r\n"
				+ "		FROM STUDYROOM S\r\n" + "		join\r\n" + "		ROOM_INFO R\r\n"
				+ "		ON s.sr_seq = r.sr_seq\r\n" + "		left outer join\r\n" + "		FAVORITES_STUDYROOM F\r\n"
				+ "		ON F.SR_SEQ = S.SR_SEQ\r\n" + "		where ";

		String choose1 = "";

		if (searchBy == 1) {
			choose1 = "S.ADDR LIKE '%" + srNameAddrName + "%'";
			System.out.println(choose1);
		} else if (searchBy == 2) {
			choose1 = "S.NAME LIKE '%" + srNameAddrName + "%'";
		}
		jpql1 += choose1;

		String jpql2 = "		And\r\n" + "		R.person >= ";

		jpql2 += person;

		String jpql3 = "   GROUP BY S.NAME , S.ADDR , S.IMG_PATH, S.SR_SEQ\r\n" + "	ORDER BY ";
		String choose2 = "";
		if (orderBy == 1) {
			choose2 = "PRICE ASC";

		} else if (orderBy == 2) {
			choose2 = "FAV_CNT DESC";
		}
		jpql3 += choose2;

		Query query = entityManager.createNativeQuery(jpql1 + jpql2 + jpql3);

		List<Object[]> list = query.getResultList();
		System.out.println("listSize=" + list.size());
		System.out.println("list.get(0).name=" + list.get(0)[0]);
		List<StudyroomDTO.StudyroomSelectBySearchDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			StudyroomDTO.StudyroomSelectBySearchDTO sDTO = new StudyroomDTO.StudyroomSelectBySearchDTO();
			sDTO.setName((String) list.get(i)[0]);
			sDTO.setAddr((String) list.get(i)[1]);
			sDTO.setImgPath((String) list.get(i)[2]);
			BigDecimal srSeq = (BigDecimal) list.get(i)[6];
			long StudyroomSeq = srSeq.longValue();
			sDTO.setSrSeq(StudyroomSeq);
			RoomInfoDTO.RoomInfoPriceAndPersonDTO ripDTO = new RoomInfoDTO.RoomInfoPriceAndPersonDTO();
			ripDTO.setPrice(Integer.parseInt(String.valueOf(list.get(i)[4])));
			ripDTO.setPerson(Integer.parseInt(String.valueOf(list.get(i)[3])));
			sDTO.setRoomInfoPriceAndPersonDTO(ripDTO);
			FavoritesStudyroomDTO.favoritesStudyroomUserIdDTO favStudyroomDTO = new FavoritesStudyroomDTO.favoritesStudyroomUserIdDTO();
			UsersDTO.UserIdDTO uIDTO = new UsersDTO.UserIdDTO();
			uIDTO.setFvCNT(Integer.parseInt(String.valueOf(list.get(i)[5])));
			favStudyroomDTO.setUserIdDTO(uIDTO);
			sDTO.setFavoritesStudyroomUserIdDTO(favStudyroomDTO);
			dto.add(sDTO);
			// 순서 name, addr, imgpath, person, price, fav_cnt
		}
		return dto;
	}

	public List<StudyroomDTO.StudyroomSelectBySearchDTO> selectListALL() throws FindException {
		List<Object[]> list = sRepository.getListAll();
		List<StudyroomDTO.StudyroomSelectBySearchDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			StudyroomDTO.StudyroomSelectBySearchDTO sDTO = new StudyroomDTO.StudyroomSelectBySearchDTO();
			sDTO.setName((String) list.get(i)[0]);
			sDTO.setAddr((String) list.get(i)[1]);
			sDTO.setImgPath((String) list.get(i)[2]);
			BigDecimal srSeq = (BigDecimal) list.get(i)[6];
			long StudyroomSeq = srSeq.longValue();
			sDTO.setSrSeq(StudyroomSeq);
			RoomInfoDTO.RoomInfoPriceAndPersonDTO ripDTO = new RoomInfoDTO.RoomInfoPriceAndPersonDTO();
			ripDTO.setPrice(Integer.parseInt(String.valueOf(list.get(i)[4])));
			ripDTO.setPerson(Integer.parseInt(String.valueOf(list.get(i)[3])));
			sDTO.setRoomInfoPriceAndPersonDTO(ripDTO);
			FavoritesStudyroomDTO.favoritesStudyroomUserIdDTO favStudyroomDTO = new FavoritesStudyroomDTO.favoritesStudyroomUserIdDTO();
			UsersDTO.UserIdDTO uIDTO = new UsersDTO.UserIdDTO();
			uIDTO.setFvCNT(Integer.parseInt(String.valueOf(list.get(i)[5])));
			favStudyroomDTO.setUserIdDTO(uIDTO);
			sDTO.setFavoritesStudyroomUserIdDTO(favStudyroomDTO);
			dto.add(sDTO);
			// 순서 name, addr, imgpath, person, price, fav_cnt
		}
		return dto;
	}

	/**
	 * [관리자 대쉬보드] 스터디카페 최신 순 5개 리스트
	 * 
	 * @author DS
	 * @return 스터디카페 최신 순 5개 리스트
	 * @throws FindException
	 */
	public List<StudyroomDTO.StudyroomList5DTO> selectList5() throws FindException {
		List<Object[]> list = sRepository.getList5();
		List<StudyroomDTO.StudyroomList5DTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			AdminDTO.getList5DTO adDTO = new AdminDTO.getList5DTO();
			StudyroomDTO.StudyroomList5DTO slDTO = new StudyroomDTO.StudyroomList5DTO();
			BigDecimal sr_seq = (BigDecimal) list.get(i)[0];
			Long resultSrSeq = sr_seq.longValue();
			slDTO.setSrSeq(resultSrSeq);
			slDTO.setName((String) list.get(i)[1]);
			HostUserDTO.HostIdDTO hiDTO = new HostUserDTO.HostIdDTO();
			hiDTO.setHostId((String) list.get(i)[2]);
			slDTO.setHostIdDTO(hiDTO);

			dto.add(slDTO);
		}
		return dto;
	}

	/**
	 * 스터디카페 1개의 상세정보 + 해당 카페와 연관된 즐겨찾기 관련 정보 출력.
	 * 
	 * @author DS
	 * @param srSeq
	 * @return StudyroomDTO
	 * @throws FindException
	 */
	public StudyroomDTO.StudyroomAndFavStuyroomInfoDTO getStudyroomDetail(Long srSeq) throws FindException {
		StudyroomDTO.StudyroomAndFavStuyroomInfoDTO sDTO = new StudyroomDTO.StudyroomAndFavStuyroomInfoDTO();
		
		Optional<Studyroom> optS = sRepository.findById(srSeq);

		Studyroom s=optS.get();
		sDTO.setSrSeq(s.getSrSeq());
		sDTO.setOpenTime(s.getOpenTime());
		sDTO.setEndTime(s.getEndTime());
		sDTO.setInfo(s.getInfo());
		sDTO.setImgPath(s.getImgPath());
		sDTO.setAddr(s.getAddr());
		sDTO.setName(s.getName());
		sDTO.setOc(s.getOc());
		
		List<FavoritesStudyroom>list = s.getFavoritesStudyroom();
		List<FavoritesStudyroomDTO.favStudyroominfoDTO> dtoList = new ArrayList<>();

		for(FavoritesStudyroom r: list) {
			FavoritesStudyroomDTO.favStudyroominfoDTO fDTO = new FavoritesStudyroomDTO.favStudyroominfoDTO();
			fDTO.setFavSeq(r.getFavSeq());
			fDTO.setUserId(r.getUsers().getUserId());
			dtoList.add(fDTO);
		}
		sDTO.setFavoritesStudyroomDTO(dtoList);
		
		return sDTO;
	}

	/**
	 * 스터디카페 예약일 조회시 예약 내역이 없을 경우 예약을 위한 룸정보(오픈시간, 마감시간, 가격) 출력
	 * 
	 * @author DS
	 * @param roomSeq
	 * @return StudyroomDTO.StudyroomAndRoomInfoDTO
	 * @throws FindException
	 */
	public List<StudyroomDTO.StudyroomAndRoomInfoDTO> getStudyroomAndRoomInfo(long roomSeq) throws FindException {
		List<Object[]> list = sRepository.getInfoOne(roomSeq);
		List<StudyroomDTO.StudyroomAndRoomInfoDTO> dto = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			StudyroomDTO.StudyroomAndRoomInfoDTO sDTO = new StudyroomDTO.StudyroomAndRoomInfoDTO();
			RoomInfoDTO.RoomInfoPriceOnlyDTO rDTO = new RoomInfoDTO.RoomInfoPriceOnlyDTO();
			sDTO.setOpenTime((String) list.get(i)[1]);
			sDTO.setEndTime((String) list.get(i)[2]);
			rDTO.setPrice(Integer.parseInt(String.valueOf(list.get(i)[0])));
			sDTO.setRoomInfoPriceDTO(rDTO);
			dto.add(sDTO);

		}
		return dto;
	}

}