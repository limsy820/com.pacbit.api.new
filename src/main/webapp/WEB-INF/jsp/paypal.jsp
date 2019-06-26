<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<script type="text/javascript" src="/js/jquery-3.1.0.min.js"></script>
<script type="text/javascript">

	$(document).ready(function(){
		//alert($("#amount").val());
		$("#paypal").submit();
	});
		
</script>
<body>

	<form id="paypal" action="https://www.sandbox.paypal.com/cgi-bin/webscr" method="post">

			<input type="hidden" name="cmd" value="_xclick"> 
			<input type="hidden" name="business" value="info-facilitator@cmesoft.co.kr"> 
			<!-- <input type="hidden" name="business" value="info@cmesoft.co.kr"> -->
			<input type="hidden" name="item_name" value="Content Name2">
			<input type="hidden" name="item_number" value="0000011"> 
			<input type="hidden" name="currency_code" value="USD"> 
			<input type="hidden" id="amount" name="amount" value="${param.amount }"> 
			<input type="hidden" name="custom" value="${param.userEmail }"> 
			<input type="hidden" name="charset" value="UTF-8"> 

	</form> 
	
</body>
</html>

