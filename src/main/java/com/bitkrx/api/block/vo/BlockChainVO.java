package com.bitkrx.api.block.vo;

public class BlockChainVO {

    private String txId = "";
    private String txBlocknumber = "";
    private String txGas = "";
    private String txGasPrice = "";
    private String txFrom = "";
    private String txTo = "";
    private String txValue = "";
    private String useYn = "";
    private String createDate = "";
    private String exchangeCode = "";
    private String blockCmt = "";

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public String getTxBlocknumber() {
        return txBlocknumber;
    }

    public void setTxBlocknumber(String txBlocknumber) {
        this.txBlocknumber = txBlocknumber;
    }

    public String getTxGas() {
        return txGas;
    }

    public void setTxGas(String txGas) {
        this.txGas = txGas;
    }

    public String getTxGasPrice() {
        return txGasPrice;
    }

    public void setTxGasPrice(String txGasPrice) {
        this.txGasPrice = txGasPrice;
    }

    public String getTxFrom() {
        return txFrom;
    }

    public void setTxFrom(String txFrom) {
        this.txFrom = txFrom;
    }

    public String getTxTo() {
        return txTo;
    }

    public void setTxTo(String txTo) {
        this.txTo = txTo;
    }

    public String getTxValue() {
        return txValue;
    }

    public void setTxValue(String txValue) {
        this.txValue = txValue;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getExchangeCode() {
        return exchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        this.exchangeCode = exchangeCode;
    }

    public String getBlockCmt() {
        return blockCmt;
    }

    public void setBlockCmt(String blockCmt) {
        this.blockCmt = blockCmt;
    }
}
