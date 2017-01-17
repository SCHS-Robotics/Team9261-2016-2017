package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutoOuterJustShoot extends LinearOpMode {
    public final String VERSION = "0.1";

    private DcMotor motor1; //left wheel
    private DcMotor motor2; //right wheel
    private DcMotor motor3; //shooting motor

    @Override
    public void runOpMode() throws InterruptedException {
        //Start of the list of constants that must be found through experimentation
        final double SHOOTING_POWER = 0.8;
        //private final int SLEEP_CNST = 1;

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor3 = hardwareMap.dcMotor.get("motor3");

        motor1.setDirection(DcMotor.Direction.REVERSE); // remember to change directions for team color
        motor2.setDirection(DcMotor.Direction.FORWARD);
        motor3.setDirection(DcMotor.Direction.FORWARD);

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();
        move(1800,0.25);
        sleep(3000);
        motor3.setPower(0.8);
        sleep(1000);
        motor3.setPower(0);

    }
    public void move(int pos, double power) {
        motor1.setTargetPosition(motor1.getCurrentPosition() + pos);
        motor2.setTargetPosition(motor2.getCurrentPosition() + pos);

        motor1.setPower(power);
        motor2.setPower(power);
        sleep(500);
        motor1.setPower(0);
        motor2.setPower(0);
    }

}