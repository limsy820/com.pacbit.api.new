package com.bitkrx.api.block.service;

import com.bitkrx.api.block.vo.BlockChainVO;

import java.util.List;

public interface BlockChainService {

    public List<BlockChainVO> selectEthBlockList(BlockChainVO vo) throws Exception;

    public int uptEthBlockUseYn(BlockChainVO vo) throws Exception;
}
