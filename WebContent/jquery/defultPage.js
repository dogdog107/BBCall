/**
 * check username whether exist
 */
$(function() {
	switch (usertype) {
	case "1":
		$("#usertype").html("User");
		break;
	case "2":
		$("#usertype").html("Master");
		break;
	case "3":
		$("#usertype").html("Admin");
		break;
	case "4":
		$("#usertype").html("SuperAdmin");
		break;
	case "5":
		$("#usertype").html("Guest");
		break;
	}

	switch (status) {
	case "1":
		$("#status").html("Active");
		break;
	case "2":
		$("#status").html("Pause");
		break;
	case "3":
		$("#status").html("Pending");
		break;
	case "4":
		$("#status").html("Locked");
		break;
	}
});
// 获得当前时间,刻度为一千分一秒
var initializationTime = (new Date()).getTime();
function showTime() {
	var now = new Date();
	// var year = now.getFullYear();
	// var month = now.getMonth();
	// var day = now.getDate();
	// var hours = now.getHours();
	// var minutes = now.getMinutes();
	// var seconds = now.getSeconds();
	var string = now.toLocaleString();
	// document.all.showTime.innerHTML = "" + year + "年" + month + "月" + day +
	// "日 "
	// + hours + ":" + minutes + ":" + seconds + ""
	document.all.showTime.innerHTML = "" + string + ""
	// 一秒刷新一次显示时间
	var timeID = setTimeout(showTime, 1000);
}

function onload() {
	if (token == "" || token == null) {
		if (confirm('Session has been expired! Please re-login again.\n Click "OK" to return login page.')) {
			window.parent.frames.location.href="./login.jsp";
		}
		$("#message").html("<font color=red> Session has been expired! Please re-login again. </font>");
		$("#div_main").hide(300);
		$("#div_message").show(300).delay(10000).hide(300);
		return;
	}
	var initializationTime = (new Date()).getTime();
	showTime();
	$("#user_photo").attr("src", photourl + "?" + Math.random());
}
