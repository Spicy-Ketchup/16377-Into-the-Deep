package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slide {
    DcMotorEx Harm;
    public static double p = .006, i = 0, d = 0.0;
    public PIDController controllerH;

    public Slide(HardwareMap hardwareMap) {
        Harm = hardwareMap.get(DcMotorEx.class, "harm");
        Harm.setDirection(DcMotor.Direction.FORWARD);
        Harm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Harm.setPower(0);
        Harm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Harm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        controllerH = new PIDController(p, i, d);
    }

    public void update(int harmTarget) {
        controllerH.setPID(p, i, d);


        int HarmPos = Harm.getCurrentPosition();


        double HarmPID = controllerH.calculate(HarmPos, harmTarget);

        double HarmPower = HarmPID;

        Harm.setPower(HarmPower);
    }
}

