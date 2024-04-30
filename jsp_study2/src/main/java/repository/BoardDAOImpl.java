package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import orm.DatabaseBuilder;



public class BoardDAOImpl implements BoardDAO {
	private static final Logger log = LoggerFactory.getLogger(BoardDAOImpl.class);
	
	private SqlSession sql;
	
	public BoardDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int insert(BoardVO bvo) {
		log.info("insert dao in!!");
		int isOk = sql.insert("BoardMapper.add", bvo);
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}



	@Override
	public BoardVO detail(int bno) {
		log.info("detail dao in!!");
		return sql.selectOne("BoardMapper.detail", bno);
	}

	@Override
	public int update(BoardVO bvo) {
		log.info("update dao in!!");
		int isOk = sql.update("BoardMapper.update",bvo);
		if(isOk>0) { sql.commit(); }
		return isOk;
	}

	@Override
	public int delete(int bno) {
		log.info("delete dao in!!");
		int isOk = sql.delete("BoardMapper.del",bno);
		if(isOk>0) {sql.commit();}
		return isOk;
	}

	@Override
	public List<BoardVO> selectList(PagingVO pgvo) {
		log.info("selectList dao in!!");
		return sql.selectList("BoardMapper.list", pgvo);
	}

	@Override
	public int totalCount(PagingVO pgvo) {
		log.info("totalCount dao in!!");
		return sql.selectOne("BoardMapper.cnt",pgvo);
	}

	@Override
	public String getFileName(int bno) {
		log.info("file dao in!!");
		return sql.selectOne("BoardMapper.file",bno);
	}

	@Override
	public int readCountUpdate(int bno) {
		int isOk = sql.update("BoardMapper.read",bno);
		if(isOk>0) {sql.commit();}
		return isOk;
	}





}
