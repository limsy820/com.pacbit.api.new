package com.bitkrx.api.rank.service.impl;

import com.bitkrx.api.rank.dao.RankDAO;
import com.bitkrx.api.rank.service.RankService;
import com.bitkrx.api.rank.vo.RankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankServiceImpl implements RankService {

    @Autowired
    RankDAO rankDAO;

    @Override
    public List<RankVO> getRankList(RankVO vo) throws Exception {
        return rankDAO.getRankList(vo);
    }

}
