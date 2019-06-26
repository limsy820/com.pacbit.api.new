package com.bitkrx.api.board.control;

import com.bitkrx.api.board.Service.BoardService;
import com.bitkrx.api.board.vo.BoardVO;
import com.bitkrx.api.sample.service.SampleService;
import com.bitkrx.config.CmeResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping (value = "/bt/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @Autowired
    SampleService sampleService;

    @RequestMapping (value = "/selectBoardList.dm")
    public @ResponseBody
    CmeResultVO boardList(BoardVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        Map<String, Object> map = new HashMap<String, Object>();

        vo.setFirstIndex((vo.getPageIndex() - 1) * 10);
        vo.setRecordCountPerPage(vo.getPageUnit());

        List<BoardVO> list = boardService.selectBoardList(vo);

        int listCnt = boardService.getTotCount(vo);

        map.put("list", list);
        map.put("listCnt", listCnt);
        map.put("pageSize", (listCnt -1 ) / 10 + 1);
        map.put("pageIndex", vo.getPageIndex());
        map.put("pageUnit", vo.getPageUnit());


        resultVO.setData(map);
        resultVO.setResultMsg("게시판 리스트 조회 성공");
        resultVO.setResultStrCode("000");

        return resultVO;

    }


    @RequestMapping (value = "/selectBoardDetail.dm")
    public @ResponseBody
    CmeResultVO boardDetail(BoardVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        resultVO.setData(boardService.selectBoardDetail(vo));
        resultVO.setResultStrCode("000");
        return resultVO;
    }

    @RequestMapping (value = "/insUptBoardOne.dm")
    public @ResponseBody
    CmeResultVO insUptBoardOne(BoardVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        if("".equals(vo.getContentId())) {
            vo.setContentId(String.valueOf(boardService.selectNttMax()));
        }
        
        String userNm = sampleService.getUserName(vo.getRegUser());
        vo.setUserName(userNm);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("contentId", vo.getContentId());
        resultVO.setData(map);

        int res = boardService.insUptBoardOne(vo);
        if(res > 0) {
            resultVO.setResultStrCode("000");
            resultVO.setResultMsg("등록 및 수정 성공");
        } else {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("등록 및 수정 실패");
        }

        return resultVO;
    }

    @RequestMapping (value = "/delBoardOne.dm")
    public @ResponseBody
    CmeResultVO delBoardOne(BoardVO vo, HttpServletRequest request, HttpServletResponse response) throws Exception {

        CmeResultVO resultVO = new CmeResultVO();

        int res = boardService.deleteBoard(vo);
        if(res > 0) {
            resultVO.setResultStrCode("000");
            resultVO.setResultMsg("삭제 성공");
        } else {
            resultVO.setResultStrCode("-1");
            resultVO.setResultMsg("삭제 실패");
        }

        return resultVO;
    }
}
