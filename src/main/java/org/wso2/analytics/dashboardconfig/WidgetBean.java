package org.wso2.analytics.dashboardconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Widget")
@XmlAccessorType(XmlAccessType.FIELD)
public class WidgetBean {
	
	@XmlElement
	Config config;
	
	@XmlElement
	boolean readOnly;
	
	@XmlElement
	Data data;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	/*
	 * Set Configs when raw data is given instead of an object
	 */
	public void setConfig(int x, int y, int width, int height) {
		Config temp = new Config();
		temp.setX(x);
		temp.setY(y);
		temp.setWidth(width);
		temp.setHeight(height);
		this.config = temp;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

}

@XmlRootElement(name = "Config")
class Config {
	
	@XmlElement
	int x;
	
	@XmlElement
	int y;
	
	@XmlElement
	int width;
	
	@XmlElement
	int height;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}

class Data {
	// data will be added later
}
