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
        moveArm(150, 200);
      
        //moving forward
        moveForward(19, 0.7, true);
      
        //strafing left
        strafeLeft(23, 0.5, true);
      
        while(arm.isBusy()) {}
        intakeservo.setPosition(0);
        
        sleep(3000);
       
        //stop intake
        intakeservo.setPosition(0.5);
      
        turnClockwise(85, 0.7, true);
        
        //strafing left
        strafeLeft(5,0.5, true);
      
        //moving forward
        moveForward(70,1.0, false);
    }
}
       