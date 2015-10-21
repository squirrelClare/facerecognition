package com.scut.math.doAction;

import java.util.ArrayList;

import org.opencv.core.*;

import com.scut.math.action.ImgDecomp;
import com.scut.math.bean.ImgBean;

public class ImgDecompAction implements ImgDecomp {
	public ImgBean decomp(String fileName,Mat src) {
		String[] tmpStrings=fileName.substring(0, fileName.length()-4).split("_");
		ImgBean imgBean=new ImgBean();
		imgBean.setPersonName(tmpStrings[0]);
		imgBean.setId(Integer.parseInt(tmpStrings[1]));
		System.loadLibrary("opencv_java2410");
		Mat w=new Mat();
		Mat u=new Mat();
		Mat vt=new Mat();
		Core.SVDecomp(src, w, u, vt);
//		imgBean.setValue(w.rowRange(0,(int) (w.rows()*0.4)));
		imgBean.setValue(w);
		return imgBean;
		
	}
	/* (non-Javadoc)
	 * @see com.scut.math.action.ImgDecomp#name(java.util.ArrayList, java.util.ArrayList)
	 */
	public ArrayList<ImgBean> decompImgs(ArrayList<String> fileNames,ArrayList<Mat> imgs) {
		ArrayList<ImgBean> imgBeans=new ArrayList<ImgBean>();
		for (int i = 0; i < fileNames.size(); i++) {
			imgBeans.add(decomp(fileNames.get(i), imgs.get(i)));
		}
		return imgBeans;
		
	}
}
