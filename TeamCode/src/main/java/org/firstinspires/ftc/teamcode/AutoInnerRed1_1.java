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
        //sleep(SLEEP_CNST);
        servo2.setPosition(1);
        servo2.setPosition(0);
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

        motor1.setTargetPosition(currentPos1 + addPos);
        motor2.setTargetPosition(currentPos2 + addPos);
        motor1.setPower(power);
        motor2.setPower(power);
    }

    public void shoot(double power) {
        final int POS_INCREMENT = 1000;
        int currentPos3 = motor3.getCurrentPosition();
        motor3.setTargetPosition(currentPos3 + POS_INCREMENT);
        motor3.setPower(power);
    }

    public void turn(int pos1, int pos2, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();
        motor1.setTargetPosition(pos1 + currentPos1);
        motor2.setTargetPosition(pos2 + currentPos2);
        motor1.setPower(power);
        motor2.setPower(power);
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
            servo1.setPosition(0);
            servo1.setPosition(1);
        }
    }
}