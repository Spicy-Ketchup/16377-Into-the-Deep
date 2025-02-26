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
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Autonomous(name="Left Solo Auto")
//@Disabled
public class A_Solo_Auto extends LinearOpMode {

    Pose2d initialPose = new Pose2d(0, 19, 0);
    PinpointDrive drive;
    public RevBlinkinLedDriver LED = null;
    public ColorSensor colorSensor = null;
    boolean blue = false;


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
        colorSensor = hardwareMap.get(ColorSensor.class, "CS");
        //  limelight = hardwareMap.get(Limelight3A.class, "LL");
     //   limelight.pipelineSwitch(2);
      //  limelight.start();


        TrajectoryActionBuilder sample_5_ascent = drive.actionBuilder(initialPose)
                .afterTime(0, (p) -> {ALiftTarget.set(245);
                    return false;})
                .afterDisp(5,(p) -> {
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    servos.wrist.setPosition(.25);
                    return false;})
                .setTangent(270)
                .splineToConstantHeading(new Vector2d(32.3,12), Math.toRadians(0), drive.customVelConstraint3)
                .stopAndAdd((p) -> {ALiftTarget.set(1050); return false;})
                .waitSeconds(0.55)
                .stopAndAdd((p) -> {servos.claw.setPosition(0); ALiftTarget.set(0); return false;})
                //Basket2
                .strafeToLinearHeading(new Vector2d(13,59.7), Math.toRadians(1))
                .afterTime(.4, (p) -> {ASlideTarget.set(700); return false;})
                .afterTime(.78, (p) -> {
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(.7);
                    servos.RightSpin.setPower(.7);
                    servos.Relbow.setPosition(0.78);
                    servos.Lelbow.setPosition(0.78);
                    servos.wrist.setPosition(0.15);
                return false;})
                .waitSeconds(1)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.18);
                    drive.leftFront.setPower(.18);
                    drive.leftBack.setPower(.18);
                    drive.rightBack.setPower(.18);
                    return false;})
                .waitSeconds(1)
                .stopAndAdd((p) -> {//
                    ASlideTarget.set(0);
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
                .strafeToLinearHeading(new Vector2d(8.2,64.8), Math.toRadians(-70))
                .afterTime(.2, (p) -> {
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                return false;})
                .afterTime(.5,(p) -> {
                    servos.LeftSpin.setPower(-1);
                    servos.RightSpin.setPower(-1);
                    return false;})
                .afterTime(.518,(p) -> {servos.claw.setPosition(0.42); return false;})
                .afterTime(.7, (p) -> {
                    servos.Relbow.setPosition(0.41);
                    servos.Lelbow.setPosition(0.41);
                    servos.wrist.setPosition(0.25);
                    return false;
                })
                .afterTime(1, (p) -> {ALiftTarget.set(3000); return false;})
                .afterTime(1, (p) -> {
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(2.5, (p) -> {servos.claw.setPosition(0); return false;})
                .afterTime(2.65, (p) -> {
                    servos.Relbow.setPosition(0.78);
                    servos.Lelbow.setPosition(0.78);
                    servos.wrist.setPosition(0.15);
                return false;})
                .afterTime(3, (p) -> {ALiftTarget.set(0); return false;})
                .waitSeconds(2.5)
                //Basket3
                .afterTime(0, (p) -> {ASlideTarget.set(700);return false;})
                .strafeToLinearHeading(new Vector2d(10.2,71), Math.toRadians(11))
                .afterTime(0, (p) -> {
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(.7);
                    servos.RightSpin.setPower(.7);
                    return false;})
                .waitSeconds(.55)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.13);
                    drive.leftFront.setPower(.13);
                    drive.leftBack.setPower(.13);
                    drive.rightBack.setPower(.13);
                    return false;})
                .waitSeconds(1.35)
                .stopAndAdd((p) -> {
                    ASlideTarget.set(0);
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
                .strafeToLinearHeading(new Vector2d(11,65.5), Math.toRadians(-62))
                .afterTime(.2, (p) -> {
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(.5,(p) -> {
                    servos.LeftSpin.setPower(-1);
                    servos.RightSpin.setPower(-1);
                    return false;})
                .afterTime(.5,(p) -> {servos.claw.setPosition(0.42); return false;})
                .afterTime(0.7, (p) -> {
                    servos.Relbow.setPosition(0.41);
                    servos.Lelbow.setPosition(0.41);
                    servos.wrist.setPosition(0.25);
                    return false;
                })
                .afterTime(1, (p) -> {ALiftTarget.set(3000); return false;})
                .afterTime(1, (p) -> {///////
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(2.1, (p) -> {servos.claw.setPosition(0); return false;})
                .afterTime(2.3, (p) -> {
                    servos.Relbow.setPosition(0.7);
                    servos.Lelbow.setPosition(0.7);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(2.3, (p) -> {ALiftTarget.set(0); return false;})
                .waitSeconds(2.3)
                //Basket4
                .strafeToLinearHeading(new Vector2d( 36.3, 65.8), Math.toRadians(83))
                .afterTime(0, (p) -> {
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(.9);
                    servos.RightSpin.setPower(.9);
                    return false;})
                .waitSeconds(.2)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.1);
                    drive.leftFront.setPower(.1);
                    drive.leftBack.setPower(.11);
                    drive.rightBack.setPower(.11);
                    return false;})
                .waitSeconds(1.35)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(0);
                    drive.leftFront.setPower(0);
                    drive.leftBack.setPower(0);
                    drive.rightBack.setPower(0);
                    return false;
                })
                .waitSeconds(.2)
                .afterTime(0,(p) -> {
                    servos.LPivot.setPosition(LPN);
                    servos.RPivot.setPosition(RPN);
                    servos.LeftSpin.setPower(-.3);
                    servos.RightSpin.setPower(-.3);
                    return false;})
                .afterTime(.1, (p) -> {
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                    return false;
                })
                .afterTime(.1, (p) -> {
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(0.7,(p) -> {
                    servos.LeftSpin.setPower(-1);
                    servos.RightSpin.setPower(-1);
                    return false;})
                .afterTime(0.8,(p) -> {servos.claw.setPosition(0.42); return false;})
                .afterTime(1, (p) -> {
                    servos.Relbow.setPosition(0.41);
                    servos.Lelbow.setPosition(0.41);
                    servos.wrist.setPosition(0.25);
                    return false;
                })//////
                .afterTime(1.2, (p) -> {ALiftTarget.set(3000); return false;})
                .afterTime(1.2, (p) -> {
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(2.3, (p) -> {servos.claw.setPosition(0); return false;})
                .afterTime(3.35, (p) -> {
                    servos.Relbow.setPosition(0.78);
                    servos.Lelbow.setPosition(0.78);
                    servos.wrist.setPosition(0.15);////
                    return false;})
                .afterTime(3.7, (p) -> {ALiftTarget.set(0); return false;})
                .strafeToLinearHeading(new Vector2d(9,67), Math.toRadians(-55))
                .waitSeconds(2)
                //Basket5
                .strafeToLinearHeading(new Vector2d(9, 26), Math.toRadians(-90))
                .afterTime(.5, (p) -> {ASlideTarget.set(700); return false;})
                .afterTime(.85, (p) -> {
                    servos.LPivot.setPosition(LPI);
                    servos.RPivot.setPosition(RPI);
                    servos.LeftSpin.setPower(1);
                    servos.RightSpin.setPower(1);
                    return false;
                })
                .waitSeconds(1.1)
                .stopAndAdd((p) -> {
                    drive.rightFront.setPower(.135);
                    drive.leftFront.setPower(.135);
                    drive.leftBack.setPower(.135);
                    drive.rightBack.setPower(.135);
                    return false;})
                .waitSeconds(1.35)
                .stopAndAdd((p) -> {
                    ASlideTarget.set(0);
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
                .strafeToLinearHeading(new Vector2d(8.2,67.8), Math.toRadians(-46))
                .afterTime(.2, (p) -> {
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(.5,(p) -> {
                    servos.LeftSpin.setPower(-1);
                    servos.RightSpin.setPower(-1);
                    return false;})
                .afterTime(.5,(p) -> {servos.claw.setPosition(0.42); return false;})
                .afterTime(0.7, (p) -> {
                    servos.Relbow.setPosition(0.41);
                    servos.Lelbow.setPosition(0.41);
                    servos.wrist.setPosition(0.25);
                    return false;
                })
                .afterTime(1, (p) -> {ALiftTarget.set(3000); return false;})
                .afterTime(1, (p) -> {///////
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition())/2 > 2200){
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.7);
                        return false;
                    } else return true;})
                .afterTime(2.1, (p) -> {servos.claw.setPosition(0); return false;})
                .afterTime(2.3, (p) -> {
                    servos.Relbow.setPosition(0.7);
                    servos.Lelbow.setPosition(0.7);
                    servos.wrist.setPosition(0.15);
                    return false;})
                .afterTime(2.3, (p) -> {ALiftTarget.set(0); return false;})
                .waitSeconds(2.3)

                ;

        ElapsedTime ebebeb = new ElapsedTime();
        servos.claw.setPosition(.42);
        while (ebebeb.seconds() < 1){}
        servos.wrist.setPosition(.59);
        servos.RPivot.setPosition(Red_Teleop.RPN);
        servos.LPivot.setPosition(Red_Teleop.LPN);
        servos.Relbow.setPosition(.83);
        servos.Lelbow.setPosition(.83);
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.RAINBOW_LAVA_PALETTE);
        waitForStart();

        Actions.runBlocking(new ParallelAction(
                sample_5_ascent.build(),
                (p) -> {lifts.update(ALiftTarget.get(), false); return true;},
                (p) -> {slide.update(ASlideTarget.get(), 0); return true;},
                (p) -> {blue = colorSensor.blue() > 375 && colorSensor.red() < 280; return true;}));
    }

}
