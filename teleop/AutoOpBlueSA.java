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
    
   //place robot arm facing away from the carousel
   
    private void autoOpFromBlueSA()
    {
       //move backward
       moveForward(-17,0.3,true);
       
       //carousel servo start
        carouselservo.setPosition(-1);
        
        sleep(6000);
        
        //carousel servo stop
        carouselservo.setPosition(0.5);
        
        strafeLeft(-29, 0.3, true);
        
        moveForward(-6,0.2,true);
    }
}
       