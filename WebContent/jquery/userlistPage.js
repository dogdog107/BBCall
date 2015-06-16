function onload() {
	$.ajax({
		type : "post",
		url : "${pageContext.request.contextPath}/user_checkUserListJson.action",
		data : {
			"token" : token
		},
		success : function(data) {
			if(data.checkUserListResult){
				var userlist = data.userlist;
				$.each(userlist, function(i, n){
					var row = $("#template").clone();
                    row.find("#userid").text(n.user_id);
                    if(n.user_pic_url == ""){                    	
                    row.find("#picurl").html("<img src='' height='60' width='60'>");
                    }else{
                    row.find("#picurl").html("<img src=" + n.user_pic_url + " height='60' width='60'>");
                    }
                    row.find("#account").text(n.user_account);
                    row.find("#name").text(n.user_name);
                    row.find("#usertype").text(n.user_type);
                    row.find("#status").text(n.user_status);
                    row.find("#logintime").text(n.user_login_time);
                    row.find("#createtime").text(n.user_create_time);
//                    row.find("#OrderDate").text(ChangeDate(n.订购日期));
//                    if(n.发货日期!== undefined) row.find("#ShippedDate").text(ChangeDate(n.发货日期));
//                    row.find("#more").html("<a href=OrderInfo.aspx?id=" + n.订单ID + "&pageindex="+pageIndex+">&nbsp;More</a>");                    
                    row.attr("id","user" + n.user_id);//改变绑定好数据的行的id
                    row.appendTo("#datas");//添加到模板的容器中
                    row.toggle();
				});
			}else{
				$("#message").html("<font color=red>Page Fail ! "+data.errmsg+"</font>");
				$("#div_message").toggle();
			}
		}
	});
}