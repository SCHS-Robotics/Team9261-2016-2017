/**
 * Created by tyrunner on 10/19/16. What...?
 * Motor test code for Team9261
 */
    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
    import com.qualcomm.robotcore.eventloop.opmode.Disabled;
    import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
    import com.qualcomm.robotcore.hardware.ColorSensor;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.LightSensor;
    import com.qualcomm.robotcore.hardware.Servo;
    import com.qualcomm.robotcore.util.ElapsedTime;

    import java.text.SimpleDateFormat;
    import java.util.Date;

    @Autonomous
    public class AutoInnerBlue extends OpMode {

        private int currentRightPos; //Current position of the motor
        private int posCheck;
        private DcMotor motor1;
        private DcMotor motor2;
        private DcMotor motor3;
        private LightSensor rightlight;
        private LightSensor leftlight;
        private LightSensor linelight;
        private boolean initial = false;
        private Servo rightservo;
        private Servo leftservo;
        private int stage;
        private boolean varCheck;
        //important numbs: one revolution = 1160, single wheel turn = 1526
       @Override
        public void init() {
               stage = 0;
               varCheck = false;
               posCheck = 0;
               motor1 = hardwareMap.dcMotor.get("motor1");
               motor2 = hardwareMap.dcMotor.get("motor2");
               motor3 = hardwareMap.dcMotor.get("motor3");
               motor1.setDirection(DcMotor.Direction.FORWARD);
               motor2.setDirection(DcMotor.Direction.FORWARD);
               motor3.setDirection(DcMotor.Direction.FORWARD);
               rightlight = hardwareMap.lightSensor.get("rightLight");
               leftlight = hardwareMap.lightSensor.get("leftLight");
               linelight = hardwareMap.lightSensor.get("lineLight");
               rightservo = hardwareMap.servo.get("rightservo");
               leftservo = hardwareMap.servo.get("leftservo");
               currentRightPos = motor1.getCurrentPosition();
           }
        @Override
        public void init_loop(){
        }

        @Override
        public void start() {
            linelight.enableLed(true);
            stage = 1; //this way, loop won't do anything if the program didn't even start correctly
        }

        @Override
        public void loop() {
            //Help i'm trapped in a case statement factory
            switch(stage) {
                case 1:
                    leftWheelTurn(1150);
                 break;

                case 2:
                    linearMove(0,1,false);
                 break;

                case 3:
                case 10:
                    lineCheck();
                 break;

                case 4:
                    spinRight(1150);
                 break;

                case 5:
                case 12:
                    linefollow();
                 break;

                case 6:
                case 13:
                    pressBeacon();
                 break;

                case 7:
                case 14:
                    linearMove(-1160,1,true);
                 break;

                case 8:
                    spinLeft(2310);
                 break;

                case 9:
                    linearMove(-100,1,false);
                 break;

                case 11:
                    spinRight(2310);
                 break;

                case 15:
                    spinRight(3480);
                 break;

                case 16:
                    linearMove(5800,1,false);
                 break;

                case 17:
                    spinLeft(9280);
                 break;

                case 18:
                    linearMove(1160,1,true);
                 break;

            }
            currentRightPos = motor1.getCurrentPosition();

        }
        @Override
        public void stop(){
            linelight.enableLed(false);
        }

        public void linearMove(int pos, double power, boolean stopAtEnd){
            if(!varCheck) {
                posCheck = pos + currentRightPos;
                varCheck = true;
            }
            if(motor1.getCurrentPosition() <= posCheck){
                if(stopAtEnd) {
                    motor1.setPower(0);
                    motor2.setPower(0);
                }
                toNextStage();
             }else {
                motor1.setPower(power);
                motor2.setPower(-power);
            }
        }
        public void leftWheelTurn(int pos){
            if(!varCheck) {
                posCheck = pos + currentRightPos;
                varCheck = true;
            }
            if(motor2.getCurrentPosition() <= posCheck){
                motor1.setPower(0);
                motor2.setPower(0);
                toNextStage();
            }else{
                motor1.setPower(1);
                motor2.setPower(0);
            }

        }

        public void rightWheelTurn(int pos){
            if(!varCheck) {
                posCheck = pos + currentRightPos;
                varCheck = true;
            }
            if(motor1.getCurrentPosition() <= posCheck){
                motor1.setPower(0);
                motor2.setPower(0);
                toNextStage();
            }else{
                motor2.setPower(-1);
                motor1.setPower(0);
            }
        }



        public void spinLeft(int pos){
                if(!varCheck) {
                    posCheck = pos + currentRightPos;
                    varCheck = true;
                }
                if(motor1.getCurrentPosition() <= posCheck){
                    toNextStage();
                }else{
                    motor2.setPower(-1);
                    motor1.setPower(-1);
                }
        }

        public void spinRight(int pos){
            if(!varCheck) {
                posCheck = pos + currentRightPos;
                varCheck = true;
            }
            if(motor1.getCurrentPosition() >= posCheck){
                motor1.setPower(0);
                motor2.setPower(0);
                toNextStage();
            }else{
                motor1.setPower(1);
                motor2.setPower(1);
            }
        }

        public void lineCheck(){
            if(linelight.getLightDetected() >= 0.5){
                motor1.setPower(0);
                motor2.setPower(0);
                toNextStage();
            }
        }

        public void linefollow() {
            boolean seesline = (linelight.getLightDetected() >= 0.5);
            if((rightlight.getLightDetected() >= 0.25 && rightlight.getLightDetected() <= 0.35 && leftlight.getLightDetected() <= 0.18 && leftlight.getLightDetected() <= 0.28)) {
                motor1.setPower(0);
                motor2.setPower(0);
                toNextStage();
            }
            if (!seesline) {
                motor1.setPower(-0.5);
                motor2.setPower(0.1);
            } else if (seesline) {
                motor2.setPower(0.5);
                motor1.setPower(-0.1);
            } else {
                motor1.setPower(0);
                motor2.setPower(0);
            }
        }

            public void pressBeacon(){
                //check Red first, then check Blue
                if (rightlight.getLightDetected() >= 0.3 && rightlight.getLightDetected() <= 0.35) {
                    if (leftlight.getLightDetected() <= 0.23 && leftlight.getLightDetected() <= 0.28) {
                        rightservo.setPosition(0.5);
                        rightservo.setPosition(0);
                    }
                 //check Blue first, then check Red
                } else if (leftlight.getLightDetected() >= 0.23 && leftlight.getLightDetected() <= 0.28) {
                    if (rightlight.getLightDetected() >= 0.3 && rightlight.getLightDetected() <= 0.35) {
                        leftservo.setPosition(0.5);
                        leftservo.setPosition(0);
                    }
                }
                toNextStage();
            }

        private void toNextStage(){
            stage++;
            varCheck = false;
        }
    }
