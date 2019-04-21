package org.firstinspires.ftc.teamcode.ChampsCode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="WORLD CRATER", group ="Concept")
//robot does everything but sample

public class WORLDCRATER extends RovMoving {

    int currentState = 0;
    static final int STATE_LowerLift =0;
    static final int STATE_AwayFromLander =1;
    static final int STATE_Sample =2;
    static final int STATE_Turn =3;
    static final int STATE_Yeet =4;
    static final int STATE_TeamMarker =5;
    static final int STATE_YeetBack = 6;
    static final int STATE_STOP = 100;


    @Override
    public void runOpMode() throws InterruptedException {

        robot.init(hardwareMap);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "AdafruitIMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        robot.imu = hardwareMap.get(BNO055IMU.class, "imu");
        robot.imu.initialize(parameters);


        telemetry.addData(">", "ready!!");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("robot is moving", currentState);
            telemetry.update();

            switch (currentState) {
                //land robot onto ground
                case STATE_LowerLift:
                    Landing();
                    currentState++;
                    break;

                //get into position to knock off
                case STATE_AwayFromLander:
                    rightStrafe(14);
                    drive (-0.8, 9);
                    rightStrafe(10);
                    currentState++;
                    break;

                //always knock off same one
                case STATE_Sample:
                    drive(-0.9, 14);
                    drive(0.9, 1);
                    currentState++;
                    break;

                //turn towards depot
                case STATE_Turn:
                    robot.resetGyro();

                    robot.BLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.BRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    //40 away from other turn
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

                //drives towards depot
                case STATE_Yeet:
                    drive(0.9, 20);
                    leftStrafe(10);
                    leftStrafe(10);
                    leftStrafe(10);
                    drive(0.9,2);
                    leftStrafe(10);
                    leftStrafe(10);
                    leftStrafe(10);
                    drive(0.9,2);
                    leftStrafe(15);
                    currentState++;
                    break;

                //drops off team marker
                case STATE_TeamMarker:
                    robot.TeamMaker.setPosition(0.7);
                    sleep(1000);
                    robot.TeamMaker.setPosition(0.3);
                    currentState++;
                    break;

                //turns around towards crater and drives back
                case STATE_YeetBack:
                    drive(-0.5, 3);
                    robot.resetGyro();

                    robot.BLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.BRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                    robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                    //40 away from other turn
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
                    drive(1, 60);
                    currentState =100;
                    break;

                //end by parking by crater
                case STATE_STOP:
                    robot.Lift.setPower(0);
                    drive(0,0);
                    sleep(1000);
                    break;


            }
        }
    }
}

