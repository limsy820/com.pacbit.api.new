package com.bitkrx.api.rank.controller;

import com.bitkrx.api.rank.service.RankService;
import com.bitkrx.api.rank.vo.RankVO;
import com.bitkrx.api.trust.vo.TrustCoinVO;
import com.bitkrx.config.CmeResultVO;
import com.bitkrx.config.control.CmeDefaultExtendController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/bt/rank")
public class RankController extends CmeDefaultExtendController {

    @Autowired
    RankService rankService;

    @RequestMapping(value = "/getRanking.dm")        // trust 신청
    @ResponseBody
    public CmeResultVO getRanking(RankVO vo , HttpServletRequest request , HttpServletResponse response) throws Exception {
        CmeResultVO resultVO = new CmeResultVO();
        Map<String, Object> resultMap = new HashMap();

        resultMap.put("failCd" , "");
        resultVO.setResultStrCode("000");
        resultVO.setResultMsg("Ranking 데이터 호출 성공");

        try {
            List<RankVO> rankList = rankService.getRankList(vo);

            /* 아이디 가리기 */
            for(int i = 0; i < rankList.size(); i++) {
                RankVO rankVO = rankList.get(i);

                String rankId = rankVO.getRankId();
                if(!"".equals(rankId)) {
                    String[] splitId = rankId.split("@");
                    String front;
                    String after;
                    if (splitId[0].length() > 2) {
                        front = splitId[0].substring(0, 2);
                        after = "";
                        for (int j = 0; j < splitId[0].length() - 2; j++) {
                            after += "*";
                        }
                    } else {
                        front = splitId[0].substring(0, 1);
                        after = "*";
                    }

                    rankVO.setRankId(front + after + "@" + splitId[1]);
                    rankList.set(i, rankVO);
                }
            }

            /* 테스트 코드 */
            /*List<RankVO> rankList = new ArrayList();
            for(int i = 0; i < 10; i ++) {
                RankVO rankVO = new RankVO();
                Random random = new Random();

                String rankId = "ID" + i + "@test.com";
                double todayAmt = random.nextInt(100) + random.nextFloat() * 10;
                double totalAmt = todayAmt + random.nextInt(100) + random.nextFloat() * 10;
                double totalPrc = random.nextInt(10000000) + 10000000;

                //rankVO.setRank("" + i);              // 순위
                rankVO.setRankId(rankId);            // 랭킹 아이디
                rankVO.setTodayAmt("" + todayAmt);   // 당일거래량
                rankVO.setTotalAmt("" + totalAmt);   // 거래량합계수량
                rankVO.setTotalPrc("" + totalPrc);   // 거래합계금액

                rankList.add(rankVO);
            }
            Collections.sort(rankList, new RankSort());

            for(int i = 0; i < rankList.size(); i++) {
                rankList.get(i).setRank("" + i);
            }*/
            /* 테스트 코드 끝 */

            resultMap.put("list", rankList);
        } catch(Exception e) {
            resultMap.put("failCd" , "-1");
            resultMap.put("errorMsg", e.toString());
            resultVO.setResultStrCode("000");
            resultVO.setResultMsg("Ranking 데이터 호출 실패");
        }

        resultVO.setData(resultMap);

        return resultVO;

    }

    /*class RankSort implements Comparator<RankVO> {
        @Override
        public int compare(RankVO t1, RankVO t2) {
            Double totalAmt1 = Double.parseDouble(t1.getTotalAmt());
            Double totalAmt2 = Double.parseDouble(t2.getTotalAmt());
            return totalAmt2.compareTo(totalAmt1);
        }
    }*/
}
