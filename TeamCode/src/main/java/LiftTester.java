import static org.firstinspires.ftc.teamcode.Red_Teleop.ld;
import static org.firstinspires.ftc.teamcode.Red_Teleop.lf;
import static org.firstinspires.ftc.teamcode.Red_Teleop.li;
import static org.firstinspires.ftc.teamcode.Red_Teleop.lp;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Lifts;

@Config
@TeleOp(name="ALiftTester")
//@Disabled
public class LiftTester extends LinearOpMode {
    DcMotorEx LLarm;
    DcMotorEx LRarm;
    public static int Target = 0;
    public static double p = 0.023, i = 0.0000000004, d = 0.00000007;
    public static double f = 0.1;
    public void runOpMode() throws InterruptedException {
        eLifts lifts = new eLifts(hardwareMap);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        waitForStart();
        while (opModeIsActive()) {telemetry.addData("Target", Target);
            telemetry.addData("R", LRarm.getCurrentPosition());
            telemetry.addData("L", LLarm.getCurrentPosition());
            telemetry.update();
            lifts.update(Target, false);
        }
    }
    public class eLifts {

        private final double eticks_in_degrees = 1.06805555556;
        public PIDController controller;
        public eLifts (HardwareMap hardwareMap){
            LLarm = hardwareMap.get(DcMotorEx.class,"ll");
            LRarm = hardwareMap.get(DcMotorEx.class,"lr");
            LLarm.setDirection(DcMotor.Direction.FORWARD);
            LRarm.setDirection(DcMotor.Direction.REVERSE);
            LLarm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            LRarm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            LLarm.setPower(0);
            LRarm.setPower(0);
            LLarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LRarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            LLarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            LRarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            controller = new PIDController(p, i, d);
        }

        public void update(int liftTarget, boolean reset) {
            controller.setPID(p, i, d);


            int LLarmPos = LLarm.getCurrentPosition();
            int LRarmPos = LRarm.getCurrentPosition();


            double LLarmPID = controller.calculate(LLarmPos, Target);
            double LRarmPID = controller.calculate(LRarmPos, Target);

            double ff = Math.cos(Math.toRadians(Target / eticks_in_degrees)) * f;

            double LLPower = LLarmPID + ff;
            double LRPower = LRarmPID + ff;

            LLarm.setPower(LLPower);
            LRarm.setPower(LRPower);


        }
    }
}