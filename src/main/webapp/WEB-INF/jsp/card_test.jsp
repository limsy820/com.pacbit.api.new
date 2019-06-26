<%@ page contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html lang="en">
<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
<head>
</head>
<body>
<h2>${exchangeNm } API SERVER</h2>

<form id="frm" name="frm" method="POST" enctype="multipart/form-data" action="/card.uptCardRegComp.dp/proc.go">
    <table class="board_view">
        <colgroup>
            <col width="15%">
            <col width="*"/>
        </colgroup>
        <caption>카드 테스트</caption>
        <tbody>
        <tr>
            <th scope="row">이메일</th>
            <td><input type="text" id="userEmail" name="userEmail" class="wdp_90"/></td>
        </tr>
        <tr>
            <th scope="row">카드번호</th>
            <td><input type="text" id="cardNum" name="cardNum" class="wdp_90"/></td>
        </tr>
        <tr>
            <th scope="row">사용자이름</th>
            <td><input type="text" id="userNm" name="userNm" class="wdp_90"/></td>
        </tr>
        </tbody>
    </table>
    <input type="submit" value="등록">
    <br/><br/>
</form>

</body>
</html>

