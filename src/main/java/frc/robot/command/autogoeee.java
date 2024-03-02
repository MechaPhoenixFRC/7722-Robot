package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subsystems.drive.Drivetrain;


public class autogoeee extends CommandBase {
    
    private final Drivetrain drivetime;
    public final Timer timer;
    public final double speed; 


    public autogoeee(Drivetrain drivetime, double speed) {
        this.drivetime = drivetime;
        this.speed = speed;
        this.timer = new Timer();
        addRequirements(drivetime);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        Drivetrain.m_robotDrive(-0.36, -0.36); // Set both sides to move backwards
    }

    @Override
    public void end(boolean interrupted) {
        Drivetrain.m_robotDrive(0, 0);; // Stop the drive train when the command ends
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(3); // Finish after 3 seconds
    }

}
