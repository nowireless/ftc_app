package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.subsystems.Arm;
import org.firstinspires.ftc.teamcode.subsystems.Claw;
import org.firstinspires.ftc.teamcode.subsystems.Drive;
import org.firstinspires.ftc.teamcode.subsystems.OI;

public class Teleop extends OpMode {
    private ElapsedTime runtime_ = new ElapsedTime();

    private OI oi_;
    private Drive drive_;
    private Claw claw_;
    private Arm arm_;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        oi_ = new OI(this);
        drive_ = new Drive(this);
        claw_ = new Claw(this);
        arm_ = new Arm(this);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
        runtime_.reset();
    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
    public void loop() {
        // Update drive subsystem
        drive_.driveCartesian(oi_.moveLinearY(), oi_.moveLinearX(), oi_.moveRotateTheta());

        // Update claw subsystem
        if(oi_.closeClaw()) {
            claw_.closeClaw();
        } else if(oi_.openClaw()) {
            claw_.openClaw();
        }

        // Update the Arm Subsystem
        // Move arm back and forward
        if(oi_.moveArmBackward()) {
            arm_.moveBackward();
        } else if(oi_.moveArmForward()) {
            arm_.moveForward();
        } else {
            // Stop movement, when no button is pressed
            arm_.moveStop();
        }

        // Tilt the arm up and down
        if(oi_.tiltArmDown()) {
            arm_.decermentTilt();
        } else if(oi_.tiltArmUp()) {
            arm_.incrementTilt();
        } else {
            arm_.stopTilt();
        }
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
