package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import handler.FileRemoveHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.BoardService;
import service.BoardServiceImpl;


@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	private RequestDispatcher rdp;
	private String destPage;
	private int isOk;
      
	private BoardService bsv;
	private String savePath; //파일 업로드 저장 경로

    public BoardController() {
        bsv = new BoardServiceImpl();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//매개변수 객체의 인코딩 설정
		request.setCharacterEncoding("utf-8"); //요청객체
		response.setCharacterEncoding("utf-8"); //응답객체
		//응답 객체 => 화면을 만들어서 응답 => jsp형식으로
		response.setContentType("text/html; charset=UTF-8"); //응답 객체가 html 객체로 전송
		
		String uri = request.getRequestURI(); //jsp에서 오는 요청 주소를 받는 객체
//		log.info(uri); // /brd.register
		
		String path = uri.substring(uri.lastIndexOf("/")+1); //register
		log.info(path);
		
		switch(path) {
		case "register":
			destPage = "/board/register.jsp";
			break;
		case "insert":
			try {
				//파일을 업로드할 물리적인 경로 설정
				savePath = getServletContext().getRealPath("/_fileUpload");
				log.info("savePath >> {}", savePath);
				File fileDir = new File(savePath);
				
				log.info("fileDir >> {}", fileDir);
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir); // 저장할 위치를 file 객체로 지정
				fileItemFactory.setSizeThreshold(1024*1024*3); // 파일 저장을 위한 임시 메모리
				
				BoardVO bvo = new BoardVO();
				
				// multipart/form-data형식으로 넘어온 request 객체를 다루기 쉽게 변환해주는 역할
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				for(FileItem item : itemList) {
					// title, writer, content => text imageFile => image
					switch(item.getFieldName()) {
					case "title":
						String title = item.getString("utf-8");
						bvo.setTitle(title);
						break;
						
					case "writer":
						bvo.setWriter(item.getString("utf-8"));
						break;
						
					case "content":
						bvo.setContent(item.getString("utf-8"));
						break;
						
					case "imageFile":
						//이미지가 있는지 없는지 확인
						//이미지 파일 여부 체크
						if(item.getSize() > 0) {
							//파일 이름 추출
							//getName() : 순수 파일이름 + 경로 ~~~/dog.jpg
							String fileName = item.getName()
									.substring(item.getName().lastIndexOf(File.separator)+1);
							//File.separator : 파일 경로 기호 => 운영체제마다 다를 수 있어서 자동 변환
							//시스템의 시간을 이용하여 파일을 구분 / 시간_dog.jpg
							fileName = System.currentTimeMillis()+"_"+fileName;
							
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							log.info(" uploadFilePath >> {}", uploadFilePath);
							
							//저장
							try {
								item.write(uploadFilePath); //객체를 디스크에 쓰기
								bvo.setImageFile(fileName); //bvo에 저장할 값 (DB에 저장할 값
								
								//썸네일 작업 : 리스트 페이지에서 트레픽 과다 사용 방지
								Thumbnails.of(uploadFilePath)
								.size(75, 75)
								.toFile(new File(fileDir+File.separator+"_th_"+fileName));
							} catch (Exception e) {
								log.info(">> file writer on disk error");
								e.printStackTrace();
							}
						}
						break;
					}
					
				}
				log.info("bvo >> {}", bvo);
				int isOk = bsv.inser(bvo);
				log.info("insert"+(isOk>0?"OK":"Fail"));
				
				destPage = "/index.jsp";
								
//				String title = request.getParameter("title");
//				String writer = request.getParameter("writer");
//				String content = request.getParameter("content");
//				
//				BoardVO bvo = new BoardVO(title, writer, content);
//				
//				int isOk = bsv.inser(bvo);
//				log.info("insert"+(isOk>0?"OK":"Fail"));
//				
//				destPage = "/index.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "list":
			try {
				//index에서 list 버튼을 클릭하면
				//컨트롤러에서 DB의 전체 리스트 요청
				//전체 리스트를 list.jsp로 가져가서 뿌리기
				//Paging 객체 설정
				PagingVO pgvo = new PagingVO(); // 1 / 10 / 0 / type / keyword
				
				if(request.getParameter("pageNo") != null) {
					int pageNo = Integer.parseInt(request.getParameter("pageNo"));
					int qty = Integer.parseInt(request.getParameter("qty"));
					String type = request.getParameter("type");
					String keyword = request.getParameter("keyword");
					
					pgvo = new PagingVO(pageNo, qty, type, keyword); 
				}
				
				
//				List<BoardVO> list = bsv.getList();
				List<BoardVO> list = bsv.getList(pgvo);
//				log.info("list >> {}", list);
//				totalCount DB에서 검색해서 가져오기
				int totalCount = bsv.getTotal(pgvo);
				log.info(">> totalCount >> {}", totalCount);
				PagingHandler ph = new PagingHandler(pgvo, totalCount);
				
				log.info(">> ph >> {}", ph);
				request.setAttribute("list", list);
				request.setAttribute("ph", ph);
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "detail": case "modify":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				if(path.equals("detail")) {
					int isOk = bsv.readCountUpdate(bno);
				}
				BoardVO bvo = bsv.getDetail(bno);
				log.info("list >> {}", bvo);
				request.setAttribute("bvo", bvo);
				destPage = "/board/"+path+".jsp";
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
		case "update":
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(1024*1024*3);
				
				BoardVO bvo = new BoardVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null;
				
				for(FileItem item : itemList) {
					switch(item.getFieldName()) {
					case "bno":
						bvo.setBno(Integer.parseInt(item.getString("utf-8")));
						break;
					case "title":
						bvo.setTitle(item.getString("utf-8"));
						break;
					case "content":
						bvo.setContent(item.getString("utf-8"));
						break;
					case "imageFile":
						//기존파일 => 있을 수도 있고, 없을 수도 있음.
						old_file = item.getString("utf-8");
						break;
					case "newfile":
						//세로 추가된 파일 => 있을 수도 있고, 없을 수도 있음.
						if(item.getSize()>0) {
							//새로운 등록 파일이 있다면...
							if(old_file != null) {
								//old_file 삭제작업
								//fileRemoveHandler 통해서 파일 삭제 작업 진행
								FileRemoveHandler fileHandler = new FileRemoveHandler();
								isOk = fileHandler.deleteFile(path, old_file);
							}
							//새로운 파일 등록작업
							String fileName = item.getName()
									.substring(item.getName().lastIndexOf(File.separator)+1);
							log.info("new File NAME >> {}", fileName);
							
							fileName = System.currentTimeMillis()+"_"+fileName;
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							
							try {
								item.write(uploadFilePath);
								bvo.setImageFile(fileName);
								
								Thumbnails.of(uploadFilePath)
								.size(75, 75)
								.toFile(new File(fileDir+File.separator+"_th_"+fileName));
							} catch (Exception e) {
								log.info("FIle Writer UPdate Error");
								e.printStackTrace();
							}
						}else {
							//기존 파일은 있지만, 새로운 이미지 파일이 없다면...
							bvo.setImageFile(old_file); //기존 객체 bvo에 담기
						}
						break;
					}
				}
				
				int isOk = bsv.update(bvo);
				log.info(">>bvo {}",bvo);
				log.info("update"+(isOk>0?"OK":"Fail"));
				destPage="list"; //내부 list 케이스를 한번 타고 실행
				
//				int bno = Integer.parseInt(request.getParameter("bno"));
//				String title = request.getParameter("title");
//				String content = request.getParameter("content");
//				
//				BoardVO bvo = new BoardVO(bno, title, content);		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
			
			
		case "remove":
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				int bno = Integer.parseInt(request.getParameter("bno"));
				//댓글, 파일도 같이 삭세
				//bno주고 파일이름 찾아오기 (DB에서)
				//찾아온 이름이 있다면... FileRemoveHandler를 이용하여 삭제
				String imageFileName = bsv.getFileName(bno);
				log.info(">> imageFileName>>{}",imageFileName);
				int isDel=0;
				if(imageFileName != null) {
					FileRemoveHandler fh = new FileRemoveHandler();
					isDel = fh.deleteFile(savePath, imageFileName);
				}
				
				//bno로 댓글 삭제 요청 => serviceImpl에서 cdao에게 요청	
				int isOk = bsv.delete(bno);
				log.info("delete"+(isOk>0?"OK":"Fail"));
				destPage="list";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		rdp = request.getRequestDispatcher(destPage);
		rdp.forward(request, response);
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

}
