package com.developer.orders.control;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.exception.AddException;
import com.developer.exception.FindException;
import com.developer.orders.dto.OrdersDTO;
import com.developer.orders.entity.Orders;
import com.developer.orders.service.OrdersService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders/*")
public class OrdersController {

	private final OrdersService oService;

	/**
	 * 프론트에서 결제요청시 서버로 콜백
	 * 
	 * @author Jin
	 * @param model
	 * @return
	 */
	@RequestMapping("payment/callback_receive")
	public ResponseEntity<?> callback_receive(@RequestBody Map<String, Object> model) {

		// 응답 header 생성
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
		JSONObject responseObj = new JSONObject();

		try {
			String imp_uid = (String) model.get("imp_uid");
			String merchant_uid = (String) model.get("merchant_uid");
			boolean success = (Boolean) model.get("success");
			String error_msg = (String) model.get("error_msg");
			String status = (String) model.get("status");

			System.out.println("--callback--");
			System.out.println("------------");
			System.out.println("imp_uid : " + imp_uid);
			System.out.println("merchant_uid : " + merchant_uid);
			System.out.println("success : " + success);
			System.out.println("status : " + status);

			if (success == true) {

				String api_key = "7721866262061330";
				String api_secret = "hCC73eHlmDKSmLXwLiKu3e3Ls4oAVAw1KBvNxGP0AlRuQwjN4FT6ugj9GB2YzTBJYuMflOrigmnptZs4";

				IamportClient ic = new IamportClient(api_key, api_secret);
				IamportResponse<Payment> response = ic.paymentByImpUid(imp_uid);

				BigDecimal iamport_amount = response.getResponse().getAmount();

				responseObj.put("process_result", "결제성공");

			} else {
				System.out.println("error_msg : " + error_msg);
				responseObj.put("process_result", "결제실패 : " + error_msg);
			}

		} catch (Exception e) {
			e.printStackTrace();
			responseObj.put("process_result", "결제실패 : 관리자에게 문의해 주세요.");
		}

		return new ResponseEntity<String>(responseObj.toString(), responseHeaders, HttpStatus.OK);

	}

	/**
	 * 이 부분 파이널에서는 제외시킵니다..
	 * 
	 * @author Jin
	 * @param model
	 * @return
	 */
	@RequestMapping("payment/webhook_receive")
	public ResponseEntity<?> webhook_receive(@RequestBody Map<String, Object> model) {

		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

		try {
			String imp_uid = (String) model.get("imp_uid");
			String merchant_uid = (String) model.get("merchant_uid");
			String status = (String) model.get("status");

			System.out.println("--webhook--");
			System.out.println("------------");
			System.out.println("imp_uid : " + imp_uid);
			System.out.println("merchant_uid : " + merchant_uid);
			System.out.println("status : " + status);

			String api_key = "7721866262061330";
			String api_secret = "hCC73eHlmDKSmLXwLiKu3e3Ls4oAVAw1KBvNxGP0AlRuQwjN4FT6ugj9GB2YzTBJYuMflOrigmnptZs4";

			IamportClient ic = new IamportClient(api_key, api_secret);
			IamportResponse<Payment> response = ic.paymentByImpUid(imp_uid);

			BigDecimal iamport_amount = response.getResponse().getAmount();

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity(("서버처리 오류"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("결과반영 성공", responseHeaders, HttpStatus.OK);
	}

	/**
	 * 결제 성공시 DB값 저장
	 * 
	 * @author Jin
	 * @param result
	 * @param session
	 * @param lessonSeq
	 * @return
	 * @throws AddException
	 * @throws FindException
	 */
	@PostMapping(value = "payment/add/{lessonSeq}", produces = { "application/json; charset=UTF-8",
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<?> addOrders(@RequestBody Map<String, Object> result, HttpSession session,
			@PathVariable Long lessonSeq
//			,Model model
//			, @RequestBody String impUid
//			,@RequestBody Map<String, Object> model
	) throws AddException, FindException {

		String logined = (String) session.getAttribute("logined");
//		ObjectMapper objectMapper = new ObjectMapper();
//		TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String,Object>>(){};
//		objectMapper.readValue(impUid, null);

//		Map<String, Object> result = new HashMap<>();
		String impUid = (String) result.get("imp_uid");
		System.out.println("이제 좀 되자?:" + impUid);

//		logined = "111";
		if (logined != null) {
			oService.addOrderDTO(impUid, logined, lessonSeq);
			System.out.println("odersDTO는 : " + impUid);

			return new ResponseEntity<>(HttpStatus.OK);

		} else {
			return new ResponseEntity<>("로그인이 되지 않은 상태입니다.", HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * 튜터의 결제내역 조회
	 * 
	 * @author Jin
	 * @param orderId
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "orderlist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> orderList(HttpSession session) throws FindException {
		String logined = (String) session.getAttribute("logined");

		if (logined != null) {
			List<OrdersDTO.selectOrdersDTO> oList = oService.findByOrderId(logined);
			return new ResponseEntity<>(oList, HttpStatus.OK);

		} else {
			return new ResponseEntity<>("로그인이 되지 않은 상태입니다.", HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * 관리자 결제 전체 내역 조회(구현X)
	 * 
	 * @author Jin
	 * @return
	 * @throws FindException
	 */
	@GetMapping(value = "wholelist")
	public ResponseEntity<?> selectAllOrders() throws FindException {
		List<Orders> list = oService.selectAll();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
