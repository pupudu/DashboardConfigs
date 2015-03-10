package org.wso2.analytics.dashboard.restapi;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Dashboard")
public class DashboardBean {

	@XmlElement
	ArrayList<DashboardElement> list = new ArrayList<DashboardElement>();

	public ArrayList<DashboardElement> getList() {
		return list;
	}

	public void setList(ArrayList<DashboardElement> list) {
		this.list = list;
	}

	/*
	 * Add a single element with given params to the element list
	 */
	public void setListElement(String title, String group, int id) {
		DashboardElement element = new DashboardElement();
		element.setTitle(title);
		element.setGroup(group);
		element.setId(id);
		list.add(element);
	}
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "internal")
class DashboardElement {
	
	@XmlElement
	String title;
	
	@XmlElement
	String group;
	
	@XmlElement
	int id;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
