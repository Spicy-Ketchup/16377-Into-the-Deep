package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.atomic.AtomicInteger;

@Autonomous(name="Cool Right Auto")
//@Disabled
public class Cooler_Right_Side_Auto extends LinearOpMode {
    public RevBlinkinLedDriver LED = null;
    public void runOpMode() throws InterruptedException{
        Pose2d initialPose = new Pose2d(0, 81, 0);
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        Servos servos = new Servos(hardwareMap);
        AtomicInteger ALiftTarget = new AtomicInteger();
        AtomicInteger ASlideTarget = new AtomicInteger();
        LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");


        TrajectoryActionBuilder specimen_5_park = drive.actionBuilder(initialPose)
                ;

                //init stuf

        ElapsedTime ebebeb = new ElapsedTime();
        servos.claw.setPosition(.42);
        while (ebebeb.seconds() < 1){}
        servos.wrist.setPosition(.6);
        servos.RPivot.setPosition(Red_Teleop.RPN);
        servos.LPivot.setPosition(Red_Teleop.LPN);
        servos.Relbow.setPosition(.2);
        servos.Lelbow.setPosition(.2);
        waitForStart();

        Actions.runBlocking(new ParallelAction(
                specimen_5_park.build(),
                (p) -> {lifts.update(ALiftTarget.get(),false); return true;},
                (p) -> {slide.update(ASlideTarget.get(),0); return true;}));


    }
}
