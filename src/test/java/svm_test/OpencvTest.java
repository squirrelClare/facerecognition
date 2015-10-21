package svm_test;

import org.opencv.core.*;
import org.opencv.highgui.*;
import org.opencv.imgproc.*;

public class OpencvTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        System.loadLibrary("opencv_java2410");  
        Mat m  = Mat.eye(3, 3, CvType.CV_8UC1);
        Mat img=Highgui.imread("C:\\Users\\lenovo\\Desktop\\untitled.tif",
        		Highgui.CV_LOAD_IMAGE_GRAYSCALE);
        System.out.println(img.empty());
        System.out.println(img.dump().length());
        System.out.println(img.size().toString());
//        System.out.println("m = " + img.dump());
        System.out.println(m.total());
        System.out.println(m.dump());
        System.out.println(m.get(1, 2)[0]);
//        m.get(1, 1)

	}

}
