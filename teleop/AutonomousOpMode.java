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
  static final double DRIVE_COUNTS_PER_ANGLE = 10.0; // Mesure and retune ?
    
  @Override
  public void runOpMode() {
    
    motorFrontLeft = hardwareMap.dcMotor.get("leftfront");
    motorBackLeft = hardwareMap.dcMotor.get("leftback");
    motorFrontRight = hardwareMap.dcMotor.get("rightfront");
    motorBackRight = hardwareMap.dcMotor.get("rightback");
    carouselservo = hardwareMap.get(Servo.class, "carouselservo");
    intakeservo = hardwareMap.get(Servo.class, "intakeserv");
    arm = hardwareMap.get(DcMotorEx.class, "arm");

    // setting forward
    motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
    motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
    motorFrontLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    motorBackLeft.setDirection(DcMotorSimple.Direction.FORWARD);
    
    // arm setup
    arm.setDirection(DcMotorEx.Direction.REVERSE);
    arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    
    waitForStart();
    if (opModeIsActive()) {
     
      autoOpFromBlueWH();
      //autoOpFromBlueSA();
      //autoOpFromRedWH();
      //autoOpFromRedSA();
      
      while (opModeIsActive()
            && (motorBackLeft.isBusy() 
                  || motorBackRight.isBusy()  
                  || motorFrontLeft.isBusy() 
                  || motorFrontRight.isBusy())) {}
     
      
      // set motor power back to 0 
       
     }
   }
   
   private void autoOpFromBlueWH()
   {
     //set the arm to the medium level
      arm.setTargetPosition(110);
      arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      arm.setVelocity(200);
      
       //moving forward
      moveForward(17, 0.5, true);
      
      //strafing right
      strafeLeft(-24, 0.5, true);
      
      while(arm.isBusy()) {}
      intakeservo.setPosition(0);
       
      // start intake
      intakeservo.setPosition(0.5);
      
      // reset arm
      arm.setTargetPosition(0);
      arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      arm.setVelocity(100);
      
      turnClockwise(90, 0.5, true);
      
      //moving forward
      moveForward(48, 0.5, false);
   }
   
   private void autoOpFromBlueSA()
   {
       //carousel servo start
     carouselservo.setPosition(1);
     
     //move backward
     moveForward(-18,0.7,true);
     
     //carousel servo stop
     carouselservo.setPosition(0.5);
     
     sleep(500);
     
     
     //strafe right
     strafeLeft(-24,0.5,false);
     
       
     
   }
   
   private void autoOpFromRedWH()
   {
      //set the arm to the medium level
      arm.setTargetPosition(110);
      arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      arm.setVelocity(200);
      
      //moving forward
      moveForward(17, 0.5, true);
      
      //strafing left
      strafeLeft(24, 0.5, true);
      
      while(arm.isBusy()) {}
      intakeservo.setPosition(0);
       
      //stop intake
      intakeservo.setPosition(0.5);
      
      // reset arm
      arm.setTargetPosition(0);
      arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      arm.setVelocity(100);
      
      turnClockwise(90, 0.5, true);
      
      //moving forward
      moveForward(48, 0.5, false); 
     
   }
   
   private void autoOpFromRedSA()
   {
     //carousel servo start
     carouselservo.setPosition(1);
     
     //move backward
     moveForward(-18,0.7,true);
     
     //carousel servo stop
     carouselservo.setPosition(0.5);
     
     sleep(500);
     
     
     //strafe right
     strafeLeft(-24,0.5,false);

     
     
     
     
     
     
   }
   
   private void moveDrivetrain(int rightfrontChange, int leftfrontChange,
                               int rightbackChange, int leftbackChange,
                               double power, boolean waitForMotorIdle)
  {
      // Create target positions
      int rightfrontTarget = motorFrontRight.getCurrentPosition() + rightfrontChange;
      int leftfrontTarget = motorFrontLeft.getCurrentPosition() + leftfrontChange;
      int rightbackTarget = motorBackRight.getCurrentPosition() + rightbackChange;
      int leftbackTarget = motorBackLeft.getCurrentPosition() + leftbackChange;
     
      //switch to run to position mode
      motorBackLeft.setTargetPosition(leftbackTarget);
      motorBackRight.setTargetPosition(rightbackTarget);
      motorFrontLeft.setTargetPosition(leftfrontTarget);
      motorFrontRight.setTargetPosition(rightfrontTarget);
      
      //run to position at the desiginated power
      
      motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
      
      motorBackLeft.setPower(power);
      motorBackRight.setPower(power);
      motorFrontLeft.setPower(power);
      motorFrontRight.setPower(power);
      
      while (opModeIsActive()
            && waitForMotorIdle
            && (motorBackLeft.isBusy() 
                  || motorBackRight.isBusy()  
                  || motorFrontLeft.isBusy() 
                  || motorFrontRight.isBusy())) {}
   }
   
   private void moveForward(int distance, double power, boolean waitForMotorIdle)
   {
      // Calculate position changes for each motor
      int change = (int)(distance * DRIVE_COUNTS_PER_IN);
      int rightfrontChange = change;
      int leftfrontChange = change;
      int rightbackChange = change;
      int leftbackChange = change;
     
      moveDrivetrain(rightfrontChange, leftfrontChange,
                     rightbackChange, leftbackChange,
                     power, waitForMotorIdle);
   }
   
   private void turnClockwise(int angle, double power, boolean waitForMotorIdle)
   {
      // Calculate position changes for each motor
      int change = (int)(angle * DRIVE_COUNTS_PER_ANGLE);
      int rightfrontChange = - change;
      int leftfrontChange = change;
      int rightbackChange = - change;
      int leftbackChange = change;
     
      moveDrivetrain(rightfrontChange, leftfrontChange,
                     rightbackChange, leftbackChange,
                     power, waitForMotorIdle);
   }
   
   private void strafeLeft(int shift, double power, boolean waitForMotorIdle)
   {
      // Calculate position changes for each motor
      int change = (int)(shift * DRIVE_COUNTS_PER_IN);
      int rightfrontChange = change;
      int leftfrontChange = - change;
      int rightbackChange = - change;
      int leftbackChange = change;
     
      moveDrivetrain(rightfrontChange, leftfrontChange,
                     rightbackChange, leftbackChange,
                     power, waitForMotorIdle);
   }
 }
