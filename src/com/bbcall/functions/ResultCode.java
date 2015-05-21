package com.bbcall.functions;

public class ResultCode {
	
	public static Integer SUCCESS = 0; //成功
	public static Integer UNKNOWN_ERROR = -1;//未知错误
	
	//用户错误码10XXX
	public static Integer USERNAME_ERROR = 10001;//用户名错误
	public static Integer PASSWORD_ERROR = 10002;//密码错误
	public static Integer USERTOKEN_ERROR = 10003;//用户token错误
	public static Integer USERTOKEN_EXPIRED = 10004;//用户token错误
	public static Integer USERID_ERROR = 10005;//用户id不存在
	public static Integer USERNAME_EXIST = 10006;//用户名已被使用
	public static Integer REGISTERINFO_NOTENOUGH = 10007;//注册信息不全
	public static Integer REGISTERINFO_TYPEERROR = 10008;//注册的用户类型错误
	public static Integer REQUIREINFO_NOTENOUGH = 10009;//请求的参数不全
	public static Integer USERSTATUS_ERROR = 10010;//用户状态错误
		
	//用户返回码11XXX
	public static Integer USERNAME_NOTEXIST = 11001;//用户名可以使用
	public static Integer USERNAME_ACCOUNT = 11002;//帐号用户名
	public static Integer USERNAME_MOBILE = 11003;//手机号用户名
	public static Integer USERNAME_EMAIL = 11004;//邮箱用户名
	
	//用户状态码12XXX
	public static Integer USERSTATUS_ACTIVE = 12001;//Active
	public static Integer USERSTATUS_PAUSE = 12002;//Pause
	public static Integer USERSTATUS_PENDING = 12003;//Pending
	public static Integer USERSTATUS_LOCKED = 12004;//Locked
	
	//新闻错误码20XXX
	public static Integer NEWSID_ERROR = 20001;//新闻id不存在
	public static Integer NEWSCATEGORY_ERROR = 20002;//新闻分类id不存在
	
	//视频错误码21XXX
	public static Integer VIDEOID_ERROR = 21001;//视频id不存在
	public static Integer VIDEOCATEGORY_ERROR = 21002;//视频分类id不存在
	
	//论坛错误码3XXXX
	public static Integer NODEID_ERROR = 30001;//论坛id不存在
	public static Integer TOPICID_ERROR = 31001;//主题id不存在
	
	//版本错误码90XXX
	public static Integer VERSIONCODE_ERROR = 90001;//版本号有误
	
	
	//根据resultcode返回错误信息
	public static String getErrmsg(Integer resultCode){
		
		String errmsg = "";
		switch (resultCode){
			case 0: errmsg = "success"; break;
			case -1: errmsg = "unknown error"; break;
			
			//用户类
			case 10001: errmsg = "invalid username"; break;
			case 10002: errmsg = "wrong password"; break;
			case 10003: errmsg = "invalid token"; break;
			case 10004: errmsg = "expired token"; break;
			case 10005: errmsg = "invalid user_id"; break;
			case 10006: errmsg = "username exist"; break;
			case 10007: errmsg = "register info not enough"; break;
			case 10008: errmsg = "register user type error"; break;
			case 10009: errmsg = "require info not enough"; break;
			case 10010: errmsg = "user status error"; break;
			
			case 11001: errmsg = "username not exist"; break;
			case 11002: errmsg = "account username"; break;
			case 11003: errmsg = "mobile username"; break;
			case 11004: errmsg = "email username"; break;
			
			case 12001: errmsg = "active status"; break;
			case 12002: errmsg = "pause status"; break;
			case 12003: errmsg = "pending status"; break;
			case 12004: errmsg = "locked status"; break;
			
			//新闻类
			case 20001: errmsg = "invalid news_id"; break;
			case 20002: errmsg = "invalid news_category"; break;
			
			//视频类
			case 21001: errmsg = "invalid video_id"; break;
			case 21002: errmsg = "invalid video_category"; break;
			
			//论坛类
			case 30001: errmsg = "invalid node_id"; break;
			case 31001: errmsg = "invalid topic_id"; break;
			
			//版本类
			case 90001: errmsg = "version_code error"; break;
		}
		return errmsg;
	}
}