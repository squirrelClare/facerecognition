package com.scut.math.bean;

import org.opencv.core.Mat;

/*
 * ����ͼ�񾭹�����ֵ�ּ��������*/
public class ImgBean {
	private String personName;//ͼ����������
	private int id;//ͼ��Ϊ���˵ĵ�id��ͼ��
	private Mat value;//����ֵ����
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
