package org.wso2.analytics.dashboardconfig;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "JsonBean")
public class JsonBean {
    private String val;

    public String getVal() {
	return val;
    }

    public void setVal(String val) {
	this.val = val;
    }

}
