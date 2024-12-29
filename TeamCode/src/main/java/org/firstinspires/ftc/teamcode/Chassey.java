package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Chassey Test")
public class Chassey extends LinearOpMode {
    public DcMotor leftFront = null;
    public DcMotor rightFront = null;
    public DcMotor leftBack = null;
    public DcMotor rightBack = null;
    double speed = 1.0;

    @Override
    public void runOpMode() throws InterruptedException {
        leftFront  = hardwareMap.get(DcMotor.class, "lf");
        rightFront = hardwareMap.get(DcMotor.class, "rf");
        leftBack  = hardwareMap.get(DcMotor.class, "lb");
        rightBack = hardwareMap.get(DcMotor.class, "rb");
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setPower(0);
        rightFront.setPower(0);
        leftBack.setPower(0);
        rightBack.setPower(0);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        waitForStart();
        while (opModeIsActive()) {

            if (gamepad1.right_bumper)
                speed = 0.1;
            else if (gamepad1.left_bumper)
                speed = 1.0;

            rightFront.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);
            leftFront.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
            leftBack.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
            rightBack.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);
        }
    }
}
