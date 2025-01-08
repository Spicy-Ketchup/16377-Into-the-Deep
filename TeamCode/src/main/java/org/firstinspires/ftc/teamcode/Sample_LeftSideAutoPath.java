package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Lm3Teleop.LPI;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.LPN;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.RPI;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.RPN;

import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Autonomous(name="Left - Auto - Path")
public class Sample_LeftSideAutoPath extends LinearOpMode {

    Pose2d initialPose = new Pose2d(0, 48, 0);
    PinpointDrive drive;
    public Limelight3A limelight = null;
    double speed_x = 0;

    public void runOpMode() throws InterruptedException {
        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        Servos servos = new Servos(hardwareMap);
        AtomicInteger ALiftTarget = new AtomicInteger();
        AtomicInteger ASlideTarget = new AtomicInteger();
        AtomicBoolean huh = new AtomicBoolean(false);
      //  limelight = hardwareMap.get(Limelight3A.class, "LL");
     //   limelight.pipelineSwitch(2);
      //  limelight.start();


        TrajectoryActionBuilder sample_5_ascent = drive.actionBuilder(initialPose)
                .afterTime(0.8, (p) -> {ALiftTarget.set(2700); return false;})
                .strafeToLinearHeading(new Vector2d(8,63.5), Math.toRadians(-45))
                .afterTime(0,(p) -> {
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2000){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.42);
                        return false;
                    } else return true;})
                .afterTime(1.5, (p) -> {
                        servos.claw.setPosition(0);
                        return false;})
                .afterTime(1.7, (p) -> {
                    servos.Relbow.setPosition(.925);
                    servos.Lelbow.setPosition(.925);
                    servos.wrist.setPosition(.375);
                    return false;})
                .afterTime(1.98, (p) -> {ALiftTarget.set(750); servos.claw.setPosition(.1); return false;})
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(8,58), Math.toRadians(5))
                .afterTime(.2, (p) -> {
                    ASlideTarget.set(750);
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                return false;})
                .waitSeconds(.45)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.22);
                    drive.leftFront.setPower(.22);
                    drive.leftBack.setPower(.22);
                    drive.rightBack.setPower(.22);
                    return false;})
                .waitSeconds(1.15)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(0);
                    drive.leftFront.setPower(0);
                    drive.leftBack.setPower(0);
                    drive.rightBack.setPower(0);
                    servos.LPivot.setPosition(LPN);
                    servos.RPivot.setPosition(RPN);
                    servos.LeftSpin.setPower(0);
                    servos.RightSpin.setPower(0);
                    ASlideTarget.set(55);
                    return false;
                    })

                .waitSeconds(30);

        servos.RPivot.setPosition(RPN);
        servos.LPivot.setPosition(LPN);
        servos.Relbow.setPosition(.83);
        servos.Lelbow.setPosition(.83);
        servos.wrist.setPosition(.69);
        servos.claw.setPosition(.42);
        waitForStart();

        Actions.runBlocking(new ParallelAction(
                sample_5_ascent.build(),
                (p) -> {lifts.update(ALiftTarget.get()); return true;},
                (p) -> {slide.update(ASlideTarget.get()); return true;}));
    }

}
