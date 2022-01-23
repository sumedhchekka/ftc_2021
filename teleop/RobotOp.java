package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public abstract class RobotOp  extends LinearOpMode {
    
    public DcMotor motorFrontLeft;
    public DcMotor motorBackLeft;
    public DcMotor motorFrontRight;
    public DcMotor motorBackRight;
    public Servo   carouselservo;
    public Servo   intakeservo;
    public DcMotorEx arm;
  
    //Convert from the counts per revolution of the encoder to counts per inch
    static final double HD_COUNTS_PER_REV = 28;
    static final double DRIVE_GEAR_REDUCTION = 20.15293;
    static final double WHEEL_CIRCUMFERENCE_MM = 100 * Math.PI;
    static final double DRIVE_COUNTS_PER_MM = (HD_COUNTS_PER_REV * DRIVE_GEAR_REDUCTION) / WHEEL_CIRCUMFERENCE_MM;
    static final double DRIVE_COUNTS_PER_IN = DRIVE_COUNTS_PER_MM * 25.4;
    static final double DRIVE_COUNTS_PER_ANGLE = 10.0; // Mesure and retune ?
    
    public void initHW() {
    
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
    }
   
    public void moveDrivetrain(int rightfrontChange, int leftfrontChange,
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
                    || motorFrontRight.isBusy()))
        {
            // Telemetry for debug
            telemetry.addData("FL wheel position:", motorFrontLeft.getCurrentPosition());
            telemetry.addData("BL wheel position:", motorBackLeft.getCurrentPosition());
            telemetry.addData("FR wheel position:", motorFrontRight.getCurrentPosition());
            telemetry.addData("BR wheel position:", motorBackRight.getCurrentPosition());
            telemetry.addData("FL wheel power:", motorFrontLeft.getPower());
            telemetry.addData("BL wheel power:", motorBackLeft.getPower());
            telemetry.addData("FR wheel power:", motorFrontRight.getPower());
            telemetry.addData("BR wheel power:", motorBackRight.getPower());
            //telemetry.addData("arm position:", arm.getCurrentPosition());
            //telemetry.addData("arm power:", arm.getPower());
            telemetry.update();
        }
    }
   
    public void moveForward(int distance, double power, boolean waitForMotorIdle)
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
   
    public void turnClockwise(int angle, double power, boolean waitForMotorIdle)
    {
        // Calculate position changes for each motor
        int change = (int)(angle * DRIVE_COUNTS_PER_ANGLE);
        int rightfrontChange = -change;
        int leftfrontChange = change;
        int rightbackChange = -change;
        int leftbackChange = change;
     
        moveDrivetrain(rightfrontChange, leftfrontChange,
                        rightbackChange, leftbackChange,
                        power, waitForMotorIdle);
    }
   
    public void strafeLeft(int shift, double power, boolean waitForMotorIdle)
    {
        // Calculate position changes for each motor
        int change = (int)(shift * DRIVE_COUNTS_PER_IN);
        int rightfrontChange = change;
        int leftfrontChange = -change;
        int rightbackChange = -change;
        int leftbackChange = change;
     
        moveDrivetrain(rightfrontChange, leftfrontChange,
                        rightbackChange, leftbackChange,
                        power, waitForMotorIdle);
   }
   public void moveArm(int position, int velocity)
   {
        arm.setTargetPosition(position);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setVelocity(velocity);
   }
}