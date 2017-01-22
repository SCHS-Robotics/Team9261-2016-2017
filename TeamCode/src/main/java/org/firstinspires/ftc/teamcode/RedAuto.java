package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Andrew on 1/17/2017.
 */
@Autonomous
public class RedAuto extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        initAll();
        waitForStart();
        Vader.start();
        motor3.setPower(0.8); //Shooting motor
        sleep(1600);
        motor3.setPower(0);  //Stop shooting
        sleep(500);
        optical1.enableLed(true);
        turn(M1T1, M2T1, PWRT1); //45 deg turn
        sleep(500);
        move(PM1 + PM3, PWRM1);  //runs to the wall

        sleep(500);
        while(optical1.getLightDetected() <= 0.012 && opModeIsActive()) {
            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor1.setPower(0.2);
            motor2.setPower(0.2);
        }
        motor1.setPower(0);
        motor2.setPower(0);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sleep(500);
        turn(M1T2, M2T2, PWRT2); //turns parallel to the wall
        resetEncoders();
        sleep(500);
        leftLinePowerBCK = -0.2;
        RightLinePowerBCK = -0.2;
        while(!seesLineBCK() && opModeIsActive()); // runs until it detects a line
        sleep(500);
        pushBeaconRed(); //hits beacons
        leftTiltDrive(PM4,PWRM4); //IF THE || CHANGE WE MADE WORKS CHANGE THIS BACK TO REGULAR MOVE
        resetEncoders();
        leftLinePowerBCK = -0.2;
        RightLinePowerBCK = -0.2;
        sleep(500);
        while(!seesLineBCK() && opModeIsActive());
        sleep(500);
        pushBeaconRed(); //hits beacons
        move(PM5, PWRM5);
        sleep(500);
        turn(M1T3,M2T3,PWRT3); //turns   towards cap ball
        sleep(500);
        //turn(M1T4,M2T4, PWRT4);
        move(PM6,PWRM6); //runs into cap ball, knocks off the ball and parks
        Vader.stop();

    }
}
