package com.developer.util;

import java.io.File;
import java.net.URLEncoder;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.developer.exception.FindException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("download/*")
public class FileDownloadController {

	/**
     * 게시판 글 상세보기 사진
     * @author choigeunhyeong
     * @param imgPath
     * @param type
     * @param opt
     * @return
     * @throws FindException
     */
    @GetMapping("board")
    public ResponseEntity<?> download(String imgPath, int type, String opt) throws FindException {

//        String saveDirectory = "/Users/choigeunhyeong/Documents/attach";
        String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/board";

        String fileName = "";
        if (type == 2) {
            fileName = "t_";
        }
        fileName += imgPath; 
        File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
        File file = null;

        for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들

            String fn = f.getName();
            
            if (fn.equals(fileName)) {
                file = f;
                fileName = f.getName();
                break;
            }
        }
        if (file == null) {
            throw new FindException(fileName + "으로 된 파일이 없습니다");
        }
        try {
            HttpHeaders headers = new HttpHeaders();
            String contentType = Files.probeContentType(file.toPath());
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length());

            if ("attachment".equals(opt)) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 다운로드
			} else {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답
			}

            byte[] bArr = FileCopyUtils.copyToByteArray(file);
            ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
            System.out.println(bArr);
            return re;
        } catch (Exception e) {
            throw new FindException(e.getMessage());
        }
    }

            /**
             * 튜터 마이페이지 수업사진
             * @author choigeunhyeong
             * @param imgPath
             * @param type
             * @param opt
             * @return
             * @throws FindException
             */
            @GetMapping("mypage/complete")
            public ResponseEntity<?> downloadCompleteLesson(String imgPath, int type, String opt) throws FindException {

//                String saveDirectory = "/Users/choigeunhyeong/Documents/attach";
            	String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/lesson";
                
                String fileName = "";
                if (type == 2) {
                    fileName = "t_";
                }
                fileName += imgPath; 
                File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
                File file = null;

                for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들

                    String fn = f.getName();
                    
                    if (fn.equals(fileName)) {
                        file = f;
                        fileName = f.getName();
                        break;
                    }
                }
                if (file == null) {
                    throw new FindException(fileName + "으로 된 파일이 없습니다");
                }
                try {
                    HttpHeaders headers = new HttpHeaders();
                    String contentType = Files.probeContentType(file.toPath());
                    headers.add(HttpHeaders.CONTENT_TYPE, contentType);
                    headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length());

                    if ("attachment".equals(opt)) {
        				headers.add(HttpHeaders.CONTENT_DISPOSITION,
        						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 다운로드
        			} else {
        				headers.add(HttpHeaders.CONTENT_DISPOSITION,
        						"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답
        			}

                    byte[] bArr = FileCopyUtils.copyToByteArray(file);
                    ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
                    System.out.println(bArr);
                    return re;
                } catch (Exception e) {
                    throw new FindException(e.getMessage());
                }
            }

            /**
             * 관리자 스터디카페 상세보기 사진 
             * @author choigeunhyeong
             * @param imgPath
             * @param type
             * @param opt
             * @return
             * @throws FindException
             */
            @GetMapping("admin/studyroom")
            public ResponseEntity<?> downloadStudyroomImg(String imgPath, int type, String opt) throws FindException {

//                String saveDirectory = "/Users/choigeunhyeong/Documents/attach";
            	String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/studyroom";
                
                String fileName = "";
                if (type == 2) {
                    fileName = "t_";
                }
                fileName += imgPath; 
                File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
                File file = null;

                for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들

                    String fn = f.getName();
                    
                    if (fn.equals(fileName)) {
                        file = f;
                        fileName = f.getName();
                        break;
                    }
                }
                if (file == null) {
                    throw new FindException(fileName + "으로 된 파일이 없습니다");
                }
                try {
                    HttpHeaders headers = new HttpHeaders();
                    String contentType = Files.probeContentType(file.toPath());
                    headers.add(HttpHeaders.CONTENT_TYPE, contentType);
                    headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length());

                    if ("attachment".equals(opt)) {
        				headers.add(HttpHeaders.CONTENT_DISPOSITION,
        						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 다운로드
        			} else {
        				headers.add(HttpHeaders.CONTENT_DISPOSITION,
        						"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답
        			}

                    byte[] bArr = FileCopyUtils.copyToByteArray(file);
                    ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
                    System.out.println(bArr);
                    return re;
                } catch (Exception e) {
                    throw new FindException(e.getMessage());
                }
            }


	/**
	 * @author SR
	 * @param imgPath
	 * @param type    첨부파일인 경우 1, 섬네일파일인 경우 2
	 * @param opt     다운로드 방식: 무조건 다운로드인 경우 "attachment", 실행경우 "inline"
	 * @return
	 * @throws FindException
	 */
	@GetMapping("lesson")
	public ResponseEntity<?> downloadLesson(String imgPath, int type, String opt) throws FindException {

		
//		String saveDirectory = "C:\\dev\\lesson";
		String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/lesson";
		
		String fileName = "";
		if (type == 2) {
			fileName = "t_";
		}
		fileName += imgPath; 


		File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
		File file = null;

		for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들

			String fn = f.getName();
			// int lastIndex = fn.lastIndexOf(".");
			// if (fn.substring(0, lastIndex).equals(fileName)) {
			if (fn.equals(fileName)) {
				file = f;
				fileName = f.getName();
				break;
			}
		}
		if (file == null) {
			throw new FindException(fileName + "으로 된 파일이 없습니다");
		}

		try {
			HttpHeaders headers = new HttpHeaders();
			String contentType = Files.probeContentType(file.toPath()); // 파일의 형식
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);// 응답형식
			headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length()); // 응답크기

			if ("attachment".equals(opt)) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 다운로드
			} else {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답
			}
			
	        // 캐시 막는 옵션 추가
	        headers.add(HttpHeaders.CACHE_CONTROL, "no-cache");

			byte[] bArr = FileCopyUtils.copyToByteArray(file);
			ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		}
	}
	
	
	/**
	 * 튜터 다운로드
	 * @author moonone
	 * @param imgPath
	 * @param type
	 * @param opt
	 * @return
	 * @throws FindException
	 */
	@GetMapping("tutor")
	public ResponseEntity<?> downloadTutor(String imgPath, int type, String opt) throws FindException {
		
//		String saveDirectory = "C:\\dev\\lesson";
		String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/tutor";
		
		
		String fileName = "";
		if (type == 2) {
			fileName = "t_";
		}
		fileName += imgPath; 
		File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
		File file = null;
		
		for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들
			
			String fn = f.getName();
			//int lastIndex = fn.lastIndexOf(".");
			//if (fn.substring(0, lastIndex).equals(fileName)) {
			// int lastIndex = fn.lastIndexOf(".");
			// if (fn.substring(0, lastIndex).equals(fileName)) {
			if (fn.equals(fileName)) {
				file = f;
				fileName = f.getName();
				break;
			}
		}
		if (file == null) {
			throw new FindException(fileName + "으로 된 파일이 없습니다");
		}
		
		try {
			HttpHeaders headers = new HttpHeaders();
			String contentType = Files.probeContentType(file.toPath()); // 파일의 형식
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);// 응답형식
			headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length()); // 응답크기
			
			if ("attachment".equals(opt)) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 다운로드
			} else {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답
			}
			
			byte[] bArr = FileCopyUtils.copyToByteArray(file);
			ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
			System.out.println(bArr);
			return re;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		}
	}

	/**
	 * 스터디룸 파일 다운로드
	 * 
	 * @author SR
	 * @param imgPath
	 * @param type
	 * @param opt
	 * @return
	 * @throws FindException
	 */
	@GetMapping("studyroom")
	public ResponseEntity<?> downloadCafe(String imgPath, int type, String opt) throws FindException {

//		String saveDirectory = "C:\\dev\\studyroom";
		String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/studyroom";

		String fileName = "";
		if (type == 2) {
			fileName = "t_";
		}
		fileName += imgPath;
		File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
		File file = null;

		for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들

			String fn = f.getName();

			if (fn.equals(fileName)) {
				file = f;
				fileName = f.getName();
				break;
			}
		}
		if (file == null) {
			throw new FindException(fileName + "으로 된 파일이 없습니다");
		}

		try {
			HttpHeaders headers = new HttpHeaders();
			String contentType = Files.probeContentType(file.toPath()); // 파일의 형식
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);// 응답형식
			headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length()); // 응답크기

			if ("attachment".equals(opt)) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 다운로드
			} else {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답
			}

			byte[] bArr = FileCopyUtils.copyToByteArray(file);
			ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		}
	}

	/**
	 * 룸인포폴더에서 파일 다운로드
	 * 
	 * @author tpfks
	 * @param imgPath
	 * @param type
	 * @param opt
	 * @return
	 * @throws FindException
	 */
	@GetMapping("roominfo")
	public ResponseEntity<?> downloadRoom(String imgPath, int type, String opt) throws FindException {

//		String saveDirectory = "C:\\dev\\roominfo";
		String saveDirectory = "/Users/moonone/Desktop/KOSTA/img/roominfo";

		String fileName = "";
		if (type == 2) {
			fileName = "t_";
		}
		fileName += imgPath;
		File dir = new File(saveDirectory); // 첨부파일이 있는 디렉토리
		File file = null;

		for (File f : dir.listFiles()) { // 디렉토리의 모든 파일들

			String fn = f.getName();

			if (fn.equals(fileName)) {
				file = f;
				fileName = f.getName();
				break;
			}
		}
		if (file == null) {
			throw new FindException(fileName + "으로 된 파일이 없습니다");
		}

		try {
			HttpHeaders headers = new HttpHeaders();
			String contentType = Files.probeContentType(file.toPath()); // 파일의 형식
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);// 응답형식
			headers.add(HttpHeaders.CONTENT_LENGTH, "" + file.length()); // 응답크기

			if ("attachment".equals(opt)) {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 다운로드
			} else {
				headers.add(HttpHeaders.CONTENT_DISPOSITION,
						"inline;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));// 바로응답
			}

			byte[] bArr = FileCopyUtils.copyToByteArray(file);
			ResponseEntity<?> re = new ResponseEntity<>(bArr, headers, HttpStatus.OK);
			return re;
		} catch (Exception e) {
			throw new FindException(e.getMessage());
		}
	}

}