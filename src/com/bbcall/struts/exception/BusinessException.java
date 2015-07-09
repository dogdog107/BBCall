package com.bbcall.struts.exception;

public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 0xc1a865c45ffdc5f9L;

	public BusinessException(String frdMessage, String actionName) {
		super(createFriendlyErrMsg(frdMessage));
	}

	// public BusinessException(String frdMessage) {
	// super(createFriendlyErrMsg(frdMessage));
	// }

	public BusinessException(Throwable throwable) {
		super(throwable);
	}

	public BusinessException(Throwable throwable, String frdMessage) {
		super(throwable);
	}

	private static String createFriendlyErrMsg(String msgBody) {
		String prefixStr = "SORRY!";
		String suffixStr = " Please try again later or contact your administrator.";
		StringBuffer friendlyErrMsg = new StringBuffer("");
		friendlyErrMsg.append(prefixStr);
		friendlyErrMsg.append(msgBody);
		friendlyErrMsg.append(suffixStr);
		System.out.println(friendlyErrMsg.toString());
		return friendlyErrMsg.toString();
	}

}
