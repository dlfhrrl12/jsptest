package service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import repository.BoardDAO;
import repository.BoardDAOImpl;

public class BoardServiceImpl implements BoardService {
	
	private static final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);
	private BoardDAO bdao;
	
	public BoardServiceImpl() {
		bdao = new BoardDAOImpl();
	}

	@Override
	public int inser(BoardVO bvo) {
		log.info("insert Service in!!");
		return bdao.insert(bvo);
	}


	@Override
	public BoardVO getDetail(int bno) {
		log.info("detail Service in!!");
		return bdao.detail(bno);
	}

	@Override
	public int update(BoardVO bvo) {
		log.info("update Service in!!");
		return bdao.update(bvo);
	}

	@Override
	public int delete(int bno) {
		//게시글을 지우기 전에 댓글을 삭제하고 글 지우기
		CommentServiceImpl csv = new CommentServiceImpl();
		int isOk = csv.removeAll(bno);
		log.info("comment removeAll", isOk);
		log.info("delete Service in!!");
		return bdao.delete(bno);
	}

	@Override
	public List<BoardVO> getList(PagingVO pgvo) {
		log.info("selectList Service in!!");
		return bdao.selectList(pgvo);
	}



	@Override
	public int getTotal(PagingVO pgvo) {
		log.info("totalCount Service in!!");
		return bdao.totalCount(pgvo);
	}

	@Override
	public String getFileName(int bno) {
		log.info("File Service in!!");
		return bdao.getFileName(bno);
	}

	@Override
	public int readCountUpdate(int bno) {
		// TODO Auto-generated method stub
		return bdao.readCountUpdate(bno);
	}




}
