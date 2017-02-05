package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by Andrew on 1/17/2017.
 */
@Autonomous
public class BlueAuto extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException {
        initAll();
        waitForStart();
        Vader.start();
        motor3.setPower(0.8);
        sleep(1600);
        motor3.setPower(0);
        optical2.enableLed(true);
        move(MOVE1B, MOVE1SPD);
        sleep(500);
        turn(TURN1B, TURN1B2, TURN1SPD);
        sleep(500);
        move(MOVE2B, MOVE2SPD);
        sleep(500);
        while(optical2.getLightDetected() < 0.012 && opModeIsActive()) { // remember to change to optical2 when is mounted
            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor1.setPower(-0.2);
            motor2.setPower(-0.2);
        }
        motor1.setPower(0);
        motor2.setPower(0);
        motor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sleep(500);
        turn(TURN2B, TURN2B2, TURN2SPD);
        sleep(500);
        leftLinePowerFWD = 0.2;
        RightLinePowerFWD = 0.2;
        while(!seesLineFWD() && opModeIsActive());
        sleep(500);
        pushBeaconBlue();
        sleep(500);
        move(MOVE3B, MOVE3SPD);
        sleep(500);
        leftLinePowerBCK = -0.2;
        RightLinePowerBCK = -0.2;
        while (!seesLineBCK() && opModeIsActive());
        sleep(500);
        pushBeaconBlue();
        sleep(500);
        move(MOVE3B, MOVE3SPD);
        sleep(500);
        turn(TURN3B, TURN3B2, TURN3SPD);
        sleep(500);
        move(MOVE4B, MOVE4SPD);
        Vader.stop();
    }
}