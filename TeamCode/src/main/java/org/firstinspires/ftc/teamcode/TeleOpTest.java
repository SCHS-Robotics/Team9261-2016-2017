/**
 * Created by tyrunner on 10/19/16. What...?
 * Motor test code for Team9261
 */
    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.robotcore.eventloop.opmode.Disabled;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.util.ElapsedTime;

    import java.text.SimpleDateFormat;
    import java.util.Date;

    @com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp", group = "Iterative Opmode")
    // @Autonomous(...) is the other common choice
    public class TeleOpTest extends OpMode {
        private DcMotor motor1;
        private DcMotor motor2;
        private Servo servo1;
        private int directionState;
        private double speed;

        @Override
        public void init() {
            motor1 = hardwareMap.dcMotor.get("motor1");
            motor1.setDirection(DcMotor.Direction.FORWARD);
            motor2 = hardwareMap.dcMotor.get("motor2");
            motor2.setDirection(DcMotor.Direction.FORWARD);
            servo1 = hardwareMap.servo.get("servo1");
            directionState = 1;
            speed = 1.25;

        }/*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */

        @Override
        public void init_loop() {

        }

        /*
         * Code to run ONCE when the driver hits PLAY
         */
        @Override
        public void start() {

        }

        /*
         * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
         */
        @Override
        public void loop() {
            if (gamepad1.right_bumper) {
                directionState = directionState * -1;
            }
            if(gamepad1.left_bumper) {
                if(speed == 1.25) {
                    speed = 1.5;
                }else {
                    speed = 1.25;
                }
            }
            motor1.setPower(gamepad1.left_stick_y / speed * directionState);
            //telemetry.addData("encoder",motor1.getCurrentPosition());
            motor2.setPower(-gamepad1.right_stick_y / speed * directionState);

            if (gamepad1.a) {
                servo1.setPosition(0);
            }
            if (gamepad1.b) {
                servo1.setPosition(1);
            }
        }




        /*
         * Code to run ONCE after the driver hits STOP
         */
        @Override
        public void stop() {
        }

    }
