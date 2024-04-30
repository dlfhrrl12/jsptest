package handler;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileRemoveHandler {
	private static final Logger log = LoggerFactory.getLogger(FileRemoveHandler.class);
	
	//savePath. imageFileName 매개변수로 받아 파일을 삭제하는 메서드
	public int deleteFile(String savePath, String imageFileName) {
		//return type int => 삭제 여부 값을 리턴
		boolean isDel = false; //삭제가 잘 되었는지 체크하는 변수
		log.info(">> deleteFile method 접근~!! {}", imageFileName);
		
		//기존 저장 파일 + _th_ 같이 삭제
		File fileDir = new File(savePath);
		File removeFile = new File(fileDir+File.separator+imageFileName);
		File removeThumFile = new File(fileDir+File.separator+"_th_"+imageFileName);
		
		//파일이 존재해야 삭제
		if(removeFile.exists() || removeThumFile.exists()) {
			isDel = removeFile.delete(); // 원 파일 삭제
			log.info(">> fileRemove >> {}", isDel);
			
			if(isDel) {
				isDel = removeThumFile.delete();
				log.info(">> fileThumFile >> {}", isDel);				
			}
		}
		
		return isDel? 1:0;
	}
}
