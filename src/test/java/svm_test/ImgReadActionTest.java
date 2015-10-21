package svm_test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.scut.math.doAction.ImgReadAction;

public class ImgReadActionTest {

	@Test
	public void imgReadActionTest(){
		String path="C:\\Users\\lenovo\\Desktop\\ORL";
		ImgReadAction imgReadAction=new ImgReadAction(path);
		imgReadAction.setImgNameStrings();
		System.out.println(path+"\\"+imgReadAction.getImgNameStrings().get(0));
		imgReadAction.setImgsArrayList();
	}

}
