package svm_test;

import java.io.IOException;

import org.junit.Test;

import com.scut.math.doAction.SVMManageAction;

public class SVMManageActionTest {

	@Test
	public void test() throws IOException {
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
