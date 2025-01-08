package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Lm3Teleop.ld;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.lf;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.li;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.lp;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lifts {
    DcMotorEx LLarm;
    DcMotorEx LRarm;

    private final double ticks_in_degrees = .36;
    public PIDController controllerL;
    public Lifts (HardwareMap hardwareMap){
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

        controllerL = new PIDController(lp, li, ld);
    }

    public void update(int liftTarget) {
        controllerL.setPID(lp, li, ld);


        int LLarmPos = LLarm.getCurrentPosition();
        int LRarmPos = LRarm.getCurrentPosition();


        double LLarmPID = controllerL.calculate(LLarmPos,liftTarget);
        double LRarmPID = controllerL.calculate(LRarmPos, liftTarget);

        double ff = Math.cos(Math.toRadians(liftTarget/ticks_in_degrees))*lf;

        double LLPower = LLarmPID+ff;
        double LRPower = LRarmPID+ff;

        LLarm.setPower(LLPower);
        LRarm.setPower(LRPower);
    }
    public int Lpos(){
        return LLarm.getCurrentPosition();
    }
    public int Rpos() {
        return LRarm.getCurrentPosition();
    }

    public void reset(){
        LLarm.setPower(-.6);
        LRarm.setPower(-.6);
        LLarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LRarm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LLarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        LRarm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
}