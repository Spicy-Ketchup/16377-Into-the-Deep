package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="LLCorrection")
public class LLCorrectionTest extends LinearOpMode {
    Robot robot = new Robot();
    double speed_x = 0;
    double speed = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        robot.limelight.pipelineSwitch(0);
        robot.limelight.start();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.square) {
                ElapsedTime detectionTime = new ElapsedTime();
                ElapsedTime forceTime = new ElapsedTime();
                while (detectionTime.seconds() < 1.5 && forceTime.seconds()<3 && opModeIsActive()) {
                    LLResult result = robot.limelight.getLatestResult();

                    if (result.getTx() == 0) {
                        speed_x = -0.2;
                        detectionTime.reset();
                    }else if (result.getTx() > 4.25) {
                        speed_x = .25;
                        detectionTime.reset();
                    }else if (result.getTx() < -4.25) {
                        speed_x = -.25;
                        detectionTime.reset();
                    }else
                        speed_x = 0;


                    robot.leftFront.setPower(speed_x);
                    robot.rightFront.setPower(-speed_x);
                    robot.leftBack.setPower(-speed_x);
                    robot.rightBack.setPower(speed_x);
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("ty", result.getTy());
                    telemetry.addData("ta", result.getTa());
                    telemetry.update();
                }
                ElapsedTime forward = new ElapsedTime();
                while (forward.seconds()<.6 && opModeIsActive()) {
                    robot.rightFront.setPower(.1);
                    robot.leftFront.setPower(.1);
                    robot.leftBack.setPower(.1);
                    robot.rightBack.setPower(.1);
                }
            } //LimeLight Detection & Correction Block


            {robot.rightFront.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);
                robot.leftFront.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
                robot.leftBack.setPower((-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x)*speed);
                robot.rightBack.setPower((-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x)*speed);} //Player1 Movement Block

        }
    }
}
