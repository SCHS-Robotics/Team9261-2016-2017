package org.firstinspires.ftc.teamcode;

import android.media.MediaPlayer;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Sage Creek Level Up on 1/14/2017.
 */
@Autonomous
public class music extends LinearOpMode {
    MediaPlayer Vader;
    @Override
    public void runOpMode() throws InterruptedException{
        Vader = MediaPlayer.create(hardwareMap.appContext, R.raw.bottheme);// this is actually right dont bother fixing
        waitForStart();
        Vader.start();
        sleep(100000);
        Vader.stop();

    }
}
