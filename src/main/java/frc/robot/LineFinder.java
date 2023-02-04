package frc.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

public class LineFinder {
  public static void main(String[] args) {
    // Start OpenCV pipeline
    UsbCamera camera = CameraServer.startAutomaticCapture();
    CvSink cvSink = CameraServer.getVideo();
    CvSource outputStream = CameraServer.putVideo("Annotated", 640, 480);

    Mat input = new Mat();
    Mat output = new Mat();

    while (!Thread.interrupted()) {
      // Get image from camera
      if (cvSink.grabFrame(input) == 0) {
        continue;
      }

      // Perform image processing operations
      Imgproc.cvtColor(input, output, Imgproc.COLOR_BGR2GRAY);

      // Put annotated output to the stream
      outputStream.putFrame(output);
    }
  }
}
