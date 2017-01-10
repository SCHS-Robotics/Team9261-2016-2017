/**
 * Created by Andrew on 1/7/2016.
 * Test auto code for Team 9261
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.I2cAddrConfig;
import com.qualcomm.robotcore.hardware.I2cController;
import com.qualcomm.robotcore.hardware.I2cControllerPortDeviceImpl;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;


import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.TypeConversion;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.ftccommon.external.SoundPlayingRobotMonitor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.concurrent.locks.Lock;

import java.lang.Math.*;

@Autonomous
public class InchesTheoryRedCenter extends LinearOpMode
{
    DcMotor motor1; //left
    DcMotor motor2; //right
    DcMotor motor3; //shoot

    //Servo servoLeft; //left beacon
    // servoRight; //right beacon

        /*ColorSensor leftColor;
        ColorSensor rightColor;
        ColorSensor groundTrack;*/

    public int pos1; //left motor target position
    public int pos2; //right motor target position
    public int degVal; //value per degree turn encoder
       /* int cnumberLCB = leftColor.blue();
        int cnumberLCR = leftColor.red();
        int cnumberRCB = rightColor.blue();
        int cnumberRCR = rightColor.red();
        int cnumberGTA = groundTrack.alpha();*/

    double inches; //inches travel
    public double degree; //degrees turn
    public double wheelcirc; //wheel diameter*pi; reference dimension
    public double robotwheelcirc; //distance between wheels*pi; reference dimension
    public double arclengthWheel; //arclength of wheel
    public double arclengthBot; //arclength of distance between wheels
    public double ratiowheelCircles; //ratio of arclength of wheels to distance between wheels
    public int encodersinglerev; //value for single revolution


        /*
        diameter of wheel is 4 or 3 5/16 inches, distance between wheels is 13.3125.
         */


    @Override
    public void runOpMode() throws InterruptedException
    {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor3 = hardwareMap.dcMotor.get("motor3");

        //servoLeft = hardwareMap.servo.get("servoLeft");
        //servoRight = hardwareMap.servo.get("servoRight");

        //          leftColor = hardwareMap.colorSensor.get("leftColor");
        //        rightColor = hardwareMap.colorSensor.get("rightColor");
        //       groundTrack = hardwareMap.colorSensor.get("groundTrack");
//
        //          I2cAddr leftColori2c = I2cAddr.create8bit(0x3c);
        //        I2cAddr rightColori2c = I2cAddr.create8bit(0x4c);
        //
        //      I2cAddr groundTracki2c = I2cAddr.create8bit(0x5c);

        //        leftColor.setI2cAddress(leftColori2c);
        //          rightColor.setI2cAddress(rightColori2c);
//            groundTrack.setI2cAddress(groundTracki2c);

        pos1 = 0;
        pos2 = 0;
        //degVal = ;
        encodersinglerev = 560;
        wheelcirc = Math.PI * 4;
        robotwheelcirc = Math.PI * 13.3125;
        arclengthWheel = degree / 360 * wheelcirc;
        arclengthBot = degree / 360 * robotwheelcirc;
        ratiowheelCircles = wheelcirc / robotwheelcirc;

        //leftColor.enableLed(false);
        //rightColor.enableLed(false);
//            groundTrack.enableLed(true);


        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);
        motor3.setDirection(DcMotor.Direction.FORWARD);

        waitForStart();
        resetEncoders();
        //doubleShot();
        forwardDrive(1 , 0.25);
        sleep(500);
        leftdegTurn(90 , 0.25); //how many degrees and at what power to turn left
        sleep(500);
        forwardDrive(12 , 0.25);//how many inches and at what power to drive forward
        sleep(500);
        rightdegTurn(90 , 0.25);//how many degrees and at what power to turn right
        sleep(500);
        forwardDrive(24 , 0.25);//how many inches and at what power to drive forward
        sleep(500);
        //beaconRed();//beacon procedure for red team
        forwardDrive(48 , 0.25);//how many inches and at what power to drive forward
        sleep(500);
        //beaconRed();
        rightdegTurn(135 , 0.25);
        sleep(500);
        forwardDrive(84 , 0.25);


    }
    public void doubleShot() throws InterruptedException
    {
        motor3.setPower(0.8);
        sleep(1500);
        motor3.setPower(0);
    }

    public void forwardDrive(float inches, double power) throws InterruptedException
    {
        pos1 = (int)(pos1 + (inches / wheelcirc * encodersinglerev));
        pos2 = (int)(pos2 + (inches / wheelcirc * encodersinglerev));
        while (pos1 > motor1.getCurrentPosition() || pos2 > motor2.getCurrentPosition() && opModeIsActive())
        {
            motor1.setPower(power);
            motor2.setPower(power);

            if (motor1.getCurrentPosition() >= pos1)
            {
                motor1.setPower(0);
            }

            if (motor2.getCurrentPosition() >= pos2)
            {
                motor2.setPower(0);
            }

               /* if (cnumberGTA > 15)
                {
                    motor1.setPower(0);
                    motor2.setPower(0);
                    resetEncoders();
                    break;
                }*/
            alltheTelemetries();
        }
        sleep(1000);
    }

    public void leftdegTurn(int degree, double power) throws InterruptedException
    {
        pos1 = (int) ((-(arclengthBot / wheelcirc * encodersinglerev) + pos1)/2);
        pos2 = (int) (((arclengthBot / wheelcirc * encodersinglerev) + pos2)/2);
        arclengthBot = degree / 360 * robotwheelcirc;
        while (pos1 < motor1.getCurrentPosition() || pos2 > motor2.getCurrentPosition() && opModeIsActive())
        {
            motor1.setPower(-power);
            motor2.setPower(power);

            if (pos1 >= motor1.getCurrentPosition())
            {
                motor1.setPower(0);
            }

            if (pos2 <= motor2.getCurrentPosition())
            {
                motor2.setPower(0);
            }
        }
        alltheTelemetries();
        sleep(1000);
    }

    public void rightdegTurn(int degree, double power) throws InterruptedException
    {
        pos1 =  (int) ((( arclengthBot / wheelcirc * encodersinglerev) + pos1)/2);
        pos2 =  (int) ((-( arclengthBot / wheelcirc * encodersinglerev) + pos2)/2);
        arclengthBot = degree / 360 *robotwheelcirc;
        while (pos1 > motor1.getCurrentPosition() || pos2 < motor2.getCurrentPosition() && opModeIsActive())
        {
            motor1.setPower(power);
            motor2.setPower(-power);

            if (pos1 <= motor1.getCurrentPosition())
            {
                motor1.setPower(0);
            }

            if (pos2 >= motor2.getCurrentPosition())
            {
                motor2.setPower(0);
            }
        }
        alltheTelemetries();
        sleep(1000);
    }

       /* public void beaconRed()
        {
            while (leftColor.red() != rightColor.red() && opModeIsActive())
            {
                if(leftColor.red() > rightColor.red()) //will try and hit red button two times and then reset to starting position
                {
                    servoLeft.setPosition(1);
                    sleep(250);
                    servoLeft.setPosition(0);
                    sleep(250);
                    servoLeft.setPosition(1);
                    sleep(250);
                    servoLeft.setPosition(0);
                    sleep(250);
                    servoLeft.setPosition(0.5);
                }

                if (leftColor.red() < rightColor.red()) //will try and hit red button two times and then reset to starting position
                {
                    servoRight.setPosition(1);
                    sleep(250);
                    servoRight.setPosition(0);
                    sleep(250);
                    servoRight.setPosition(1);
                    sleep(250);
                    servoRight.setPosition(0);
                    sleep(250);
                    servoRight.setPosition(0.5);
                }
            }
        }

        public void beaconBlue()
        {
            while (leftColor.blue() != rightColor.blue() && opModeIsActive())
            {
                if(leftColor.blue() < rightColor.blue())
                {
                    servoRight.setPosition(1);
                    sleep(250);
                    servoRight.setPosition(0);
                    sleep(250);
                    servoRight.setPosition(1);
                    sleep(250);
                    servoRight.setPosition(0);
                    sleep(250);
                    servoRight.setPosition(0.5);
                }

                if (leftColor.blue() > rightColor.blue())
                {
                    servoLeft.setPosition(1);
                    sleep(250);
                    servoLeft.setPosition(0);
                    sleep(250);
                    servoLeft.setPosition(1);
                    sleep(250);
                    servoLeft.setPosition(0);
                    sleep(250);
                    servoLeft.setPosition(0.5);
                }
            }
        }*/

    public void resetEncoders()
    {
        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

      /*  public void initColor()
        {
            ColorSensor leftColor;
            ColorSensor rightColor;
            ColorSensor groundTrack;

            leftColor = hardwareMap.colorSensor.get("leftColor");
            rightColor = hardwareMap.colorSensor.get("rightColor");
            groundTrack = hardwareMap.colorSensor.get("groundTrack");

            I2cAddr leftColori2c = I2cAddr.create8bit(0x3c);
            I2cAddr rightColori2c = I2cAddr.create8bit(0x4c);
            I2cAddr groundTracki2c = I2cAddr.create8bit(0x5c);

            leftColor.setI2cAddress(leftColori2c);
            rightColor.setI2cAddress(rightColori2c);
            groundTrack.setI2cAddress(groundTracki2c);

            int cnumberLCB = leftColor.blue();
            int cnumberLCR = leftColor.red();
            int cnumberRCB = rightColor.blue();
            int cnumberRCR = rightColor.red();
            int cnumberGTA = groundTrack.alpha();

            leftColor.enableLed(false);
            rightColor.enableLed(false);
            groundTrack.enableLed(true);
        }*/

    public void initMotor()
    {
        DcMotor motor1; //left
        DcMotor motor2; //right
        DcMotor motor3; //shoot

        int pos1; //left motor target position
        int pos2; //right motor target position
        int degVal; //value per degree turn encoder
        int encodersinglerev; //value for single revolution

        double inches; //inches travel
        double degree; //degrees turn
        double wheelcirc; //wheel diameter*pi; reference dimension
        double robotwheelcirc; //distance between wheels*pi; reference dimension
        double arclengthWheel; //arclength of wheel
        double arclengthBot; //arclength of distance between wheels
        double ratiowheelCircles; //ratio of arclength of wheels to distance between wheels

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor3 = hardwareMap.dcMotor.get("motor3");

        pos1 = 0;
        pos2 = 0;
        //degVal = ;
        encodersinglerev = 560;
        wheelcirc = Math.PI * 4;
        robotwheelcirc = Math.PI * 13.3125;

        motor1.setDirection(DcMotor.Direction.REVERSE);
        motor2.setDirection(DcMotor.Direction.FORWARD);
        motor3.setDirection(DcMotor.Direction.FORWARD);
        resetEncoders();
    }

       /* public void initServo()
        {
            Servo servoLeft; //left beacon
            Servo servoRight; //right beacon

            servoLeft = hardwareMap.servo.get("servoLeft");
            servoRight = hardwareMap.servo.get("servoRight");

            servoLeft.setPosition(0.5);
            servoRight.setPosition(0.5);
        }*/

    public void totalInit()
    {
        //initServo();
        initMotor();
        // initColor();
    }

    public void alltheTelemetries()
    {
           /* telemetry.addData("LeftBlue", leftColor.blue());
            telemetry.addData("LeftRed", leftColor.red());
            telemetry.addData("RightBlue", rightColor.blue());
            telemetry.addData("RightRed", rightColor.red());
            telemetry.addData("GroundLight", groundTrack.alpha());

            telemetry.addData("LeftServoPos", servoLeft.getPosition());
            telemetry.addData("RightServoPos", servoRight.getPosition());
*/
        telemetry.addData("LeftMotorEnc", motor1.getCurrentPosition());
        telemetry.addData("RightMotorEnc", motor2.getCurrentPosition());

        telemetry.update();
    }
}