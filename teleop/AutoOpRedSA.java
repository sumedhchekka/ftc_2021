package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoOpRedSA extends RobotOp
{
    @Override
    public void runOpMode()
    {
        initHW();
    
        if (opModeIsActive())
        {
     
            autoOpFromRedSA();
      
            while (opModeIsActive()
                    && (motorBackLeft.isBusy() 
                        || motorBackRight.isBusy()  
                        || motorFrontLeft.isBusy() 
                        || motorFrontRight.isBusy())) {}
     
      
            // set motor power back to 0 
       
        }
    }
    
//place robot arm facing forward

    private void autoOpFromRedSA()
    {
       //move forward
       moveForward(5,0.3,true);
       
       strafeLeft(25,0.3, true);
       
       //carousel servo start
        carouselservo.setPosition(1);
        
        sleep(5600);
        
        //carousel servo stop
        carouselservo.setPosition(0.5);
        
        //move forward
        moveForward(20,0.3,true);
        
        //strafe left
         strafeLeft(4,0.3, true);

    }
}
       