


function onload() {
	var level = document.getElementById('referdoc_level').value;
	
		document.getElementById('parentno_tr').style.display="";
		document.getElementById('price_tr').style.display="";
		document.getElementById('flag_tr').style.display="";
		
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/referdoc_getparentlistJson.action",
			data : {},
			success : function(data) {
				if(data.result){
					var parentreferdoclist = data.parentreferdoclist;
					document.getElementById('referdoc_parentno').options.length=0;
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