package com.bitkrx.api.board.dao;

import com.bitkrx.api.board.vo.BoardVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardDAO extends CmeComAbstractDAO {

    public List<BoardVO> selectBoardList(BoardVO vo) throws Exception {
        return list("BoardDAO.selectBoardList", vo);
    }

    public int getTotCount(BoardVO vo) throws Exception {
        return (int) selectByPk("BoardDAO.getTotCount", vo);
    }

    public BoardVO selectBoardDetail(BoardVO vo) throws Exception {
        return (BoardVO) selectByPk("BoardDAO.selectBoardOne", vo);
    }

    public int insUptBoardOne(BoardVO vo) throws Exception {
        return update("BoardDAO.insUptBoardOne", vo);
    }

    public int selectNttMax() throws Exception {
        return (int) selectByPk("BoardDAO.selectNttMax", null);
    }

    public int deleteBoard(BoardVO vo) throws Exception {
        return (int) delete("BoardDAO.deleteBoard", vo);
    }

    public BoardVO selectQuestion(BoardVO vo) throws Exception{
        return (BoardVO) selectByPk("BoardDAO.selectQuestion", vo);
    }
}
