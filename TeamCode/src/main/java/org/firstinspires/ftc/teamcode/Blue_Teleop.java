package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name="A - Blue Teleop")
//@Disabled
public class Blue_Teleop extends LinearOpMode {

    Robot robot = new Robot();
    public ColorSensor colorSensor = null;
    public RevBlinkinLedDriver LED = null;
    public static int LiftTarget = 0;
    public static int HarmTarget = 0;
    double speed = 1.0;
    boolean high = false;
    boolean low = false;
    boolean resetting = false;
    public boolean blue = false;
    public boolean yellow = false;
    public boolean red = false;
    enum lift_State {
        up,
        down,
        grab,
        lowBasket,
        highBasket,
        specimenGrab,
        specimenMidTime,
        specimenScore,
        resettiSpaghetti

    }
    enum slide_State {
        transfer,
        out,
        mid
    }

    double clawGrab = .42;
    double clawOpen = 0;

    public static double RPI = 0.87;
    public static double LPI = 0.5;
    public static double RPN = 0.74;
    public static double LPN = 0.74;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        colorSensor = hardwareMap.get(ColorSensor.class, "CS");
        LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");
        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        Servos servos = new Servos(hardwareMap);
        LiftTarget = 0;
        HarmTarget = 0;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        servos.Relbow.setPosition(.5);
        servos.Lelbow.setPosition(.5);
        servos.wrist.setPosition(.3);
        servos.claw.setPosition(clawGrab);
        servos.RPivot.setPosition(RPN);
        servos.LPivot.setPosition(LPN);
        lift_State liftState = lift_State.up;
        slide_State slideState = slide_State.transfer;
        ElapsedTime transferTime = new ElapsedTime();
        ElapsedTime speedChangeTime = new ElapsedTime();
        ElapsedTime specGrabWait = new ElapsedTime();
        ElapsedTime resetTime = new ElapsedTime();
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
        waitForStart();
        while (opModeIsActive()) {
            {
                robot.rightFront.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * speed);
                robot.leftFront.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * speed);
                robot.leftBack.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * speed);
                robot.rightBack.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * speed);
            }  //Movement Block
            blue = colorSensor.blue() > 375 && colorSensor.red() < 280;
            red = colorSensor.red() > 500 && colorSensor.green() > 200 && colorSensor.green() < 600;
            yellow = colorSensor.red() > 580 && colorSensor.green() > 600;
            switch (liftState) {
                case up:
                    speed = 1;
                    LiftTarget = 0;
                    servos.Relbow.setPosition(0.78);
                    servos.Lelbow.setPosition(0.78);
                    servos.wrist.setPosition(0.15);
                    if (gamepad2.dpad_up) {
                        high = true;
                        liftState = lift_State.down;
                        transferTime.reset();
                    } else if (gamepad2.dpad_right) {
                        low = true;
                        liftState = lift_State.down;
                        transferTime.reset();
                    } else if (gamepad2.dpad_down) {
                        liftState = lift_State.specimenGrab;
                        transferTime.reset();
                } else if (gamepad2.circle){
                        resetTime.reset();
                        liftState = lift_State.resettiSpaghetti;
                    }
                    break;
                //////////////
                case down:
                    servos.Relbow.setPosition(0.82);
                    servos.Lelbow.setPosition(0.82);
                    servos.wrist.setPosition(0.15);
                    servos.claw.setPosition(clawOpen);
                    if (transferTime.seconds() > 0.2) {
                        transferTime.reset();
                        liftState = lift_State.grab;
                    }
                    break;
                //////////////
                case grab:
                    if (transferTime.seconds() > .2) {
                        servos.claw.setPosition(clawGrab);
                        servos.LeftSpin.setPower(-1);
                        servos.RightSpin.setPower(-1);
                    }
                    if (transferTime.seconds() > .6) {
                        servos.Relbow.setPosition(0.41);
                        servos.Lelbow.setPosition(0.41);
                        servos.wrist.setPosition(0.25);
                        if (high) {
                            liftState = lift_State.highBasket;
                            high = false;
                        } else if (low) {
                            liftState = lift_State.lowBasket;
                            low = false;
                        } else {
                            specGrabWait.reset();
                            liftState = lift_State.specimenMidTime; //////////////
                        }
                    }
                    break;
                //////////////
                case highBasket:
                    speed = 0.3;
                    LiftTarget = 3000;
                    if ((lifts.Lpos() + lifts.Rpos())/2 > 2200)
                        servos.wrist.setPosition(.65);
                    if (gamepad2.a)
                        liftState = lift_State.up;
                    break;
                //////////////
                case lowBasket:
                    speed = 0.3;
                    LiftTarget = 1200;
                    if ((lifts.Lpos() + lifts.Rpos())/2 > 700)
                        servos.wrist.setPosition(.65);
                    if (gamepad2.a)
                        liftState = lift_State.up;
                    break;
                //////////////
                case specimenGrab:
                    LiftTarget = 0;
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.25);
                    if (gamepad2.triangle) {
                        liftState = lift_State.specimenScore;
                        LiftTarget = 245;
                    }
                    else if (gamepad2.a)
                        liftState = lift_State.up;
                    break;
                //////////////
                case specimenScore:
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    servos.wrist.setPosition(.25);
                    if (gamepad2.dpad_up)
                        LiftTarget = 1050;
                    else if (gamepad2.dpad_down)
                        LiftTarget = 150;
                    else if (gamepad2.a)
                        liftState = lift_State.up;
                    break;
                    ///////////////////
                case resettiSpaghetti:
                    resetting = true;
                    if (resetTime.seconds()>.4) {
                        resetting = false;
                        liftState = lift_State.up;
                    }
            }

            switch (slideState){
                case transfer:
                    HarmTarget = 0;
                    if (gamepad1.dpad_up)
                        slideState = slide_State.out;
                    else if (gamepad1.dpad_right || gamepad1.dpad_left)
                        slideState = slide_State.mid;
                    break;
                //////////////
                case out:
                    HarmTarget = 700;
                    if (gamepad1.dpad_down) {
                        slideState = slide_State.transfer;
                        servos.RPivot.setPosition(RPN);
                        servos.LPivot.setPosition(LPN);
                    }
                    else if (gamepad1.dpad_right || gamepad1.dpad_left)
                        slideState = slide_State.mid;
                    break;
                //////////////
                case mid:
                    HarmTarget = 350;
                    if (gamepad1.dpad_down) {
                        slideState = slide_State.transfer;
                        servos.RPivot.setPosition(RPN);
                        servos.LPivot.setPosition(LPN);
                    }
                    else if (gamepad1.dpad_up)
                        slideState = slide_State.out;
                    break;
            }
                if (liftState == lift_State.up)
                    if (gamepad1.left_trigger>0.8 || red){
                        servos.LeftSpin.setPower(-1);
                        servos.RightSpin.setPower(-1);
                    } else if (gamepad1.right_trigger>0.8){
                        servos.LeftSpin.setPower(1);
                        servos.RightSpin.setPower(1);
                    } else{
                            servos.LeftSpin.setPower(0);
                            servos.RightSpin.setPower(0);
                        }
                if (gamepad1.left_bumper){
                        servos.RPivot.setPosition(RPN);
                        servos.LPivot.setPosition(LPN);
                    } else if (gamepad1.right_bumper){
                        servos.RPivot.setPosition(RPI);
                        servos.LPivot.setPosition(LPI);
                    }

                    if (gamepad1.triangle && speedChangeTime.seconds()>0.6){
                        speedChangeTime.reset();
                        speed = (speed == 1 ? 0.3 : 1);
                    }

                    if (gamepad2.left_trigger>0.8)
                        servos.claw.setPosition(clawOpen);
                    else if (gamepad2.right_trigger>0.8)
                        servos.claw.setPosition(clawGrab);

            if (blue)
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            else if (red)
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            else if (yellow)
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            else
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);

            lifts.update(LiftTarget, resetting);
                    slide.update(HarmTarget);
                    telemetry.addData("Htarget", HarmTarget);
                    telemetry.addData("Hpos", slide.pos());
                    telemetry.addData("lift target", LiftTarget);
                    telemetry.addData("L lift pos", lifts.Lpos());
                    telemetry.addData("R lift Pos", lifts.Rpos());
                    telemetry.addData("LP pos", servos.LPivot.getPosition());
                    telemetry.addData("RP pos", servos.RPivot.getPosition());

                    telemetry.update();
            }
        }
    }