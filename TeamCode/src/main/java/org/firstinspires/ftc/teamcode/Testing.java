package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.Red_Teleop.LPI;
import static org.firstinspires.ftc.teamcode.Red_Teleop.LPN;
import static org.firstinspires.ftc.teamcode.Red_Teleop.RPI;
import static org.firstinspires.ftc.teamcode.Red_Teleop.RPN;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.hardware.rev.RevBlinkinLedDriver;

@TeleOp(name="A-Tester")
@Disabled
public class Testing extends LinearOpMode {
    public static int targeto = 0;
    public DcMotor leftFront = null;
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    public CRServo LeftSpin = null;
    public CRServo RightSpin = null;
    public ColorSensor colorSensor = null;
    public RevBlinkinLedDriver LED = null;
    public boolean blue = false;
    public boolean yellow = false;
    public boolean red = false;
    public boolean nothin = false;


    public static double speed = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        leftFront = hardwareMap.get(DcMotor.class, "lf");
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        leftBack = hardwareMap.get(DcMotor.class, "lb");
        rightBack = hardwareMap.get(DcMotor.class, "rb");
        LeftSpin = hardwareMap.get(CRServo.class, "LS");
        RightSpin = hardwareMap.get(CRServo.class, "RS");
        colorSensor = hardwareMap.get(ColorSensor.class, "CS");
        LED = hardwareMap.get(RevBlinkinLedDriver.class, "LED");



        LeftSpin.setPower(0);
        RightSpin.setPower(0);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ElapsedTime wait = new ElapsedTime();
        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        Servos servos = new Servos(hardwareMap);
        LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);
        servos.Relbow.setPosition(.5);
        servos.Lelbow.setPosition(.5);
        servos.wrist.setPosition(.3);
        servos.RPivot.setPosition(RPN);
        servos.LPivot.setPosition(LPN);

        waitForStart();
        while (opModeIsActive()) {
            blue = colorSensor.blue() > 375 && colorSensor.red() < 280;
            red = colorSensor.red() > 500 && colorSensor.green() > 200 && colorSensor.green() < 600;
            yellow = colorSensor.red() > 580 && colorSensor.green() > 600;
            telemetry.addData("red", colorSensor.red());
            telemetry.addData("blue", colorSensor.blue());
            telemetry.addData("green", colorSensor.green());
            telemetry.addData("Target", targeto);
            telemetry.addData("PosL", lifts.Lpos());
            telemetry.addData("PosR", lifts.Rpos());
            if (gamepad1.left_trigger > 0.8 || blue){
                LeftSpin.setPower(-1);
                RightSpin.setPower(-1);
            } else if (gamepad1.right_trigger > 0.8){
                LeftSpin.setPower(1);
                RightSpin.setPower(1);
            } else {
                LeftSpin.setPower(0);
                RightSpin.setPower(0);
            }
            if (gamepad1.dpad_down) {
                targeto = 0;
                servos.RPivot.setPosition(RPN);
                servos.LPivot.setPosition(LPN);
            }
            else if (gamepad1.dpad_right)
                    targeto = 800;
            else if (gamepad1.dpad_up)
                    targeto = 2500;
            if (gamepad1.right_bumper){
                servos.RPivot.setPosition(RPI);
                servos.LPivot.setPosition(LPI);
            } else if (gamepad1.left_bumper){
                servos.RPivot.setPosition(RPN);
                servos.LPivot.setPosition(LPN);
            }

            if (blue)
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.BLUE);
            else if (red)
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
            else if (yellow)
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
            else
                LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.WHITE);

            if (gamepad1.x){
                servos.claw.setPosition(0);
                servos.Relbow.setPosition(0.78);
                servos.Lelbow.setPosition(0.78);
                servos.wrist.setPosition(0.15);
            }
            if (gamepad1.triangle){
                servos.claw.setPosition(0);
                servos.Relbow.setPosition(0.82);
                servos.Lelbow.setPosition(0.82);
                servos.wrist.setPosition(0.15);
            }
            if (gamepad1.b)
                servos.claw.setPosition(.42);
            else if (gamepad1.a)
                servos.claw.setPosition(0);

            if (gamepad1.options){
                servos.Relbow.setPosition(0.41);
                servos.Lelbow.setPosition(0.41);
                servos.wrist.setPosition(0.62);
            }




            rightFront.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);
            leftFront.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
            leftBack.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
            rightBack.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);
            telemetry.update();
           // lifts.update(targeto);
           // slide.update(0);
        }
    }
}


