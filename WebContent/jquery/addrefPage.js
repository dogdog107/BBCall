


function referdoc_level_change() {
	var level = document.getElementById('referdoc_level').value;
	
	if (level == 2) {
		document.getElementById('parentno_tr').style.display="";
		document.getElementById('price_tr').style.display="";
		
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
	} else {
		document.getElementById('parentno_tr').style.display="none";
		document.getElementById('price_tr').style.display="none";
	}
}