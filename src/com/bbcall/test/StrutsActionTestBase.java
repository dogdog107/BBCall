package com.bbcall.test;

import org.apache.struts2.StrutsSpringTestCase;

public class StrutsActionTestBase extends StrutsSpringTestCase{

	@Override
	protected String[] getContextLocations() {
		// TODO Auto-generated method stub
		return new String[] {"/BBCall/conf_xml/applicationContext.xml","/BBCall/conf_xml/struts.xml"};
	}
}
