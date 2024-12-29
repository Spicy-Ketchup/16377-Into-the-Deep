package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Left - Auto - Path")
public class Sample_LeftSideAutoPath extends LinearOpMode {

    Pose2d initialPose = new Pose2d(0, 24, 0);
    PinpointDrive drive;
    public Limelight3A limelight = null;
    double speed_x = 0;

    public void runOpMode() throws InterruptedException {
        drive = new PinpointDrive(hardwareMap, initialPose);
        limelight = hardwareMap.get(Limelight3A.class, "LL");
        limelight.pipelineSwitch(2);
        limelight.start();


        TrajectoryActionBuilder sample_5_ascent = drive.actionBuilder(initialPose)
                .strafeToLinearHeading(new Vector2d(30, 24), 0)
                .waitSeconds(0.5)
                .turn(Math.toRadians(-90))
                .waitSeconds(.2)
                .stopAndAdd((p) -> {
                    ElapsedTime detectionTime = new ElapsedTime();
                    ElapsedTime forceTime = new ElapsedTime();
                    while (detectionTime.seconds() < 1.5 && forceTime.seconds() < 3.5 && opModeIsActive()) {
                        LLResult result = limelight.getLatestResult();
                        if (result.getTx() == 0) {
                            speed_x = -0.2;
                            detectionTime.reset();
                        } else if (result.getTx() > 4.25) {
                            speed_x = .25;
                            detectionTime.reset();
                        } else if (result.getTx() < -4.25) {
                            speed_x = -.25;
                            detectionTime.reset();
                        } else
                            speed_x = 0;

                        setAutoPower(speed_x, speed_x);

                        telemetry.addData("tx", result.getTx());
                        telemetry.addData("ty", result.getTy());
                        telemetry.addData("ta", result.getTa());
                        telemetry.update();
                    }
                    ElapsedTime forward = new ElapsedTime();
                    while (forward.seconds() < .6 && opModeIsActive()) {
                        setAutoPower(.1, -.1);
                    }
                    return false;
                }) //Correction
                .waitSeconds(1)
                .turnTo(0)
                .stopAndAdd((p) -> {limelight.pipelineSwitch(0); return false;})
                 .waitSeconds(0.5)
                .stopAndAdd((p) -> {
                    ElapsedTime detectionTime = new ElapsedTime();
                    ElapsedTime forceTime = new ElapsedTime();
                    while (detectionTime.seconds() < 1.5 && forceTime.seconds() < 3.5 && opModeIsActive()) {
                        LLResult result = limelight.getLatestResult();
                        if (result.getTx() == 0) {
                            speed_x = -0.2;
                            detectionTime.reset();
                        } else if (result.getTx() > 4.25) {
                            speed_x = .25;
                            detectionTime.reset();
                        } else if (result.getTx() < -4.25) {
                            speed_x = -.25;
                            detectionTime.reset();
                        } else
                            speed_x = 0;

                        setAutoPower(speed_x, speed_x);

                        telemetry.addData("tx", result.getTx());
                        telemetry.addData("ty", result.getTy());
                        telemetry.addData("ta", result.getTa());
                        telemetry.update();
                    }
                    ElapsedTime forward = new ElapsedTime();
                    while (forward.seconds() < .6 && opModeIsActive()) {
                        setAutoPower(.1, -.1);
                    }
                    return false;
                }) //Correction
                 .waitSeconds(1)
                .splineToLinearHeading(new Pose2d(20,24,90),0)
                .stopAndAdd((p) -> {
                    telemetry.addData("|\\---/|\n" + "| o_o |\n" + " \\_^_/",""); telemetry.update(); return false;})
                .waitSeconds(30);


        waitForStart();

        Actions.runBlocking(sample_5_ascent.build());

    }
    public void setAutoPower(double speed, double speed2) {
        drive.rightFront.setPower(-speed2);
        drive.leftFront.setPower(speed);
        drive.leftBack.setPower(-speed2);
        drive.rightBack.setPower(speed);
    }

}
