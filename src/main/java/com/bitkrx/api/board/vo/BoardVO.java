package com.bitkrx.api.board.vo;

import com.bitkrx.config.vo.CmeExtendListVO;

public class BoardVO extends CmeExtendListVO {

    private String boardId = "";					/*게시판 아이디*/
    private String boardName = "";					/*게시판 이름*/
    private String boardDesc ="";					/*게시판 설명*/
    private String useYn = "";						/*사용 여부*/
    private String replyYn = "";					/*게시판 답변 사용 여부*/
    private String commentYn="";					/*게시판 댓글 사용 여부 */
    private String regYn="";						/*등록버튼 여부*/
    private String writerYn="";					/*작성자 노출 여부*/
    private String fileYn ="";						/*파일 업로드 사용 여부*/
    private String fileId ="";						/*게시판파일아이디*/
    private String noticeYn = "";					/*공지 게시판 여부*/
    private String noticeVwYn ="";				/*공지 노출 여부*/
    private String noticeCnt = "";					/*상위 공지 리스트 개수*/
    private String editorYn = "";					/*에디터 사용 여부*/
    private String regDateYn = "";					/*작성일자 출력 여부*/
    private String catViewYn = "";				/*카테고리 사용 여부*/
    private String newIconYn="";					/*새글 아이콘 여부*/
    private String secretUseYn ="";				/*비밀글 여부*/
    private String regEmail ="";					/*게시판 생성 email*/
    private String regDt ="";						/*게시판 생성일 */
    private String uptEmail ="";					/*게시판 수정 eamil*/
    private String uptDt="";						/*게시판 수정일*/
    private String regIp ="";						/*게시판 생성 IP*/
    private String uptIp ="";						/*게시판 수정 IP */

    private String stdDate = "";
    private String endDate = "";
    private String no = "";

    /*tbBoard 게시판 기본 정보 */
    private String contentId = "";					/*게시글 번호*/
    private String boardTitle = "";				/*게시글 이름*/
    private String contentMsg = "";				/*게시글 내용*/
    private Integer readCnt = 0;					/*조회수*/
    private String boardPwd = "";					/*게시글 비밀번호*/

    private String secretYn= "";					/*비밀글 여부*/
    private String commentSn ="";					/*댓글 순서*/
    private String noticeFromDt ="";				/*공지 게시판 언제까지*/
    private String noticeToDt ="";				/*공지 게시판 언제부터*/
    private String regUser= "";					/*작성자 아이디*/
    private String userPhone="";					/*작성자 전화번호*/
    private String userName="";					/*작성자 이름*/

    /*TBBOARDCATLIST & TBBOARDCATMST 게시판별 옵션 정보*/
    private String catMstId = "";					/*옵션 부모 코드*/
    private String catMstName = "";				/*옵션 부모 이름 */
    private String catId = "";						/*옵션 자식 코드*/
    private String catName = "";					/*옵션 이름*/
    private String catType ="";					/*옵션 타입 01:셀렉트 02:체크박스 03:라디오*/
    private String chileCatName="";				/*자식코드이름 array*/


    /*TBBOARDREPLY 테이블*/
    private String replyId ="";					/*답변아이디*/
    private String replyTitle ="";					/*답변내용*/
    private String replyPwd ="";					/*답변 암호*/
    private String replyMsg="";

    /*TBATCHFILEMST , TBATCHFILEDETAIL 파일 테이블*/
    private String atchFileId = "";				/*업로드파일아이디*/
    private String upFileMstId ="";			/*파일 업로드 마스터 id*/

    private String fileSn = "";					/*파일 번호*/
    private String fileStrePath ="";				/*파일 경로*/
    private String fileSaveName="";				/*파일 저장 이름*/
    private String fileOrgName ="";				/*파일 원본 이름*/
    private String mainYn ="";

    private String lang ="";
    private int cnt = 0;
    private String tableNm ="";

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDesc() {
        return boardDesc;
    }

    public void setBoardDesc(String boardDesc) {
        this.boardDesc = boardDesc;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getReplyYn() {
        return replyYn;
    }

    public void setReplyYn(String replyYn) {
        this.replyYn = replyYn;
    }

    public String getCommentYn() {
        return commentYn;
    }

    public void setCommentYn(String commentYn) {
        this.commentYn = commentYn;
    }

    public String getRegYn() {
        return regYn;
    }

    public void setRegYn(String regYn) {
        this.regYn = regYn;
    }

    public String getWriterYn() {
        return writerYn;
    }

    public void setWriterYn(String writerYn) {
        this.writerYn = writerYn;
    }

    public String getFileYn() {
        return fileYn;
    }

    public void setFileYn(String fileYn) {
        this.fileYn = fileYn;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getNoticeYn() {
        return noticeYn;
    }

    public void setNoticeYn(String noticeYn) {
        this.noticeYn = noticeYn;
    }

    public String getNoticeVwYn() {
        return noticeVwYn;
    }

    public void setNoticeVwYn(String noticeVwYn) {
        this.noticeVwYn = noticeVwYn;
    }

    public String getNoticeCnt() {
        return noticeCnt;
    }

    public void setNoticeCnt(String noticeCnt) {
        this.noticeCnt = noticeCnt;
    }

    public String getEditorYn() {
        return editorYn;
    }

    public void setEditorYn(String editorYn) {
        this.editorYn = editorYn;
    }

    public String getRegDateYn() {
        return regDateYn;
    }

    public void setRegDateYn(String regDateYn) {
        this.regDateYn = regDateYn;
    }

    public String getNewIconYn() {
        return newIconYn;
    }

    public void setNewIconYn(String newIconYn) {
        this.newIconYn = newIconYn;
    }

    public String getCatViewYn() {
        return catViewYn;
    }

    public void setCatViewYn(String catViewYn) {
        this.catViewYn = catViewYn;
    }


    public String getSecretUseYn() {
        return secretUseYn;
    }

    public void setSecretUseYn(String secretUseYn) {
        this.secretUseYn = secretUseYn;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public String getRegDt() {
        return regDt;
    }

    public void setRegDt(String regDt) {
        this.regDt = regDt;
    }

    public String getUptEmail() {
        return uptEmail;
    }

    public void setUptEmail(String uptEmail) {
        this.uptEmail = uptEmail;
    }

    public String getUptDt() {
        return uptDt;
    }

    public void setUptDt(String uptDt) {
        this.uptDt = uptDt;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getUptIp() {
        return uptIp;
    }

    public void setUptIp(String uptIp) {
        this.uptIp = uptIp;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    public String getContentMsg() {
        return contentMsg;
    }

    public void setContentMsg(String contentMsg) {
        this.contentMsg = contentMsg;
    }

    public Integer getReadCnt() {
        return readCnt;
    }

    public void setReadCnt(Integer readCnt) {
        this.readCnt = readCnt;
    }

    public String getBoardPwd() {
        return boardPwd;
    }

    public void setBoardPwd(String boardPwd) {
        this.boardPwd = boardPwd;
    }

    public String getSecretYn() {
        return secretYn;
    }

    public void setSecretYn(String secretYn) {
        this.secretYn = secretYn;
    }

    public String getCommentSn() {
        return commentSn;
    }

    public void setCommentSn(String commentSn) {
        this.commentSn = commentSn;
    }

    public String getNoticeFromDt() {
        return noticeFromDt;
    }

    public void setNoticeFromDt(String noticeFromDt) {
        this.noticeFromDt = noticeFromDt;
    }

    public String getNoticeToDt() {
        return noticeToDt;
    }

    public void setNoticeToDt(String noticeToDt) {
        this.noticeToDt = noticeToDt;
    }

    public String getRegUser() {
        return regUser;
    }

    public void setRegUser(String regUser) {
        this.regUser = regUser;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCatMstId() {
        return catMstId;
    }

    public void setCatMstId(String catMstId) {
        this.catMstId = catMstId;
    }

    public String getCatMstName() {
        return catMstName;
    }

    public void setCatMstName(String catMstName) {
        this.catMstName = catMstName;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatType() {
        return catType;
    }

    public void setCatType(String catType) {
        this.catType = catType;
    }

    public String getChileCatName() {
        return chileCatName;
    }

    public void setChileCatName(String chileCatName) {
        this.chileCatName = chileCatName;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyTitle() {
        return replyTitle;
    }

    public void setReplyTitle(String replyTitle) {
        this.replyTitle = replyTitle;
    }

    public String getReplyPwd() {
        return replyPwd;
    }

    public void setReplyPwd(String replyPwd) {
        this.replyPwd = replyPwd;
    }

    public String getReplyMsg() {
        return replyMsg;
    }

    public void setReplyMsg(String replyMsg) {
        this.replyMsg = replyMsg;
    }

    public String getAtchFileId() {
        return atchFileId;
    }

    public void setAtchFileId(String atchFileId) {
        this.atchFileId = atchFileId;
    }

    public String getUpFileMstId() {
        return upFileMstId;
    }

    public void setUpFileMstId(String upFileMstId) {
        this.upFileMstId = upFileMstId;
    }

    public String getFileSn() {
        return fileSn;
    }

    public void setFileSn(String fileSn) {
        this.fileSn = fileSn;
    }

    public String getFileStrePath() {
        return fileStrePath;
    }

    public void setFileStrePath(String fileStrePath) {
        this.fileStrePath = fileStrePath;
    }

    public String getFileSaveName() {
        return fileSaveName;
    }

    public void setFileSaveName(String fileSaveName) {
        this.fileSaveName = fileSaveName;
    }

    public String getFileOrgName() {
        return fileOrgName;
    }

    public void setFileOrgName(String fileOrgName) {
        this.fileOrgName = fileOrgName;
    }

    public String getMainYn() {
        return mainYn;
    }

    public void setMainYn(String mainYn) {
        this.mainYn = mainYn;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getTableNm() {
        return tableNm;
    }

    public void setTableNm(String tableNm) {
        this.tableNm = tableNm;
    }

    public String getStdDate() {
        return stdDate;
    }

    public void setStdDate(String stdDate) {
        this.stdDate = stdDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
