package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name="LM3 TeleOp")
public class Lm3Teleop extends LinearOpMode {

    Robot robot = new Robot();
    public static int LiftTarget = 0;
    public static int HarmTarget = 150;
    public static double lp = 0.01, li = 0, ld = 0.0;
    public static double lf = 0.05;
    public static double hp = 0.015, hi = 0, hd = 0.0;
    public static double hf = 0;
    public static double ticks_in_degrees = .36;
    double speed = 1.0;
    boolean high = false;
    boolean low = false;
    enum lift_State {
        up,
        down,
        grab,
        lowBasket,
        highBasket,
        specimenGrab,
        specimenScore

    }
    enum slide_State {
        transfer,
        out,
        mid
    }

    double clawGrab = .42;
    double clawOpen = 0;

    public static double RPI = 0.81;
    public static double LPI = 0.44;
    public static double RPN = 0.71;
    public static double LPN = 0.62;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        Servos servos = new Servos(hardwareMap);
        LiftTarget = 0;
        HarmTarget = 15;
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        servos.Relbow.setPosition(.5);
        servos.Lelbow.setPosition(.5);
        servos.wrist.setPosition(.4);
        servos.claw.setPosition(clawGrab);
        servos.RPivot.setPosition(RPN);
        servos.LPivot.setPosition(LPN);
        lift_State liftState = lift_State.up;
        slide_State slideState = slide_State.transfer;
        ElapsedTime transferTime = new ElapsedTime();
        ElapsedTime specTime = new ElapsedTime();
        ElapsedTime dropTime = new ElapsedTime();
        boolean x = false;
        waitForStart();
        while (opModeIsActive()) {
            {
                robot.rightFront.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x) * speed);
                robot.leftFront.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x) * speed);
                robot.leftBack.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x) * speed);
                robot.rightBack.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x) * speed);
            }  //Movement Block

            switch (liftState) {
                case up:
                    speed = 1;
                    LiftTarget = 750;
                    servos.Relbow.setPosition(.925);
                    servos.Lelbow.setPosition(.925);
                    servos.wrist.setPosition(.375);
                    servos.claw.setPosition(clawGrab);
                    if (gamepad2.dpad_up) {
                        high = true;
                        liftState = lift_State.down;
                        transferTime.reset();
                    } else if (gamepad2.dpad_down) {
                        low = true;
                        liftState = lift_State.down;
                        transferTime.reset();
                    } else if (gamepad2.b)
                        liftState = lift_State.specimenGrab;
                    break;
                //////////////
                case down:
                    servos.claw.setPosition(clawOpen);
                    if (transferTime.seconds() > 0.2) {
                        LiftTarget = 310;
                        transferTime.reset();
                        liftState = lift_State.grab;
                    }
                    break;
                //////////////
                case grab:
                    if (transferTime.seconds() > .2)
                        servos.claw.setPosition(clawGrab);
                    if (transferTime.seconds() > .6) {
                        if (high) {
                            liftState = lift_State.highBasket;
                            high = false;
                        } else if (low) {
                            liftState = lift_State.lowBasket;
                            low = false;
                        }
                    }
                    break;
                //////////////
                case highBasket:
                    speed = 0.3;
                    LiftTarget = 2700;
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition()) / 2 > 2000) {
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.42);
                    }
                    if (gamepad2.a)
                        liftState = lift_State.up;
                    break;
                //////////////
                case lowBasket:
                    speed = 0.3;
                    LiftTarget = 950;
                    if ((lifts.LLarm.getCurrentPosition() + lifts.LRarm.getCurrentPosition()) / 2 > 300) {
                        servos.Relbow.setPosition(.41);
                        servos.Lelbow.setPosition(.41);
                        servos.wrist.setPosition(.42);
                    }
                    if (gamepad2.a)
                        liftState = lift_State.up;
                    break;
                //////////////
                case specimenGrab:
                    LiftTarget = 0;
                    servos.Relbow.setPosition(.15);
                    servos.Lelbow.setPosition(.15);
                    servos.wrist.setPosition(.35);
                    if (gamepad1.x)
                        liftState = lift_State.specimenScore;
                    else if (gamepad1.a)
                        liftState = lift_State.up;
                    break;
                //////////////
                case specimenScore:
                    servos.Relbow.setPosition(.6);
                    servos.Lelbow.setPosition(.6);
                    if (gamepad2.dpad_up)
                        LiftTarget = 1030;
                    else if (gamepad2.dpad_down)
                        LiftTarget = 0;
                    else if (gamepad2.a)
                        liftState = lift_State.up;
                    break;
            }

            switch (slideState){
                case transfer:
                    HarmTarget = 55;
                    servos.RPivot.setPosition(RPN);
                    servos.LPivot.setPosition(LPN);
                    if (gamepad1.dpad_up)
                        slideState = slide_State.out;
                    else if (gamepad1.dpad_right || gamepad1.dpad_left)
                        slideState = slide_State.mid;
                    break;
                //////////////
                case out:
                    HarmTarget = 750;
                    if (gamepad1.dpad_down)
                        slideState = slide_State.transfer;
                    else if (gamepad1.dpad_right || gamepad1.dpad_left)
                        slideState = slide_State.mid;
                    break;
                //////////////
                case mid:
                    HarmTarget = 375;
                    if (gamepad1.dpad_down)
                        slideState = slide_State.transfer;
                    else if (gamepad1.dpad_up)
                        slideState = slide_State.out;
                    break;
            }
                    if (gamepad1.left_trigger>0.8){
                        servos.LeftSpin.setPower(1);
                        servos.RightSpin.setPower(1);
                    } else if (gamepad1.right_trigger>0.8){
                        servos.LeftSpin.setPower(-1);
                        servos.RightSpin.setPower(-1);
                    } else{
                        servos.LeftSpin.setPower(0);
                        servos.RightSpin.setPower(0);}

                    if (gamepad1.left_bumper){
                        servos.RPivot.setPosition(RPN);
                        servos.LPivot.setPosition(LPN);
                    } else if (gamepad1.right_bumper){
                        servos.RPivot.setPosition(RPI);
                        servos.LPivot.setPosition(LPI);
                    }

                    if (gamepad2.left_trigger>0.8)
                        servos.claw.setPosition(clawOpen);
                    else if (gamepad2.right_trigger>0.8)
                        servos.claw.setPosition(clawGrab);

                    lifts.update(LiftTarget);
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