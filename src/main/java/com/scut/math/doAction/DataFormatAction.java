package com.scut.math.doAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.opencv.core.Mat;

import com.scut.math.action.DataFormat;
import com.scut.math.bean.DataBean;
import com.scut.math.bean.ImgBean;

import libsvm.svm_node;

public class DataFormatAction implements DataFormat {
	/* (non-Javadoc)
	 * @see com.scut.math.doAction.DataFormat#dataFormat(java.lang.Math)
	 */
	private int m;
	
	//将单张图片分解后的特征值转化为libsvm所能处理的数据集，一张图片为训练集或者测试集中的一行记录
	public svm_node[] dataFormat(Mat svdValue) {
		int n=(int) svdValue.total();
		svm_node[] nodes=new svm_node[n];
		
		svm_node tmpNode;
		for (int i = 0; i < n; i++) {
			tmpNode=new svm_node();
			tmpNode.index=i+1;
			tmpNode.value=svdValue.get(i, 0)[0];
			nodes[i]=tmpNode;
		}
		return nodes;
	}
	
	//将所有图片的特征向量转化为ibsvm所能处理的数据集
	public svm_node[][] datasFormat(ArrayList<ImgBean> imgBeans) {
		int n=imgBeans.size();
		this.m=(int) imgBeans.get(0).getValue().total();
		svm_node[][] datas=new svm_node[n][m];
		for (int i = 0; i < n; i++) {
			datas[i]=dataFormat(imgBeans.get(i).getValue());
			
		}
		return datas;
	}
	
	//获取数据集对应的人名集
	public ArrayList<String> getPersonName(ArrayList<ImgBean> imgBeans) {
		ArrayList<String> nameStrings=new ArrayList<String>();
		for (ImgBean img : imgBeans) {
			nameStrings.add(img.getPersonName());
		}
		return nameStrings;
	}
	
	//获取数据集对应的索引集
	public ArrayList<Integer> getImgIndex(ArrayList<ImgBean> imgBeans) {
		ArrayList<Integer> indexs=new ArrayList<Integer>();
		for (ImgBean img : imgBeans) {
			indexs.add(img.getId());
		}
		return indexs;
	}
	
	//获取一个训练所需要的DataBean
	public DataBean generateDataBean(String personName,ArrayList<String> personNames
			,ArrayList<Integer> imgIndex,svm_node[][] datas) {
		DataBean dataBean=new DataBean();
		int n=personNames.size()/2;
		
		//初始训练集和测试集标签
		double[] trainLabels=new double[n];
		double[] testLabels=new double[n];
		for (int i = 0; i < n; i++) {
			trainLabels[i]=0;
			testLabels[i]=0;
		}
		
		//初始换训练集和测试集数据
		svm_node[][] trainData=new svm_node[n][m];
		svm_node[][] testData=new svm_node[n][m];
		
		int train_index=0;
		int test_index=0;
		ArrayList<Integer> tags=separate(personNames, imgIndex);
		for (int i = 0; i < personNames.size(); i++) {
			if (tags.get(i)==1) {
				//训练集数据选择
				trainData[train_index]=datas[i];
				//训练集标签分配
				if (personNames.get(i).equals(personName)) {
					trainLabels[train_index]=1;
				}else {
					trainLabels[train_index]=-1;
				}
				train_index++;
			} else {
				//测试集数据选择
				testData[test_index]=datas[i];
				//测试集标签分配
				if (personNames.get(i).equals(personName)) {
					testLabels[test_index]=1;
				}else {
					testLabels[test_index]=-1;
				}
				test_index++;
			}
		}
		dataBean.setTrainLabels(trainLabels);
		dataBean.setTestLabels(testLabels);
		dataBean.setTrainDataS(trainData);
		dataBean.setTestDataS(testData);
		return dataBean;
	}
	
	//随机生意一个1到10范围内的元素个数为5的集合
	private Set<Integer> getRandomSet(){
		Set<Integer> set=new HashSet<Integer>();
		Random random=new Random();
		
		while (set.size()<5) {
			set.add(random.nextInt(236)%10+1);
		}
		return set;
	}
	
	//将初始数据集进行划分，训练集中的元素用1表示，测试集中的用0表示
	private  ArrayList<Integer> separate(ArrayList<String> personNames
			,ArrayList<Integer> imgIndex) {
		Set<String> personNameSet=new HashSet<String>(personNames);//图像中所有人的名字
		
		//选中为训练集中的元素放入table中
		HashMap<String, Set<Integer>> table=new HashMap<String, Set<Integer>>();
		for (String name : personNameSet) {
//			System.out.println(name);
			table.put(name, getRandomSet());
		}
		
		ArrayList<Integer> tags=new ArrayList<Integer>();
		int tmp=0;
		String tmpName=null;
		for (int i = 0; i < personNames.size(); i++) {
			tmp=imgIndex.get(i);
			tmpName=personNames.get(i);
			if (table.get(tmpName).contains(tmp)) {
				tags.add(1);
			}else {
				tags.add(0);
			}
		}
		return tags;
	}

	public DataBean generateDataBean(String personName) {
		// TODO Auto-generated method stub
		return null;
	}
}
