<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
<head>


<script type="text/javascript">


</script>

</head>
<body>
<h2>${exchangeNm } API SERVER</h2>

<form id="frm" name="frm" method="POST" enctype="multipart/form-data" action="/bt.auth.uploadKycFile.dp/proc.go">
    <table class="board_view">
        <colgroup>
            <col width="15%">
            <col width="*"/>
        </colgroup>
        <caption>게시글 작성</caption>
        <tbody>
        <tr>
            <th scope="row">제목</th>
            <td><input type="text" id="userEmail" name="userEmail" class="wdp_90"/></td>
        </tr>
        <tr>
            <th scope="row">파일순번</th>
            <td><input type="text" id="fileSn" name="fileSn" class="wdp_90"/></td>
        </tr>
        <tr>
            <th scope="row">전화번호</th>
            <td><input type="text" id="userMobile" name="userMobile" class="wdp_90"/></td>
        </tr>
        <tr>
            <th scope="row">부모코드</th>
            <td><input type="text" id="certGrade" name="certGrade" class="wdp_90"/></td>
        </tr>
        <tr>
            <th scope="row">자식코드</th>
            <td><input type="text" id="certSubGrade" name="certSubGrade" class="wdp_90"/></td>
        </tr>
        <tr>
            <th scope="row">파일ID</th>
            <td><input type="text" id="fileId" name="fileId" class="wdp_90"/></td>
        </tr>
        <tr>
            <td colspan="2" class="view_text">
                <textarea rows="20" cols="100" title="내용" id="certMsg" name="certMsg"></textarea>
            </td>
        </tr>
        </tbody>
    </table>
    <input type="file" name="file1">
    <input type="file" name="file2">
    <input type="file" name="file3">
    <input type="submit" value="작성하기">
    <br/><br/>

    26<img src="C:\\home\\kyc\\FID000000000000043_1.jpg"/>
    27<img src="/home/kyc/FID000000000000043_2.jpg"/>
    28<img src="/home/web/webapp/com.planbit.api/kyc/FID000000000000028_1.jpg"/>
    29<img src="/home/web/webapp/com.planbit.api/kyc/FID000000000000029_1.jpg"/>
</form>

</body>
</html>

