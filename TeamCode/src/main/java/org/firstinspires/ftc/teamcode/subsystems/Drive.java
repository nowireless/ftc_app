package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.util.Util;

public class Drive {
    private static final double kDeadband = 0.01;

    private final DcMotor leftFrontMotor_;
    private final DcMotor leftBackMotor_;
    private final DcMotor rightFrontMotor_;
    private final DcMotor rightBackMotor_;


    public Drive(OpMode opmode) {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftFrontMotor_ = opmode.hardwareMap.get(DcMotor.class, RobotMap.kDriveLeftFront);
        leftBackMotor_  = opmode.hardwareMap.get(DcMotor.class, RobotMap.kDriveLeftBack);
        rightFrontMotor_ = opmode.hardwareMap.get(DcMotor.class, RobotMap.kDriveRightFront);
        rightBackMotor_  = opmode.hardwareMap.get(DcMotor.class, RobotMap.kDriveRightBack);

        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftFrontMotor_.setDirection(DcMotor.Direction.FORWARD);
        leftBackMotor_.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor_.setDirection(DcMotor.Direction.REVERSE);
        rightBackMotor_.setDirection(DcMotor.Direction.REVERSE);
    }

    public void stop() {
        leftBackMotor_.setPower(0);
        leftBackMotor_.setPower(0);
        rightFrontMotor_.setPower(0);
        rightBackMotor_.setPower(0);
    }

    /**
     * Drive method for Mecanum platform.
     *
     * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent
     * from its angle or rotation rate.
     *
     * @param ySpeed    The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
     * @param xSpeed    The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                  positive.
     */
    @SuppressWarnings("ParameterName")
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation) {
        driveCartesian(ySpeed, xSpeed, zRotation, 0.0);
    }

    /**
     * Drive method for Mecanum platform.
     *
     * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent
     * from its angle or rotation rate.
     *
     * @param ySpeed    The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
     * @param xSpeed    The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
     * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is
     *                  positive.
     * @param gyroAngle The current angle reading from the gyro in degrees around the Z axis. Use
     *                  this to implement field-oriented controls.
     */
    @SuppressWarnings("ParameterName")
    public void driveCartesian(double ySpeed, double xSpeed, double zRotation, double gyroAngle) {
        ySpeed = Range.clip(ySpeed, -1.0, 1.0);
        ySpeed = Util.applyDeadband(ySpeed, kDeadband);

        xSpeed = Range.clip(ySpeed, -1.0, 1.0);
        xSpeed = Util.applyDeadband(xSpeed, kDeadband);

        // Compensate for gyro angle.
        // Vector2d input = new Vector2d(ySpeed, xSpeed);
        // input.rotate(-gyroAngle);
        double cosA = Math.cos(-gyroAngle * (Math.PI / 180.0));
        double sinA = Math.sin(-gyroAngle * (Math.PI / 180.0));
        double[] out = new double[2];
        out[0] = xSpeed * cosA - ySpeed * sinA;
        out[1] = xSpeed * sinA + ySpeed * cosA;
        xSpeed = out[0];
        ySpeed = out[1];

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] =  xSpeed + ySpeed + zRotation;
        wheelSpeeds[1] =  xSpeed - ySpeed + zRotation;
        wheelSpeeds[2] = -xSpeed + ySpeed + zRotation;
        wheelSpeeds[3] = -xSpeed - ySpeed + zRotation;

        Util.normalize(wheelSpeeds);

        leftFrontMotor_.setPower(wheelSpeeds[0]);
        rightFrontMotor_.setPower(wheelSpeeds[1]);
        leftBackMotor_.setPower(wheelSpeeds[2]);
        rightBackMotor_.setPower(wheelSpeeds[3]);
    }


}
