package org.wso2.analytics.dashboard.restapi;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "list" })
@XmlRootElement
public class PageMetaBean implements Serializable{

	@XmlElement(name = "list")
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
	public String getElementTitleFromListIndex(int id){
		return list.get(id).getTitle();
	}
	
//	public void clearAllData(String cause){
//		if(cause.equals("ConfirmDeletion")){
//			this.list=null;
//		}
//		else{
//			System.err.println("Invalid Reqest");
//		}
//		
//	}
	
}

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id" , "title" })
@XmlRootElement(name = "list")
class MetaData implements Serializable{

	@XmlElement(name = "id")
	int id;

	@XmlElement(name = "title")
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
