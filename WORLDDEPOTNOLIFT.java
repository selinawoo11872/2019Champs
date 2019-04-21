package org.firstinspires.ftc.teamcode.ChampsCode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="WORLD DEPOT NO LIFT", group ="Concept")
    //robot does everything but land

    public class WORLDDEPOTNOLIFT extends RovMoving {

        //the state that the robot starts in
        int currentState = 0;

        //states correspond with #
        static final int STATE_AwayFromLander =0;
        static final int STATE_ReadBlock =1;
        static final int STATE_Left =10;
        static final int STATE_Center =11;
        static final int STATE_Right =12;
        static final int STATE_Turn =20;
        static final int STATE_TeamMarker =21;
        static final int STATE_Yeet =22;
        static final int STATE_STOP = 100;


        @Override
        public void runOpMode() throws InterruptedException {

            robot.init(hardwareMap);

            //gyro stuff
            BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
            parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
            parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
            parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
            parameters.loggingEnabled = true;
            parameters.loggingTag = "IMU";
            parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

            robot.imu = hardwareMap.get(BNO055IMU.class, "imu");
            robot.imu.initialize(parameters);

            //tells when robot is ready
            telemetry.addData(">", "camera on!!");
            telemetry.update();
            waitForStart();

            while (opModeIsActive()) {
                //displays current state to the robot
                telemetry.addData("robot is moving", currentState);
                telemetry.update();

                switch (currentState) {
                    //drive away from lander
                    case STATE_AwayFromLander:
                        rightStrafe(14);
                        drive (-0.8, 9);
                        rightStrafe(10);
                        currentState++;
                        break;

                    //samples
                    case STATE_ReadBlock:
                        Samping();
                        if (i ==10)
                            currentState =10;
                        else if (i ==11)
                            currentState =11;
                        else if (i==12)
                            currentState =12;
                        break;

                    //left position
                    case STATE_Left:
                        sleep(1000);
                        drive(-0.7,25);
                        currentState =20;
                        break;

                    //center position
                    case STATE_Center:
                        leftStrafe(22);
                        drive(-0.7,30);
                        rightStrafe(10);
                        currentState = 20;
                        break;

                    //right position
                    case STATE_Right:
                        leftStrafe(40);
                        drive(-0.7,30);
                        rightStrafe(42);
                        currentState = 20;
                        break;

                    //turn away towards depot
                    case STATE_Turn:
                        robot.resetGyro();

                        robot.BLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        robot.BRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                        robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


                        while (opModeIsActive() && robot.updateGyro() < 40) {
                            robot.FLDrive.setPower(0.5);
                            robot.FRDrive.setPower(-0.5);
                            robot.BLDrive.setPower(0.5);
                            robot.BRDrive.setPower(0.5);
                        }

                        robot.FLDrive.setPower(0);
                        robot.FRDrive.setPower(0);
                        robot.BLDrive.setPower(0);
                        robot.BRDrive.setPower(0);

                        telemetry.addData("Heading = ", robot.updateGyro());
                        telemetry.update();

                        currentState++;
                        break;

                    //drops team marker
                    case STATE_TeamMarker:
                        leftStrafe(15);
                        drive(-0.7, 5);
                        robot.TeamMaker.setPosition(0.7);
                        sleep(1000);
                        robot.TeamMaker.setPosition(0.3);
                        currentState++;

                    //drives towards crater
                    case STATE_Yeet:
                        drive(0.7, 1);
                        rightStrafe(10);
                        rightStrafe(10);
                        rightStrafe(10);
                        rightStrafe(10);
                        rightStrafe(10);
                        drive(-0.7,6);
                        rightStrafe(10);
                        rightStrafe(25);
                        currentState =100;
                        break;

                    //parks at crater & stop all motion
                    case STATE_STOP:
                        robot.Lift.setPower(0);
                        drive(0,0);
                        sleep(1000);
                        break;


                }
            }
        }
    }


