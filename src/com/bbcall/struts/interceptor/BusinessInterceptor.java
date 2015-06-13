package com.bbcall.struts.interceptor;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;

import com.bbcall.struts.exception.BusinessException;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class BusinessInterceptor extends AbstractInterceptor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5091701334705371275L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		before(invocation);

		String result = "";

		try {
			result = invocation.invoke();
		} catch (DataAccessException ex) {
			throw new BusinessException("数据库操作失败！");
		} catch (NullPointerException ex) {
			throw new BusinessException("调用了未经初始化的对象或者是不存在的对象！");
		} catch (IOException ex) {
			throw new BusinessException("IO异常！");
		} catch (ClassNotFoundException ex) {
			throw new BusinessException("指定的类不存在！");
		} catch (ArithmeticException ex) {
			throw new BusinessException("数学运算异常！");
		} catch (ArrayIndexOutOfBoundsException ex) {
			throw new BusinessException("数组下标越界!");
		} catch (IllegalArgumentException ex) {
			throw new BusinessException("方法的参数错误！");
		} catch (ClassCastException ex) {
			throw new BusinessException("类型强制转换错误！");
		} catch (SecurityException ex) {
			throw new BusinessException("违背安全原则异常！");
		} catch (SQLException ex) {
			throw new BusinessException("操作数据库异常！");
		} catch (NoSuchMethodError ex) {
			throw new BusinessException("方法末找到异常！");
		} catch (InternalError ex) {
			throw new BusinessException("Java虚拟机发生了内部错误");
		} catch (Exception ex) {
			throw new BusinessException("程序内部错误，操作失败！");
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

}
