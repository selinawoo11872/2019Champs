package org.firstinspires.ftc.teamcode.ChampsCode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="WORLD CRATER NO LIFT", group ="Concept")
//robot starts on ground
//will complete team marker and park back at crater

public class WORLDCRATERNOLIFT extends RovMoving {

    //each state is assigned a number
    //at the end of the state it calls the next number
    int currentState = 0;
    static final int STATE_AwayFromLander =0;
    static final int STATE_Sample =1;
    static final int STATE_Turn =2;
    static final int STATE_Yeet =3;
    static final int STATE_TeamMarker =4;
    static final int STATE_YeetBack = 5;
    static final int STATE_STOP = 100;


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        //all the gyro stuff
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        robot.imu = hardwareMap.get(BNO055IMU.class, "imu");
        robot.imu.initialize(parameters);


        //displays "ready" for the drivers
        telemetry.addData(">", "ready!!");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            //displays current state of the robot so easy to debug
            telemetry.addData("robot is moving", currentState);
            telemetry.update();

            switch (currentState) {
                //get away from lander
                case STATE_AwayFromLander:
                    drive (-0.9, 9);
                    rightStrafe(15);
                    rightStrafe(10);
                    currentState++;
                    break;

                //will hit off the same one everytime
                //doesn't actually use camera to sample
                case STATE_Sample:
                    drive(-0.9, 10);
                    drive(0.9, 1);
                    currentState++;
                    break;

                //turns towards depot
                case STATE_Turn:
                    drive(0.9, 5);
                    robot.resetGyro();

                    robot.BLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.BRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


                    while (opModeIsActive() && robot.updateGyro() > -120) {
                        robot.FLDrive.setPower(-0.7);
                        robot.FRDrive.setPower(0.7);
                        robot.BLDrive.setPower(-0.7);
                        robot.BRDrive.setPower(-0.7);
                    }

                    robot.FLDrive.setPower(0);
                    robot.FRDrive.setPower(0);
                    robot.BLDrive.setPower(0);
                    robot.BRDrive.setPower(0);

                    telemetry.addData("Heading = ", robot.updateGyro());
                    telemetry.update();

                    currentState++;
                    break;

                //make way down to depot
                case STATE_Yeet:
                    drive(0.9, 26);
                    leftStrafe(10);
                    leftStrafe(10);
                    leftStrafe(10);
                    drive(0.9,2);
                    leftStrafe(10);
                    leftStrafe(10);
                    leftStrafe(10);
                    drive(0.9,4);
                    leftStrafe(15);
                    currentState++;
                    break;

                //drops team marker
                case STATE_TeamMarker:
                    robot.TeamMaker.setPosition(0.7);
                    sleep(1000);
                    robot.TeamMaker.setPosition(0.3);
                    currentState++;
                    break;

                //turns back to crater and drives back
                case STATE_YeetBack:
                    drive(-0.5, 3);
                    robot.resetGyro();

                    robot.BLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.BRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


                    while (opModeIsActive() && robot.updateGyro() < 120) {
                        robot.FLDrive.setPower(0.8);
                        robot.FRDrive.setPower(-0.8);
                        robot.BLDrive.setPower(0.8);
                        robot.BRDrive.setPower(0.8);
                    }

                    robot.FLDrive.setPower(0);
                    robot.FRDrive.setPower(0);
                    robot.BLDrive.setPower(0);
                    robot.BRDrive.setPower(0);

                    telemetry.addData("Heading = ", robot.updateGyro());
                    telemetry.update();
                    drive(1, 70);
                    currentState =100;
                    break;

                //stop and end program by parking
                case STATE_STOP:
                    robot.Lift.setPower(0);
                    drive(0,0);
                    sleep(1000);
                    break;


            }
        }
    }
}

