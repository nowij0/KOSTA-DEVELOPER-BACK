package com.developer.studycafe.service;

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
import com.developer.favoritesstudycafe.dto.FavoritesStudycafeDTO;
import com.developer.favoritesstudycafe.entity.FavoritesStudycafe;
import com.developer.host.dto.HostDTO;
import com.developer.host.entity.Host;
import com.developer.host.repository.HostRepository;
import com.developer.roominfo.dto.RoomInfoDTO;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;
import com.developer.studycafe.repository.StudycafeRepository;
import com.developer.users.dto.UsersDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudycafeService {

	private final StudycafeRepository sRepository;
	private final HostRepository hRepository;
	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 관리자 스터디카페 목록 출력
	 * 
	 * @author choigeunhyeong
	 * @return
	 * @throws FindException
	 */
	public List<StudycafeDTO.getAllStudycafeDTO> getAllStudyroom() throws FindException {
		List<Object[]> Slist = sRepository.getAllStudyroom();
		List<StudycafeDTO.getAllStudycafeDTO> dtoList = new ArrayList<>();
		for (int i = 0; i < Slist.size(); i++) {
			StudycafeDTO.getAllStudycafeDTO srDTO = new StudycafeDTO.getAllStudycafeDTO();
			BigDecimal sr_seq = (BigDecimal) Slist.get(i)[0];
			Long resultsr_seq = sr_seq.longValue();
			srDTO.setCafeSeq(resultsr_seq);
			srDTO.setEndTime((String) Slist.get(i)[5]);
			srDTO.setOpenTime((String) Slist.get(i)[4]);
			srDTO.setCafeName((String) Slist.get(i)[1]);		
			srDTO.setAddr((String) Slist.get(i)[2]);
			HostDTO.getAllHostDTO hDTO = new HostDTO.getAllHostDTO();
			hDTO.setHostId((String) Slist.get(i)[8]);
			srDTO.setHost(hDTO);
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
	public Studycafe detailStudyroom(Long srSeq) throws FindException {
		Optional<Studycafe> optS = sRepository.findById(srSeq);
		Studycafe s = optS.get();
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
	public StudycafeDTO selectStudyroom(long srSeq) throws FindException {
		Optional<Studycafe> optStudyroom = sRepository.findById(srSeq);
		if (optStudyroom.isPresent()) {
			Studycafe StudyroomEntity = optStudyroom.get();
			StudycafeDTO studyroomDTO = modelMapper.map(StudyroomEntity, StudycafeDTO.class);
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
		StudycafeDTO studyroomDTO = this.selectStudyroom(srSeq);
		studyroomDTO.setOpenStatus(0);
		Studycafe studyroomEntity = modelMapper.map(studyroomDTO, Studycafe.class);
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
		StudycafeDTO studyroomDTO = this.selectStudyroom(srSeq);
		studyroomDTO.setOpenStatus(1);
		Studycafe studyroomEntity = modelMapper.map(studyroomDTO, Studycafe.class);
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
	public StudycafeDTO.getHostAndStudycafeDTO getHostAndStudycafe(String hostId) throws FindException {
		Optional<Studycafe> optS = sRepository.getHostAndStudyroom(hostId);

		StudycafeDTO.getHostAndStudycafeDTO sDto = new StudycafeDTO.getHostAndStudycafeDTO();
		sDto.setCafeSeq(optS.get().getCafeSeq());
		sDto.setCafeName(optS.get().getCafeName());
		sDto.setAddr(optS.get().getAddr());
		sDto.setInfo(optS.get().getInfo());
		sDto.setOpenTime(optS.get().getOpenTime());
		sDto.setEndTime(optS.get().getEndTime());
		sDto.setCafeImg(optS.get().getCafeImg());
		sDto.setOpenStatus(optS.get().getOpenStatus()	);
		
		HostDTO.getHostDTO hDto = new HostDTO.getHostDTO();
		hDto.setHostId(optS.get().getHost().getHostId());
		hDto.setPwd(optS.get().getHost().getPwd());
		hDto.setBusinessNum(optS.get().getHost().getBusinessNum());	
		hDto.setStatus(optS.get().getHost().getStatus());
		hDto.setHostName(optS.get().getHost().getHostName());
		hDto.setHostTel(optS.get().getHost().getHostTel());
		hDto.setHostEmail(optS.get().getHost().getHostEmail());
		
		sDto.setHostDTO(hDto);
		return sDto;

	}

	/**
	 * 스터디카페를 추가한다.
	 * 
	 * @author SR
	 * @param studyroomDTO
	 * @throws AddException
	 */
	public void insertCafe(StudycafeDTO studyroomDTO, String hostId) throws AddException {
		Optional<Host> hostUser = hRepository.findById(hostId);
		Host hostUserEntity = hostUser.get();
		studyroomDTO.setHost(hostUserEntity);
		Studycafe studyroomEntity = modelMapper.map(studyroomDTO, Studycafe.class);
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
	public void updateCafe(long srSeq, StudycafeDTO studyroomDTO) throws FindException {

		Optional<Studycafe> optCafe = sRepository.findById(srSeq);
		if (optCafe.isPresent()) {
			Studycafe cafeEntity = optCafe.get();
			cafeEntity.setInfo(studyroomDTO.getInfo());
			cafeEntity.setCafeImg(studyroomDTO.getCafeImg());
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

	public List<StudycafeDTO.StudycafeSelectBySearchDTO> selectBySearch(String srNameAddrName, Integer searchBy,
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
		List<StudycafeDTO.StudycafeSelectBySearchDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			StudycafeDTO.StudycafeSelectBySearchDTO sDTO = new StudycafeDTO.StudycafeSelectBySearchDTO();
			sDTO.setCafeName((String) list.get(i)[0]);		
			sDTO.setAddr((String) list.get(i)[1]);
			sDTO.setCafeImg((String) list.get(i)[2]);
			BigDecimal cafeSeq = (BigDecimal) list.get(i)[6];
			long StudycafeSeq = cafeSeq.longValue();
			sDTO.setCafeSeq(StudycafeSeq);
			RoomInfoDTO.RoomInfoPriceAndPersonDTO ripDTO = new RoomInfoDTO.RoomInfoPriceAndPersonDTO();
			ripDTO.setPrice(Integer.parseInt(String.valueOf(list.get(i)[4])));
			ripDTO.setPerson(Integer.parseInt(String.valueOf(list.get(i)[3])));
			sDTO.setRoomInfoPriceAndPersonDTO(ripDTO);
			FavoritesStudycafeDTO.favoritesStudycafeUserIdDTO favStudycafeDTO = new FavoritesStudycafeDTO.favoritesStudycafeUserIdDTO();
			UsersDTO.UserIdDTO uIDTO = new UsersDTO.UserIdDTO();
			uIDTO.setFvCNT(Integer.parseInt(String.valueOf(list.get(i)[5])));
			favStudycafeDTO.setUserIdDTO(uIDTO);
			sDTO.setFavoritesStudycafeUserIdDTO(favStudycafeDTO);
			dto.add(sDTO);
			// 순서 name, addr, imgpath, person, price, fav_cnt
		}
		return dto;
	}

	public List<StudycafeDTO.StudycafeSelectBySearchDTO> selectListALL() throws FindException {
		List<Object[]> list = sRepository.getListAll();
		List<StudycafeDTO.StudycafeSelectBySearchDTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			StudycafeDTO.StudycafeSelectBySearchDTO sDTO = new StudycafeDTO.StudycafeSelectBySearchDTO();
			sDTO.setCafeName((String) list.get(i)[0]);		
			sDTO.setAddr((String) list.get(i)[1]);
			sDTO.setCafeImg((String) list.get(i)[2]);
			BigDecimal cafeSeq = (BigDecimal) list.get(i)[6];
			long StudycafeSeq = cafeSeq.longValue();
			sDTO.setCafeSeq(StudycafeSeq);
			RoomInfoDTO.RoomInfoPriceAndPersonDTO ripDTO = new RoomInfoDTO.RoomInfoPriceAndPersonDTO();
			ripDTO.setPrice(Integer.parseInt(String.valueOf(list.get(i)[4])));
			ripDTO.setPerson(Integer.parseInt(String.valueOf(list.get(i)[3])));
			sDTO.setRoomInfoPriceAndPersonDTO(ripDTO);
			FavoritesStudycafeDTO.favoritesStudycafeUserIdDTO favStudycafeDTO = new FavoritesStudycafeDTO.favoritesStudycafeUserIdDTO();
			UsersDTO.UserIdDTO uIDTO = new UsersDTO.UserIdDTO();
			uIDTO.setFvCNT(Integer.parseInt(String.valueOf(list.get(i)[5])));
			favStudycafeDTO.setUserIdDTO(uIDTO);
			sDTO.setFavoritesStudycafeUserIdDTO(favStudycafeDTO);
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
	public List<StudycafeDTO.StudycafeList5DTO> selectList5() throws FindException {
		List<Object[]> list = sRepository.getList5();
		List<StudycafeDTO.StudycafeList5DTO> dto = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			StudycafeDTO.StudycafeList5DTO slDTO = new StudycafeDTO.StudycafeList5DTO();
			BigDecimal sr_seq = (BigDecimal) list.get(i)[0];
			Long resultSrSeq = sr_seq.longValue();
			slDTO.setCafeSeq(resultSrSeq);
			slDTO.setCafeName((String) list.get(i)[1]);
			
			HostDTO.HostIdDTO hiDTO = new HostDTO.HostIdDTO();
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
	public StudycafeDTO.StudycafeAndFavStudycafeInfoDTO getStudyroomDetail(Long srSeq) throws FindException {
		StudycafeDTO.StudycafeAndFavStudycafeInfoDTO sDTO = new StudycafeDTO.StudycafeAndFavStudycafeInfoDTO();
		
		Optional<Studycafe> optS = sRepository.findById(srSeq);

		Studycafe s=optS.get();
		sDTO.setCafeSeq(s.getCafeSeq());
		sDTO.setOpenTime(s.getOpenTime());
		sDTO.setEndTime(s.getEndTime());
		sDTO.setInfo(s.getInfo());
		sDTO.setCafeImg(s.getCafeImg());	
		sDTO.setAddr(s.getAddr());
		sDTO.setCafeName(s.getCafeName());
		sDTO.setOpenStatus(s.getOpenStatus());
		
		List<FavoritesStudycafe>list = s.getFavoritesStudycafe();
		List<FavoritesStudycafeDTO.favStudycafeinfoDTO> dtoList = new ArrayList<>();

		for(FavoritesStudycafe r: list) {
			FavoritesStudycafeDTO.favStudycafeinfoDTO fDTO = new FavoritesStudycafeDTO.favStudycafeinfoDTO();
			fDTO.setFavCafeSeq(r.getFavCafeSeq());
			fDTO.setUserId(r.getUsers().getUserId());
			dtoList.add(fDTO);
		}
		sDTO.setFavoritesStudycafeDTO(dtoList);
		
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
	public List<StudycafeDTO.StudycafeAndRoomInfoDTO> getStudyroomAndRoomInfo(long roomSeq) throws FindException {
		List<Object[]> list = sRepository.getInfoOne(roomSeq);
		List<StudycafeDTO.StudycafeAndRoomInfoDTO> dto = new ArrayList<>();

		for (int i = 0; i < list.size(); i++) {
			StudycafeDTO.StudycafeAndRoomInfoDTO sDTO = new StudycafeDTO.StudycafeAndRoomInfoDTO();
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