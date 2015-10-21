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
		//读取制定目录中的图像
		ImgReadAction imgReadAction=new ImgReadAction(path);
		ArrayList<String> imgNames=imgReadAction.getImgNameStrings();
		ArrayList<Mat> imgs=imgReadAction.getImgsArrayList();

		//抽取每张图片的信息，并用SVD求出每张图片的特征值
		ImgDecomp imgDecomp=new ImgDecompAction();
		ArrayList<ImgBean> imgBeans=imgDecomp.decompImgs(imgNames, imgs);
		
		//标准化图像信息
		DataFormat dataFormat=new DataFormatAction();
		ArrayList<String> personNames=dataFormat.getPersonName(imgBeans);
		ArrayList<Integer> imgIndex=dataFormat.getImgIndex(imgBeans);
		svm_node[][] datas=dataFormat.datasFormat(imgBeans);
		this.dataSetBean=new DataSetBean();
		dataSetBean.setPersonNames(personNames);
		dataSetBean.setImgIndex(imgIndex);
		dataSetBean.setDatas(datas);		
	}
	
	//产生personName的图像为一类，其他所有人的图像为一类的二分类问题的数据集
	public DataBean generatTrainTestData(String personName) {
		return new DataFormatAction().generateDataBean(personName,
				this.dataSetBean.getPersonNames(),this.dataSetBean.getImgIndex()
				, this.dataSetBean.getDatas());
	}
	
	//用libsvm处理数据集
    public void svmCompute(String personName) throws IOException {
    	
    	DataBean dataBean=generatTrainTestData(personName);
    	int n=dataBean.getTestLabels().length;
    	
        //定义svm_problem对象
        svm_problem problem = new svm_problem();
        problem.l = n; //向量个数
        problem.x = dataBean.getTrainDataS(); //训练集向量表
        problem.y = dataBean.getTrainLabels(); //对应的lable数组
        
        //定义svm_parameter对象
        svm_parameter param = new svm_parameter();
        param.svm_type = svm_parameter.C_SVC;
        param.kernel_type = svm_parameter.RBF;//径向基核函数
        param.gamma=0.0001;
        param.cache_size = 100;
        param.eps = 0.00001;
        param.C = 100;
        
        System.out.println(svm.svm_check_parameter(problem, param)); //如果参数没有问题，则svm.svm_check_parameter()函数返回null,否则返回error描述。
        svm_model model = svm.svm_train(problem, param); //svm.svm_train()训练出SVM分类模型
        
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
        System.out.println("正确率为："+(double)(count)/(double)(n)+"%");
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
