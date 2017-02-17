package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Sage Creek Level Up on 2/8/2017.
 */
@Autonomous
public class AutonomousRed extends BaseAutoRunWithEncoders {
    @Override
    public void runOpMode() throws InterruptedException {
        initAll();
        waitForStart();
        Vader.start();
        motor3.setPower(0.8); //Shooting motor
        sleep(1600);
        motor3.setPower(0);  //Stop shooting
        wheel1.setPosition(1);
        wheel2.setPosition(0.35);
        sleep(500);
        turnForward(0, 42, 0, 0.9); //45 deg turn
        sleep(500);
        driveForward(66, 0.9);  //runs to the wall
        sleep(500);
        /*while(optical1.getLightDetected() <= 0.012 && opModeIsActive()) {
            motor1.setPower(0.2);
            motor2.setPower(0.2);
        }
        motor1.setPower(0);
        motor2.setPower(0);
        */
        sleep(500);
        turnBackward(0, 20, 0, 0.9); //turns parallel to the wall
        sleep(500);
        driveForward(6,0.5);
        leftLinePowerFWD = 0.2;
        RightLinePowerFWD = 0.2;
        while(!seesLineFWD() && opModeIsActive());
        sleep(500);
        pushBeaconRed(); //hits beacons
        turnBackward(0, 15, 0, 0.5);
        driveBackward(28, 0.5); //IF THE || CHANGE WE MADE WORKS CHANGE THIS BACK TO REGULAR MOVE
        resetEncoders();
        leftLinePowerBCK = -0.2;
        RightLinePowerBCK = -0.2;
        sleep(500);
        while(!seesLineBCK() && opModeIsActive());
        sleep(500);
        pushBeaconRed(); //hits beacons
        driveForward(15, 0.9);
        sleep(500);
        turnBackward(45, 0, 0.9 , 0); //turns   towards cap ball
        sleep(500);
        driveBackward(75, 0.9); //runs into cap ball, knocks off the ball and parks
        Vader.stop();
    }
}
