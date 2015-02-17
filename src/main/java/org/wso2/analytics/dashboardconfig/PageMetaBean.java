package org.wso2.analytics.dashboardconfig;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PageMetaBean")
public class PageMetaBean {

	ArrayList<MetaData> list = new ArrayList<MetaData>();

	public ArrayList<MetaData> getList() {
		return list;
	}

	public void setList(ArrayList<MetaData> list) {
		this.list = list;
	}

	public void setListElement(int id, String title) {
		MetaData element = new MetaData();
		element.setId(id);
		element.setTitle(title);
		list.add(element);
	}
}

@XmlRootElement(name = "internal")
class MetaData {
	int id;
	String title;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
