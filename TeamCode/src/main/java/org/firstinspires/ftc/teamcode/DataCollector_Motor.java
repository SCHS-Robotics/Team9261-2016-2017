package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "DataCollector_Motor", group = "Iterative Opmode")
public class DataCollector_Motor extends OpMode {

    public final String VERSION = "0.1";

    //One rotation = 560; 90 degree single wheel rotation = 890; 1 tile = 373

    private DcMotor left_motor;
    private DcMotor right_motor;

    public void init() {

        left_motor = hardwareMap.dcMotor.get("left_motor");
        right_motor = hardwareMap.dcMotor.get("right_motor");

        left_motor.setDirection(DcMotor.Direction.FORWARD);
        right_motor.setDirection(DcMotor.Direction.REVERSE);

    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        telemetry.addData("left motor pos", left_motor.getCurrentPosition());
        telemetry.addData("right motor pos", right_motor.getCurrentPosition());

    }

    public void stop() {

    }
}