package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

public class Arm {

    private enum TiltState {
        kUp,
        kDown,
        kIdle
    }

    private final static double kMoveSpeed = 0.5;

    //TODO: Find these actual values
    private final static double kArmLowAngle = -45;
    private final static double kArmHighAngle = 45;
    private final static double kArmServoLow = 0;
    private final static double kArmServoHigh = 1.0;

    //The angle adjustment per increment and decrement
    private final static double kArmStepAngle = 1.0;

    private final static double kArmTiltRateLimitMS = 20;

    private final CRServo moveServo_;
    private final Servo tiltLeftServo_;
    private final Servo tiltRightServo_;

    private double angle_ = kArmLowAngle;
    private TiltState tiltState_ = TiltState.kIdle;
    private ElapsedTime timer_ = new ElapsedTime();

    public Arm(OpMode opmode) {
        moveServo_ = opmode.hardwareMap.get(CRServo.class, "arm_move");
        tiltLeftServo_ = opmode.hardwareMap.get(Servo.class, "arm_tilt_left");
        tiltRightServo_ = opmode.hardwareMap.get(Servo.class, "arm_tilt_right");

        // Set the direction of the motor, such that a positive value moves the arm forward
        moveServo_.setDirection(DcMotorSimple.Direction.FORWARD);

        // One of the tilting servo's is inverted to the other
        tiltLeftServo_.setDirection(Servo.Direction.FORWARD);
        tiltRightServo_.setDirection(Servo.Direction.REVERSE);
    }

    /**
     * Convert a tilt angle to a servo position
     * @param angle Tilt angle in degrees
     * @return Servo position
     */
    private double angleToPosition(double angle) {
        return Range.scale(angle, kArmLowAngle, kArmHighAngle, kArmServoLow, kArmServoHigh);
    }

    /**
     * Move the arm forward
     */
    public void moveForward() {
        this.move(kMoveSpeed);
    }

    /**
     * Move the arm backward
     */
    public void moveBackward() {
       this.move(-kMoveSpeed);
    }

    public void moveStop() {
        this.move(0);
    }

    /**
     * Set the arm's linear movement speed
     * @param percentSpeed
     */
    public void move(double percentSpeed) {
        moveServo_.setPower(percentSpeed);
    }

    /**
     * Set the tilt servo position
     * @param pos position
     */
    private void setTiltPosition(double pos) {
        tiltLeftServo_.setPosition(pos);
        tiltRightServo_.setPosition(pos);
    }

    /**
     * Set the tilt servo angle
     * @param angle angle in degrees
     */
    public void setTiltAngle(double angle) {
        this.setTiltPosition(this.angleToPosition(angle));
    }

    /**
     * Tilt arm up.
     * This is rate limited, so that angle doesnt increases on each iteration
     * of the main loop.
     */
    public void incrementTilt() {
        boolean firstRun = false;
        // Check the old state
        if(tiltState_ != TiltState.kUp) {
            // Start/Reset timer
            timer_.reset();
            firstRun = true;
        }
        tiltState_ = TiltState.kUp;

        if(firstRun || timer_.milliseconds() > kArmTiltRateLimitMS) {
            firstRun = false;

            // Perform update
            angle_ = Math.min(angle_ + kArmStepAngle, kArmHighAngle);
            this.setTiltAngle(angle_);
        }
    }

    public void decermentTilt() {
        boolean firstRun = false;
        // Check the old state
        if(tiltState_ != TiltState.kDown) {
            // Start/Reset timer
            timer_.reset();
            firstRun = true;
        }
        tiltState_ = TiltState.kDown;

        if(firstRun || timer_.milliseconds() > kArmTiltRateLimitMS) {
            firstRun = false;

            // Perform update
            angle_ = Math.max(angle_ - kArmStepAngle, kArmLowAngle);
            this.setTiltAngle(angle_);
        }

    }

    public void stopTilt() {
        tiltState_ = TiltState.kIdle;
    }
}
