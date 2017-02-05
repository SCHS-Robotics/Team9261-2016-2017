package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.I2cAddr;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "DataCollector_Light", group = "Iterative Opmode")
public class DataCollector_Light extends OpMode {

    public final String VERSION = "0.2";

    private ColorSensor line1;
    private ColorSensor line2;

    private ColorSensor color1;
    private ColorSensor color2;

    private OpticalDistanceSensor optical1;
    private OpticalDistanceSensor optical2;

    public void init() {

        line1 = hardwareMap.colorSensor.get("line1");
        line2 = hardwareMap.colorSensor.get("line2");

        color1 = hardwareMap.colorSensor.get("color1");
        color2 = hardwareMap.colorSensor.get("color2");

        optical1 = hardwareMap.opticalDistanceSensor.get("optical1");
        optical2 = hardwareMap.opticalDistanceSensor.get("optical2");



        line1.setI2cAddress(I2cAddr.create8bit(0x4c));
        line2.setI2cAddress(I2cAddr.create8bit(0x5c));

        color1.setI2cAddress(I2cAddr.create8bit(0x6c));
        color2.setI2cAddress(I2cAddr.create8bit(0x7c));

    }

    public void init_loop() {

    }

    public void start() {

    }

    public void loop() {
        color1.enableLed(false);
        color2.enableLed(false);
        optical1.enableLed(true);
        optical2.enableLed(true);

        telemetry.addData("left line light", line1.alpha());
        telemetry.addData("right line light", line2.alpha());

        telemetry.addData("left color red", color1.red());
        telemetry.addData("left color blue", color1.blue());
        telemetry.addData("right color red", color2.red());
        telemetry.addData("right color blue", color2.blue());

        telemetry.addData("optical distance", optical1.getLightDetected());
        telemetry.addData("optical distance blue", optical2.getLightDetected());


    }
//3.81
    public void stop() {

    }
}