package com.bitkrx.api.board.Service.impl;

import com.bitkrx.api.board.Service.BoardService;
import com.bitkrx.api.board.dao.BoardDAO;
import com.bitkrx.api.board.vo.BoardVO;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.dbinfo.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardDAO boardDAO;

    @CommonDataSource(DataSourceType.OPRBOARD)
    public List<BoardVO> selectBoardList(BoardVO vo) throws Exception {
        return boardDAO.selectBoardList(vo);
    }


    @CommonDataSource(DataSourceType.OPRBOARD)
    public int getTotCount(BoardVO vo) throws Exception {
        return boardDAO.getTotCount(vo);
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    @Override
    public BoardVO selectBoardDetail(BoardVO vo) throws Exception {
        return boardDAO.selectBoardDetail(vo);
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    public int insUptBoardOne(BoardVO vo) throws Exception {
        return boardDAO.insUptBoardOne(vo);
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    public int selectNttMax() throws Exception {
        return boardDAO.selectNttMax();
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    public int deleteBoard(BoardVO vo) throws Exception {
        return boardDAO.deleteBoard(vo);
    }

    @CommonDataSource(DataSourceType.OPRBOARD)
    @Override
    public BoardVO selectQuestion(BoardVO vo) throws Exception{
        return boardDAO.selectQuestion(vo);
    }
}
