package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Lm3Teleop.hd;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.hf;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.hi;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.hp;
import static org.firstinspires.ftc.teamcode.Lm3Teleop.ticks_in_degrees;


import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slide {
    DcMotorEx Harm;

    public PIDController controllerH;

    public Slide(HardwareMap hardwareMap) {
        Harm = hardwareMap.get(DcMotorEx.class, "harm");
        Harm.setDirection(DcMotor.Direction.REVERSE);
        Harm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Harm.setPower(0);
        Harm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Harm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        controllerH = new PIDController(hp, hi, hd);
    }
    public int pos(){
        return Harm.getCurrentPosition();
    }

    public void update(int harmTarget) {
        controllerH.setPID(hp, hi, hd);


        int HarmPos = Harm.getCurrentPosition();

        double HarmPID = controllerH.calculate(HarmPos,harmTarget);

        double ff = Math.cos(Math.toRadians(harmTarget/ticks_in_degrees))*hf;

        double HarmPower = HarmPID+ff;

        Harm.setPower(HarmPower);
    }
}

