package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robot.Claw;
import org.firstinspires.ftc.teamcode.robot.Drive;
import org.firstinspires.ftc.teamcode.robot.OI;

public class Teleop extends OpMode {
    private ElapsedTime runtime_ = new ElapsedTime();

    private OI oi_;
    private Drive drive_;
    private Claw claw_;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        oi_ = new OI(this);
        drive_ = new Drive(this);
        claw_ = new Claw(this);
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
        if(oi_.closeClawButton()) {
            claw_.closeClaw();
        } else if(oi_.openClawButton()) {
            claw_.openClaw();
        }

        // Update the Arm Subsystem
    }

    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {
    }
}
