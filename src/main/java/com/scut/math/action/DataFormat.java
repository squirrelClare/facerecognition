package com.scut.math.action;

import java.util.ArrayList;

import com.scut.math.bean.DataBean;
import com.scut.math.bean.ImgBean;

import libsvm.svm_node;

public interface DataFormat {
	public abstract svm_node[][] datasFormat(ArrayList<ImgBean> imgBeans);
	public abstract ArrayList<String> getPersonName(ArrayList<ImgBean> imgBeans);
	public abstract ArrayList<Integer> getImgIndex(ArrayList<ImgBean> imgBeans);
	public abstract DataBean generateDataBean(String personName);
	public abstract DataBean generateDataBean(String personName,ArrayList<String> personNames
			,ArrayList<Integer> imgIndex,svm_node[][] datas);

}