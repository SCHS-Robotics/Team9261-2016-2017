package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;

/**
 * Created by Cole Savage on 12/27/2016.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class One_Revolution_Test extends LinearOpMode{

    public final String VERSION = "1.1";

    private DcMotor motor1; //left motor
    private DcMotor motor2; //right motor
    private GyroSensor gyro1; //orienting gyroscope, attach to left wheel
    private GyroSensor gyro2; //orienting gyroscope, attach to right wheel


    @Override
    public void runOpMode() throws InterruptedException {

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        gyro1 = hardwareMap.gyroSensor.get("gyro1");
        gyro2 = hardwareMap.gyroSensor.get("gyro2");

        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.REVERSE);

        gyro1.calibrate();
        gyro2.calibrate();

        int initMotor1Pos = motor1.getCurrentPosition();
        int initMotor2Pos = motor2.getCurrentPosition();

        waitForStart();

        motor1.setTargetPosition(motor1.getCurrentPosition()+1);
        motor1.setPower(0.5);

        while(gyro1.getHeading() != 0) {
            motor1.setTargetPosition(motor1.getCurrentPosition()+1);
            motor1.setPower(0.5);
        }
        telemetry.addData("left motor position change", motor1.getCurrentPosition()-initMotor1Pos);

        motor2.setTargetPosition(motor2.getCurrentPosition()+1);
        motor2.setPower(0.5);

        while(gyro2.getHeading() != 0) {
            motor2.setTargetPosition(motor2.getCurrentPosition()+1);
            motor2.setPower(0.5);
        }
        telemetry.addData("right motor position change", motor2.getCurrentPosition()-initMotor2Pos);
    }
}