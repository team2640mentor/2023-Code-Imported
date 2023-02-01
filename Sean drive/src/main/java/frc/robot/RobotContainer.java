package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ArcadeDrive;
//import frc.robot.commands.DriveTime;
import frc.robot.commands.LiftArm;
import frc.robot.commands.Outake;
import frc.robot.commands.MoveArmBack;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arms;
import frc.robot.subsystems.Intake;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
//import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
import edu.wpi.first.wpilibj.Joystick;
import frc.robot.Constants;

public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final Drivetrain m_drivetrain = new Drivetrain();
  private final Arms m_arm = new Arms();
  private final Intake m_intake = new Intake();
  // TO-DO Declare a joystick
  public Joystick driverController = new Joystick(Constants.DRIVER_Controller);
  // Create SmartDashboard chooser for autonomous routines
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings

    configureButtonBindings();

    m_arm.setDefaultCommand(new LiftArm(m_arm, 0));
    m_intake.setDefaultCommand(new IntakeBall(m_intake, 0));

  
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // Default command is arcade drive. This will run unless another command
    // is scheduled over it.
    m_drivetrain.setDefaultCommand(getArcadeDriveCommand());
    SmartDashboard.putData(m_chooser);
   
    final JoystickButton outtakeButton = new JoystickButton(driverController, 1);
    final JoystickButton intakeButton = new JoystickButton(driverController, 2);
    final JoystickButton armButton = new JoystickButton(driverController, 3);
    final JoystickButton armBackButton = new JoystickButton(driverController, 4);

    intakeButton.whileHeld(new RunCommand(() -> m_intake.setIntakeSpeed(Constants.INTAKE_SPEED)));
    outtakeButton.whileHeld(new RunCommand(() -> m_intake.setIntakeSpeed(-1 * Constants.INTAKE_SPEED)));
    armButton.whileHeld(new RunCommand(() -> m_arm.setArmSpeed(Constants.ARM_SPEED)));
    armBackButton.whileHeld(new RunCommand(() -> m_arm.setArmSpeed(-1 * Constants.ARM_SPEED)));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return new SequentialCommandGroup(
      new LiftArm(m_arm, Constants.ARM_SPEED).withTimeout(1),  
      new DriveTime(Constants.DRIVE_SPEED, -.2, m_drivetrain), 
      new LiftArm(m_arm, Constants.ARM_SPEED).withTimeout(1),
      new Outake(m_intake,0.1).withTimeout(3),
      //new Outake(m_intake, Constants.INTAKE_SPEED).withTimeout(3),
      new MoveArmBack(m_arm, Constants.ARM_SPEED).withTimeout(1));

  }
  public Command getArcadeDriveCommand()
  {   return new ArcadeDrive(
      m_drivetrain, () -> -driverController.getRawAxis(1), () -> driverController.getRawAxis(4));
  }
  /**
   * Use this to pass the teleop command to the main {@link Robot} class.
   *
   * @return the command to run in teleop
   */
}