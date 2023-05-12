package com.developer.favoritesstudycafe.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.exception.RemoveException;
import com.developer.favoritesstudycafe.dto.FavoritesStudycafeDTO;
import com.developer.favoritesstudycafe.entity.FavoritesStudycafe;
import com.developer.favoritesstudycafe.repository.FavoritesStudycafeRepository;
import com.developer.studycafe.dto.StudycafeDTO;
import com.developer.studycafe.entity.Studycafe;
import com.developer.studycafe.repository.StudycafeRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoritesStudycafeService {

	private final FavoritesStudycafeRepository fsRepository;
	private final StudycafeRepository sRepository;
	private final UsersRepository uRepository;

	/**
	 * [스터디카페 상세페이지]즐겨찾기 추가기능
	 * 
	 * @author ds
	 * @param fvDTO
	 * @throws AddException
	 */

	public void insertFVstudyroom(Long srSeq, String logined) throws AddException {
		FavoritesStudycafe fs = new FavoritesStudycafe();
		Optional<Users> optU = uRepository.findById(logined);
		Users u = optU.get();
		fs.setUsers(u);
		Optional<Studycafe> optS = sRepository.findById(srSeq);
		Studycafe s = optS.get();
		fs.setStudycafe(s);
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
	public List<FavoritesStudycafeDTO.favStudycafeListDTO> listFavStudyroom(String userId) throws FindException {
		List<Object[]> favList = fsRepository.selectAllFavStudyroom(userId);

		List<FavoritesStudycafeDTO.favStudycafeListDTO> favListDto = new ArrayList<>();

		for (int i = 0; i < favList.size(); i++) {
			FavoritesStudycafeDTO.favStudycafeListDTO favDto = new FavoritesStudycafeDTO.favStudycafeListDTO();
			BigDecimal cafeSeq = (BigDecimal) favList.get(i)[0];
			long convertSeq = cafeSeq.longValue();
			favDto.setCafeSeq(convertSeq);

			StudycafeDTO.selectAllFavStudycafeDTO sDto = new StudycafeDTO.selectAllFavStudycafeDTO();
			sDto.setCafeName((String) favList.get(i)[1]);
			sDto.setAddr((String) favList.get(i)[2]);

			favDto.setStudycafe(sDto);
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

	public List<FavoritesStudycafeDTO.favStudycafeCafeseqDTO> getSrSeqAndFavSeq(String userId) throws FindException {
		List<Object[]> list = fsRepository.getfvInfo(userId);

		List<FavoritesStudycafeDTO.favStudycafeCafeseqDTO> dto = new ArrayList<>();
		FavoritesStudycafeDTO.favStudycafeCafeseqDTO fDTO = new FavoritesStudycafeDTO.favStudycafeCafeseqDTO();
		for (int i = 0; i < list.size(); i++) {
			BigDecimal fav_seq = (BigDecimal) list.get(i)[1];
			Long resultFavSeq = fav_seq.longValue();
			fDTO.setFavCafeSeq(resultFavSeq);
			BigDecimal sr_seq = (BigDecimal) list.get(i)[0];
			Long resultSrSeq = sr_seq.longValue();
			StudycafeDTO.StudycafeCafeSeqDTO sDTO = new StudycafeDTO.StudycafeCafeSeqDTO();
			sDTO.setCafeSeq(resultSrSeq);
			fDTO.setCafeseqDTO(sDTO);
			dto.add(fDTO);
		}
		return dto;

	}

}