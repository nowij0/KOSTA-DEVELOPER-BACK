package com.developer.util;

import java.io.File;
import java.io.IOException;

import org.springframework.util.FileCopyUtils;

public class Attach {
	
	/**
	 * 파일을 첨부한다
	 * @param bytes 첨부파일(원본)의 바이트
	 * @param file 저장될 경로와 파일명 ex) new File("경로", "파일명")
	 * @throws IOException
	 */
	public static void upload(byte[]bytes,File file) throws IOException {
		File saveDirFile = file.getParentFile();
		if(!saveDirFile.exists()) {
			saveDirFile.mkdir(); 
		}
		FileCopyUtils.copy(bytes, file); //파일저장
	}
}
