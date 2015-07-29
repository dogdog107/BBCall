function onload() {
	showAccess(function() {
		showAGA();
	});
}

function showAccess(callback) {
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/access_getAccessListJson.action",
		data : {
			"token" : token
		},
		success : function(data) {
			if (data.result) {
				// 拿出列表
				var accessList = data.accesslist;
				if (accessList !='' && accessList != null) {
					var list2 = $("#list2");
					$.each(accessList, function(i, n) {
//						var row = new Option();
//						row.value = n.accesslist_id;
//						row.title = n.accesslist_description;
//						row.text = n.accesslist_name;
						var row = $("<option value='"+ n.accesslist_id + "' title='" + n.accesslist_description + "'>"+ n.accesslist_name + "</option>").hide();
						list2.append(row);   //为Select追加一个Option(下拉项)
						row.show(300);
					});
					SortD(document.getElementById("list2"));
				}
			} else {
				//隐藏分页条
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
			if (typeof (callback) == "function") {
				callback();
			}
		}
	});
}

function showAGA(){
	$
	.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/access_getAccessByAccessGroupJson.action",
		data : {
			"token" : token,
			"accessgroup_name" : accessGroupName
		},
		success : function(data) {
			if (data.result) {
				// 拿出列表
				var accessList = data.accesslist;
				if (accessList !='' && accessList != null) {
					var list1 = $("#list1");
					var list2 = $("#list2");
					$.each(accessList, function(i, n) {
						var row = $("<option value='"+ n.accesslist_id + "' title='" + n.accesslist_description + "'>"+ n.accesslist_name + "</option>").hide();
						list1.append(row);   //为Select追加一个Option(下拉项)
						$("#list2 option[value='" + n.accesslist_id + "']").remove(); //删除Select中Text='4'的Option
						row.show(300);
					});
					SortD(document.getElementById("list1"));
				}
			} else {
				//隐藏分页条
				$("#message").html(
						"<font color=red>Page Fail ! " + data.errmsg
								+ "</font>");
				$("#div_message").show(300).delay(10000).hide(300);
			}
		}
	});
}

function showDesc(box){
	for (var i = 0; i < box.options.length; i++) {
		if (box.options[i].selected && box.options[i].value != "") {
			$("#access_desc").hide().text(box.options[i].title).show(300);
		}
	}
}

function validate(){
	$("#list1 option").attr("selected","selected");
	$("#list2 option").attr("selected",false);
	return true;
}