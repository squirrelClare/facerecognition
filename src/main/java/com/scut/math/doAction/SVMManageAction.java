package com.scut.math.doAction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import libsvm.svm_parameter;
import libsvm.svm_problem;

import org.opencv.core.Mat;

import com.scut.math.action.DataFormat;
import com.scut.math.action.ImgDecomp;
import com.scut.math.bean.DataBean;
import com.scut.math.bean.DataSetBean;
import com.scut.math.bean.ImgBean;

public class SVMManageAction {
	private DataSetBean dataSetBean;

	public DataSetBean getDataSetBean() {
		return dataSetBean;
	}

	public void setDataSetBean(String path) {
		//��ȡ�ƶ�Ŀ¼�е�ͼ��
		ImgReadAction imgReadAction=new ImgReadAction(path);
		ArrayList<String> imgNames=imgReadAction.getImgNameStrings();
		ArrayList<Mat> imgs=imgReadAction.getImgsArrayList();

		//��ȡÿ��ͼƬ����Ϣ������SVD���ÿ��ͼƬ������ֵ
		ImgDecomp imgDecomp=new ImgDecompAction();
		ArrayList<ImgBean> imgBeans=imgDecomp.decompImgs(imgNames, imgs);
		
		//��׼��ͼ����Ϣ
		DataFormat dataFormat=new DataFormatAction();
		ArrayList<String> personNames=dataFormat.getPersonName(imgBeans);
		ArrayList<Integer> imgIndex=dataFormat.getImgIndex(imgBeans);
		svm_node[][] datas=dataFormat.datasFormat(imgBeans);
		this.dataSetBean=new DataSetBean();
		dataSetBean.setPersonNames(personNames);
		dataSetBean.setImgIndex(imgIndex);
		dataSetBean.setDatas(datas);		
	}
	
	//����personName��ͼ��Ϊһ�࣬���������˵�ͼ��Ϊһ��Ķ�������������ݼ�
	public DataBean generatTrainTestData(String personName) {
		return new DataFormatAction().generateDataBean(personName,
				this.dataSetBean.getPersonNames(),this.dataSetBean.getImgIndex()
				, this.dataSetBean.getDatas());
	}
	
	//��libsvm�������ݼ�
    public void svmCompute(String personName) throws IOException {
    	
    	DataBean dataBean=generatTrainTestData(personName);
    	int n=dataBean.getTestLabels().length;
    	
        //����svm_problem����
        svm_problem problem = new svm_problem();
        problem.l = n; //��������
        problem.x = dataBean.getTrainDataS(); //ѵ����������
        problem.y = dataBean.getTrainLabels(); //��Ӧ��lable����
        
        //����svm_parameter����
        svm_parameter param = new svm_parameter();
        param.svm_type = svm_parameter.C_SVC;
        param.kernel_type = svm_parameter.RBF;//������˺���
        param.gamma=0.0001;
        param.cache_size = 100;
        param.eps = 0.00001;
        param.C = 100;
        
        System.out.println(svm.svm_check_parameter(problem, param)); //�������û�����⣬��svm.svm_check_parameter()��������null,���򷵻�error������
        svm_model model = svm.svm_train(problem, param); //svm.svm_train()ѵ����SVM����ģ��
        
//        System.out.println(svm.svm_predict(model, dataBean.getTestDataS()[0]));
        
        svm_node[][] testData=dataBean.getTestDataS();
        int count=0;
        for (int i = 0; i < n; i++) {
			if (svm.svm_predict(model, testData[i])==dataBean.getTestLabels()[i]) {
				count++;
			}
//        	System.out.println(dataBean.getTestLabels()[i]+"**"+svm.svm_predict(model, testData[i]));
		}
        System.out.println("count="+count);
        System.out.println("��ȷ��Ϊ��"+(double)(count)/(double)(n)+"%");
        System.out.println(model.nSV[0]+"    "+model.nSV[1]);
        
	}
    
    public void  write(DataBean dataBean) throws IOException {
		FileWriter fw=new FileWriter("tmp.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		double[] labels=dataBean.getTestLabels();
		svm_node[][] data=dataBean.getTestDataS();
		
		svm_node[] tmpNodes;
		for (int i = 0; i < labels.length; i++) {
			String tmpString=Double.toString(labels[i]);

			tmpNodes=data[i];
			for (int j = 0; j < data[0].length; j++) {
				tmpString=tmpString+'\t'+tmpNodes[j].index+":"+tmpNodes[j].value;
			}
			bw.write(tmpString);
			bw.newLine();
		}
		bw.flush();
		fw.flush();
	}
}
