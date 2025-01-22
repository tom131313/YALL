package frc.robot;

import static edu.wpi.first.units.Units.DegreesPerSecond;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.wpilibj.TimedRobot;
import limelight.Limelight;
import limelight.estimator.LimelightPoseEstimator;
import limelight.structures.AngularVelocity3d;

public class Robot extends TimedRobot {

  String name = "limelight";
  Limelight limelight;
  LimelightPoseEstimator limelightPoseEstimatorMT2;
  LimelightPoseEstimator limelightPoseEstimator;

  public Robot() {
      limelight = new Limelight(name);

      var useMegaTag2 = true;
      limelightPoseEstimatorMT2 = limelight.getPoseEstimator(useMegaTag2);

      useMegaTag2 = false;
      limelightPoseEstimator = limelight.getPoseEstimator(useMegaTag2);
  }

  @Override
  public void robotPeriodic() {

    limelight.settingsBuilder().withRobotOrientation
        (
        new limelight.structures.Orientation3d
            (
            new Rotation3d(),
            new AngularVelocity3d(DegreesPerSecond.of(0.),DegreesPerSecond.of(0.),DegreesPerSecond.of(0.))
            )
        );


// PICK YOUR TESTS TO RUN
//                            0       1     2       3       4       5  
    var tests = new boolean[]{false, true, false, false, false, false};

/**
 * Caution -- test case restrictions:
 * I have some ".orElseThrow" which crashes the robot on no data. Yes, that's what I wanted to test at the time.
 * More realistic examples are checking for ".isPresent()".
 */

    if (tests[0])
    {
        System.out.println("2d target status (validity Tv) " + limelight.getData().targetData.getTargetStatus());
        for (limelight.results.RawFiducial tag : limelight.getData().getRawFiducials()) // tested okay; insensitive to LL target grouping; returns all targets
        { 
        System.out.println(tag);
        }      
    }

    if (tests[1])
    {
        if (limelight.getLatestResults().isPresent())
        {
            System.out.println(limelight.getLatestResults());
        }
    }

    if (tests[2])
    {
        if (limelightPoseEstimatorMT2.getPoseEstimate().isPresent() && limelightPoseEstimatorMT2.getPoseEstimate().get().hasData)
        {
            System.out.println("MT2 pose estimate\n" + limelightPoseEstimatorMT2.getPoseEstimate());
        }
    }

    if (tests[3])
    {
        System.out.println("MT2 alliance pose estimate\n" + limelightPoseEstimatorMT2.getAlliancePoseEstimate().orElseThrow());
    }

    if (tests[4])
    {
        System.out.println("pose estimate\n" + limelightPoseEstimator.getPoseEstimate().orElseThrow());
    }

    if (tests[5])
    {
        System.out.println("alliance pose estimate\n" +limelightPoseEstimator.getAlliancePoseEstimate().orElseThrow());
    }

  }

  @Override
  public void autonomousInit() {limelight.settingsBuilder().withPipelineIndex(0);}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {limelight.settingsBuilder().withPipelineIndex(1);
    // limelight.snapshot("RickTestToDelete"); // couldn't get this to work nor get LL to manage snapshots like it's supposed to
}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
// Example 1: Classifier Target Tracking
// Limelight limelight = new Limelight("limelight");
// // Get the results
// limelight.getLatestResults().ifPresent((LimelightResults result) -> {
//     for (NeuralClassifier object : result.targets_Classifier)
//     {
//         // Classifier says its a coral.
//         if (object.className.equals("coral"))
//         {
//             // Check pixel location of coral.
//             if (object.ty > 2 && object.ty < 1)
//             {
//             // Coral is valid! do stuff!
//             }
//         }
//     }
// });

// Example 2: Vision Pose Estimation with MegaTag2
// Limelight limelight = new Limelight("limelight");

// // Required for megatag2 in periodic() function before fetching pose.
// limelight.getSettings()
// 		 .withRobotOrientation(new Orientation3d(gyro.getRotation3d(),
// 												 new AngularVelocity3d(DegreesPerSecond.of(gyro.getPitchVelocity()),
// 																	   DegreesPerSecond.of(gyro.getRollVelocity()),
// 																	   DegreesPerSecond.of(gyro.getYawVelocity()))))
// 		 .save();
		 
// // Get MegaTag2 pose
// Optional<PoseEstimate> visionEstimate = poseEstimator.getPoseEstimate();
// // If the pose is present
// visionEstimate.ifPresent((PoseEstimate poseEstimate) -> {
//     // Add it to the pose estimator.
//     poseEstimator.addVisionMeasurement(poseEstimate.pose.toPose2d(), poseEstimate.timestampSeconds);
// });
