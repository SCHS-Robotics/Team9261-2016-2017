package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Cole Savage on 12/27/2016.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutoInnerRed1_1 extends LinearOpMode {
    public final String VERSION = "1.6";

    //21 pos: 1 degree
    //280/pi pos : 1 inch

    private boolean dank = false;
    private MediaPlayer Vader;

    //1st turn
    private final int M1T1 = 0;
    private final int M2T1 = degreesToPositions(42);
    private final double PWRT1 = 0.5;

    //2nd turn
    private final int M1T2 = degreesToPositions(42);
    private final int M2T2 = 0;
    private final double PWRT2 = 0.5;

    //3rd turn
    private final int M1T3 = 1;
    private final int M2T3 = 1;
    private final double PWRT3 = 1.0;

    //1st move
    private final int PM1 = inchesToPositions(75);
    private final double PWRM1 = 0.5;

    //linefollow increment
    private int LMVAR1 = inchesToPositions(10);
    private int LMVAR2 = inchesToPositions(10);
    private double LPWR = 0.3;

    //2nd move
    private final int PM2 = (int) inchesToPositions(-10);
    private final double PWRM2 = 0.2;

    //3rd move
    private final int PM3 = (int) (90 / (Math.PI * 4) * 560);
    private final double PWRM3 = -1.0;

    //4th move
    private final int PM4 = (int) (90 / (Math.PI * 4) * 560);
    ;
    private final double PWRM4 = 1.0;

    //5th move
    private final int PM5 = (int) (90 / (Math.PI * 4) * 560);

    private final double PWRM5 = 1.0;

    //Beacon
    private final int PBM = 37;
    private final double PWRBM = 1.0;

    //Line Sensor
    private final int LMIN = 7;
    private final int LMAX = 40;

    private DcMotor motor1; //left wheel
    private DcMotor motor2; //right wheel
    private DcMotor motor3; //shooting motor

    private Servo servo1; //left button pusher
    private Servo servo2; //right button pusher

    private ColorSensor color1; //left color sensor
    private ColorSensor color2; //right color sensor

    private ColorSensor line1; //left line sensor
    private ColorSensor line2; //right line sensor

    @Override
    public void runOpMode() throws InterruptedException {
        //Start of the list of constants that must be found through experimentation
        final double SHOOTING_POWER = 0.8;

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor3 = hardwareMap.dcMotor.get("motor3");

        servo1 = hardwareMap.servo.get("servo1");
        servo2 = hardwareMap.servo.get("servo2");

        color1 = hardwareMap.colorSensor.get("color1");
        color2 = hardwareMap.colorSensor.get("color2");

        color1.setI2cAddress(I2cAddr.create8bit(0x6c));
        color2.setI2cAddress(I2cAddr.create8bit(0x7c));

        line1 = hardwareMap.colorSensor.get("line1"); //left line sensor
        line2 = hardwareMap.colorSensor.get("line2"); //right line sensor

        line1.setI2cAddress(I2cAddr.create8bit(0x4c));
        line2.setI2cAddress(I2cAddr.create8bit(0x5c));

        motor1.setDirection(DcMotor.Direction.FORWARD); // remember to change directions for team color
        motor2.setDirection(DcMotor.Direction.REVERSE);
        motor3.setDirection(DcMotor.Direction.FORWARD);

        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor3.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Vader = MediaPlayer.create(hardwareMap.appContext, R.raw.bottheme);// this is actually fine dont bother with it

        waitForStart();

        Vader.start();

        /*pseudocode for new algorithm:
        1. shoot twice
        2. turn to face wall
        3. move for a while
        4. turn parallel to wall
        5. move forward until line sensor sees line
        6. pushbeacon method
        7. same as step 5
        8. same as 6
        the following steps are OPTIONAL
        9. turn to face center
        10. move to cap ball
        11. spin around
        */
        //shoot(SHOOTING_POWER);
        //shoot(SHOOTING_POWER);
        motor3.setPower(SHOOTING_POWER);
        sleep(1600);
        motor3.setPower(0);

        turn(M1T1, M2T1, PWRT1);
        move(PM1, PWRM1);
        turn(M1T2, M2T2, PWRT2);
        while(!seesLine() && opModeIsActive()); //runs seesLine() until it returns true
        pushBeacon();
        Vader.stop();
        /*
        move(PM3,PWRM3);
        pushBeacon();

        move(PM4,PWRM4);
        while(!seesLine()) {
            move(PM2,PWRM2);
        }
        pushBeacon();
        turn(M1T3,M2T3,PWRT3);
        move(PM5,PWRM5);
        MLG360quickscopeNOSCOPEbillythekidmtndewDORITOSBasti0nMaIn(dank);
        */
    }

    private void move(int addPos, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();

        motor1.setTargetPosition(currentPos1 + addPos);
        motor2.setTargetPosition(currentPos2 + addPos);
        motor1.setPower(power);
        motor2.setPower(power);

        while (opModeIsActive() && motor1.isBusy() && motor2.isBusy()) {

        }
        motor1.setPower(0);
        motor2.setPower(0);
    }

    private void shoot(double power) {
        final int POS_INCREMENT = 1000;
        int currentPos3 = motor3.getCurrentPosition();
        motor3.setTargetPosition(currentPos3 + POS_INCREMENT);
        motor3.setPower(power);
        while (opModeIsActive() && motor3.isBusy());
        motor3.setPower(0);
    }

    private void turn(int pos1, int pos2, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();
        motor1.setTargetPosition(pos1 + currentPos1);
        motor2.setTargetPosition(pos2 + currentPos2);
        motor1.setPower(power);
        motor2.setPower(power);
        while (motor1.isBusy() || motor2.isBusy() && opModeIsActive()); //delays code until motor stops running
        motor1.setPower(0);
        motor2.setPower(0);
    }

    private boolean seesLine() throws InterruptedException{ //boolean function that returns if the robot sees the line
        reverseMotors();
        sleep(500);
        turn(LMVAR1, LMVAR2, LPWR);
        if (seesLineLeft() && seesLineRight()) {
            return true;
        }
        else if (seesLineLeft()) {
            LMVAR1 = 0;
            return false;
        }
        else if (seesLineRight()) {
            LMVAR2 = 0;
            return false;
        }
        resetMotors();
        sleep(600);
        return false;
    }

    private boolean seesLineLeft() {
        return ((line1.alpha() > LMIN));
    }

    private boolean seesLineRight() {
        return ((line2.alpha() > LMIN));
    }

    public void pushBeacon() throws InterruptedException{
        if(color1.red() > color2.red()|| color1.blue() < color2.blue()) {
            servo2.setPosition(1);
            sleep(500);
            servo2.setPosition(0);
            sleep(500);
            servo2.setPosition(1);
        }

        else if (color1.red() < color2.red() || color2.blue() < color1.blue()) {
            servo1.setPosition(0);
            sleep(500);
            servo1.setPosition(1);
            sleep(500);
            servo1.setPosition(0);
        }
        sleep(500);
        servo1.setPosition(1);
        servo2.setPosition(0);
    }

    private void MLG360quickscopeNOSCOPEbillythekidmtndewDORITOSBasti0nMaIn(boolean dank) {
        if(!dank) {
            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            motor1.setPower(0.75);
            motor2.setPower(-0.75);
        }
    }

    private int degreesToPositions(int degrees){
        return degrees*21;
    }
    private int inchesToPositions(double inches) {
        return (int) Math.round(inches*280/Math.PI);
    }
    private void reverseMotors() {
        motor1.setDirection(DcMotor.Direction.FORWARD);
        motor2.setDirection(DcMotor.Direction.REVERSE);
    }
    private void resetMotors(){
        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);
    }

}