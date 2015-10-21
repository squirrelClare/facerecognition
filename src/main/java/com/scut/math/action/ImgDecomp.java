package com.scut.math.action;

import java.util.ArrayList;

import org.opencv.core.Mat;

import com.scut.math.bean.ImgBean;

public interface ImgDecomp {

	public abstract ImgBean decomp(String fileName, Mat src);

	public abstract ArrayList<ImgBean> decompImgs(ArrayList<String> fileNames,
			ArrayList<Mat> imgs);

}