package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by Andrew on 1/16/2017.
 */
@Autonomous
public class AutoInnerBlue1_1 extends LinearOpMode {
    //Move 1
    public final int MOVE1B = inchesToPositions(18);
    public final double MOVE1SPD = 0.5;

    //Move 2
    public final int MOVE2B = inchesToPositions(-60);
    public final double MOVE2SPD = -0.5;

    //Move 3
    public final int MOVE3B = inchesToPositions(-12);
    public final double MOVE3SPD = -0.2;

    //Move 4
    public final int MOVE4B = inchesToPositions(15);
    public final double MOVE4SPD = 0.5;


    //Turn 1
    public final int TURN1B = degreesToPositions(-135);
    public final int TURN1B2 = 0;
    public final double TURN1SPD = -0.5;

    //Turn 2
    public final int TURN2B = 0;
    public final int TURN2B2 = degreesToPositions(47);
    public final double TURN2SPD = 0.5;

    //Turn 3
    public final int TURN3B = 0;
    public final int TURN3B2 = degreesToPositions(-90);
    public final double TURN3SPD = -0.5;

    //Turn 4
    public final int TURN4B = degreesToPositions(45);
    public final int TURN4B2 = 0;
    public final double TURN4SPD = 0.5;



    //Line Sensor
    private double leftLinePower1 = -0.1;
    private double RightLinePower1 = -0.1;
    private double leftLinePower2 = 0.1;
    private double RightLinePower2 = 0.1;

    private DcMotor motor1; //left wheel
    private DcMotor motor2; //right wheel
    private DcMotor motor3; //shooting motor

    private Servo servo1; //left button pusher
    private Servo servo2; //right button pusher

    private ColorSensor color1; //left color sensor
    private ColorSensor color2; //right color sensor

    private ColorSensor line1; //left line sensor
    private ColorSensor line2; //right line sensor
    private OpticalDistanceSensor optical1;
    private MediaPlayer Vader;

    //Line Sensor
    final int LLMIN = 20;
    final int LRMIN = 7;

    double leftLinePowerBCK = -0.1;
    double RightLinePowerBCK = -0.1;
    double leftLinePowerFWD = 0.1;
    double RightLinePowerFWD = 0.1;
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

        Vader.start();
        motor3.setPower(0.8);//shoot
        sleep(1600);
        motor3.setPower(0);
        move(MOVE1B, MOVE1SPD);//move a bit forward
        turn(TURN1B, TURN1B2, TURN1SPD);//spin to face the wall
        move(MOVE2B, MOVE2SPD);//move towards the wall
        move(MOVE3B, MOVE3SPD);//slow move towards the wall
        while(optical1.getLightDetected() < 0.06 && opModeIsActive()) { //ODS sense clear plexi
            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor1.setPower(-0.2);
            motor2.setPower(-0.2);
        }
        turn(TURN2B, TURN2B2, TURN2SPD); //align with wall
        while(!seesLineBCK() && opModeIsActive()); //seesline
        pushBeaconBlue(); //blue beacon push
        move(MOVE4B, MOVE4SPD);//move forward
        while (!seesLineFWD() && opModeIsActive());//seesline
        pushBeaconBlue(); //blue beacon push
        move(MOVE4B, MOVE4SPD);//move slightly forward
        turn(TURN3B, TURN3B2, TURN3SPD);// turn to face cap ball
        move(MOVE1B, MOVE1SPD); //move up to cap ball
        turn(TURN4B, TURN4B2, TURN4SPD);//knock it off
        move(MOVE4B, MOVE4SPD);//park
        Vader.stop();
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
    private int inchesToPositions(double inches) {
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


}