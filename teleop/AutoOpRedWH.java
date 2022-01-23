package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoOpRedWH extends RobotOp
{
    @Override
    public void runOpMode()
    {
        initHW();
    
        if (opModeIsActive())
        {
     
            autoOpFromRedWH();
      
            while (opModeIsActive()
                    && (motorBackLeft.isBusy() 
                        || motorBackRight.isBusy()  
                        || motorFrontLeft.isBusy() 
                        || motorFrontRight.isBusy())) {}
     
      
            // set motor power back to 0 
       
        }
    }
   
    private void autoOpFromRedWH()
    {
        double motor_power = 0.5;
        
        //set the arm to the medium level
        moveArm(110, 200);
      
        //moving forward
        moveForward(18, 0.7, true);
      
        //strafing right
        strafeLeft(-24, 0.5, true);
      
        while(arm.isBusy()) {}
        intakeservo.setPosition(0);
        
        sleep(3000);
       
        //stop intake
        intakeservo.setPosition(0.5);
      
        turnClockwise(90, 0.7, true);
        
        //strafing left
        strafeLeft(10,0.5, true);
      
        //moving forward
        moveForward(60,1.0, false);
    }
}
       