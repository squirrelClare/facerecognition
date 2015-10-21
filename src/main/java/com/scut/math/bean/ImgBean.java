package com.scut.math.bean;

import org.opencv.core.Mat;

/*
 * 单张图像经过奇异值分级后的数据*/
public class ImgBean {
	private String personName;//图像所属人名
	private int id;//图像为此人的第id张图像
	private Mat value;//奇异值矩阵
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Mat getValue() {
		return value;
	}
	public void setValue(Mat value) {
		this.value = value;
	}
}
