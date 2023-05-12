package com.developer.recommend.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.board.entity.Board;
import com.developer.board.repository.BoardRepository;
import com.developer.exception.AddException;
import com.developer.exception.RemoveException;
import com.developer.recommend.entity.Recommend;
import com.developer.recommend.repository.RecommendRepository;
import com.developer.users.entity.Users;
import com.developer.users.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendService {
	
	private final BoardRepository bRepository;
	private final RecommendRepository rRepository;
	private final UsersRepository uRepository;
	
	ModelMapper modelMapper = new ModelMapper();
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 추천수 증가 !
	 * @author choigeunhyeong
	 * @param recommend
	 * @param postSeq
	 * @param logined
	 * @throws AddException
	 */
	@Transactional
	public void addRecommend(Recommend recommend, Long postSeq, String logined) throws AddException{
		Optional<Board> optB = bRepository.findById(postSeq);
		Optional<Users> optU = uRepository.findById(logined);
		Board board = optB.get();
		Users users = optU.get();
		recommend.setBoard(board);
		recommend.setUsers(users);
		recommend = rRepository.save(recommend);
	}
	
	/**
	 * 추천수 감소 
	 * @author choigeunhyeong
	 * @param recSeq
	 * @throws RemoveException
	 */
	public void delRecommend(Long recSeq) throws RemoveException{
		rRepository.deleteById(recSeq);
	}
}