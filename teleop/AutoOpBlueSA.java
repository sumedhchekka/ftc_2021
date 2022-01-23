package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoOpBlueSA extends RobotOp
{
    @Override
    public void runOpMode()
    {
        initHW();
    
        if (opModeIsActive())
        {
            autoOpFromBlueSA();
      
            while (opModeIsActive()
                    && (motorBackLeft.isBusy() 
                        || motorBackRight.isBusy()  
                        || motorFrontLeft.isBusy() 
                        || motorFrontRight.isBusy())) {}
     
      
            // set motor power back to 0 
       
        }
    }
   
    private void autoOpFromBlueSA()
    {
        //move forward
        moveForward(10,0.7,true);
        
        //strafing right
        strafeLeft(-20,0.5,true);
        
        //carousel servo start
        carouselservo.setPosition(1);
        
        sleep(2000);
        
        //carousel servo stop
        carouselservo.setPosition(0.5);
     
        //move forward
        moveForward(24,0.5,true);
        
        //strafing left
        strafeLeft(-20,0.5,true);
    }
}
       