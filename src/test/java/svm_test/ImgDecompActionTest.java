package svm_test;

import static org.junit.Assert.*;
import libsvm.svm_node;

import org.junit.Test;

import com.scut.math.action.ImgDecomp;
import com.scut.math.bean.ImgBean;
import com.scut.math.doAction.DataFormatAction;
import com.scut.math.doAction.ImgDecompAction;
import com.scut.math.doAction.ImgReadAction;

public class ImgDecompActionTest {

	@Test
	public void test() {
		ImgDecomp imgDecomp=new ImgDecompAction();
		
		String path="C:\\Users\\lenovo\\Desktop\\ORL";
		ImgReadAction imgReadAction=new ImgReadAction(path);
		imgReadAction.setImgNameStrings();
		System.out.println(path+"\\"+imgReadAction.getImgNameStrings().get(0));
		imgReadAction.setImgsArrayList();
		
		ImgBean imgBean=imgDecomp.decomp(imgReadAction.getImgNameStrings().get(0),
				imgReadAction.getImgsArrayList().get(0));
		System.out.println(imgBean.getPersonName()+"  "+imgBean.getId());
//		System.out.println(imgBean.getValue().dump());
		System.out.println(imgBean.getValue().total());
		System.err.println(imgBean.getValue().get(0, 0)[0]);
		svm_node[] nodes=new DataFormatAction().dataFormat(imgBean.getValue());
		for (int i = 0; i < imgBean.getValue().total(); i++) {
			System.out.print(nodes[i].value+",");
		}
	}
}
