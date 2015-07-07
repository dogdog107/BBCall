function onload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getparentlistJson.action",
		data : {},
		success : function(data) {
			if(data.getparentlistResult){
				var parentreferdoclist = data.parentreferdoclist;
				$.each(parentreferdoclist, function(i, n){
					$("#referdoc_parentno").append("<option value='"+n.referdoc_id+"'>"+n.referdoc_type+"</option>");
				});
			}else{
				$("#message").html("<font color=red>Page Fail ! "+data.errmsg+"</font>");
				$("#div_message").toggle();
			}
		}
	});
}

function referdoc_parentno_change() {
	if (parentno == 0 || parentno == null){
		document.getElementById('referdoc_getchildlist').submit();
	} else {
		
		document.getElementById('referdoc_parentno').value = parentno;
		document.getElementById('referdoc_getchildlist').submit();
	}
		
	
}

function referdoclist_update(formnum) {
	
	document.getElementById(formnum).submit();
}