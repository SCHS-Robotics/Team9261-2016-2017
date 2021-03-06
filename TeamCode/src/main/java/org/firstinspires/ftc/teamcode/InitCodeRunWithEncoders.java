package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cDevice;
import com.qualcomm.robotcore.hardware.I2cDeviceSynch;
import com.qualcomm.robotcore.hardware.I2cDeviceSynchImpl;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * Created by Andrew on 1/21/2017.
 */

public abstract class InitCodeRunWithEncoders extends LinearOpMode  {
    //Line Sensor
    final int LLMIN = 20;
    final int LRMIN = 7;

    double leftLinePowerBCK = -0.2;
    double RightLinePowerBCK = -0.2;
    double leftLinePowerFWD = 0.15;
    double RightLinePowerFWD = 0.15;

    MediaPlayer Vader;

    DcMotor motor1; //left wheel
    DcMotor motor2; //right wheel
    DcMotor motor3; //shooting motor

    ColorSensor color1; //left color sensor
    ColorSensor color2; //right color sensor

    ColorSensor line1; //left line sensor
    ColorSensor line2; //right line sensor

    //OpticalDistanceSensor optical1;
    //OpticalDistanceSensor optical2;

    Servo servo1; //left button pusher
    Servo servo2; //right button pusher

    Servo wheel1;
    Servo wheel2;

    public void initSound() throws InterruptedException
    {
        Vader = MediaPlayer.create(hardwareMap.appContext, R.raw.bottheme);
    }

    public void initMotors() throws InterruptedException
    {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor3 = hardwareMap.dcMotor.get("motor3");

        motor1.setDirection(DcMotor.Direction.FORWARD); // remember to change directions for team color
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.FORWARD);

        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1.setMaxSpeed(1120*160/60);
        motor2.setMaxSpeed(1120*160/60);
    }

    public void initSensors() throws InterruptedException
    {

        color1 = hardwareMap.colorSensor.get("color1");
        color2 = hardwareMap.colorSensor.get("color2");

        //optical1 = hardwareMap.opticalDistanceSensor.get("optical1");
        //optical2 = hardwareMap.opticalDistanceSensor.get("optical2");

        color1.setI2cAddress(I2cAddr.create8bit(0x6c));
        color2.setI2cAddress(I2cAddr.create8bit(0x7c));


        line1 = hardwareMap.colorSensor.get("line1"); //left line sensor
        line2 = hardwareMap.colorSensor.get("line2"); //right line sensor

        line1.setI2cAddress(I2cAddr.create8bit(0x4c));
        line2.setI2cAddress(I2cAddr.create8bit(0x5c));
    }

    public void initServos() throws InterruptedException
    {
        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");

        wheel1 = hardwareMap.servo.get("wheel1");
        wheel2 = hardwareMap.servo.get("wheel2");

        servo1.setPosition(1);
        servo2.setPosition(0);

        wheel1.setPosition(0.4);
        wheel2.setPosition(1);

    }

    public void initAll() throws InterruptedException
    {
        initMotors();
        initServos();
        initSensors();
        initSound();
    }
}
