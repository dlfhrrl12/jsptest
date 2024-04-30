package service;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardService {

	int inser(BoardVO bvo);


	BoardVO getDetail(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	List<BoardVO> getList(PagingVO pgvo);


	int getTotal(PagingVO pgvo);


	String getFileName(int bno);


	int readCountUpdate(int bno);
	
}
