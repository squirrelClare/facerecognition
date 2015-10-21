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
	
	//������ͼƬ�ֽ�������ֵת��Ϊlibsvm���ܴ�������ݼ���һ��ͼƬΪѵ�������߲��Լ��е�һ�м�¼
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
	
	//������ͼƬ����������ת��Ϊibsvm���ܴ�������ݼ�
	public svm_node[][] datasFormat(ArrayList<ImgBean> imgBeans) {
		int n=imgBeans.size();
		this.m=(int) imgBeans.get(0).getValue().total();
		svm_node[][] datas=new svm_node[n][m];
		for (int i = 0; i < n; i++) {
			datas[i]=dataFormat(imgBeans.get(i).getValue());
			
		}
		return datas;
	}
	
	//��ȡ���ݼ���Ӧ��������
	public ArrayList<String> getPersonName(ArrayList<ImgBean> imgBeans) {
		ArrayList<String> nameStrings=new ArrayList<String>();
		for (ImgBean img : imgBeans) {
			nameStrings.add(img.getPersonName());
		}
		return nameStrings;
	}
	
	//��ȡ���ݼ���Ӧ��������
	public ArrayList<Integer> getImgIndex(ArrayList<ImgBean> imgBeans) {
		ArrayList<Integer> indexs=new ArrayList<Integer>();
		for (ImgBean img : imgBeans) {
			indexs.add(img.getId());
		}
		return indexs;
	}
	
	//��ȡһ��ѵ������Ҫ��DataBean
	public DataBean generateDataBean(String personName,ArrayList<String> personNames
			,ArrayList<Integer> imgIndex,svm_node[][] datas) {
		DataBean dataBean=new DataBean();
		int n=personNames.size()/2;
		
		//��ʼѵ�����Ͳ��Լ���ǩ
		double[] trainLabels=new double[n];
		double[] testLabels=new double[n];
		for (int i = 0; i < n; i++) {
			trainLabels[i]=0;
			testLabels[i]=0;
		}
		
		//��ʼ��ѵ�����Ͳ��Լ�����
		svm_node[][] trainData=new svm_node[n][m];
		svm_node[][] testData=new svm_node[n][m];
		
		int train_index=0;
		int test_index=0;
		ArrayList<Integer> tags=separate(personNames, imgIndex);
		for (int i = 0; i < personNames.size(); i++) {
			if (tags.get(i)==1) {
				//ѵ��������ѡ��
				trainData[train_index]=datas[i];
				//ѵ������ǩ����
				if (personNames.get(i).equals(personName)) {
					trainLabels[train_index]=1;
				}else {
					trainLabels[train_index]=-1;
				}
				train_index++;
			} else {
				//���Լ�����ѡ��
				testData[test_index]=datas[i];
				//���Լ���ǩ����
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
	
	//�������һ��1��10��Χ�ڵ�Ԫ�ظ���Ϊ5�ļ���
	private Set<Integer> getRandomSet(){
		Set<Integer> set=new HashSet<Integer>();
		Random random=new Random();
		
		while (set.size()<5) {
			set.add(random.nextInt(236)%10+1);
		}
		return set;
	}
	
	//����ʼ���ݼ����л��֣�ѵ�����е�Ԫ����1��ʾ�����Լ��е���0��ʾ
	private  ArrayList<Integer> separate(ArrayList<String> personNames
			,ArrayList<Integer> imgIndex) {
		Set<String> personNameSet=new HashSet<String>(personNames);//ͼ���������˵�����
		
		//ѡ��Ϊѵ�����е�Ԫ�ط���table��
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
