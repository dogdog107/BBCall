/**
 * check username whether exist
 */
$(function() {
	$("#account").html(window.parent.account);
	$("#accountName").html(window.parent.account);
	$("#name").html(window.parent.name);
	$("#email").html(window.parent.email);
	$("#logintime").html(window.parent.logintime);
	$("#createtime").html(window.parent.createtime);
	$("#mobile").html(window.parent.mobile);
	$("#gender").html(window.parent.gender);
	$("#language").html(window.parent.language);
	$("#skill").html(window.parent.skill);
	$("#status").html(window.parent.status);
	$("#token").html(window.parent.token);
	switch (window.parent.type) {
	case "1":
		$("#type").html("用户");
		break;
	case "2":
		$("#type").html("师傅");
		break;
	case "3":
		$("#type").html("管理员");
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
