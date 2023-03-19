package com.developer.email;

import com.developer.exception.FindException;

public interface EmailService {
	// [SR] 본인인증 메일
	String sendSimpleMessage(String to) throws Exception;

	// [SR] 튜터 승인거절
	void tutorReject(String to) throws FindException, Exception;

	// [SR] 호스트 승인거절
	void hostReject(String to) throws FindException, Exception;

	// [SR] 임시비밀번호
	String updatePwd(String to) throws Exception;

	// [SR] 튜터 승인
	void tutorOk(String to) throws Exception;

	// [SR] 호스트 승인
	void hostOk(String to) throws Exception;

}