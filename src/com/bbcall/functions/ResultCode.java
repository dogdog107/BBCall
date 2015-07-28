package com.bbcall.functions;

public class ResultCode {

	public static Integer SUCCESS = 0; // 成功
	public static Integer UNKNOWN_ERROR = -1;// 未知错误

	// 用户错误码10XXX
	public static Integer USERNAME_ERROR = 10001;// 用户名错误
	public static Integer PASSWORD_ERROR = 10002;// 密码错误
	public static Integer USERTOKEN_ERROR = 10003;// 用户token错误
	public static Integer USERTOKEN_EXPIRED = 10004;// 用户token过期
	public static Integer USERID_ERROR = 10005;// 用户id不存在
	public static Integer USERNAME_EXIST = 10006;// 用户名已被使用
	public static Integer REQUIREINFO_NOTENOUGH = 10007;// 请求的参数不全
	public static Integer REQUIREINFO_ERROR = 10008;// 请求的参数错误
	public static Integer USERSTATUS_ERROR = 10009;// 用户状态错误
	public static Integer USERLIST_NULL = 10010;// 用户表不存在
	public static Integer REGISTERINFO_NOTENOUGH = 10011;// 注册信息不全
	public static Integer USERTYPE_ERROR = 10012;// 注册的用户类型错误
	public static Integer USERACCOUNT_ERROR = 10013;// 请求注册的帐号长度少于6位
	public static Integer USERPASSWORD_ERROR = 10014;// 请求注册的密码长度少于6位
	public static Integer USERMOBILE_ERROR = 10015;// 请求注册的手机号有误
	public static Integer USERGENDER_ERROR = 10016;// 请求注册的性别有误
	public static Integer USEREMAIL_ERROR = 10017;// 请求注册的邮箱地址有误
	public static Integer USERSKILLINFO_EXIST = 10018;// 用户技能信息已存在
	public static Integer USERSKILLINFO_NOTEXIST = 10019;// 用户技能信息不存在
	public static Integer USERSKILLINFO_ISSAME = 10020;// 用户技能信息相同
	public static Integer USERSKILLLIST_NULL = 10021;// 用户技能列表为空

	// 用户返回码11XXX
	public static Integer USERNAME_NOTEXIST = 11001;// 用户名可以使用
	public static Integer USERNAME_ACCOUNT = 11002;// 帐号用户名
	public static Integer USERNAME_MOBILE = 11003;// 手机号用户名
	public static Integer USERNAME_EMAIL = 11004;// 邮箱用户名

	// 用户状态码12XXX
	public static Integer USERSTATUS_ACTIVE = 12001;// Active
	public static Integer USERSTATUS_PAUSE = 12002;// Pause
	public static Integer USERSTATUS_PENDING = 12003;// Pending
	public static Integer USERSTATUS_LOCKED = 12004;// Locked

	// 地址错误码13XXX
	public static Integer ADDRESS_NULL = 13001;// 地址不存在
	public static Integer ADDRESS_NOTMATCH = 13002;// 地址码与地址名不一致
	public static Integer ADDRESSCODE_ERROR = 13003;// 地址码错误

	// 权限返回码14XXX
	public static Integer ACCESS_REJECT = 14001;// 拒绝访问
	public static Integer ACCESSGROUP_ERROR = 14002;// 权限组不存在
	public static Integer ACCESSGROUP_EXIST = 14003;// 权限组已存在

	// 参数数据返回码15XXX
	public static Integer REFERDOC_TYPE_NOTEXIST = 15001;// 参数订单类型不存在
	public static Integer REFERDOC_TYPE_TEXIST = 15002;// 参数订单类型存在

	// 参数数据错误码16XXX
	public static Integer REFERDOC_TYPE_EMPTY = 16001;// 参数订单类型为空
	public static Integer REFERDOC_ADD_FAILED = 16002;// 添加订单失败

	// 广告错误码20XXX
	public static Integer ADVERTID_ERROR = 20001;// 广告id不存在

	// 视频错误码21XXX
	public static Integer VIDEOID_ERROR = 21001;// 视频id不存在
	public static Integer VIDEOCATEGORY_ERROR = 21002;// 视频分类id不存在

	// 论坛错误码3XXXX
	public static Integer NODEID_ERROR = 30001;// 论坛id不存在
	public static Integer TOPICID_ERROR = 31001;// 主题id不存在

	// 版本错误码90XXX
	public static Integer VERSIONCODE_ERROR = 90001;// 版本号有误

	// 根据resultcode返回错误信息
	public static String getErrmsg(Integer resultCode) {

		String errmsg = "";
		switch (resultCode) {
		case 0:
			errmsg = "success";
			break;
		case -1:
			errmsg = "unknown error";
			break;

		// 用户类
		case 10001:
			errmsg = "invalid username";
			break;
		case 10002:
			errmsg = "wrong password";
			break;
		case 10003:
			errmsg = "invalid token";
			break;
		case 10004:
			errmsg = "expired token";
			break;
		case 10005:
			errmsg = "invalid user_id";
			break;
		case 10006:
			errmsg = "username exist";
			break;
		case 10007:
			errmsg = "require info not enough";
			break;
		case 10008:
			errmsg = "require info error";
			break;
		case 10009:
			errmsg = "user status error";
			break;
		case 10010:
			errmsg = "user list null";
			break;
		case 10011:
			errmsg = "register info not enough";
			break;
		case 10012:
			errmsg = "user type error";
			break;
		case 10013:
			errmsg = "account length smaller than 6 chars";
			break;
		case 10014:
			errmsg = "password length smaller than 6 chars";
			break;
		case 10015:
			errmsg = "mobile error";
			break;
		case 10016:
			errmsg = "gender error";
			break;
		case 10017:
			errmsg = "email error";
			break;
		case 10018:
			errmsg = "user Skill Information exist";
			break;
		case 10019:
			errmsg = "user Skill Information not exist";
			break;
		case 10020:
			errmsg = "user Skill Information is same";
			break;
		case 10021:
			errmsg = "user Skill List is empty";
			break;

		case 11001:
			errmsg = "username not exist";
			break;
		case 11002:
			errmsg = "account exist";
			break;
		case 11003:
			errmsg = "mobile exist";
			break;
		case 11004:
			errmsg = "email exist";
			break;

		case 12001:
			errmsg = "active status";
			break;
		case 12002:
			errmsg = "pause status";
			break;
		case 12003:
			errmsg = "pending status";
			break;
		case 12004:
			errmsg = "locked status";
			break;

		case 13001:
			errmsg = "address list null";
			break;
		case 13002:
			errmsg = "addresscode not match with address";
			break;
		case 13003:
			errmsg = "addresscode invalid";
			break;

		case 14001:
			errmsg = "access reject";
			break;
		case 14002:
			errmsg = "accessgroup not exist";
			break;
		case 14003:
			errmsg = "accessgroup exist";
			break;

		//参数表
		case 15001:
			errmsg = "reference type not exist";
			break;

		case 15002:
			errmsg = "reference type exist";
			break;

		case 16001:
			errmsg = "reference type is empty";
			break;
			
		case 16002:
			errmsg = "reference add failed";
			break;
			
		// 新闻类
		case 20001:
			errmsg = "invalid advertisement_id";
			break;

		// 视频类
		case 21001:
			errmsg = "invalid video_id";
			break;
		case 21002:
			errmsg = "invalid video_category";
			break;

		// 论坛类
		case 30001:
			errmsg = "invalid node_id";
			break;
		case 31001:
			errmsg = "invalid topic_id";
			break;

		// 版本类
		case 90001:
			errmsg = "version_code error";
			break;
		}
		return errmsg;
	}
}