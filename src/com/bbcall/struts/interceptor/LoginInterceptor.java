package com.bbcall.struts.interceptor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.bbcall.functions.ResultCode;
import com.bbcall.functions.Tools;
import com.bbcall.struts.services.AccessServices;
import com.bbcall.struts.services.UserServices;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

public class LoginInterceptor extends AbstractInterceptor {
	private static Logger logger = Logger.getLogger(LoginInterceptor.class);  
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserServices userServices;
	@Autowired
	private AccessServices accessServices;
	private Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
	private String sessionCheckName; // 由struts.xml传过来的参数
	private String excludeAction; // 由struts.xml传过来的参数
	private boolean disable;
	private List<String> list;
	private Map<String, Object> session;
	private String token;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		System.out.println("Here is Login Validation Intercepter.");
		String actionName = invocation.getProxy().getActionName();
		System.out.println("Requesting : " + actionName);
		if (disable) { // 被停用时，放行。
			System.out.println("Login Validation Intercepter is Disabled.");
			return invocation.invoke();
		}
		
		if (actionName.contains("login")){ // 如果是请求登录action
			return accessValidate(invocation,"login");
		}
		
		if (list.contains(actionName)) {// 检测struts.xml 放行的action
			// 请求的是合法
			return invocation.invoke();
		} else {

			// 查看session
			session = invocation.getInvocationContext().getSession();
			System.out.println("session:" + session);
			while (session.size() != 0 && session.get(sessionCheckName) != null && !Tools.isEmpty(session.get(sessionCheckName).toString())) {
				
				int tokenResult = userServices.checkUserToken(session.get(sessionCheckName).toString());
				if (tokenResult != ResultCode.SUCCESS) {
					String errmsg = "(session) " + ResultCode.getErrmsg(tokenResult) + ", Please contact your Admin.";
					logger.info("userOpr:" + errmsg);
					invocation.getInvocationContext().getSession().clear();
					return "login";
				}
				
				// 输出 log4j 信息
				logger.info("userOpr:[" + session.get("user_id") + "]" + session.get("user_account") + " - " + actionName);  
				// 检测到sessionCheckName,放行 并进入access Validation.
				return accessValidate(invocation,"session");
//				return invocation.invoke();
			}
			// 查看token
			String[] tokenParam = (String[]) invocation.getInvocationContext().getParameters().get("token");
			if (tokenParam != null) {
				token = tokenParam[0];
				System.out.println("token:" + token);
				while (!Tools.isEmpty(token)) {
					dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
					int result = userServices.checkUserToken(token); // 调用userServices.checkUserTokenes
					if (result == ResultCode.SUCCESS) {
						// 输出 log4j 信息
						logger.info("userOpr:[" + userServices.getUserinfo().getUser_id() + "]" + userServices.getUserinfo().getUser_account() + " - " + actionName);  
						// 检测到token,放行 并进入access Validation.
						return accessValidate(invocation,"token");
					} else {
						ValueStack stack = invocation.getStack();
						stack.set("dataMap", dataMap);
						dataMap.putAll(Tools.JsonHeadMap(result, false));
						return "interceptorjson";
					}
				}
			}
			// 判断是否Json请求
			if (actionName.contains("Json")){
				// Json请求时返回json串
				dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
				int result = ResultCode.REQUIREINFO_NOTENOUGH;
				ValueStack stack = invocation.getStack();
				stack.set("dataMap", dataMap);
				dataMap.putAll(Tools.JsonHeadMap(result, false));
				return "interceptorjson";
			} else {
				// 不是Json请求的返回login页面
				return "login";
			}
		}
	}

	/**
	 *  ACCESS Validation
	 * @param invocation
	 * @param inputflag
	 * @return
	 * @throws Exception
	 */
	public String accessValidate(ActionInvocation invocation, String inputflag)
			throws Exception {
		System.out.println("Here ACCESS Validation Intercepter");
		String actionName = invocation.getProxy().getActionName();
		int accessResult;
		String errmsg; // 返回给session的参数

		switch (inputflag) {
		case "login": // session check access
			// 查看username
			String[] usernameParam = (String[]) invocation
					.getInvocationContext().getParameters().get("username");
			if (usernameParam == null) {
				accessResult =ResultCode.REQUIREINFO_NOTENOUGH;
				errmsg = "(Login) Require information not enough.";
				logger.info("userOpr:[null][" + actionName + "]" + errmsg);
				System.out.println(errmsg);
				return "login";
			}
			
			String username = usernameParam[0];
			if (!Tools.isEmpty(username)) {
				int checkUserNameResult = userServices.checkUserName(username, null);// 调用checkUserName方法并得到返回码
				if (checkUserNameResult != ResultCode.USERNAME_NOTEXIST) {
					String accessgroup = userServices.getUserinfo()
							.getUser_access_group(); // 引用user对象
					accessResult = accessServices.checkUserAccess(accessgroup,
							actionName);
					if (accessResult == ResultCode.SUCCESS) {
						return invocation.invoke();
					} else {
						errmsg = "(Login) Access Reject, Please contact your Admin.";
						logger.info("userOpr:[" + username + "][" + actionName + "]" + errmsg);
						System.out.println(errmsg);
					}
				} else {
					accessResult = ResultCode.USERNAME_NOTEXIST;
					errmsg = "(Login) Username is invalid, Please contact your Admin.";
					logger.info("userOpr:[" + username + "][" + actionName + "]" + errmsg);
					System.out.println(errmsg);
					return invocation.invoke();
				}
			} else {
				accessResult =ResultCode.REQUIREINFO_NOTENOUGH;
				errmsg = "(Login) Require information not enough.";
				logger.info("userOpr:[" + username + "][" + actionName + "]" + errmsg);
				System.out.println(errmsg);
				return invocation.invoke();
			}
			break;
		case "session": // session check access

			if (session.get("user_access_group") == null
			|| Tools.isEmpty(session.get("user_access_group")
					.toString())) {
				accessResult = ResultCode.ACCESSGROUP_ERROR;
				errmsg = "(session) Access_group NULL, Please contact your Admin.";
				logger.info("userOpr:" + errmsg);
				System.out.println(errmsg);
				break;
			}
			
			accessResult = accessServices.checkUserAccess(
					session.get("user_access_group").toString(), actionName);
			if (accessResult == ResultCode.SUCCESS) {
				return invocation.invoke();
			} else {
				errmsg = "(session) Access Reject, Please contact your Admin.";
				logger.info("userOpr:" + errmsg);
				System.out.println(errmsg);
			}
			break;
		case "token": // token check access

			while (userServices.getUserinfo() == null) {
				userServices.checkUserToken(token); // 调用userServices.checkUserTokenes
			}

			if (Tools
					.isEmpty(userServices.getUserinfo().getUser_access_group())) {
				accessResult = ResultCode.ACCESSGROUP_ERROR;
				errmsg = "(token) Access_group NULL, Please contact your Admin.";
				logger.info("userOpr:" + errmsg);
				System.out.println(errmsg);
				break;
			}

			accessResult = accessServices.checkUserAccess(userServices
					.getUserinfo().getUser_access_group(), actionName);
			if (accessResult == ResultCode.SUCCESS) {
				return invocation.invoke();
			} else {
				errmsg = "(token) Access Reject, Please contact your Admin.";
				logger.info("userOpr:" + errmsg);
				System.out.println(errmsg);
			}
			break;
		default:
			accessResult = ResultCode.UNKNOWN_ERROR;
			errmsg = "Fail to validate Access, please contact your Admin.";
			logger.info("userOpr:" + errmsg);
			System.out.println(errmsg);
			break;
		}

		if (actionName.contains("Json")) {
			// Json请求时返回json串
			dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据
			ValueStack stack = invocation.getStack();
			stack.set("dataMap", dataMap);
			dataMap.putAll(Tools.JsonHeadMap(accessResult, false));
			System.out.println(dataMap);
			return "interceptorjson";
		} else {
			// 不是Json请求的返回login页面
			ServletActionContext.getRequest().getSession()
					.setAttribute("accessMsg", errmsg);
			return "accessReject";
		}
	}

	// 把配置文件的exclude参数转换成list
	public List<String> strlsit(String str) {
		String[] s = str.split(",");
		List<String> list = new ArrayList<String>();
		for (String ss : s) {
			list.add(ss.trim());
		}
		return list;
	}

	@Override
	public void init() {
		list = strlsit(excludeAction);
	}
	
	/**
	 * getter & setter
	 * @return
	 */
	public String getSessionCheckName() {
		return sessionCheckName;
	}

	public void setSessionCheckName(String sessionCheckName) {
		this.sessionCheckName = sessionCheckName;
	}

	public String getExcludeAction() {
		return excludeAction;
	}

	public void setExcludeAction(String excludeAction) {
		this.excludeAction = excludeAction;
	}

	public boolean isDisable() {
		return disable;
	}

	public void setDisable(boolean disable) {
		this.disable = disable;
	}


}