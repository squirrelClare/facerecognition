package com.scut.math.bean;

import libsvm.svm_node;
/*
 *���ζ����������ǵ����ݼ�*/
public class DataBean {
	private double[] trainLabels;//ѵ������ǩ
	private double[] testLabels;//���Լ���ǩ
	private svm_node[][] trainDataS;//ѵ�����ݼ�
	private svm_node[][] testDataS;//�������ݼ�
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
