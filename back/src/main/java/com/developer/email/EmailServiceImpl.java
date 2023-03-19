package com.developer.email;

import java.util.Random;

import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	JavaMailSender emailSender;

	// public static final String ePw = createKey();

	// 본인 인증(user)
	private MimeMessage createMessage(String to) throws Exception {
		String ePw = createKey();
		System.out.println("보내는 대상 : " + to);
		System.out.println("인증 번호 : " + ePw);
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("[DEVELOPER] 회원가입 이메일 인증");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<div style='text-align: center;'><h1> 안녕하세요 :) </h1></div>";
		msgg += "<div style='text-align: center;'><h1> DEVELOPER를 가입해주셔서 감사합니다. </h1></div>";
		msgg += "<br>";
		msgg += "<div style='text-align: center;'><p>인증 페이지로 돌아가 아래의 코드를 입력해주세요<p></div>";
		msgg += "<br>";
		msgg += "<div style='text-align: center;'><p>감사합니다.<p></div>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; background-color: #FFFACD; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>회원가입 인증 코드</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += ePw + "</strong><div><br/> ";
		msgg += "</div>";

		String code = ePw;
		message.setDescription(code);
		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("developer.kosta@gmail.com", "DEVELOPER"));// 보내는 사람
		return message;

	}

	// 튜터 승인
	private MimeMessage tutorOkMessage(String to) throws Exception {
		System.out.println("보내는 대상 : " + to);
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("[DEVELOPER] 튜터 승인 결과 알림");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<div style='text-align: center;'><h1> 당신의 가치를 높이는 곳, [DEVELOPER]입니다. </h1></div>";
		msgg += "<div style='text-align: center;'><h1> 먼저, DEVELOPER에 많은 관심을 가져 주셔서 감사합니다. </h1></div>";
		msgg += "<br>";
		msgg += "<div style='text-align: center;'><p>아래의 승인 결과를 확인해주세요.</p>";
		msgg += "</div>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; background-color: #FFFACD; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'> [DEVELOPER] 튜터 승인 신청 결과 </h3>";
		msgg += "<div style='font-size:130%; text-align: center;'>";
		msgg += "<p>귀하께서 작성해주신 내용을 검토한 결과,</p>";
		msgg += "<p>저희 모집요건에 부합하지 않아 튜터 승인심사가 반려되었음을 알려드립니다.</p>";
		msgg += "<p>자세한 사유는 고객센터에 문의바랍니다. </p>";
		msgg += "<p>앞으로도 변함없는 관심과 성원을 부탁드리며, 언제든지 재신청이 가능한 점 알려드립니다. </p>";
		msgg += "<p>심사가 진행되는 동안 기다려주셔서 감사합니다.</p>";
		msgg += "<br>";
		msgg += "<p>귀하께서 작성해주신 내용을 검토한 결과,</p>";
		msgg += "<p>튜터심사가 <strong>승인</strong>되었음을 알려드립니다.</p>";
		msgg += "<br>";
		msgg += "<p>앞으로도 변함없는 관심과 성원을 부탁드리며, 귀하와 성공적인 파트너 관계를 유지할 수 있기를 바랍니다.</p>";
		msgg += "<p>감사합니다.</p>";
		msgg += "<br>";
		msgg += "<p>DEVELOPER 고객센터 올림</p>";
		msgg += "<div style='text-align: center; font-size:70%;'><p>고객센터 TEL: 031-000-0000</p>";
		msgg += "<br>";
		msgg += "</div>";

		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("developer.kosta@gmail.com", "DEVELOPER"));// 보내는 사람
		return message;
	}

	// 튜터 승인 거절
	private MimeMessage tutorRejectMessage(String to) throws Exception {
		System.out.println("보내는 대상 : " + to);
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("[DEVELOPER] 튜터 승인 결과 알림");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<div style='text-align: center;'><h1> 당신의 가치를 높이는 곳, [DEVELOPER]입니다. </h1></div>";
		msgg += "<div style='text-align: center;'><h1> 먼저, DEVELOPER에 많은 관심을 가져 주셔서 감사합니다. </h1></div>";
		msgg += "<br>";
		msgg += "<div style='text-align: center;'><p>아래의 승인 결과를 확인해주세요.</p>";
		msgg += "</div>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; background-color: #FFFACD; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'> [DEVELOPER] 튜터 승인 신청 결과 </h3>";
		msgg += "<div style='font-size:130%; text-align: center;'>";
		msgg += "<p>귀하께서 작성해주신 내용을 검토한 결과,</p>";
		msgg += "<p>저희 모집요건에 부합하지 않아 튜터 승인심사가 <strong>반려</strong>되었음을 알려드립니다.</p>";
		msgg += "<p>자세한 사유는 고객센터에 문의바랍니다. </p>";
		msgg += "<p>앞으로도 변함없는 관심과 성원을 부탁드리며, 언제든지 재신청이 가능한 점 알려드립니다. </p>";
		msgg += "<br>";
		msgg += "<p>DEVELOPER 고객센터 올림</p>";
		msgg += "<div style='text-align: center; font-size:70%;'><p>고객센터 TEL: 031-000-0000</p>";
		msgg += "<br>";
		msgg += "</div>";

		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("developer.kosta@gmail.com", "DEVELOPER"));// 보내는 사람
		return message;
	}

	// 비밀번호찾기
	private MimeMessage updatePwdMessage(String to) throws Exception {
		String ePw = createKey();
		System.out.println("보내는 대상 : " + to);
		System.out.println("인증 번호 : " + ePw);
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("[DEVELOPER] 임시 비밀번호 발송");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<div style='text-align: center;'><h1> 안녕하세요 :) </h1></div>";
		msgg += "<div style='text-align: center;'><h1> 당신의 가치를 높이는 곳, [DEVELOPER]입니다. </h1></div>";
		msgg += "<br>";
		msgg += "<div style='text-align: center;'><p>귀하께서 요청하신 임시 비밀번호 수신을 위해 발송된 메일입니다. </p>";
		msgg += "<p>아래의 임시 비밀번호로 로그인하여 꼭 새로운 비밀번호으로 변경 부탁드립니다.</p>" + "<p>감사합니다.<p></div>";
		msgg += "<br>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; background-color: #FFFACD; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>임시 비밀번호</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "<strong>";
		msgg += ePw + "</strong><br/> ";
		msgg += "<br/> ";
		msgg += "<p>DEVELOPER 고객센터</p>";
		msgg += "<div style='text-align: center; font-size:70%;'><p>고객센터 TEL: 031-000-0000</p>";
		msgg += "</div>";

		String code = ePw;
		message.setDescription(code);
		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("developer.kosta@gmail.com", "DEVELOPER"));// 보내는 사람
		return message;

	}

	// 호스트 승인
	private MimeMessage hostOkMessage(String to) throws Exception {
		System.out.println("보내는 대상 : " + to);
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("[DEVELOPER] 호스트 승인 결과 알림");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<div style='text-align: center;'><h1> 당신의 가치를 높이는 곳, [DEVELOPER]입니다. </h1></div>";
		msgg += "<div style='text-align: center;'><h1> 먼저, DEVELOPER에 많은 관심을 가져 주셔서 감사합니다. </h1></div>";
		msgg += "<br>";
		msgg += "<div style='text-align: center;'><p>아래의 승인 결과를 확인해주세요.</p>";
		msgg += "</div>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; background-color: #FFFACD; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'> [DEVELOPER] 호스트 승인 신청 결과 </h3>";
		msgg += "<div style='font-size:130%; text-align: center;'>";
		msgg += "<p>심사가 진행되는 동안 기다려주셔서 감사합니다.</p>";
		msgg += "<br>";
		msgg += "<p>귀하께서 작성해주신 내용을 검토한 결과,</p>";
		msgg += "<p>호스트회원 심사가 <strong>승인</strong>되었음을 알려드립니다.</p>";
		msgg += "<br>";
		msgg += "<p>앞으로도 변함없는 관심과 성원을 부탁드리며, 귀하와 성공적인 파트너 관계를 유지할 수 있기를 바랍니다.</p>";
		msgg += "<p>감사합니다.</p>";
		msgg += "<br>";
		msgg += "<p>DEVELOPER 고객센터 올림</p>";
		msgg += "<div style='text-align: center; font-size:70%;'><p>고객센터 TEL: 031-000-0000</p>";
		msgg += "<br>";
		msgg += "</div>";

		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("developer.kosta@gmail.com", "DEVELOPER"));// 보내는 사람
		return message;
	}

	// 호스트 승인 거절
	private MimeMessage hostRejectMessage(String to) throws Exception {
		System.out.println("보내는 대상 : " + to);
		MimeMessage message = emailSender.createMimeMessage();

		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("[DEVELOPER] 호스트 승인 결과 알림");// 제목

		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<div style='text-align: center;'><h1> 당신의 가치를 높이는 곳, [DEVELOPER]입니다. </h1></div>";
		msgg += "<div style='text-align: center;'><h1> 먼저, DEVELOPER에 많은 관심을 가져 주셔서 감사합니다. </h1></div>";
		msgg += "<br>";
		msgg += "<div style='text-align: center;'><p>아래의 승인 결과를 확인해주세요.</p>";
		msgg += "</div>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; background-color: #FFFACD; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'> [DEVELOPER] 호스트 승인 신청 결과 </h3>";
		msgg += "<div style='font-size:130%; text-align: center;'>";
		msgg += "<p>귀하께서 작성해주신 내용을 검토한 결과,</p>";
		msgg += "<p>저희 모집요건에 부합하지 않아 호스트 승인심사가 반려되었음을 알려드립니다.</p>";
		msgg += "<p>저희 모집요건에 부합하지 않아 호스트회원 승인심사가 <strong>반려</strong>되었음을 알려드립니다.</p>";
		msgg += "<p>자세한 사유는 고객센터에 문의바랍니다. </p>";
		msgg += "<p>앞으로도 변함없는 관심과 성원을 부탁드리며, 언제든지 재신청이 가능한 점 알려드립니다. </p>";
		msgg += "<br>";
		msgg += "<p>DEVELOPER 고객센터 올림</p>";
		msgg += "<div style='text-align: center; font-size:70%;'><p>고객센터 TEL: 031-000-0000</p>";
		msgg += "<br>";
		msgg += "</div>";

		message.setText(msgg, "utf-8", "html");// 내용
		message.setFrom(new InternetAddress("developer.kosta@gmail.com", "DEVELOPER"));// 보내는 사람
		return message;
	}

	public String createKey() {
		StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				// A~Z
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				// 0~9
				break;
			}
		}
		return key.toString();
	}

	// 본인인증 메일
	@Override
	public String sendSimpleMessage(String to) throws Exception {
		MimeMessage message = createMessage(to);
		try {// 예외처리
			emailSender.send(message);
			String msg = (String) message.getDescription();
			// System.out.println("서비스단 키:" + msg);
			return msg;
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	// 튜터 승인 메일
	@Override
	public void tutorOk(String to) throws Exception {
		MimeMessage message = tutorOkMessage(to);
		try {// 예외처리
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	// 튜터 승인 거절 메일
	@Override
	public void tutorReject(String to) throws Exception {
		MimeMessage message = tutorRejectMessage(to);
		try {// 예외처리
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	// 호스트 승인 메일
	@Override
	public void hostOk(String to) throws Exception {
		MimeMessage message = hostOkMessage(to);
		try {// 예외처리
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	// 호스트 승인 거절 메일
	@Override
	public void hostReject(String to) throws Exception {
		MimeMessage message = hostRejectMessage(to);
		try {// 예외처리
			emailSender.send(message);
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

	// 임시 비밀번호 발송
	@Override
	public String updatePwd(String to) throws Exception {
		MimeMessage message = updatePwdMessage(to);
		try {// 예외처리
			emailSender.send(message);
			String msg = (String) message.getDescription();
			System.out.println("서비스단 msg:" + msg);
			return msg;
		} catch (MailException es) {
			es.printStackTrace();
			throw new IllegalArgumentException();
		}
	}

}