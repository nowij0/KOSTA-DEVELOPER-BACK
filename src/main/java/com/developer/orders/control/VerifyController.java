package com.developer.orders.control;

import java.io.IOException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/verifyIamport")
public class VerifyController {

    /** Iamport 결제 검증 컨트롤러 **/
	private final IamportClient iamportClient;
	
    // 생성자를 통해 REST API 와 REST API secret 입력
    public VerifyController(){
        this.iamportClient = new IamportClient("7721866262061330", "hCC73eHlmDKSmLXwLiKu3e3Ls4oAVAw1KBvNxGP0AlRuQwjN4FT6ugj9GB2YzTBJYuMflOrigmnptZs4", true);
    }
    
    /** 프론트에서 받은 PG사 결과값을 통해 아임포트 토큰 발행 **/
    @PostMapping("/{imp_uid}")
    public IamportResponse<Payment> paymentByImpUid(@PathVariable String imp_uid) throws IamportResponseException, IOException{
        log.info("paymentByImpUid 진입");
        return iamportClient.paymentByImpUid(imp_uid);
    }
    
    
	
	
}
