package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import java.util.List;

/**
 * Created by Andrew on 1/21/2017.
 */

public abstract class BaseAutoRunWithEncoders extends InitCodeRunWithEncoders {
    //21 pos: 1 degree
    //280/pi pos : 1 inch

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
        motor1.setPower(leftLinePowerBCK);
        motor2.setPower(RightLinePowerBCK);

        if (motor1.getPower() == 0 && motor2.getPower() == 0) //only if both see the line
        {
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
        motor1.setPower(leftLinePowerFWD);
        motor2.setPower(RightLinePowerFWD);

        if (motor1.getPower() == 0 && motor2.getPower() == 0) //only if both see the line
        {
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

        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void driveForward(int inches, double power) throws InterruptedException
    {
        int encoderTicks = inchesToPositions(inches);
        resetEncoders();
        waitforEncoders(encoderTicks, power);
        motor1.setPower(0);
        motor2.setPower(0);

    }
    public void driveBackward(int inches, double power) throws InterruptedException
    {
        driveForward(inches, -power);
    }
    public void turnForward(int degreesleft, int degreesright, double powerleft, double powerright) throws InterruptedException
    {
        int encoderTicksleft = degreesToPositions(degreesleft);
        int encoderTicksright = degreesToPositions(degreesright);
        resetEncoders();
        waitforEncodersTurn(encoderTicksleft, encoderTicksright, powerleft, powerright);
        motor1.setPower(0);
        motor2.setPower(0);
    }
    public void turnBackward(int degreesleft, int degreesright, double powerleft, double powerright) throws InterruptedException
    {
        turnForward(degreesleft, degreesright, -powerleft, -powerright);
    }
    private void waitforEncodersTurn(double encoderTicksleft, double encoderTicksright, double powerleft, double powerright) throws InterruptedException
    {
        while(getFurthestEncoder() < encoderTicksleft || getFurthestEncoder() < encoderTicksright && opModeIsActive())
        {
            if (encoderTicksleft!=0) {
                motor1.setPower(powerleft * Brake(encoderTicksleft));
            }
            else if (encoderTicksright!=0) {
                motor2.setPower(powerright * Brake(encoderTicksright));
            }
            sleep(1);
        }
    }
    private void waitforEncoders(double encoderTicks, double power) throws InterruptedException
    {
        while (getFurthestEncoder() < encoderTicks && opModeIsActive())
        {
            motor1.setPower(power * Brake(encoderTicks));
            motor2.setPower(power * Brake(encoderTicks));
            sleep(1);
        }
    }
    private int getFurthestEncoder()
    {
        return Math.max(Math.abs(motor1.getCurrentPosition()), Math.abs(motor2.getCurrentPosition()));
    }
    private double Brake(double encoderTicks){
        return Range.clip(Math.cbrt(Math.abs(getFurthestEncoder() - encoderTicks)/ encoderTicks), 0.2, 1);
    }
    public void runalongWall() throws InterruptedException
    {

    }
}
