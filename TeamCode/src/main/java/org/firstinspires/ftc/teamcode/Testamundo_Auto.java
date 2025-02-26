package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Red_Teleop.LPI;
import static org.firstinspires.ftc.teamcode.Red_Teleop.LPN;
import static org.firstinspires.ftc.teamcode.Red_Teleop.RPI;
import static org.firstinspires.ftc.teamcode.Red_Teleop.RPN;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Autonomous(name="Split Path Test")
//@Disabled
public class Testamundo_Auto extends LinearOpMode {

    Pose2d initialPose = new Pose2d(0, 0, 0);
    PinpointDrive drive;
    public RevBlinkinLedDriver LED = null;

    double speed_x = 0;

    public void runOpMode() throws InterruptedException {
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
        Random random = new Random();
        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        Servos servos = new Servos(hardwareMap);
        AtomicInteger ALiftTarget = new AtomicInteger();
        AtomicInteger ASlideTarget = new AtomicInteger();
        AtomicBoolean huh = new AtomicBoolean(false);
        LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
        //  limelight = hardwareMap.get(Limelight3A.class, "LL");
        //   limelight.pipelineSwitch(2);
        //  limelight.start();


        TrajectoryActionBuilder p1 = drive.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(24, 0));
        TrajectoryActionBuilder p2p1 = p1.endTrajectory().fresh()
                .turnTo(Math.toRadians(-90))
                .waitSeconds(30);
        TrajectoryActionBuilder p2p2 = p1.endTrajectory().fresh()
                .turnTo(Math.toRadians(90))
                .waitSeconds(30);
        TrajectoryActionBuilder P2T = (random.nextInt(2) == 1 ? p2p1 : p2p2);

        ElapsedTime ebebeb = new ElapsedTime();
        servos.claw.setPosition(.42);
        while (ebebeb.seconds() < 1){}
        servos.wrist.setPosition(.59);
        servos.RPivot.setPosition(Red_Teleop.RPN);
        servos.LPivot.setPosition(Red_Teleop.LPN);
        servos.Relbow.setPosition(.83);
        servos.Lelbow.setPosition(.83);
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
        telemetry.addData("Turn", P2T == p2p1 ? "Left" : "Right");
        telemetry.update();
        waitForStart();

        Actions.runBlocking(new ParallelAction(
                new SequentialAction(
                p1.build(), P2T.build()),
                (p) -> {lifts.update(ALiftTarget.get(), false); return true;},
                (p) -> {slide.update(ASlideTarget.get(),0); return true;}));
    }

}
