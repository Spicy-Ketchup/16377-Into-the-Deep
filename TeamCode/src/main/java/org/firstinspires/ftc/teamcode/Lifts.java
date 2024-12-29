package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Lifts {
    DcMotorEx LLarm;
    DcMotorEx LRarm;
    public static double p = .006, i = 0, d = 0.0;
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

        controllerL = new PIDController(p, i, d);
    }

    public void update(int liftTarget) {
        controllerL.setPID(p, i, d);


        int LLarmPos = LLarm.getCurrentPosition();
        int LRarmPos = LRarm.getCurrentPosition();


        double LLarmPID = controllerL.calculate(LLarmPos,liftTarget);
        double LRarmPID = controllerL.calculate(LRarmPos, liftTarget);



        double LLPower = LLarmPID;
        double LRPower = LRarmPID;

        LLarm.setPower(LLPower);
        LRarm.setPower(LRPower);
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