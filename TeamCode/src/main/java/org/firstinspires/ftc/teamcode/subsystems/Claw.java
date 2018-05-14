package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {

    private static final double kOpenPosition = 1.0;
    private static final double kClosePosition = 1.0;

    private final Servo leftServo_;
    private final Servo rightServo_;

    public Claw(OpMode opmode) {
        leftServo_  = opmode.hardwareMap.get(Servo.class, "clow_left");
        rightServo_ = opmode.hardwareMap.get(Servo.class, "clow_right");

        // One side should be inverted because each side is the mirror of the other
        leftServo_.setDirection(Servo.Direction.FORWARD);
        rightServo_.setDirection(Servo.Direction.FORWARD);
    }

    /**
     * Move the claw to the open position
     */
    public void openClaw() {
        leftServo_.setPosition(kOpenPosition);
        rightServo_.setPosition(kOpenPosition);
    }

    /**
     * Move the claw to the close position
     */
    public void closeClaw() {
        leftServo_.setPosition(kClosePosition);
        rightServo_.setPosition(kClosePosition);
    }
}
