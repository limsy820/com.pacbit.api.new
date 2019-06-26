
function sesstionSubmit(frm, url){
	
	$("#"+frm).find("input").each(function(){
		alert($(this).val());
	});
	window.open("/bt.sessionTest.dp/proc.go", 'aaa', '');
	//location.href = url;
	
}