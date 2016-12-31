package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

import static java.lang.Math.asin;
import static java.lang.Math.round;

/**
 * Created by Cole Savage on 12/27/2016.
 */

public class Map2D {
    public final String VERSION = "1.3";

    private final int ONEREVOLUTION = 1160;
    public double currentX;
    public double currentY;
    private OpticalDistanceSensor xSensor;
    private OpticalDistanceSensor ySensor;
    private DcMotor leftMotor;
    private DcMotor rightMotor;
    private GyroSensor gyro;

    public Map2D(OpticalDistanceSensor sensor1, OpticalDistanceSensor sensor2, DcMotor motor1, DcMotor motor2) {
        this.xSensor = sensor1;
        this.ySensor = sensor2;
        this.leftMotor = motor1;
        this.rightMotor = motor2;
        this.currentX = xSensor.getLightDetected();
        this.currentY = ySensor.getLightDetected();
    }

    public Map2D(OpticalDistanceSensor sensor1, OpticalDistanceSensor sensor2, DcMotor motor1, DcMotor motor2, GyroSensor gyro1) {
        this.xSensor = sensor1;
        this.ySensor = sensor2;
        this.leftMotor = motor1;
        this.rightMotor = motor2;
        this.currentX = xSensor.getLightDetected();
        this.currentY = ySensor.getLightDetected();
        this.gyro = gyro1;
        gyro.calibrate();
    }
    private void updateX() {
        currentX = xSensor.getLightDetected();
    }

    private void updateY() {
        currentY = ySensor.getLightDetected();
    }

    private double distanceToTarget(double targetX, double targetY) {
        updateX();
        updateY();
        double xChange = targetX-currentX;
        double yChange = targetY-currentY;
        return Math.sqrt((Math.pow(xChange,2))+(Math.pow(yChange,2))); //Uses distance formula
    }

    public void moveToTarget(double targetX, double targetY, boolean useGyro) {
        if(!useGyro) {
            updateX();
            updateY();
            double xChange = targetX-currentX;
            double yChange = targetY-currentY;
            double angle = asin(yChange/xChange);
            int turnPos = (int)round((angle/360)*ONEREVOLUTION);
            int distance = (int)round(distanceToTarget(targetX,targetY)*ONEREVOLUTION);
            if(currentX < targetX) {
                turn(turnPos, 0.5, "r");
                move(distance, 0.5);
                turn(turnPos, 0.5, "l");
                updateX();
                updateY();
            }
            else if(this.currentX > targetX) {
                turn(turnPos, 0.5, "l");
                move(distance, 0.5);
                turn(turnPos, 0.5, "r");
                updateX();
                updateY();
            }
        }
        else if(useGyro) {
            if(gyro != null) {
                updateX();
                updateY();
                double xChange = targetX-currentX;
                double yChange = targetY-currentY;
                double angle1 = asin(yChange/xChange) - gyro.getHeading();
                double angle2 = asin(yChange/xChange);
                int turnPos1 = (int)round((angle1/360)*ONEREVOLUTION);
                int turnPos2 = (int)round((angle2/360)*ONEREVOLUTION);
                int distance = (int)round(distanceToTarget(targetX,targetY)*ONEREVOLUTION);
                if(currentX < targetX) {
                    turn(turnPos1, 0.5, "r");
                    move(distance, 0.5);
                    turn(turnPos2, 0.5, "l");
                    updateX();
                    updateY();
                }
            }
        }
    }

    public double getX() {
        updateX();
        return currentX;
    }

    public double getY() {
        updateY();
        return currentY;
    }

    private void turn(int pos, double power, String direction) {
        int currentPos;
        if(direction == "r") {
            currentPos = leftMotor.getCurrentPosition();
            leftMotor.setTargetPosition(pos + currentPos);
            leftMotor.setPower(power);
        }
        else if(direction == "l") {
            currentPos = rightMotor.getCurrentPosition();
            rightMotor.setTargetPosition(pos + currentPos);
            rightMotor.setPower(power);
        }
    }

    public void move(int addPos, double power) {
        int currentPos1 = leftMotor.getCurrentPosition();
        int currentPos2 = rightMotor.getCurrentPosition();
        leftMotor.setTargetPosition(currentPos1+addPos);
        rightMotor.setTargetPosition(currentPos2+addPos);
        leftMotor.setPower(power);
        rightMotor.setPower(power);
    }
}