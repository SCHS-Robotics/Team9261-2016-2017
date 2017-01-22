package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutoOuterSimple extends LinearOpMode {
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

        motor1.setDirection(DcMotor.Direction.FORWARD); // remember to change directions for team color
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.FORWARD);

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();
        move(560,1);
        motor3.setPower(1);
        sleep(2000);
        motor3.setPower(0);
        move(870,1);
        spinLeft(1780,1);
    }

    public void move(int pos, double power) {
        motor1.setTargetPosition(motor1.getCurrentPosition() + pos);
        motor2.setTargetPosition(motor2.getCurrentPosition() + pos);
        motor1.setPower(power);
        motor2.setPower(power);
        while(motor1.isBusy() && motor2.isBusy()){
        }
        motor1.setPower(0);
        motor2.setPower(0);
    }

    public void fwamove(int pos, double power){
        int targetPos = motor1.getCurrentPosition() + pos;
        if(pos > 0) {
            motor1.setPower(power);
            motor2.setPower(power);
            while (motor1.getCurrentPosition() < targetPos) {
                if (motor1.getCurrentPosition() >= targetPos) {
                    motor1.setPower(0);
                    motor2.setPower(0);
                }
            }
        }
        else if(pos < 0){
            motor1.setPower(-power);
            motor2.setPower(-power);
            while (motor1.getCurrentPosition() > targetPos) {
                if (motor1.getCurrentPosition() <= targetPos) {
                    motor1.setPower(0);
                    motor2.setPower(0);
                }
            }
        }
    }
    public void spinLeft(int pos,double power){
        motor1.setTargetPosition(motor1.getCurrentPosition() - pos);
        motor2.setTargetPosition(motor2.getCurrentPosition() + pos);
        motor1.setPower(-power);
        motor2.setPower(power);
    }
}