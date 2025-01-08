package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="LL-Sample Detection and Correction")
@Disabled
public class ColorSensorTesting extends LinearOpMode {
    Robot robot = new Robot();
    double speed_x = 0;
    ElapsedTime detectionTime = new ElapsedTime();

    boolean llOveride = false;
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
     //   robot.limelight.pipelineSwitch(0);
     //   robot.limelight.start();

        waitForStart();

        while (opModeIsActive()) {

      //      LLResult result = robot.limelight.getLatestResult();

            if (gamepad1.triangle)
                llOveride = true;
            else if (gamepad1.circle)
                llOveride = false;

            if(!llOveride) {
        //        if (result.getTx() == 0)
                    speed_x = 0;
        //        else if (result.getTx() > 4.25)
                    speed_x = .03;
        //        else if (result.getTx() < -4.25)
                    speed_x = -.03;
          //      else
                    speed_x = 0;


                robot.leftFront.setPower(speed_x);
                robot.rightFront.setPower(-speed_x);
                robot.leftBack.setPower(-speed_x);
                robot.rightBack.setPower(speed_x);
            }
            else {
                robot.rightFront.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);
                robot.leftFront.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);
                robot.leftBack.setPower(-gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x);
                robot.rightBack.setPower(-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);
            }

          //      telemetry.addData("tx", result.getTx());
          //      telemetry.addData("ty", result.getTy());
          //       telemetry.addData("ta", result.getTa());


            //     int red = robot.colorSensor.red();
       //     int green = robot.colorSensor.green();
       //     int blue = robot.colorSensor.blue();

        //    if (red>=250 &&  green>=450) {
       //         robot.LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.YELLOW);
       //     } else if (red >= 250 && green<900) {
        //        robot.LED.setPattern(RevBlinkinLedDriver.BlinkinPattern.RED);
        //    }


           //   if (red > 300 && green <=400) {
          //        robot.Spintake1.setPower(-1.0);
          //        robot.Spintake2.setPower(-1.0);
          //  } else if (gamepad1.a) {
          //     robot.Spintake1.setPower(0.28);
          //        robot.Spintake2.setPower(0.28);
          //    }
          //    else if (gamepad1.b) {
           //       robot.Spintake1.setPower(-1.0);
          //        robot.Spintake2.setPower(-1.0);
          //    }
          //    else{
            //      robot.Spintake1.setPower(0);
           //       robot.Spintake2.setPower(0);
           //   }


           // telemetry.addData("Blue: ", blue);
           // telemetry.addData("Red: ", red);
          //  telemetry.addData("Green: ", green);
            telemetry.update();

        }
    }
}
