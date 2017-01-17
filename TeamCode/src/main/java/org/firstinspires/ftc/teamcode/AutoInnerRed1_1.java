package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Cole Savage on 12/27/2016.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutoInnerRed1_1 extends LinearOpMode {
    public final String VERSION = "1.5";

    //21 pos: 1 degree
    //280/pi pos : 1 inch

    private boolean dank = false;
    MediaPlayer Vader;

    //1st turn
    private final int M1T1 = 0;
    private final int M2T1 = degreesToPositions(41);
    private final double PWRT1 = 0.5;

    //2nd turn
    private final int M1T2 = 0;
    private final int M2T2 = degreesToPositions(-37);
    private final double PWRT2 = -0.5;

    //2.5th turn
    private final int M1T25 = 0;
    private final int M2T25 = degreesToPositions(10);
    private final double PWRT25 = 0.5;


    //3rd turn
    private final int M1T3 = 0;
    private final int M2T3 = degreesToPositions(-90);
    private final double PWRT3 = -.5;

    //4th turn
    private final int M1T4 = degreesToPositions(45);
    private final int M2T4 = 0;
    private final double PWRT4 = 0.25;

    //1st move
    private final int PM1 = inchesToPositions(60);
    private final double PWRM1 = 0.5;

    //linefollow increment
    private int LMCNST1 = inchesToPositions(-2);
    private int LMCNST2 = inchesToPositions(-2);
    private double LPWR = -0.08;
    private double LPWR2 = 0.08;

    //2nd move
    private final int PM2 = (int) inchesToPositions(-10);
    private final double PWRM2 = 0.2;

    //3rd move
    private final int PM3 = inchesToPositions(6);
    private final double PWRM3 = 0.2;

    //4th move
    private final int PM4 = inchesToPositions(27);
    private final double PWRM4 = 0.5;

    //5th move
    private final int PM5 = inchesToPositions(15);
    private final double PWRM5 = 0.7;

    //6th move
    private final int PM6 = inchesToPositions(80);

    private final double PWRM6 = 0.7;

    //Beacon
    private final int PBM = 37;
    private final double PWRBM = 1.0;

    private OpticalDistanceSensor optical1;

    //Line Sensor
    private final int LLMIN = 20;
    private final int LRMIN = 7;
    private double leftLinePower = -0.1;
    private double RightLinePower = -0.1;

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

        servo1.setPosition(1);
        servo2.setPosition(0);

        color1 = hardwareMap.colorSensor.get("color1");
        color2 = hardwareMap.colorSensor.get("color2");
        optical1 = hardwareMap.opticalDistanceSensor.get("optical1");

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

        Vader = MediaPlayer.create(hardwareMap.appContext, R.raw.bottheme);

        waitForStart();

        //Vader.start(); //Will play the Imperial March

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
        motor3.setPower(0.8); //Shooting motor
        sleep(1600);
        motor3.setPower(0);  //Stop shooting
        optical1.enableLed(true);
        turn(M1T1, M2T1, PWRT1); //45 deg turn

        move(PM1, PWRM1);  //runs to the wall
        move(PM3, PWRM3);
        while(optical1.getLightDetected() < 0.06 && opModeIsActive()) {
            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor1.setPower(0.2);
            motor2.setPower(0.2);
        }
        motor1.setPower(0);
        motor2.setPower(0);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        turn(M1T2, M2T2, PWRT2); //turns parallel to the wall
        while(!seesLine()); // runs until it detects a line
        pushBeacon(); //hits beacons
        //turn(M1T25, M2T25, PWRT25);
        move(PM4,PWRM4);
        while(!seesLine());
        pushBeacon(); //hits beacons
        move(PM5, PWRM5);
        turn(M1T3,M2T3,PWRT3); //turns   towards cap ball
        turn(M1T4,M2T4, PWRT4);
        move(PM6,PWRM6); //runs into cap ball, knocks off the ball and parks

        //Vader.stop();
       // MLG360quickscopeNOSCOPEbillythekidmtndewDORITOSBasti0nMaIn(dank);

    }

    public void move(int addPos, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();

        motor1.setTargetPosition(currentPos1 + addPos);
        motor2.setTargetPosition(currentPos2 + addPos);
        motor1.setPower(power);
        motor2.setPower(power);

        while (opModeIsActive() && motor1.isBusy() && motor2.isBusy());
        motor1.setPower(0);
        motor2.setPower(0);
    }

    public void shoot(double power) {
        final int POS_INCREMENT = 1000;
        int currentPos3 = motor3.getCurrentPosition();
        motor3.setTargetPosition(currentPos3 + POS_INCREMENT);
        motor3.setPower(power);
        while (opModeIsActive() && motor3.isBusy());
        motor3.setPower(0);
    }

    public void turn(int pos1, int pos2, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();
        motor1.setTargetPosition(pos1 + currentPos1);
        motor2.setTargetPosition(pos2 + currentPos2);
        motor1.setPower(power);
        motor2.setPower(power);
        while (motor1.isBusy() || motor2.isBusy() && opModeIsActive());
        motor1.setPower(0);
        motor2.setPower(0);
    }


    private boolean seesLineLeft() {
        return ((line1.alpha() > LLMIN));
    }

    private boolean seesLineRight() {
        return ((line2.alpha() > LRMIN));
    }

    public void pushBeacon() {
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

    public void MLG360quickscopeNOSCOPEbillythekidmtndewDORITOSBasti0nMaIn(boolean dank) {
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

    public boolean seesLine()
    {
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1.setPower(leftLinePower);
        motor2.setPower(RightLinePower);

        if (motor1.getPower() == 0 && motor2.getPower() == 0) //only if both see the line
            {
                motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                return true;
            }

            else if (seesLineLeft()) //runs left sensor function
            {
                leftLinePower = 0;
                motor1.setPower(0);
                return false;
            }

            else if (seesLineRight()) //runs right sensor function
            {
                RightLinePower = 0;
                motor2.setPower(0);
                return false;
            }
        return false;
        }
    }
