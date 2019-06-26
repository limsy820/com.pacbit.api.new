package com.bitkrx.api.rank.dao;

import com.bitkrx.api.rank.vo.RankVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RankDAO extends CmeComAbstractDAO {

    public List<RankVO> getRankList(RankVO vo) {
        return (List<RankVO>)list("RankDAO.getRankList", vo);
    }
}
