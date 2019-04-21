package org.firstinspires.ftc.teamcode.ChampsCode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class RovMoving extends LinearOpMode {

    //calls hardware class to get access to all the hardware
    RovHardwareMap robot = new RovHardwareMap(hardwareMap, telemetry);

    //variables for the methods below
    static final double COUNTS_PER_INCH = 83;
    int i =1;
    private ElapsedTime runtime = new ElapsedTime();

    //gyro stuff
    Orientation angles;
    Acceleration gravity;

    //declarations for vuforia
    public static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    public static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    public static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AYuZ93L/////AAAAGaeDP2whGUB1mYRTf+7K5mkKOZ7JZkCmhJ/CVpiQ/m54+K2cLO9SqE3Adltxdu8ELQKwL8Gb2Qwu7pRSCC+ISjxyVjSiPDinmLstY95mk4nW/MMN+MHMjidPy33Wxr+IDrKRC+wl7lRkhoEs23GGU6TYx4oXZTBuoPFctLS0WNrlE9bfFIBLPv5BiIcYZAPl6zfScG2zgeWyFh7sR2g0wsulpP+La9xdhl301rnQ4k+HvurA1qmEBCeag/r98/lUs7D7W7952MECHmbQWStvFyX4GS9RipvxQ4zhMTyQM1lPoG3hsN6T3PvTAH+YxRobl28QR5jp/ScGr+w8zp4p2kM1o8LmgDTzhIsG3CTyGNNr";
    public VuforiaLocalizer vuforia;
    public TFObjectDetector tfod;


    //right strafe method (just input distance)
    public void rightStrafe(double inches) {
        int Left;
        int Right;
        int sign = (int) (0.45 / (Math.abs(0.45)));

        if (opModeIsActive()) {
            // only works going forwards (need to invert count based on motor speed)
            Left = robot.BLDrive.getCurrentPosition() + ((int) (inches * COUNTS_PER_INCH * sign));
            Right = robot.BRDrive.getCurrentPosition() + ((int) (inches * COUNTS_PER_INCH * sign));
            robot.BLDrive.setTargetPosition(Left);
            robot.BRDrive.setTargetPosition(Right);


            // Turn On RUN_TO_POSITION
            robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.BLDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BRDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.BRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.FLDrive.setPower(-0.45);
            robot.FRDrive.setPower(0.45);
            robot.BLDrive.setPower(-0.6);
            robot.BRDrive.setPower(0.6);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && robot.BLDrive.isBusy() && robot.BRDrive.isBusy()) {

                // Display it for the driver.
                //telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget);
                telemetry.addData("Path2", "Running at %7d :%7d");

                int position = robot.FLDrive.getCurrentPosition();
                telemetry.addData("FL", position);

                int position1 = robot.FRDrive.getCurrentPosition();
                telemetry.addData("FR", position1);

                int position2 = robot.BLDrive.getCurrentPosition();
                telemetry.addData("BL", position2);

                int position3 = robot.BRDrive.getCurrentPosition();
                telemetry.addData("BR", position3);

                telemetry.addData("Target Val", Left);

                telemetry.addData("BackLeft Status", robot.BLDrive.isBusy());

                telemetry.update();

                // Allow time for other processes to run.
                idle();

            }

            // Stop all motion;
            robot.FLDrive.setPower(0);
            robot.FRDrive.setPower(0);
            robot.BLDrive.setPower(0);
            robot.BRDrive.setPower(0);
        }
    }

    //left strafe method (input distance)
    public void leftStrafe(double inches) {
        int Left;
        int Right;
        int sign = (int) (-0.45 / (Math.abs(0.45)));

        if (opModeIsActive()) {
            // only works going forwards (need to invert count based on motor speed)
            Left = robot.BLDrive.getCurrentPosition() + ((int) (inches * COUNTS_PER_INCH * sign));
            Right = robot.BRDrive.getCurrentPosition() + ((int) (inches * COUNTS_PER_INCH * sign));
            robot.BLDrive.setTargetPosition(Left);
            robot.BRDrive.setTargetPosition(Right);


            // Turn On RUN_TO_POSITION
            robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.BLDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BRDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.BRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.FLDrive.setPower(0.45);
            robot.FRDrive.setPower(-0.45);
            robot.BLDrive.setPower(0.6);
            robot.BRDrive.setPower(-0.6);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && robot.BLDrive.isBusy() && robot.BRDrive.isBusy()) {

                // Display it for the driver.
                //telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget);
                telemetry.addData("Path2", "Running at %7d :%7d");

                int position = robot.FLDrive.getCurrentPosition();
                telemetry.addData("FL", position);

                int position1 = robot.FRDrive.getCurrentPosition();
                telemetry.addData("FR", position1);

                int position2 = robot.BLDrive.getCurrentPosition();
                telemetry.addData("BL", position2);

                int position3 = robot.BRDrive.getCurrentPosition();
                telemetry.addData("BR", position3);

                telemetry.addData("Target Val", Left);

                telemetry.addData("BackLeft Status", robot.BLDrive.isBusy());

                telemetry.update();

                // Allow time for other processes to run.
                idle();

            }

            // Stop all motion;
            robot.FLDrive.setPower(0);
            robot.FRDrive.setPower(0);
            robot.BLDrive.setPower(0);
            robot.BRDrive.setPower(0);
        }
    }

    //drive forwards and backward method
    //forward is positive speed
    //backward is negative speed
    //input speed & distance)
    public void drive(double speed, double inches) {
        int Left;
        int Right;
        int sign = (int) (speed / (Math.abs(speed)));

        if (opModeIsActive()) {
            Left = robot.BLDrive.getCurrentPosition() - ((int) (inches * COUNTS_PER_INCH * sign));
            Right = robot.BRDrive.getCurrentPosition() + ((int) (inches * COUNTS_PER_INCH * sign));
            robot.BLDrive.setTargetPosition(Left);
            robot.BRDrive.setTargetPosition(Right);


            robot.FRDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.FLDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.FLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            robot.BLDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.BRDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            robot.BRDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            //robot.BLDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.FLDrive.setPower(-speed);
            robot.FRDrive.setPower(-speed);
            robot.BRDrive.setPower(speed);
            robot.BLDrive.setPower(speed);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() && robot.BLDrive.isBusy() && robot.BRDrive.isBusy()) {

                // Display it for the driver.
                //telemetry.addData("Path1", "Running to %7d :%7d", newLeftTarget);
                telemetry.addData("Path2", "Running at %7d :%7d");

                int position = robot.FLDrive.getCurrentPosition();
                telemetry.addData("FL", position);

                int position1 = robot.FRDrive.getCurrentPosition();
                telemetry.addData("FR", position1);

                int position2 = robot.BLDrive.getCurrentPosition();
                telemetry.addData("BL", position2);

                int position3 = robot.BRDrive.getCurrentPosition();
                telemetry.addData("BR", position3);

                telemetry.addData("Target Val", Left);

                telemetry.addData("BackLeft Status", robot.BLDrive.isBusy());

                telemetry.update();

                // Allow time for other processes to run.
                idle();

            }


            // Stop all motion;
            robot.FLDrive.setPower(0);
            robot.FRDrive.setPower(0);
            robot.BLDrive.setPower(0);
            robot.BRDrive.setPower(0);
        }
    }

    //vuforia methods that open the software and the camera
    public void initVuforia () {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    //Initialize the Tensor Flow Object Detection engine.
    public void initTfod () {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    //method that lands the robot
    public void Landing (){
        robot.Lift.setPower(-1);
        sleep(4000);
        robot.Lift.setPower(0);
        sleep(500);
    }

    //method that samples by reading two at once and determining gold mineral position
    public void Samping (){
        if (tfod != null) {
            tfod.activate();
        }
        while (opModeIsActive() && i==1) {
            if (tfod != null) {
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    int goldMineralX = -1;
                    int silverMineral1X = -1;
                    telemetry.addData("# Object Detected", updatedRecognitions.size());
                    telemetry.update();

                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            goldMineralX = (int) recognition.getLeft();
                        } else if (silverMineral1X == -1) {
                            silverMineral1X = (int) recognition.getLeft();
                        }

                        if (goldMineralX != -1 && silverMineral1X != -1) {
                            if (goldMineralX < silverMineral1X) {
                                telemetry.addData("Gold Mineral Position", "Left");
                                telemetry.update();
                                i =11;
                            } else if (goldMineralX > silverMineral1X) {
                                telemetry.addData("Gold Mineral Position", "Right");
                                telemetry.update();
                                i=12;
                            }
                        }
                        else {
                            i=10;
                        }
                    }
                }
            }
        }
    }

    //inits robot hardware map
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

    }
}
