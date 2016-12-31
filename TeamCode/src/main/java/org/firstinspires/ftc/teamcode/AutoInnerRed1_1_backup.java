package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Cole Savage on 12/27/2016.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutoInnerRed1_1 extends LinearOpMode {
    public final String VERSION = "1.1";

    //1st turn
    private final int M1T1 = 1;
    private final int M2T1 = 1;
    private final double PWRT1 = 1.0;

    //2nd turn
    private final int M1T2 = 1;
    private final int M2T2 = 1;
    private final double PWRT2 = 1.0;

    //1st move
    private final int PM1 = 1;
    private final double PWRM1 = 1.0;

    //2nd move
    private final int PM2 = 1;
    private final double PWRM2 = 1.0;

    //3rd move
    private final int PM3 = 1;
    private final double PWRM3 = 1.0;

    //Beacon
    private final double REDCNSTMIN = 1.0;
    private final double REDCNSTMAX = 1.0;
    private final double BLUECNSTMIN = 1.0;
    private final double BLUECNSTMAX = 1.0;
    private final int PBM = 1;
    private final double PWRBM = 1.0;

    private DcMotor motor1; //left wheel
    private DcMotor motor2; //right wheel
    private DcMotor motor3; //shooting motor

    private Servo servo1; //button pusher (on right side of robot)
    private Servo servo2; //loading servo

    private ColorSensor color1; //left color sensor
    private ColorSensor color2; //right color sensor

    @Override
    public void runOpMode() throws InterruptedException {
        //Start of the list of constants that must be found through experimentation
        final double SHOOTING_POWER = 1.0;
        //private final int SLEEP_CNST = 1;

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor3 = hardwareMap.dcMotor.get("motor3");

        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");

        color1 = hardwareMap.colorSensor.get("color1");
        color2 = hardwareMap.colorSensor.get("color2");

        motor1.setDirection(DcMotor.Direction.FORWARD); // remember to change directions for team color
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.FORWARD);

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        shoot(SHOOTING_POWER);
        turn(M1T1, M2T1, PWRT1); // turn to the wall
        move(PM1, PWRM1);
        pushBeacon();
        move(PM2, PWRM2);
        pushBeacon();
        turn(M1T2, M2T2, PWRT2);
        move(PM3, PWRM3);
    }

    public void move(int addPos, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();
        int targetPos1 = currentPos1 + addPos;
        int targetPos2 = currentPos2 + addPos;

        if (addPos > 0) {
            while ((motor1.getCurrentPosition() < targetPos1) && (motor2.getCurrentPosition() < targetPos2) && opModeIsActive()) {
                motor1.setPower(power);
                motor2.setPower(power);
            }
            motor1.setPower(0);
            motor2.setPower(0);
        }else if(addPos < 0){
            while ((motor1.getCurrentPosition() > targetPos1) && (motor2.getCurrentPosition() > targetPos2) && opModeIsActive()) {
                motor1.setPower(-power);
                motor2.setPower(-power);
            }
            motor1.setPower(0);
            motor2.setPower(0);
        }
    }

    public void shoot(double power) {
        final int POS_INCREMENT = 2200;
        int currentPos3 = motor3.getCurrentPosition();
        int targetPos3 = currentPos3 + POS_INCREMENT;

        while ((motor3.getCurrentPosition() > targetPos3) && opModeIsActive()) {
            motor3.setPower(power);
        }
        motor3.setPower(0);
    }

    public void turn(int pos1, int pos2, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();
        int targetPos1 = currentPos1 + pos1;
        int targetPos2 = currentPos2 + pos2;
        boolean isTurning = true;
        boolean isMotor1Moving = true;
        boolean isMotor2Moving = true;

        while(isTurning){
            //move motor1
            if (pos1 > 0) {
                if((motor1.getCurrentPosition() < targetPos1) && opModeIsActive()) {
                    motor1.setPower(power);
                }else {
                    isMotor1Moving = false;
                    motor1.setPower(0);
                }
            }else if(pos1 < 0){
                if((motor1.getCurrentPosition() > targetPos1) && opModeIsActive()) {
                    motor1.setPower(-power);
                }else {
                    isMotor1Moving = false;
                    motor1.setPower(0);
                }
            }

            //move motor2

            if (pos2 > 0) {
                if((motor2.getCurrentPosition() < targetPos2) && opModeIsActive()) {
                    motor2.setPower(power);
                }else {
                    isMotor2Moving = false;
                    motor2.setPower(0);
                }
            }else if(pos2 < 0){
                if((motor2.getCurrentPosition() > targetPos2) && opModeIsActive()) {
                    motor2.setPower(-power);
                }else {
                    isMotor2Moving = false;
                    motor2.setPower(0);
                }
            }

            //check for turning
            isTurning = (isMotor1Moving || isMotor2Moving);
        }
    }

    public void pushBeacon() {
//check right for Red, then check Blue
        if ((color2.red() >= REDCNSTMIN && color2.red() <= REDCNSTMAX) && (color1.blue() <= BLUECNSTMIN && color1.blue() <= BLUECNSTMAX)) {
            move(PBM, PWRBM);
            servo1.setPosition(1);
            servo1.setPosition(0);
        }
        //right is Blue, so check left
        else if (color1.red() >= REDCNSTMIN && color1.red() <= REDCNSTMAX) {
            move(-PBM, PWRBM);
            servo1.setPosition(1);
            servo1.setPosition(0);
        }
    }
}