package org.v4vision.reVision.core.harris;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class Harris {

    private Bitmap outBitmap;
    private Mat in, orig;
    public Harris() {
    }

    public void setFrame(byte[] frame, int width, int height) {
        in = new Mat(height*3/2, width, CvType.CV_8UC1);
        in.put(0, 0, frame);
        orig = new Mat(height, height, CvType.CV_8UC4);
        Imgproc.cvtColor(in, orig, Imgproc.COLOR_YUV2RGBA_NV21);
    }

    public Bitmap process() {
        MatOfPoint corners = new MatOfPoint();
        Imgproc.goodFeaturesToTrack(in, corners, 200, 0.1, 11, new Mat(), 3, true, 0.04);

        Point[] cornerArray = corners.toArray();

        for(int i = 0; i < cornerArray.length; i++) {
            Imgproc.circle(orig, cornerArray[i], 2, new Scalar(0, 255, 0));
        }

        outBitmap = Bitmap.createBitmap(orig.cols(), orig.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(orig, outBitmap);
        return outBitmap;
    }
}
