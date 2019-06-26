package com.bitkrx.api.board.Service;

import com.bitkrx.api.board.vo.BoardVO;

import java.util.List;

public interface BoardService {

    List<BoardVO> selectBoardList(BoardVO vo) throws Exception;

    int getTotCount(BoardVO vo) throws Exception;

    BoardVO selectBoardDetail(BoardVO vo) throws Exception;

    int insUptBoardOne(BoardVO vo) throws Exception;

    int selectNttMax() throws Exception;

    int deleteBoard(BoardVO vo) throws Exception;

    BoardVO selectQuestion(BoardVO vo) throws Exception;
}
