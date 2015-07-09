package com.bbcall.struts.interceptor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.bbcall.struts.exception.BusinessException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

public class BusinessInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5091701334705371275L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getProxy().getActionName();
		String frdMsg;

		before(invocation);

		String result = "";

		try {
			result = invocation.invoke();
		} catch (DataAccessException ex) {
			frdMsg = " Database operation fail!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (NullPointerException ex) {
			frdMsg = " Calling the object that is not initialized or the object does not exist!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (IOException ex) {
			frdMsg = " I/O Exception happened!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (ClassNotFoundException ex) {
			frdMsg = " The specified class does not exist!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (ArithmeticException ex) {
			frdMsg = " Abnormal mathematical operation!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (ArrayIndexOutOfBoundsException ex) {
			frdMsg = " Array index out of bounds!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (IllegalArgumentException ex) {
			frdMsg = " Parameter error on the method!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (ClassCastException ex) {
			frdMsg = " Type forced conversion error!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (SecurityException ex) {
			frdMsg = " Security principle exception!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (SQLException ex) {
			frdMsg = " Operating database exception!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (NoSuchMethodError ex) {
			frdMsg = " Method not found exception!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (InternalError ex) {
			frdMsg = " Java virtual machine has an internal error!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		} catch (Exception ex) {
			frdMsg = " Internal error, operation failure!";
			if (actionName.contains("Json")) {
				jsonException(invocation, frdMsg);
				return "exceptionjson";
			}
			throw new BusinessException(frdMsg, actionName);
		}

		after(invocation, result);
		return result;
	}

	/**
	 * 验证登陆等...
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	public void before(ActionInvocation invocation) throws Exception {
		// ...
	}

	/**
	 * 记录日志等...
	 * 
	 * @param invocation
	 * @return
	 * @throws Exception
	 */
	public void after(ActionInvocation invocation, String result)
			throws Exception {
		// ...
	}

	/**
	 * Return Json format method
	 * @param invocation
	 * @param frdMessage
	 * @throws Exception
	 */
	public void jsonException(ActionInvocation invocation, String frdMessage)
			throws Exception {
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>(); // 新建dataMap来储存JSON字符串
		dataMap.clear(); // dataMap中的数据将会被Struts2转换成JSON字符串，所以这里要先清空其中的数据

		ValueStack stack = invocation.getStack();
		stack.set("dataMap", dataMap);
		dataMap.put("resultcode", -1); // 放入一个是否操作成功的标识
		dataMap.put("errmsg", frdMessage);
		dataMap.put("result", false); // 放入loginResult
	}
}
