package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoOpBlueWH extends RobotOp
{
    @Override
    public void runOpMode()
    {
        initHW();
    
        if (opModeIsActive())
        {
     
            autoOpFromBlueWH();
      
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
        moveArm(150, 200);
      
        //moving forward
        moveForward(20, 0.7, true);
      
        //strafing right
        strafeLeft(-23, 0.5, true);
      
        while(arm.isBusy()) {}
        intakeservo.setPosition(0);
        
        sleep(3000);
        
        //stop intake
        intakeservo.setPosition(0.5);
      
        turnClockwise(-85, 0.7, true);
        
        //strafing right
        //strafeLeft(-4,0.5, true);
     
        //moving forward
        moveForward(70, 1.0, false);
    }
}
       