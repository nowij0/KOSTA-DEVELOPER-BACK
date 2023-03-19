package com.developer.email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EmailDTO {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class MailDTO {
	    private String address;
	    private String title;
	    private String message;
	}
}
