package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.Red_Teleop.hd;
import static org.firstinspires.ftc.teamcode.Red_Teleop.hf;
import static org.firstinspires.ftc.teamcode.Red_Teleop.hi;
import static org.firstinspires.ftc.teamcode.Red_Teleop.hp;


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

    public void update(int harmTarget, int custom) {
        controllerH.setPID(hp, hi, hd);


        int HarmPos = Harm.getCurrentPosition();

        double HarmPID = controllerH.calculate(HarmPos,harmTarget);

        double ff = Math.cos(Math.toRadians(harmTarget/.36))*hf;

        double HarmPower = HarmPID+ff;

        if (custom == 0)
        Harm.setPower(HarmPower);
        else if (custom == 1)
            Harm.setPower(.2);
        else if (custom == 2)
            Harm.setPower(-.2);
        else if (custom == 3){
            Harm.setPower(-.5);
            Harm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Harm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } else if (custom == 4){
            Harm.setPower(0);
        }

    }
}

