package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class AutonomousOpMode extends LinearOpMode {

  private DcMotor motorFrontLeft;
  private DcMotor motorBackLeft;
  private DcMotor motorFrontRight;
  private DcMotor motorBackRight;
  private Servo   carouselservo;
  private Servo   intakeservo;
  private DcMotorEx arm;
  
  //Convert from the counts per revolution of the encoder to counts per inch
  static final double HD_COUNTS_PER_REV = 28;
  static final double DRIVE_GEAR_REDUCTION = 20.15293;
  static final double WHEEL_CIRCUMFERENCE_MM = 100 * Math.PI;
  static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
  static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;
    
  @Override
  public void runOpMode() {
    
    motorFrontLeft = hardwareMap.dcMotor.get("leftfront");
    motorBackLeft = hardwareMap.dcMotor.get("leftback");
    motorFrontRight = hardwareMap.dcMotor.get("rightfront");
    motorBackRight = hardwareMap.dcMotor.get("rightback");
    carouselservo = hardwareMap.get(Servo.class, "carouselservo");
    intakeservo = hardwareMap.get(Servo.class, "intakeserv");
    arm = hardwareMap.get(DcMotorEx.class, "arm");

   // reverse left drive motor direciton
    motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);    
    
    
    waitForStart();
    if (opModeIsActive()) {
      
      
      
      // Create target positions
      int rightfrontTarget = motorFrontRight.getCurrentPosition() + (int)(30 * DRIVE_COUNTS_PER_IN);
      int leftfrontTarget = motorFrontLeft.getCurrentPosition() + (int)(30 * DRIVE_COUNTS_PER_IN);
      int rightbackTarget = motorBackRight.getCurrentPosition() + (int)(30 * DRIVE_COUNTS_PER_IN);
      int leftbackTarget = motorBackLeft.getCurrentPosition() + (int)(30 * DRIVE_COUNTS_PER_IN);
     
      //set the arm to the medium level
      arm.setDirection(DcMotorEx.Direction.REVERSE);
      arm.setMode(DcMotor.RunMode.RESET_ENCODERS);
      arm.setTargetPosition(90);
      arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      arm.setVelocity(200);    
      
      //while(arm.isBusy()){
        
      //}
      //intake //Turn clockwise
     //intakeservo.setPosition(1);
      
      // set target position
      motorBackLeft.setTargetPosition(leftbackTarget);
      motorBackRight.setTargetPosition(rightbackTarget);
      motorFrontLeft.setTargetPosition(leftfrontTarget);
      motorFrontRight.setTargetPosition(rightfrontTarget);
      
      //switch to run to position mode
      motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      
      //run to position at the desiginated power
      motorBackLeft.setPower(0.7);
      motorBackRight.setPower(0.7);
      motorFrontLeft.setPower(0.7);
      motorFrontRight.setPower(0.7);
    
      while(arm.isBusy()) {}
      intakeservo.setPosition(0);
      
      // wait until both motors are no longer busy running to position
      while (opModeIsActive() && (motorBackLeft.isBusy() || motorBackRight.isBusy())) {
      }
      
      // set motor power back to 0 
       
      } 
      
      motorBackLeft.setPower(0);
      motorBackRight.setPower(0);
      motorFrontLeft.setPower(0);
      motorFrontRight.setPower(0);
     }
 }
