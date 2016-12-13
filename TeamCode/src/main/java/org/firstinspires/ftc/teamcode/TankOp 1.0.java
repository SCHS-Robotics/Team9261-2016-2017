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

    @com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TankOp", group = "Iterative Opmode")
    // @Autonomous(...) is the other common choice
    public class TankOp extends OpMode {
        private DcMotor motor1;
        private DcMotor motor2;
        private DcMotor motor3;
        private DcMotor motor4;
        private Servo servo1;
        private Servo servo2;
        private Servo servo3;
        private Servo servo4;
        private Servo servo5;
        private int directionState;
        private double speed;
        private int delay;

        @Override
        public void init() {
            motor1 = hardwareMap.dcMotor.get("motor1");
            motor1.setDirection(DcMotor.Direction.FORWARD);
            motor2 = hardwareMap.dcMotor.get("motor2");
            motor2.setDirection(DcMotor.Direction.FORWARD);
            motor3 = hardwareMap.dcMotor.get("motor3");
            motor3.setDirection(DcMotor.Direction.FORWARD);
            motor4 = hardwareMap.dcMotor.get("motor4");
            motor4.setDirection(DcMotor.Direction.FORWARD);
            servo1 = hardwareMap.servo.get("servo1");
            servo2 = hardwareMap.servo.get("servo2");
            servo3 = hardwareMap.servo.get("servo3");
            servo4 = hardwareMap.servo.get("servo4");
            servo5 = hardwareMap.servo.get("servo5");
            directionState = 1;
            speed = 1;

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
                if(delay == 0) {
                    delay = 3;
                    directionState = directionState * -1;
                    if (directionState == -1) {
                        motor1 = hardwareMap.dcMotor.get("motor2");
                        motor1.setDirection(DcMotor.Direction.FORWARD);
                        motor2 = hardwareMap.dcMotor.get("motor1");
                        motor2.setDirection(DcMotor.Direction.FORWARD);
                    } else {
                        motor1 = hardwareMap.dcMotor.get("motor1");
                        motor1.setDirection(DcMotor.Direction.FORWARD);
                        motor2 = hardwareMap.dcMotor.get("motor2");
                        motor2.setDirection(DcMotor.Direction.FORWARD);
                    }
                }else{
                    delay--;
                }
            }
            if(gamepad1.left_bumper) {
                if(delay == 0) {
                    delay = 5;
                    if (speed == 1) {
                        speed = 0.5;
                    } else {
                        speed = 1;
                    }
                }
                else{
                    delay--;
                }
            }
            motor1.setPower(gamepad1.left_stick_y * speed * 0.8);
            motor2.setPower(-gamepad1.right_stick_y * speed);

            if (gamepad1.a){
                servo5.setPosition(0);
            }
            if (gamepad1.y) {
                servo5.setPosition(.5);
            }
            if(gamepad1.dpad_up) {
                servo4.setPosition(0);
            }
            if(gamepad1.dpad_down) {
                servo4.setPosition(0.5);
            }

            motor3.setPower(gamepad2.left_stick_y/2);
            motor4.setPower(gamepad2.right_stick_y);
;            if(gamepad2.x){
                servo1.setPosition(0);
            }
            if(gamepad2.b){
                servo1.setPosition(0.45);
            }
            if(gamepad2.a){
                servo2.setPosition(0.67);
            }
            if(gamepad2.y){
                servo2.setPosition(0);
            }
            if(gamepad2.dpad_up){
                servo3.setPosition(2);
            }
            if(gamepad2.dpad_down){
                servo3.setPosition(0);
            }
        }




        /*
         * Code to run ONCE after the driver hits STOP
         */
        @Override
        public void stop() {
        }

    }
