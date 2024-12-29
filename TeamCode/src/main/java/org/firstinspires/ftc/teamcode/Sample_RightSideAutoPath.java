package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Right - Auto - Path")
public class Sample_RightSideAutoPath extends LinearOpMode {

    public void runOpMode() throws InterruptedException{

        Pose2d initialPose = new Pose2d(0, 48, 0);
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);

        TrajectoryActionBuilder specimen_5_park = drive.actionBuilder(initialPose) //MWV-120, minPA--50, maxPA-110
                .strafeToConstantHeading(new Vector2d(32,48))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(22,18))
                .waitSeconds(1.5)
                .strafeToConstantHeading(new Vector2d(22,9))
                .waitSeconds(1.5)
                .turn(Math.toRadians(-25))
                .waitSeconds(1.5)
                .strafeToLinearHeading(new Vector2d(6, 24), 0)
                .strafeToConstantHeading(new Vector2d(0,24))
                .waitSeconds(.3)
                .strafeToConstantHeading(new Vector2d(32,48))
                .waitSeconds(.5)
                .strafeToLinearHeading(new Vector2d(6, 24), 0)
                .strafeToConstantHeading(new Vector2d(0,24))
                .waitSeconds(.3)
                .strafeToConstantHeading(new Vector2d(32,48))
                .waitSeconds(.5)
                .strafeToLinearHeading(new Vector2d(6, 24), 0)
                .strafeToConstantHeading(new Vector2d(0,24))
                .waitSeconds(.3)
                .strafeToConstantHeading(new Vector2d(32,48))
                .waitSeconds(.5)
                .strafeToLinearHeading(new Vector2d(6, 24), 0)
                .strafeToConstantHeading(new Vector2d(0,24))
                .waitSeconds(.3)
                .strafeToConstantHeading(new Vector2d(32,48))
                .waitSeconds(.5)
                .strafeToConstantHeading(new Vector2d(5,5));


        waitForStart();

        Actions.runBlocking(specimen_5_park.build());


    }
}
