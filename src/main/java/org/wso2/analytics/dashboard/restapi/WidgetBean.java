package org.wso2.analytics.dashboard.restapi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Widget")
@XmlAccessorType(XmlAccessType.FIELD)
public class WidgetBean {

	@XmlElement
	String id;

	@XmlElement
	String title;

	@XmlElement
	Config config;

	@XmlElement
	Position position;

	@XmlElement
	Data data;

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}

@XmlRootElement(name = "Config")
class Config {

	@XmlElement
	String chartType;

	@XmlElement
	int x;

	@XmlElement
	int y;

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

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
}

@XmlRootElement(name = "Position")
class Position {
	
	@XmlElement
	int column;
	
	@XmlElement
	int row;
	
	@XmlElement
	int size_x;
	
	@XmlElement
	int size_y;

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getSize_x() {
		return size_x;
	}

	public void setSize_x(int size_x) {
		this.size_x = size_x;
	}

	public int getSize_y() {
		return size_y;
	}

	public void setSize_y(int size_y) {
		this.size_y = size_y;
	}
	
}

@XmlRootElement(name = "Data")
class Data {
	// TODO data will be added later
	
	@XmlElement
	String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
