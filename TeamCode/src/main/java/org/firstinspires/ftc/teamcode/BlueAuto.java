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
        move(MOVE1B, MOVE1SPD);
        turn(TURN1B, TURN1B2, TURN1SPD);
        move(MOVE2B, MOVE2SPD);
        move(MOVE3B, MOVE3SPD);
        while(optical1.getLightDetected() < 0.06 && opModeIsActive()) {
            motor1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor1.setPower(-0.2);
            motor2.setPower(-0.2);
        }
        turn(TURN2B, TURN2B2, TURN2SPD);
        while(!seesLineBCK() && opModeIsActive());
        pushBeaconBlue();
        move(MOVE4B, MOVE4SPD);
        while (!seesLineFWD() && opModeIsActive());
        pushBeaconBlue();
        move(MOVE4B, MOVE4SPD);
        turn(TURN3B, TURN3B2, TURN3SPD);
        move(MOVE1B, MOVE1SPD);
        turn(TURN4B, TURN4B2, TURN4SPD);
        move(MOVE4B, MOVE4SPD);
        Vader.stop();

    }
}
