function checkpwd(id) {
	childobj = document.getElementById(id);
	if (childobj.value != document.getElementById('prepassword').value) {
		childobj.value = '';
		childobj.style.borderColor = 'red';
		document.getElementById('pwdnotice').innerHTML = '两次的密码不一致！请重新输入。';
		document.getElementById('pwdnotice').style.color = 'red';
	} else {
		childobj.style.borderColor = '#00e500';
		document.getElementById('pwdnotice').innerHTML = '密码正确。';
		document.getElementById('pwdnotice').style.color = 'green';
	}
	return;
}


function onload() {	
	if(usertype == 1 || usertype == 2){
		document.getElementById('token').disabled="";
		document.getElementById('token_tr').style.display="";
		document.getElementById('usertype').options.remove(3);
	}
	
	if(usertype == 3){
		document.getElementById('userid').disabled="";
		document.getElementById('userid_tr').style.display="";
	}
	
	document.getElementById('usertype')[usertype].selected=true;
	document.getElementById('gender')[gender].selected=true;
}