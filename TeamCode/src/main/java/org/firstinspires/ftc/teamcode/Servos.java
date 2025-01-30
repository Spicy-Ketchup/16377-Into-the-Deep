package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Servos {
    public Servo claw = null;
    public Servo Relbow = null;
    public Servo Lelbow = null;
    public Servo wrist = null;
    public Servo LPivot = null;
    public Servo RPivot = null;
    public CRServo LeftSpin = null;
    public CRServo RightSpin = null;
    public Servos(HardwareMap hardwareMap){
        claw = hardwareMap.get(Servo.class, "claw");
        wrist = hardwareMap.get(Servo.class, "wrist");
        LPivot = hardwareMap.get(Servo.class, "LP");
        RPivot = hardwareMap.get(Servo.class, "RP");
        Relbow = hardwareMap.get(Servo.class, "Relbow");
        Lelbow = hardwareMap.get(Servo.class, "Lelbow");
        LeftSpin = hardwareMap.get(CRServo.class, "LS");
        RightSpin = hardwareMap.get(CRServo.class, "RS");
        LeftSpin.setPower(0);
        RightSpin.setPower(0);
    }

}