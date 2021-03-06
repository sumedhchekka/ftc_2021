package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MecanumTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Declare our motors
        // Make sure your ID's match your configuration
        DcMotor motorFrontLeft = hardwareMap.dcMotor.get("leftfront");
        DcMotor motorBackLeft = hardwareMap.dcMotor.get("leftback");
        DcMotor motorFrontRight = hardwareMap.dcMotor.get("rightfront");
        DcMotor motorBackRight = hardwareMap.dcMotor.get("rightback");
        Servo   carouselservo = hardwareMap.get(Servo.class, "carouselservo");
        Servo   intakeservo = hardwareMap.get(Servo.class, "intakeserv");
        DcMotorEx arm = hardwareMap.get(DcMotorEx.class, "arm");

        // Reverse the right side motors
        // Reverse left motors if you are using NeveRests
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
        
        arm.setDirection(DcMotorEx.Direction.REVERSE);
        
        // Reset the encoder during initialization for arm

        waitForStart();
        
        arm.setTargetPosition(0);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, this is reversed!
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio, but only when
            // at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1.0);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            motorFrontLeft.setPower(frontLeftPower);
            motorBackLeft.setPower(backLeftPower);
            motorFrontRight.setPower(frontRightPower);
            motorBackRight.setPower(backRightPower);
            
            //Carousel
            if (gamepad1.a) { //Turn anticlockwise
                carouselservo.setPosition(1); 
            } else if (gamepad1.x) { // Stop
                carouselservo.setPosition(0.5);
            }
            else if (gamepad1.b) { //Turn Clockwise
                carouselservo.setPosition(0);
            }
            
            //Arm 

            if (gamepad2.dpad_up) { //set the arm to top level
                arm.setTargetPosition(140);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setVelocity(200);
              
            } else if (gamepad2.dpad_right) { //set the arm to the medium level
                arm.setTargetPosition(90);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setVelocity(200);            
            } else if (gamepad2.dpad_down) { //set the arm to the low level
                arm.setTargetPosition(30);
                arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                arm.setVelocity(200);            
            } else if (gamepad2.dpad_left) { //reset
              arm.setTargetPosition(0);
              arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              arm.setVelocity(100); 
            }
            
            if (Math.abs(arm.getCurrentPosition()) < 5)
            {
                arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            
            //intake
            if (gamepad2.a) { //Turn anticlockwise
                intakeservo.setPosition(1); 
            } else if (gamepad2.x) { // Stop
                intakeservo.setPosition(0.5);
            }
            else if (gamepad2.b) { //Turn Clockwise
                intakeservo.setPosition(0);
            }
            
            // Telemetry for debug
            telemetry.addData("FL wheel position:", motorFrontLeft.getCurrentPosition());
            telemetry.addData("BL wheel position:", motorBackLeft.getCurrentPosition());
            telemetry.addData("FR wheel position:", motorFrontRight.getCurrentPosition());
            telemetry.addData("BR wheel position:", motorBackRight.getCurrentPosition());
            telemetry.addData("FL wheel power:", motorFrontLeft.getPower());
            telemetry.addData("BL wheel power:", motorBackLeft.getPower());
            telemetry.addData("FR wheel power:", motorFrontRight.getPower());
            telemetry.addData("BR wheel power:", motorBackRight.getPower());
            telemetry.addData("arm position:", arm.getCurrentPosition());
            telemetry.addData("arm power:", arm.getPower());
            telemetry.update();
            
        }
    }
}