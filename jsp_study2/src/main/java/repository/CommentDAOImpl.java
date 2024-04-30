package repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.CommentVO;
import orm.DatabaseBuilder;



public class CommentDAOImpl implements CommentDAO {
	private static final Logger log = LoggerFactory.getLogger(CommentDAOImpl.class);
	private SqlSession sql;
	
	public CommentDAOImpl() {
		new DatabaseBuilder();
		sql = DatabaseBuilder.getFactory().openSession();
	}

	@Override
	public int post(CommentVO cvo) {
		log.info("post DAO check 3!!");
		int isOk = sql.insert("CommentMapper.insert",cvo);
		
		if(isOk>0) {
			sql.commit();
		}
		return isOk;
	}

	@Override
	public List<CommentVO> list(int bno) {
		log.info("list DAO check 3!!");
		return sql.selectList("CommentMapper.list",bno);
	}

	@Override
	public int remove(int cno) {
		log.info("remove DAO check 3!!");
		int isOk = sql.delete("CommentMapper.del",cno);
		
		if(isOk>0) {sql.commit();}
		return isOk;
	}



	@Override
	public int modify(CommentVO cvo) {
		log.info("modify DAO check 3!!");
		int isOk = sql.update("CommentMapper.mod",cvo);
		
		if(isOk>0) {sql.commit();}
		return isOk;
	}

	@Override
	public int fileDel(int bno) {
		// TODO Auto-generated method stub
		int isOk = sql.delete("CommentMapper.Fdel",bno);
		if(isOk>=0) {sql.commit();}
		return isOk;
	}
}
