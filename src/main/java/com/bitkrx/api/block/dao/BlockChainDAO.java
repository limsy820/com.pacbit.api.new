package com.bitkrx.api.block.dao;

import com.bitkrx.api.block.vo.BlockChainVO;
import com.bitkrx.config.dao.CmeComAbstractDAO;
import jdk.nashorn.internal.ir.Block;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlockChainDAO extends CmeComAbstractDAO {

    public List<BlockChainVO> selectEthBlockList(BlockChainVO vo) throws Exception {

        return list("blockChainDAO.selectEthBlockList", vo);
    }

    public int uptEthBlockUseYn(BlockChainVO vo) throws Exception {

        return update("blockChainDAO.uptEthBlockUseYn", vo);
    }
}
