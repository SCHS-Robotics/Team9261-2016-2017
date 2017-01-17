package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by marco on 1/12/2017.
 */
@com.qualcomm.robotcore.eventloop.opmode.Autonomous
public class RunToAutoTest extends LinearOpMode {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    public void runOpMode() {
        leftMotor = hardwareMap.dcMotor.get("motor2");
        rightMotor = hardwareMap.dcMotor.get("motor1");

        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);

        while (leftMotor.getCurrentPosition() != 0 && opModeIsActive()) {
            leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        while (rightMotor.getCurrentPosition() != 0 && opModeIsActive()) {
            rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        waitForStart();
        moveForward(3000, .5);
        sleep(2500);
        move(-1500, 1500, -.5, .5);
        sleep(2500);
    }

    public void moveForward(int pos, double power) {
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + pos);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + pos);

        leftMotor.setPower(power);
        rightMotor.setPower(power);

        while (leftMotor.isBusy() || rightMotor.isBusy() && opModeIsActive()) {
            telemetry.addData("leftMotorPos", leftMotor.getCurrentPosition());
            telemetry.addData("rightMotorPos", rightMotor.getCurrentPosition());
            telemetry.update();
        }
        leftMotor.setPower(0);
        rightMotor.setPower(0);

    }

    public void move(int pos1, int pos2, double power1, double power2) {
        leftMotor.setTargetPosition(leftMotor.getCurrentPosition() + pos1);
        rightMotor.setTargetPosition(rightMotor.getCurrentPosition() + pos2);

        leftMotor.setPower(power1);
        rightMotor.setPower(power2);

        while (leftMotor.isBusy() || rightMotor.isBusy() && opModeIsActive()) {
            telemetry.addData("leftMotorPos", leftMotor.getCurrentPosition());
            telemetry.addData("rightMotorPos", rightMotor.getCurrentPosition());
            telemetry.update();

            if (!leftMotor.isBusy()) {
                leftMotor.setPower(0);
            }
            if (!rightMotor.isBusy()) {
                rightMotor.setPower(0);
            }
        }
        //safety stop
        leftMotor.setPower(0);
        rightMotor.setPower(0);

    }
}
