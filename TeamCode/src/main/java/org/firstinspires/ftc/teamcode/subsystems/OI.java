package org.firstinspires.ftc.teamcode.subsystems;

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

    public boolean openClaw() {
        return gamepad2_.dpad_left;
    }

    public boolean closeClaw() {
        return gamepad2_.dpad_right;
    }

    public boolean moveArmForward() {
        return gamepad2_.a;
    }

    public boolean moveArmBackward() {
        return gamepad2_.b;
    }

    public boolean tiltArmUp() {
        return gamepad2_.dpad_up;
    }

    public boolean tiltArmDown() {
        return gamepad2_.dpad_down;
    }
}
