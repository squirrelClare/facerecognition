package svm_test;

import java.io.IOException;

import com.scut.math.doAction.SVMManageAction;

public class Manage {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		SVMManageAction s=new SVMManageAction();
		s.setDataSetBean("C:\\Users\\lenovo\\Desktop\\ORL");
//		double[]l=s.generatTrainTestData("s2").getTestLabels();
//		for (int i = 0; i < l.length; i++) {
//			System.out.println(l[i]);
//			System.out.println(s.generatTrainTestData("s2").getTrainDataS().length);
//		}
		s.svmCompute("s5");
	}

}
