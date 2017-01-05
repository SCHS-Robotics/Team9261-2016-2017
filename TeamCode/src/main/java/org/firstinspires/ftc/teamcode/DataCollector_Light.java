package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.LightSensor;

public class DataCollector_Light extends OpMode {

    public final String VERSION = "0.1";

    private ColorSensor left_color;
    private ColorSensor right_color;

    public void init() {

        left_color = hardwareMap.colorSensor.get("left_color");
        right_color = hardwareMap.colorSensor.get("right_color");
    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {

        telemetry.addData("left color red", left_color.red());
        telemetry.addData("left color blue", left_color.blue());
        telemetry.addData("right color red", right_color.red());
        telemetry.addData("right color blue", right_color.blue());

    }

    public void stop() {

    }
}