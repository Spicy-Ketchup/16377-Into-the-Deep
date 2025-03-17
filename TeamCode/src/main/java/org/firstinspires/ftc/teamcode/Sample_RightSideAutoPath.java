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

@Autonomous(name="Right - Auto - Path")
//@Disabled
public class Sample_RightSideAutoPath extends LinearOpMode {
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
                .afterTime(0, (p) -> {ALiftTarget.set(245);
                    LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BEATS_PER_MINUTE_RAINBOW_PALETTE);
                    return false;})
                .afterDisp(5,(p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    servos.wrist.setPosition(.25);
                    return false;})
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(32.5,88), Math.toRadians(0), drive.customVelConstraint3)
                .stopAndAdd((p) -> {ALiftTarget.set(1050); return false;})
                .waitSeconds(0.54)
                .stopAndAdd((p) -> {servos.claw.setPosition(0); ALiftTarget.set(0); return false;})
                .waitSeconds(0.34)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(14,58), Math.toRadians(0),drive.customVelConstraint2)
                .splineToConstantHeading(new Vector2d(45,61),Math.toRadians(0), drive.customVelConstraint)
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(49,35), Math.toRadians(65))
                .splineToConstantHeading(new Vector2d(14,42.3), Math.toRadians(65), drive.customVelConstraint2)
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(51,40), Math.toRadians(0), drive.customVelConstraint3)
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(49,29.5),Math.toRadians(65),drive.customVelConstraint)
                .setTangent(270)
                .afterTime(0, (p) -> {
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.25);
                return false;})
                .strafeToConstantHeading(new Vector2d(8.98, 26.7),drive.customVelConstraint6, drive.customAccelConstraint2)
                .stopAndAdd((p) -> {
                    servos.claw.setPosition(.42);
                    return false;})
                .afterTime(.15, (p) -> {ALiftTarget.set(400); return false;})
                .afterTime(.25, (p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    servos.wrist.setPosition(.25);
                    return false;})
                .afterTime(1.7, (p) -> {ALiftTarget.set(230); return false;})
                .waitSeconds(.85)
                .splineToConstantHeading(new Vector2d(32, 89.6),Math.toRadians(0),drive.customVelConstraint3)
                .setTangent(Math.toRadians(0))
                .stopAndAdd((p) -> {ALiftTarget.set(1050); return false;})
                .waitSeconds(0.55)
                .stopAndAdd((p) -> {servos.claw.setPosition(0); ALiftTarget.set(0); return false;})
                .waitSeconds(0.4)
                .afterDisp(1, (p) -> {
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.25);
                    return false;})
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(15,58.5), Math.toRadians(65), drive.customVelConstraint3)
                .waitSeconds(0.1)
                .strafeToConstantHeading(new Vector2d(9.1,55.2), drive.customVelConstraint5)
                .waitSeconds(.25)
                .stopAndAdd((p) -> {
                    servos.claw.setPosition(.42);
                    return false;})
                .afterTime(.15, (p) -> {ALiftTarget.set(400); return false;})
                .afterTime(.25, (p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    servos.wrist.setPosition(.25);
                    return false;})
                .afterTime(1.7, (p) -> {ALiftTarget.set(210); return false;})
                .waitSeconds(.85)
                .splineToConstantHeading(new Vector2d(32, 91.5),Math.toRadians(0),drive.customVelConstraint3)
                .stopAndAdd((p) -> {ALiftTarget.set(1030); return false;})
                .waitSeconds(0.55)
                .stopAndAdd((p) -> {servos.claw.setPosition(0); ALiftTarget.set(0); return false;})
                .waitSeconds(0.4)
                .afterDisp(1, (p) -> {
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.25);
                    return false;})
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(15,57.8), Math.toRadians(65),drive.customVelConstraint3)
                .waitSeconds(.15)
                .strafeToConstantHeading(new Vector2d(9.15,55.2), drive.customVelConstraint5)
                .waitSeconds(.25)
                .stopAndAdd((p) -> {
                    servos.claw.setPosition(.42);
                    return false;})
                .afterTime(.15, (p) -> {ALiftTarget.set(400); return false;})
                .afterTime(.25, (p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    servos.wrist.setPosition(.25);
                    return false;})
                .afterTime(1.7, (p) -> {ALiftTarget.set(210); return false;})
                .waitSeconds(.85)
                .splineToConstantHeading(new Vector2d(31.7, 84),Math.toRadians(0),drive.customVelConstraint3)
                .stopAndAdd((p) -> {ALiftTarget.set(1030); return false;})
                .waitSeconds(0.45)
                .afterTime(0, (p) -> {servos.claw.setPosition(0); return false;})
                .waitSeconds(.15)
                .afterTime(0,(p) -> { ALiftTarget.set(0); return false;})
                .strafeToLinearHeading(new Vector2d(7, 30),Math.toRadians(90), drive.customVelConstraint4, drive.customAccelConstraint)
                // .splineToConstantHeading(new Vector2d(50,56),Math.toRadians(0))
                //  .splineToConstantHeading(new Vector2d(50, 39),Math.toRadians(90))
               // .splineToConstantHeading(new Vector2d(9, 39),Math.toRadians(90))
               // .splineToConstantHeading(new Vector2d(50, 39),Math.toRadians(90))
               // .splineToConstantHeading(new Vector2d(50, 30), Math.toRadians(90))
              //  .splineToConstantHeading(new Vector2d(9, 30),Math.toRadians(90))
               // .splineToConstantHeading(new Vector2d(50, 30),Math.toRadians(90))
               // .splineToConstantHeading(new Vector2d(50, 24.5), Math.toRadians(90))
              //  .splineToConstantHeading(new Vector2d(9, 24.5),Math.toRadians(90))
                ;

                //init stuf

        ElapsedTime ebebeb = new ElapsedTime();
        servos.claw.setPosition(.42);
        while (ebebeb.seconds() < 1){}
        servos.wrist.setPosition(.59);
        servos.RPivot.setPosition(Red_Teleop.RPN);
        servos.LPivot.setPosition(Red_Teleop.LPN);
        servos.Relbow.setPosition(.83);
        servos.Lelbow.setPosition(.83);
        waitForStart();

        Actions.runBlocking(new ParallelAction(
                specimen_5_park.build(),
                (p) -> {lifts.update(ALiftTarget.get(),false); return true;},
                (p) -> {slide.update(ASlideTarget.get(),0); return true;}));


    }
}
