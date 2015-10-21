package com.scut.math.doAction;

import java.io.File;
import java.util.ArrayList;

import org.opencv.highgui.*;
import org.opencv.core.*;

public class ImgReadAction {

	private ArrayList<String> imgNameStrings;
	private String dirName;
	private ArrayList<Mat> imgsArrayList;
	
	
	public ImgReadAction(String dirName){
		this.dirName=dirName;
		this.imgNameStrings=new ArrayList<String>();
		this.imgsArrayList=new ArrayList<Mat>();
	}
	public ArrayList<String> getImgNameStrings() {
		setImgNameStrings();
		return imgNameStrings;
	}
	public void setImgNameStrings() {
		File file=new File(this.dirName);
		
		if (file.isDirectory()) {
			File[] files=file.listFiles();
			for (File f : files) {
				if (f.isFile()&&f.getName().endsWith("bmp")) {
//					System.out.println(f.getName());
					imgNameStrings.add(f.getName());
				}
			}
		}
//		System.out.println(this.imgNameStrings.size());
	}
	public ArrayList<Mat> getImgsArrayList() {
		setImgsArrayList();
		return imgsArrayList;
	}
	public void setImgsArrayList() {
		System.loadLibrary("opencv_java2410");
		Mat tmp=new Mat();
		for (String string : imgNameStrings) {
			tmp=Highgui.imread(this.dirName+"\\"+string,Highgui.CV_LOAD_IMAGE_GRAYSCALE);
			tmp.convertTo(tmp, CvType.CV_64F);
			imgsArrayList.add(tmp);
		}
//		System.out.println(imgsArrayList.get(0).dump());
//		System.out.println(imgsArrayList.get(21).dump());
	}
	
}
