package org.firstinspires.ftc.teamcode.subsystems;

import android.hardware.Sensor;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.RobotMap;
import org.firstinspires.ftc.teamcode.util.RGB;

public class Sensors {

    private static final double kJewelArmDownPosition = 0;
    private static final double kJewelArmUpPosition = 1.0;

    private final Servo jewelColorSensorServo_;
    private final ColorSensor jewelColorSensor_;

    public Sensors(OpMode opmode) {
        jewelColorSensor_ = opmode.hardwareMap.get(ColorSensor.class, RobotMap.kSensorJewelColor);
        jewelColorSensorServo_ = opmode.hardwareMap.get(Servo.class, RobotMap.kSensorJewelColorServo);
    }

    public RGB getJewelColor() {
        return new RGB(jewelColorSensor_.red(), jewelColorSensor_.green(), jewelColorSensor_.blue());
    }

    public void moveJewelArmDown() {
        jewelColorSensorServo_.setPosition(kJewelArmDownPosition);
    }

    public void moveJewelArmUp() {
        jewelColorSensorServo_.setPosition(kJewelArmUpPosition);
    }
}
