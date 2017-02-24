/**
 * Created by tyrunner on 10/19/16. What...?
 * Motor test code for Team9261
 */

package org.firstinspires.ftc.teamcode;
// @Autonomous(...) is the other common choice

import android.media.MediaPlayer;

    import com.qualcomm.robotcore.eventloop.opmode.Disabled;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.CRServo;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.util.ElapsedTime;

    import java.text.SimpleDateFormat;
    import java.util.Date;

    import static android.os.SystemClock.sleep;
@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TankOp", group = "Iterative Opmode")
public class TankOp extends OpMode {
        private DcMotor motor1;
        private DcMotor motor2;
        private DcMotor motor3;
        private DcMotor motor4;
        private DcMotor motor5;
        private DcMotor motor6;
    /*
        private DcMotor motor7;
        private DcMotor motor8;
       */
        private Servo servo1;
        private Servo servo2;
        private Servo wheel1;
        private Servo wheel2;
        private int directionState;
        private double speed;
        private int directionDelay;
        private int speedDelay;
        MediaPlayer airhorn;
        MediaPlayer alone;

        @Override
        public void init() {
            servo1 = hardwareMap.servo.get("servo1");
            servo2 = hardwareMap.servo.get("servo2");
            wheel1 = hardwareMap.servo.get("wheel1");
            wheel2 = hardwareMap.servo.get("wheel2");

            servo1.setPosition(1);
            servo2.setPosition(0);
            wheel1.setPosition(0.4);
            wheel2.setPosition(1);

            motor1 = hardwareMap.dcMotor.get("motor2");
            motor1.setDirection(DcMotor.Direction.FORWARD);
            motor2 = hardwareMap.dcMotor.get("motor1");
            motor2.setDirection(DcMotor.Direction.FORWARD);
            motor3 = hardwareMap.dcMotor.get("motor3");
            motor3.setDirection(DcMotor.Direction.FORWARD);
            motor3.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor4 = hardwareMap.dcMotor.get("motor4");
            motor4.setDirection(DcMotor.Direction.FORWARD);
            motor5 = hardwareMap.dcMotor.get("motor5");
            motor5.setDirection(DcMotor.Direction.FORWARD);
            motor6 = hardwareMap.dcMotor.get("motor6");
            motor6.setDirection(DcMotor.Direction.FORWARD);
            /*
            motor7 = hardwareMap.dcMotor.get("motor7");
            motor7.setDirection(DcMotor.Direction.FORWARD);
            motor8 = hardwareMap.dcMotor.get("motor8");
            motor8.setDirection(DcMotor.Direction.FORWARD);
            */


            //airhorn = MediaPlayer.create(hardwareMap.appContext, R.raw.airhorn);
            //alone = MediaPlayer.create(hardwareMap.appContext, R.raw.alone);

            directionState = -1;
            speed = 1;
            directionDelay = 0;
            speedDelay = 0;
        }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
        @Override
        public void init_loop() {}

        /*
         * Code     to run ONCE when the driver hits PLAY
         */
        @Override
        public void start() {}

        /*
         * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
         */
        @Override
        public void loop() {
            if (gamepad1.left_bumper) {
                if(directionDelay == 0) {
                    directionDelay = 10;
                    directionState = -directionState;
                    setMotors();
                }
            }else{
                directionDelay = 0;
            }
            if(gamepad1.right_bumper) {










                if(speedDelay == 0) {
                    speedDelay = 10;
                    if (speed == 1) {
                        speed = 0.5;
                    } else {
                        speed = 1;
                    }
                }
            }else{
                speedDelay = 0;
            }
            /*
            if(gamepad1.x || gamepad2.x){
                airhorn.start();
            }
            if(gamepad1.a || gamepad2.a){
                alone.start();
            }
            */
            motor5.setPower(-gamepad2.right_trigger);
            motor4.setPower(-gamepad2.right_stick_y);
            motor3.setPower(-gamepad2.left_stick_y * 0.8);
            motor1.setPower(-gamepad1.left_stick_y * speed);
            motor2.setPower(gamepad1.right_stick_y * speed);
            motor6.setPower(-gamepad2.left_trigger);
            /*
            motor7.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
            motor8.setPower(gamepad1.left_trigger - gamepad1.right_trigger);
            */
        }

        /*
         * Code to run ONCE after the driver hits STOP
         */
        @Override
        public void stop() {}

        private void setMotors() {
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
        }
    }
