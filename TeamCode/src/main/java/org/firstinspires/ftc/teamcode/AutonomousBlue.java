package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Andrew on 1/22/2017.
 */
@Autonomous
public class AutonomousBlue extends BaseAutoRunWithEncoders {
    @Override
    public void runOpMode() throws InterruptedException {
        initAll();
        waitForStart();
        Vader.start();
        motor3.setPower(0.8); // shooter start
        sleep(1600);
        motor3.setPower(0); // shooter stop
        wheel1.setPosition(1);
        wheel2.setPosition(0.35);
        //optical2.enableLed(true);
        driveForward(18, 0.5);// forwards 18 inches, at 0.5 of max power initially
        sleep(250);
        turnBackward(147, 0, 0.9, 0); //left wheel backwards pivot 160 degrees, at an initial speed of 0.5
        sleep(250);
        driveBackward(50, 0.9); // backwards 55 inches, at 0.5 of max power initially
        sleep(250);
        /*while(optical2.getLightDetected() < 0.09 && opModeIsActive()) { // remember to change to optical2 when is mounted
            motor1.setPower(-0.2);
            motor2.setPower(-0.2);
        }*/
        motor1.setPower(0);
        motor2.setPower(0);
        sleep(250);
        turnForward(0, 10, 0, 0.9); //right wheel forwards pivot 40 degrees at a speed of 0.5
        sleep(250);
        driveBackward(10, 0.5);
        leftLinePowerBCK = -0.35 ;
        RightLinePowerBCK = -0.35;
        while(!seesLineBCK() && opModeIsActive());
        sleep(250);
        pushBeaconBlue(); //senses color and hits the according beacon
        sleep(250);
        turnForward(0, 15, 0, 0.5);
        driveForward(25, 0.9); //backwards 15 inches, at a speed of 0.5
        sleep(250);
        leftLinePowerFWD = 0.4;
        RightLinePowerFWD = 0.4;
        while (!seesLineFWD() && opModeIsActive());
        sleep(250);
        pushBeaconBlue(); // senses color and hits the according beacon
        turnForward(0,5, 0, 0.5);
        driveBackward(60, 0.9);
        resetWheels();
        sleep(250);
        turnForward(30, 0, 0.9, 0); // left wheel forwards pivot 42 degrees at an initial speed of 0.5
        sleep(250);
        driveForward(75, 0.9); // forwards 75 inches at an initial speed of 0.5
        Vader.stop();
    }
}