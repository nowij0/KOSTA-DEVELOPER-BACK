package com.developer.favoritesstudyroom.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.favoritesstudyroom.dto.FavoritesStudyroomDTO;
import com.developer.favoritesstudyroom.entity.FavoritesStudyroom;
import com.developer.favoritesstudyroom.repository.FavoritesStudyroomRepository;
import com.developer.studyroom.dto.StudyroomDTO;
import com.developer.studyroom.entity.Studyroom;
import com.developer.studyroom.repository.StudyroomRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoritesStudyroomService {

	private final FavoritesStudyroomRepository fsRepository;
	private final StudyroomRepository sRepository;
	private final UsersRepository uRepository;

	/**
	 * [스터디카페 상세페이지]즐겨찾기 추가기능
	 * 
	 * @author ds
	 * @param fvDTO
	 * @throws AddException
	 */

	public void insertFVstudyroom(Long srSeq, String logined) throws AddException {
		FavoritesStudyroom fs = new FavoritesStudyroom();
		Optional<Users> optU = uRepository.findById(logined);
		Users u = optU.get();
		fs.setUsers(u);
		Optional<Studyroom> optS = sRepository.findById(srSeq);
		Studyroom s = optS.get();
		fs.setStudyroom(s);
		fsRepository.save(fs);
	}

	/**
	 * [스터디카페 상세페이지]즐겨찾기 삭제기능
	 * 
	 * @author ds
	 * @param fvSeq
	 * @throws AddException
	 */
	public void deleteFvstudyroom(Long srSeq, String userId) throws RemoveException {
		fsRepository.deleteFvstudyroom(srSeq, userId);
	}

	/**
	 * [마이페이지] 즐겨찾기 스터디카페 목록
	 * 
	 * @author SR
	 * @param userId
	 * @return
	 * @throws FindException
	 */
	public List<FavoritesStudyroomDTO.favStudyroomListDTO> listFavStudyroom(String userId) throws FindException {
		List<Object[]> favList = fsRepository.selectAllFavStudyroom(userId);

		List<FavoritesStudyroomDTO.favStudyroomListDTO> favListDto = new ArrayList<>();

		for (int i = 0; i < favList.size(); i++) {
			FavoritesStudyroomDTO.favStudyroomListDTO favDto = new FavoritesStudyroomDTO.favStudyroomListDTO();
			BigDecimal srSeq = (BigDecimal) favList.get(i)[0];
			long convertSeq = srSeq.longValue();
			favDto.setSrSeq(convertSeq);

			StudyroomDTO.selectAllFavStudyroomDTO sDto = new StudyroomDTO.selectAllFavStudyroomDTO();
			sDto.setName((String) favList.get(i)[1]);
			sDto.setAddr((String) favList.get(i)[2]);

			favDto.setStudyroom(sDto);
			favListDto.add(favDto);
		}
		return favListDto;
	}

	/**
	 * [스터디카페 상세페이지]즐겨찾기 추가 또는 삭제시 이미 db에 있는지 체크
	 * 
	 * @author DS
	 * @param logined
	 * @return srSeq
	 * @throws FindException 객체 한개만 반환하지만 에러때문에 리스트로 받음
	 */

	public List<FavoritesStudyroomDTO.favStudyroomSrSeqDTO> getSrSeqAndFavSeq(String userId) throws FindException {
		List<Object[]> list = fsRepository.getfvInfo(userId);

		List<FavoritesStudyroomDTO.favStudyroomSrSeqDTO> dto = new ArrayList<>();
		FavoritesStudyroomDTO.favStudyroomSrSeqDTO fDTO = new FavoritesStudyroomDTO.favStudyroomSrSeqDTO();
		for (int i = 0; i < list.size(); i++) {
			BigDecimal fav_seq = (BigDecimal) list.get(i)[1];
			Long resultFavSeq = fav_seq.longValue();
			fDTO.setFavSeq(resultFavSeq);
			BigDecimal sr_seq = (BigDecimal) list.get(i)[0];
			Long resultSrSeq = sr_seq.longValue();
			StudyroomDTO.StudyroomSrSeqDTO sDTO = new StudyroomDTO.StudyroomSrSeqDTO();
			sDTO.setSrSeq(resultSrSeq);
			fDTO.setSrseqDTO(sDTO);
			dto.add(fDTO);
		}
		return dto;

	}

}