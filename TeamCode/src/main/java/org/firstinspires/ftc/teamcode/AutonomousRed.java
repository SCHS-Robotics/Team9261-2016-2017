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
        sleep(250);
        turnForward(0, 40, 0, 0.9); //45 deg turn
        sleep(250);
        driveForward(62, 0.9);  //runs to the wall
        sleep(250);
        /*while(optical1.getLightDetected() <= 0.012 && opModeIsActive()) {
            motor1.setPower(0.2);
            motor2.setPower(0.2);
        }
        motor1.setPower(0);
        motor2.setPower(0);
        */
        sleep(250);
        turnBackward(0, 19, 0, 0.7); //turns parallel to the wall
        sleep(250);
        driveForward(10,0.5);
        leftLinePowerFWD = 0.35;
        RightLinePowerFWD = 0.35;
        while(!seesLineFWD() && opModeIsActive());
        sleep(250);
        pushBeaconRed(); //hits beacons
        turnBackward(0, 10, 0, 0.5);
        driveBackward(28, 0.5); //IF THE || CHANGE WE MADE WORKS CHANGE THIS BACK TO REGULAR MOVE
        resetEncoders();
        leftLinePowerBCK = -0.35;
        RightLinePowerBCK = -0.35;
        sleep(250);
        while(!seesLineBCK() && opModeIsActive());
        sleep(250);
        pushBeaconRed(); //hits beacons
        resetWheels();
        turnForward(15,0,0.5,0);
        driveBackward(3,0.5);
        turnForward(45,0,0.5,0);
        driveForward(42, 0.8);
        turnBackward(0,45, 0, 0.8);
        driveForward(25, 0.5);
        Vader.stop();
    }
}