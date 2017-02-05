package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class AutoOuterSimple extends BaseAutoRunWithEncoders {
    public final String VERSION = "0.1";

    @Override
    public void runOpMode() throws InterruptedException {

        initAll();

        waitForStart();
        driveForward(5, 0.5);
        sleep(500);
        turnForward(0, 25, 0, 0.5);
        sleep(500);
        motor3.setPower(0.8);
        sleep(2000);
        motor3.setPower(0);
    }

}