package org.wso2.analytics.dashboardconfig;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Content")
public class ContentBean {
	String type;
	String externalURL;
	ArrayList<WidgetBean> list = new ArrayList<WidgetBean>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExternalURL() {
		return externalURL;
	}

	public void setExternalURL(String externalURL) {
		this.externalURL = externalURL;
	}

	public ArrayList<WidgetBean> getList() {
		return list;
	}

	public void setList(ArrayList<WidgetBean> list) {
		this.list = list;
	}

}
