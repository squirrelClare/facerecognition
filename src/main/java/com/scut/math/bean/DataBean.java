package com.scut.math.bean;

import libsvm.svm_node;
/*
 *单次二分类问题是的数据集*/
public class DataBean {
	private double[] trainLabels;//训练集标签
	private double[] testLabels;//测试集标签
	private svm_node[][] trainDataS;//训练数据集
	private svm_node[][] testDataS;//测试数据集
	public double[] getTrainLabels() {
		return trainLabels;
	}
	public void setTrainLabels(double[] trainLabels) {
		this.trainLabels = trainLabels;
	}
	public double[] getTestLabels() {
		return testLabels;
	}
	public void setTestLabels(double[] testLabels) {
		this.testLabels = testLabels;
	}
	public svm_node[][] getTrainDataS() {
		return trainDataS;
	}
	public void setTrainDataS(svm_node[][] trainDataS) {
		this.trainDataS = trainDataS;
	}
	public svm_node[][] getTestDataS() {
		return testDataS;
	}
	public void setTestDataS(svm_node[][] testDataS) {
		this.testDataS = testDataS;
	}
}
