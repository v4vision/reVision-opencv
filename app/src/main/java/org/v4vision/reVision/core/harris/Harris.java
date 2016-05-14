package org.v4vision.reVision.core.harris;

import android.graphics.Bitmap;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
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
        Mat har = new Mat();
        Imgproc.cornerHarris(in, har, 2, 3, 0.04);
        Core.normalize(har, har, 0, 255, Core.NORM_MINMAX, CvType.CV_32FC1, new Mat());
        Core.convertScaleAbs(har, har);

        for(int i=0;i<har.height();i++) {
            for(int j=0; j<har.width();j++) {
                if(har.get(i, j)[0] > 200) {
                    Imgproc.circle(orig, new Point(j,i), 6, new Scalar(255,0,0));
                }
            }
        }

        outBitmap = Bitmap.createBitmap(orig.cols(), orig.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(orig, outBitmap);
        return outBitmap;
    }
}
