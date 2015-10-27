global_parentno = '';


//调用pagechange方法
function pagechange(pagenum){
	getlist(global_parentno, pagenum);
}

function onload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getparentlistJson.action",
		data : {},
		success : function(data) {
			if(data.result){
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
	getlist(global_parentno);
}

function getlist(parentno,pagenum) {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getlistJson.action",
		data : {
			"referdoc_parentno" : parentno,
			"pagenum" : pagenum
		},
		success : function(data) {
			if (data.result) {
				//*初始化分页条
				if (data.lastPageNum <= 1){
					//当只有一页时隐藏分页条
					$("#page_bar").hide(300);
				} else {
					//显示分页条
					$("#page_bar").show(300);
					//当前页页码
					$("#xiye").html(data.currentPageNum);
					//尾页页码
					$("#mo").html(data.totalpagesNum);
				}
				// 清除现有列表
//				$("#datas").hide();
				$("tr[id^='referdoc_']").remove();
				// 拿出列表
				var templist = data.referdoclist;
				
				$.each(templist, function(i, n) {
					var row = $("#template").clone();
					row.find("#referid").val(n.referdoc_id);
					row.find("#referid").attr("id","referid_"+n.referdoc_id);
					if (n.referdoc_level == 1) {
						row.find("#referlevel1").text("一級項");
						row.find("#referdelete").attr("style", "display:none");
					} else {
//						row.find("#referlevel1").text("┣");
						row.find("#referlevel2").text("二級項");
						row.find("#referdelete").attr("onclick", "location.href='referdoc_delete.action?referdoc_id=" + n.referdoc_id + "&pagenum='");
					}
//					row.find("#referlevel").attr("id","referlevel_"+n.referdoc_id);
					row.find("#referparentno").val(n.referdoc_parentno);
					row.find("#referparentno").attr("id","referparentno_"+n.referdoc_id);
					row.find("#refertype").val(n.referdoc_type);
					row.find("#refertype").attr("id","refertype_"+n.referdoc_id);
					row.find("#referprice").val(n.referdoc_price);
					row.find("#referprice").attr("id","referprice_"+n.referdoc_id);
					if (n.referdoc_flag) {
						row.find("#referflag").html("<select name='referdoc_flag' id='referdoc_flag_" + n.referdoc_id + "'> <option id='select1' value='true' selected='selected'>true</option> <option id='select2' value='false'>false</option>");
					} else {
						row.find("#referflag").html("<select name='referdoc_flag' id='referdoc_flag_" + n.referdoc_id + "'> <option id='select2' value='false' selected='selected'>false</option> <option id='select1' value='true'>true</option>");
					}
					row.find("#referflag").attr("id","referflag_"+n.referdoc_id);
					
					row.find("#referupdate").attr("onclick", "referdoclist_update("+n.referdoc_id+")");
					
					row.attr("id", "referdoc_" + n.referdoc_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle(300);
				});
				// 显示数据
//				$("#datas").show(300);
			} else {
				//隐藏分页条
				$("#page_bar").hide(300);
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
}

function referdoc_parentno_change() {
	global_parentno = $("#referdoc_parentno").val();
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_getchildlist2Json.action",
		data : {
			"referdoc_parentno" : global_parentno
		},
		success : function(data) {
			if (data.result) {
				//*初始化分页条
				if (data.lastPageNum <= 1){
					//当只有一页时隐藏分页条
					$("#page_bar").hide(300);
				} else {
					//显示分页条
					$("#page_bar").show(300);
					//当前页页码
					$("#xiye").html(data.currentPageNum);
					//尾页页码
					$("#mo").html(data.totalpagesNum);
				}
				// 清除现有列表
//				$("#datas").hide();
				$("tr[id^='referdoc_']").remove();
				// 拿出列表
				var templist = data.referdoclist;
				
				$.each(templist, function(i, n) {
					var row = $("#template").clone();
					row.find("#referid").val(n.referdoc_id);
					row.find("#referid").attr("id","referid_"+n.referdoc_id);
					if (n.referdoc_level == 1) {
						row.find("#referlevel1").text("一級項");
						row.find("#referdelete").attr("style", "display:none");
					} else {
//						row.find("#referlevel1").text("┣");
						row.find("#referlevel2").text("二級項");
						row.find("#referdelete").attr("onclick", "location.href='referdoc_delete.action?referdoc_id=" + n.referdoc_id + "&pagenum='");
					}
//					row.find("#referlevel").attr("id","referlevel_"+n.referdoc_id);
					row.find("#referparentno").val(n.referdoc_parentno);
					row.find("#referparentno").attr("id","referparentno_"+n.referdoc_id);
					row.find("#refertype").val(n.referdoc_type);
					row.find("#refertype").attr("id","refertype_"+n.referdoc_id);
					row.find("#referprice").val(n.referdoc_price);
					row.find("#referprice").attr("id","referprice_"+n.referdoc_id);
					if (n.referdoc_flag) {
						row.find("#referflag").html("<select name='referdoc_flag' id='referdoc_flag_" + n.referdoc_id + "'> <option id='select1' value='true' selected='selected'>true</option> <option id='select2' value='false'>false</option>");
					} else {
						row.find("#referflag").html("<select name='referdoc_flag' id='referdoc_flag_" + n.referdoc_id + "'> <option id='select2' value='false' selected='selected'>false</option> <option id='select1' value='true'>true</option>");
					}
					row.find("#referflag").attr("id","referflag_"+n.referdoc_id);
					
					row.find("#referupdate").attr("onclick", "referdoclist_update("+n.referdoc_id+")");
					row.attr("id", "referdoc_" + n.referdoc_id);// 改变绑定好数据的行的id
					row.appendTo("#datas");// 添加到模板的容器中
					row.toggle(300);
				});
				// 显示数据
//				$("#datas").show(300);
			} else {
				//隐藏分页条
				$("#page_bar").hide(300);
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
//	document.getElementById('referdoc_getchildlist').submit();
	
}

function referdoclist_update(formnum) {
	var refid = document.getElementById("referid_"+formnum).value;
	var refparentno = document.getElementById("referparentno_"+formnum).value;
	var refprice = document.getElementById("referprice_"+formnum).value;
	var refflag = document.getElementById("referflag_"+formnum).value;
	var pagenumber = document.getElementById("xiye").text;
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/referdoc_updateJson.action",
		data : {
			"referdoc_id" : refid,
			"referdoc_parentno" : refparentno,
			"referdoc_price" : refprice,
			"referdoc_flag" : refflag,
			"pagenum" : pagenumber
		},
		success : function(data) {
			if (data.result) {
//				//*初始化分页条
//				if (data.lastPageNum <= 1){
//					//当只有一页时隐藏分页条
//					$("#page_bar").hide(300);
//				} else {
//					//显示分页条
//					$("#page_bar").show(300);
//					//当前页页码
//					$("#xiye").html(data.currentPageNum);
//					//尾页页码
//					$("#mo").html(data.totalpagesNum);
//				}
//				// 清除现有列表
////				$("#datas").hide();
//				$("tr[id^='referdoc_']").remove();
//				// 拿出列表
//				var templist = data.referdoclist;
//				
//				$.each(templist, function(i, n) {
//					var row = $("#template").clone();
//					row.find("#referid").val(n.referdoc_id);
//					row.find("#referid").attr("id","referid_"+n.referdoc_id);
//					if (n.referdoc_level == 1) {
//						row.find("#referlevel1").text("一級項");
//						row.find("#referdelete").attr("style", "display:none");
//					} else {
//						row.find("#referlevel2").text("二級項");
//						row.find("#referdelete").attr("onclick", "location.href='referdoc_delete.action?referdoc_id=" + n.referdoc_id + "&pagenum='");
//					}
//					row.find("#referlevel").attr("id","referlevel_"+n.referdoc_id);
//					row.find("#referparentno").val(n.referdoc_parentno);
//					row.find("#referparentno").attr("id","referparentno_"+n.referdoc_id);
//					row.find("#refertype").val(n.referdoc_type);
//					row.find("#refertype").attr("id","refertype_"+n.referdoc_id);
//					row.find("#referprice").val(n.referdoc_price);
//					row.find("#referprice").attr("id","referprice_"+n.referdoc_id);
//					if (n.referdoc_flag) {
//						row.find("#referflag").html("<select name='referdoc_flag' id='referdoc_flag_" + n.referdoc_id + "'> <option id='select1' value='true' selected='selected'>true</option> <option id='select2' value='false'>false</option>");
//					} else {
//						row.find("#referflag").html("<select name='referdoc_flag' id='referdoc_flag_" + n.referdoc_id + "'> <option id='select2' value='false' selected='selected'>false</option> <option id='select1' value='true'>true</option>");
//					}
//					row.find("#referflag").attr("id","referflag_"+n.referdoc_id);
//					
//					row.find("#referupdate").attr("onclick", "referdoclist_update("+n.referdoc_id+")");
//					row.attr("id", "referdoc_" + n.referdoc_id);// 改变绑定好数据的行的id
//					row.appendTo("#datas");// 添加到模板的容器中
//					row.toggle(300);
//				});
				// 显示数据
//				$("#datas").show(300);
				var typeId = "refertype_"+formnum;
				$("#" + typeId).attr("style", "border:solid #55AA00 2px;");
				$("#" + typeId).show(300);
				$("#message").html("<font color=green> (ID:"+ formnum +") Update Success ! </font>");
				$("#div_message").show(300).delay(5000).hide(300);
				
			} else {
				//隐藏分页条
				$("#page_bar").hide(300);
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
}

