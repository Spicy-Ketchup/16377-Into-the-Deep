package org.firstinspires.ftc.teamcode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Config
@TeleOp(name="LM3 TeleOp")
public class Lm3Teleop extends LinearOpMode {
    Robot robot = new Robot();
    public static int LiftTarget = 0;
    public static int HarmTarget = 0;
    double speed = 1.0;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        Lifts lifts = new Lifts(hardwareMap);
        Slide slide = new Slide(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        while (opModeIsActive()) {
            {
                robot.rightFront.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);
                robot.leftFront.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
                robot.leftBack.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
                robot.rightBack.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);
            }  //Movement Block


            lifts.update(LiftTarget);
            slide.update(HarmTarget);
        }
        }
    }