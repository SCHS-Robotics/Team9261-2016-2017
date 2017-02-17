package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/**
 *
 * Created by Sage Creek Level Up on 1/18/2017.
 */
@Autonomous
public class AutoWithBTeamRed extends BaseAuto {
    @Override
    public void runOpMode() throws InterruptedException{
        initAll();
        waitForStart();
        Vader.start();
        motor3.setPower(0.8);
        sleep(1600);
        motor3.setPower(0);
        sleep(8400);
        move(DEF, PWRDEF);
        Vader.stop();
    }
}
