package repository;

import java.util.List;

import domain.BoardVO;
import domain.PagingVO;

public interface BoardDAO {

	int insert(BoardVO bvo);

	List<BoardVO> selectList(PagingVO pgvo);

	BoardVO detail(int bno);

	int update(BoardVO bvo);

	int delete(int bno);

	int totalCount(PagingVO pgvo);

	String getFileName(int bno);

	int readCountUpdate(int bno);





}
