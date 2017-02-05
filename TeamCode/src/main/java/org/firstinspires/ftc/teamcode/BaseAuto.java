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

public abstract class BaseAuto extends InitCode {
    //21 pos: 1 degree
    //280/pi pos : 1 inch

    //RED CNST BELOW
    protected boolean dank = false;
    //defensive run
    public final int DEF = inchesToPositions(144);
    public final double PWRDEF = 0.7;

    //1st turn
    public final int M1T1 = 0;
    public final int M2T1 = degreesToPositions(43);
    public final double PWRT1 = 0.5;

    //2nd turn
    public final int M1T2 = 0;
    public final int M2T2 = degreesToPositions(-30);
    public final double PWRT2 = -0.5;

    //2.5th turn
    public final int M1T25 = degreesToPositions(5);
public final int M2T25 = 0;
    public final double PWRT25 = 0.5;


    //3rd turn
    public final int M1T3 = degreesToPositions(-60);
    public final int M2T3 = 0;
    public final double PWRT3 = -.5;

    //4th turn
    public final int M1T4 = degreesToPositions(37);
    public final int M2T4 = 0;
    public final double PWRT4 = 0.5;

    //1st move
    public final int PM1 = inchesToPositions(60);
    public final double PWRM1 = 0.5;

    //linefollow increment
    public int LMCNST1 = inchesToPositions(-2);
    public int LMCNST2 = inchesToPositions(-2);
    public double LPWR = -0.5;
    public double LPWR2 = 0.5;

    //2nd move
    public final int PM2 = (int) inchesToPositions(-10);
    public final double PWRM2 = 0.5;

    //3rd move
    public final int PM3 = inchesToPositions(6);
    public final double PWRM3 = 0.5;

    //4th move
    public final int PM4 = inchesToPositions(58);
    public final double PWRM4 = 0.45;

    //5th move
    public final int PM5 = inchesToPositions(15);
    public final double PWRM5 = 0.65;

    //6th move
    public final int PM6 = inchesToPositions(-75);
    public final double PWRM6 = -0.7;

    //Beacon
    public final int PBM = 37;
    public final double PWRBM = 1.0;

    //BLUE CNST BELOW
    //Move 1
    public final int MOVE1B = inchesToPositions(18);
    public final double MOVE1SPD = 0.5;

    //Move 2
    public final int MOVE2B = inchesToPositions(-55);
    public final double MOVE2SPD = -0.5;

    //Move 3
    public final int MOVE3B = inchesToPositions(-15);
    public final double MOVE3SPD = -0.5;

    //Move 4
    public final int MOVE4B = inchesToPositions(75);
    public final double MOVE4SPD = 0.5;


    //Turn 1
    public final int TURN1B = degreesToPositions(-160);
    public final int TURN1B2 = 0;
    public final double TURN1SPD = -0.5;

    //Turn 2
    public final int TURN2B = 0;
    public final int TURN2B2 = degreesToPositions(40);
    public final double TURN2SPD = 0.5;

    //Turn 3
    public final int TURN3B = degreesToPositions(42);
    public final int TURN3B2 = 0;
    public final double TURN3SPD = 0.5;

    //Turn 4
    public final int TURN4B = degreesToPositions(-23);
    public final int TURN4B2 = degreesToPositions(22);
    public final double TURN4SPD = 0.5;

    public void move(int addPos, double power) {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();

        motor1.setTargetPosition(currentPos1 + addPos);
        motor2.setTargetPosition(currentPos2 + addPos);
        motor1.setPower(power);
        motor2.setPower(power);

        while (opModeIsActive() && (motor1.isBusy() || motor2.isBusy())); // DONT FORGET TO ROLL THIS BACK TO AN && IF THIS DOESNT WORK OUT DURING TESTING TOMORROW
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

    public void pushBeaconRed() throws InterruptedException{
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

    public void pushBeaconBlue() throws InterruptedException{
        if(color1.red() < color2.red()|| color1.blue() > color2.blue()) {
            servo2.setPosition(1);
            sleep(500);
            servo2.setPosition(0);
            sleep(500);
            servo2.setPosition(1);
        }

        else if (color1.red() > color2.red() || color2.blue() > color1.blue()) {
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
    public int inchesToPositions(double inches) {
        return (int) Math.round(inches*280/Math.PI);
    }

    public boolean seesLineBCK()
    {
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1.setPower(leftLinePowerBCK);
        motor2.setPower(RightLinePowerBCK);

        if (motor1.getPower() == 0 && motor2.getPower() == 0) //only if both see the line
        {
            motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftLinePowerBCK = -0.2;
            RightLinePowerBCK = -0.2;
            return true;
        }

        else if (seesLineLeft()) //runs left sensor function
        {
            leftLinePowerBCK = 0;
            motor1.setPower(0);
            return false;
        }

        else if (seesLineRight()) //runs right sensor function
        {
            RightLinePowerBCK = 0;
            motor2.setPower(0);
            return false;
        }
        return false;
    }
    public boolean seesLineFWD()
    {
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        motor1.setPower(leftLinePowerFWD);
        motor2.setPower(RightLinePowerFWD);

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
            leftLinePowerFWD = 0;
            motor1.setPower(0);
            return false;
        }

        else if (seesLineRight()) //runs right sensor function
        {
            RightLinePowerFWD = 0;
            motor2.setPower(0);
            return false;
        }
        return false;
    }

    public void resetEncoders()
    {
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void rangeDrive(int pos, double pwr) {

    }
    public void leftTiltDrive(int addPos, double power)
    {
        int currentPos1 = motor1.getCurrentPosition();
        int currentPos2 = motor2.getCurrentPosition();

        motor1.setTargetPosition(currentPos1 + addPos - inchesToPositions(10));
        motor2.setTargetPosition(currentPos2 + addPos);
        motor1.setPower(power * 0.9375);
        motor2.setPower(power);

        while (opModeIsActive() && (motor1.isBusy() || motor2.isBusy()));  //REMEMBER TO ROLL THIS BACK IF IT DOESNT WORK OUT IN TESTING TO &&
        motor1.setPower(0);
        motor2.setPower(0);
    }
}
