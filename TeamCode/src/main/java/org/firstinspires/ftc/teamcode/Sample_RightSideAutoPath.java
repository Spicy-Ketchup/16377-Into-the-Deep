package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.atomic.AtomicInteger;

@Autonomous(name="Right - Auto - Path")
public class Sample_RightSideAutoPath extends LinearOpMode {

    public void runOpMode() throws InterruptedException{
        Pose2d initialPose = new Pose2d(0, 81, 0);
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        Servos servos = new Servos(hardwareMap);
        AtomicInteger ALiftTarget = new AtomicInteger();
        AtomicInteger ASlideTarget = new AtomicInteger();
        
        TrajectoryActionBuilder specimen_5_park = drive.actionBuilder(initialPose) //MWV-120, minPA--50, maxPA-110
                .afterDisp(5,(p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    servos.wrist.setPosition(.35);
                    ALiftTarget.set(100);
                    return false;})
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(31.8,88.5), Math.toRadians(0))
                .stopAndAdd((p) -> {ALiftTarget.set(1030); return false;})
                .waitSeconds(0.54)
                .stopAndAdd((p) -> {servos.claw.setPosition(0); ALiftTarget.set(0); return false;})
                .waitSeconds(0.34)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(14,58), Math.toRadians(0),drive.customVelConstraint2)
                .splineToConstantHeading(new Vector2d(51,58),Math.toRadians(0))
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(49,40), Math.toRadians(65),drive.customVelConstraint)
                .splineToConstantHeading(new Vector2d(14,40), Math.toRadians(65))
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(51,40), Math.toRadians(0))
                .setTangent(Math.toRadians(0))
                .splineToConstantHeading(new Vector2d(49,30),Math.toRadians(65),drive.customVelConstraint)
                .splineToConstantHeading(new Vector2d(14,30), Math.toRadians(65))
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(51,36.5), Math.toRadians(0),drive.customVelConstraint)
                .strafeToConstantHeading(new Vector2d(50,27), drive.customVelConstraint2)
                .afterTime(0, (p) -> {
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.45);
                return false;})
                .strafeToConstantHeading(new Vector2d(9.5, 27),drive.customVelConstraint3)
                .stopAndAdd((p) -> {
                    servos.claw.setPosition(.42);
                    ElapsedTime clawTime = new ElapsedTime();
                    while (clawTime.seconds()<.45 && opModeIsActive()){}
                        ALiftTarget.set(400);
                    return false;})
                .afterTime(.25, (p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    return false;})
                .afterTime(1, (p) -> {ALiftTarget.set(0); return false;})
                .splineToConstantHeading(new Vector2d(31.1, 90),Math.toRadians(0))
                .setTangent(Math.toRadians(0))
                .stopAndAdd((p) -> {ALiftTarget.set(1030); return false;})
                .waitSeconds(0.55)
                .stopAndAdd((p) -> {servos.claw.setPosition(0); ALiftTarget.set(0); return false;})
                .waitSeconds(0.4)
                .afterDisp(1, (p) -> {
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.35);
                    return false;})
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(14.25,54.5), Math.toRadians(65))
                .strafeToConstantHeading(new Vector2d(9.7,54.5), drive.customVelConstraint)
                .stopAndAdd((p) -> {
                    servos.claw.setPosition(.42);
                    ElapsedTime clawTime = new ElapsedTime();
                    while (clawTime.seconds()<.45 && opModeIsActive()){}
                    ALiftTarget.set(400);
                    return false;})
                .afterTime(.25, (p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    return false;})
                .afterTime(1, (p) -> {ALiftTarget.set(0); return false;})
                .splineToConstantHeading(new Vector2d(31, 92),Math.toRadians(0))
                .stopAndAdd((p) -> {ALiftTarget.set(1030); return false;})
                .waitSeconds(0.55)
                .stopAndAdd((p) -> {servos.claw.setPosition(0); ALiftTarget.set(0); return false;})
                .waitSeconds(0.4)
                .afterDisp(1, (p) -> {
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.35);
                    return false;})
                .setTangent(Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(14.25,54.5), Math.toRadians(65))
                .strafeToConstantHeading(new Vector2d(9.5,54.5), drive.customVelConstraint)
                .stopAndAdd((p) -> {
                    servos.claw.setPosition(.42);
                    ElapsedTime clawTime = new ElapsedTime();
                    while (clawTime.seconds()<.45 && opModeIsActive()){}
                    ALiftTarget.set(400);
                    return false;})
                .afterTime(.25, (p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    return false;})
                .afterTime(1, (p) -> {ALiftTarget.set(0); return false;})
                .splineToConstantHeading(new Vector2d(31, 84.5),Math.toRadians(0))
                .stopAndAdd((p) -> {ALiftTarget.set(1030); return false;})
                .waitSeconds(0.45)
                .afterTime(0, (p) -> {servos.claw.setPosition(0); return false;})
                .waitSeconds(.15)
                .afterTime(0,(p) -> { ALiftTarget.set(0); return false;})
                .strafeToConstantHeading(new Vector2d(20, 84.5), drive.customVelConstraint4, drive.customAccelConstraint)
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
        servos.RPivot.setPosition(Lm3Teleop.RPN);
        servos.LPivot.setPosition(Lm3Teleop.LPN);
        servos.Relbow.setPosition(.83);
        servos.Lelbow.setPosition(.83);
        servos.wrist.setPosition(.69);
        servos.claw.setPosition(.42);
        waitForStart();

        Actions.runBlocking(new ParallelAction(
                specimen_5_park.build(),
                (p) -> {lifts.update(ALiftTarget.get()); return true;},
                (p) -> {slide.update(ASlideTarget.get()); return true;}));


    }
}
