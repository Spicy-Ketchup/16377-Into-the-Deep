package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Red_Teleop.LPI;
import static org.firstinspires.ftc.teamcode.Red_Teleop.LPN;
import static org.firstinspires.ftc.teamcode.Red_Teleop.RPI;
import static org.firstinspires.ftc.teamcode.Red_Teleop.RPN;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Autonomous(name="Left - Auto - Path")
//@Disabled
public class Sample_LeftSideAutoPath extends LinearOpMode {

    Pose2d initialPose = new Pose2d(0, 48, 0);
    PinpointDrive drive;
    public RevBlinkinLedDriver LED = null;

    double speed_x = 0;

    public void runOpMode() throws InterruptedException {
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
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


        TrajectoryActionBuilder sample_5_ascent = drive.actionBuilder(initialPose)
                .afterTime(0.8, (p) -> {ALiftTarget.set(3000); return false;})
                .strafeToLinearHeading(new Vector2d(8,63.5), Math.toRadians(-55))
                .afterTime(0,(p) -> {
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(1.45, (p) -> {
                        servos.claw.setPosition(0);
                        return false;})
                .afterTime(1.7, (p) -> {
                    servos.Relbow.setPosition(0.78);
                    servos.Lelbow.setPosition(0.78);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(1.98, (p) -> {ALiftTarget.set(0); servos.claw.setPosition(0); return false;})
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(9,58), Math.toRadians(8))
                .afterTime(.2, (p) -> {
                    ASlideTarget.set(700);
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                return false;})
                .waitSeconds(.45)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.18);
                    drive.leftFront.setPower(.18);
                    drive.leftBack.setPower(.18);
                    drive.rightBack.setPower(.18);
                    return false;})
                .waitSeconds(1.5)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(0);
                    drive.leftFront.setPower(0);
                    drive.leftBack.setPower(0);
                    drive.rightBack.setPower(0);
                    servos.LPivot.setPosition(LPN);
                    servos.RPivot.setPosition(RPN);
                    servos.LeftSpin.setPower(-.4);
                    servos.RightSpin.setPower(-4);
                    return false;})
                .afterTime(.1, (p) -> {
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                    return false;
                })
                .strafeToLinearHeading(new Vector2d(8.1,63.8), Math.toRadians(-70))
                .afterTime(0, (p) -> {ASlideTarget.set(0); return false;})
                .afterTime(.8, (p) -> {
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                return false;})
                .afterTime(1.1,(p) -> {
                    servos.LeftSpin.setPower(-1);
                    servos.RightSpin.setPower(-1);
                    return false;})
                .afterTime(1.1,(p) -> {servos.claw.setPosition(0.42); return false;})
                .afterTime(1.3, (p) -> {
                    servos.Relbow.setPosition(0.41);
                    servos.Lelbow.setPosition(0.41);
                    servos.wrist.setPosition(0.25);
                    return false;
                })
                .afterTime(1.5, (p) -> {ALiftTarget.set(3000); return false;})
                .afterTime(1.5, (p) -> {
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(3, (p) -> {servos.claw.setPosition(0); return false;})
                .afterTime(3.15, (p) -> {
                    servos.Relbow.setPosition(0.78);
                    servos.Lelbow.setPosition(0.78);
                    servos.wrist.setPosition(0.15);
                return false;})
                .afterTime(3.5, (p) -> {ALiftTarget.set(0); return false;})
                .waitSeconds(3.6)
                .strafeToLinearHeading(new Vector2d(9.5,67.55), Math.toRadians(10))
                .afterTime(.2, (p) -> {
                    ASlideTarget.set(700);
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                    return false;})
                .waitSeconds(.45)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.18);
                    drive.leftFront.setPower(.18);
                    drive.leftBack.setPower(.18);
                    drive.rightBack.setPower(.18);
                    return false;})
                .waitSeconds(1.35)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(0);
                    drive.leftFront.setPower(0);
                    drive.leftBack.setPower(0);
                    drive.rightBack.setPower(0);
                    servos.LPivot.setPosition(LPN);
                    servos.RPivot.setPosition(RPN);
                    servos.LeftSpin.setPower(-.4);
                    servos.RightSpin.setPower(-.4);
                    return false;})
                .afterTime(.1, (p) -> {
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                    return false;
                })
                .strafeToLinearHeading(new Vector2d(8,64), Math.toRadians(-62))
                .afterTime(0, (p) -> {ASlideTarget.set(0); return false;})
                .afterTime(.8, (p) -> {
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(1.1,(p) -> {
                    servos.LeftSpin.setPower(-1);
                    servos.RightSpin.setPower(-1);
                    return false;})
                .afterTime(1.1,(p) -> {servos.claw.setPosition(0.42); return false;})
                .afterTime(1.3, (p) -> {
                    servos.Relbow.setPosition(0.41);
                    servos.Lelbow.setPosition(0.41);
                    servos.wrist.setPosition(0.25);
                    return false;
                })
                .afterTime(1.5, (p) -> {ALiftTarget.set(3000); return false;})
                .afterTime(1.5, (p) -> {///////
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(3.5, (p) -> {servos.claw.setPosition(0); return false;})
                .afterTime(3.65, (p) -> {
                    servos.Relbow.setPosition(0.7);
                    servos.Lelbow.setPosition(0.7);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(4, (p) -> {ALiftTarget.set(0); return false;})
                .waitSeconds(4.1)
                .strafeToLinearHeading(new Vector2d( 37.9, 57.7), Math.toRadians(90))
                .afterTime(.4, (p) -> {
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                    return false;})
                .waitSeconds(.45)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.12);
                    drive.leftFront.setPower(.12);
                    drive.leftBack.setPower(.125);
                    drive.rightBack.setPower(.125);
                    return false;})
                .waitSeconds(1.9)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(0);
                    drive.leftFront.setPower(0);
                    drive.leftBack.setPower(0);
                    drive.rightBack.setPower(0);
                    return false;
                })
                .waitSeconds(.05)
                .stopAndAdd((p) -> {
                    servos.LPivot.setPosition(LPN);
                    servos.RPivot.setPosition(RPN);
                    servos.LeftSpin.setPower(-.4);
                    servos.RightSpin.setPower(-.4);
                    return false;})
                .afterTime(.1, (p) -> {
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                    return false;
                })
                .strafeToLinearHeading(new Vector2d(9,66), Math.toRadians(-55))
                .afterTime(0, (p) -> {ASlideTarget.set(0); return false;})
                .afterTime(.6, (p) -> {
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(1.1,(p) -> {
                    servos.LeftSpin.setPower(-1);
                    servos.RightSpin.setPower(-1);
                    return false;})
                .afterTime(1.1,(p) -> {servos.claw.setPosition(0.42); return false;})
                .afterTime(1.3, (p) -> {
                    servos.Relbow.setPosition(0.41);
                    servos.Lelbow.setPosition(0.41);
                    servos.wrist.setPosition(0.25);
                    return false;
                })//////
                .afterTime(1.5, (p) -> {ALiftTarget.set(3000); return false;})
                .afterTime(1.5, (p) -> {
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(3.5, (p) -> {servos.claw.setPosition(0); return false;})
                .afterTime(3.65, (p) -> {
                    servos.Relbow.setPosition(0.78);
                    servos.Lelbow.setPosition(0.78);
                    servos.wrist.setPosition(0.15);////
                    return false;})
                .afterTime(4, (p) -> {ALiftTarget.set(0); return false;})
                .waitSeconds(4.1)
                .afterTime(.5, (p) -> {
                    servos.Relbow.setPosition(0.36);
                    servos.Lelbow.setPosition(0.36);
                    servos.wrist.setPosition(0.42);
                    return false;
                })
                .strafeToLinearHeading(new Vector2d(54, 38), Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(54, 24), Math.toRadians(90))
                .waitSeconds(30);

        ElapsedTime ebebeb = new ElapsedTime();
        servos.claw.setPosition(.42);
        while (ebebeb.seconds() < 1){}
        servos.wrist.setPosition(.59);
        servos.RPivot.setPosition(Red_Teleop.RPN);
        servos.LPivot.setPosition(Red_Teleop.LPN);
        servos.Relbow.setPosition(.83);
        servos.Lelbow.setPosition(.83);
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BREATH_RED);
        waitForStart();

        Actions.runBlocking(new ParallelAction(
                sample_5_ascent.build(),
                (p) -> {lifts.update(ALiftTarget.get(), false); return true;},
                (p) -> {slide.update(ASlideTarget.get()); return true;}));
    }

}
