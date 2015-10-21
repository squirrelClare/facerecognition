package com.scut.math.bean;

import java.util.ArrayList;
import libsvm.svm_node;
public class DataSetBean {
	private ArrayList<String> personNames;
	private ArrayList<Integer> imgIndex;
	private svm_node[][] datas;
	public ArrayList<String> getPersonNames() {
		return personNames;
	}
	public void setPersonNames(ArrayList<String> personNames) {
		this.personNames = personNames;
	}
	public ArrayList<Integer> getImgIndex() {
		return imgIndex;
	}
	public void setImgIndex(ArrayList<Integer> imgIndex) {
		this.imgIndex = imgIndex;
	}
	public svm_node[][] getDatas() {
		return datas;
	}
	public void setDatas(svm_node[][] datas) {
		this.datas = datas;
	}
}
