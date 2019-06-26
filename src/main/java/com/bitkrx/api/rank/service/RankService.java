package com.bitkrx.api.rank.service;

import com.bitkrx.api.rank.vo.RankVO;

import java.util.List;

public interface RankService {

    List<RankVO> getRankList(RankVO vo) throws Exception;

}
