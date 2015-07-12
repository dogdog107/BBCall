
function onload() {
	$('#statusOpr').val(status);
	$('#statusOpr').attr("id", "statusOpr_" + userid);
}

$(function() {

	switch (usertype) {
	case "1":
		$('#usertype').text("User");
		break;
	case "2":
		$('#usertype').text("Master");
		break;
	case "3":
		$('#usertype').text("Admin");
		break;
	case "4":
		$('#usertype').text("SuperAdmin");
		break;
	}
	switch (gender) {
	case "1":
		$('#gender').text("Male");
		break;
	case "2":
		$('#gender').text("Female");
		break;
	}
});

function updateStatus(idname, value) {
	var userid = idname.split("_")[1];
	var defaultStatusName = "status_" + userid;
	if (confirm('確定要修改用戶(ID:'+ userid +')的狀態嗎？\n Confirm to change the status for user (ID:'+ userid +')?')) {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}/user_updateStatusJson.action",
			data : {
				"token" : token,
				"userid" : userid,
				"status" : value
			},
			success : function(data) {
				if (data.result) {
//					window.location.reload();

					$("#message").html("<font color=green> (ID:"+ userid +") Status Update Success ! </font>");
					$("#div_message").show(300).delay(3000).hide(300);
				} else {
					$("#message").html("<font color=red> (ID:"+ userid +") Status Update Failed ! </font>");
					$("#div_message").show(300).delay(3000).hide(300);
					alert("Update failed. " + data.errmsg);
				}
			}
		});
	} else {
		$('#' + idname).val(status);
	}

}