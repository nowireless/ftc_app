package org.firstinspires.ftc.teamcode.robot;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

public class OI {

    private final Gamepad gamepad1_;
    private final Gamepad gamepad2_;


    public OI(OpMode opmode) {
        gamepad1_ = opmode.gamepad1;
        gamepad2_ = opmode.gamepad2;
    }


    public double moveLinearX() {
        return gamepad1_.left_stick_x;
    }


    public double moveLinearY() {
        return -gamepad1_.left_stick_y;
    }

    public double moveRotateTheta() {
        return gamepad1_.right_stick_x;
    }
}
