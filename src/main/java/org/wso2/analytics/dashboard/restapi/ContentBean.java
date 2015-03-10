package org.wso2.analytics.dashboard.restapi;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Content")
public class ContentBean {
	
	@XmlElement
	String type;
	
	@XmlElement
	String externalURL;
	
	@XmlElement
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
