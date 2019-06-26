package com.bitkrx.api.block.service.impl;

import com.bitkrx.api.block.dao.BlockChainDAO;
import com.bitkrx.api.block.service.BlockChainService;
import com.bitkrx.api.block.vo.BlockChainVO;
import com.bitkrx.api.trade.service.DepositService;
import com.bitkrx.api.trade.vo.DepositVO;
import com.bitkrx.config.annotation.CommonDataSource;
import com.bitkrx.config.dbinfo.DataSourceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockChainServiceImpl implements BlockChainService {

    @Autowired
    BlockChainDAO blockChainDAO;

    @Autowired
    DepositService depositService;

    @Override
    @CommonDataSource(DataSourceType.BLOCKCHAIN)
    public List<BlockChainVO> selectEthBlockList(BlockChainVO vo) throws Exception {
        return blockChainDAO.selectEthBlockList(vo);
    }

    @Override
    @CommonDataSource(DataSourceType.BLOCKCHAIN)
    public int uptEthBlockUseYn(BlockChainVO vo) throws Exception {

        return blockChainDAO.uptEthBlockUseYn(vo);
    }
}
