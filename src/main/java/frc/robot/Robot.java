// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

/**
 * This is a demo program showing the use of the DifferentialDrive class, specifically it contains
 * the code necessary to operate a robot with tank drive.
 */
public class Robot extends TimedRobot {
  private DifferentialDrive m_myRobot;
  private Joystick m_leftStick;
  private Joystick m_rightStick;

  private final MotorController m_leftMotora = new CANSparkMax(0);
  private final MotorController m_rightMotora = new CANSparkMax(1);
  private final MotorController m_leftMotorb = new CANSparkMax(0);
  private final MotorController m_rightMotorb = new CANSparkMax(1);
  varname NewLeftMotor = (leftMotora,leftmotorb);
  varname NewRightMotor = (rightMotora,rightMotorb);

  m_leftmotor = m_leftmotorb(m_leftMotora,leftmotorb);
  private final SpeedControllerGroup RightSpeedGroup = new SpeedControllerGroup(NewRightMotor);
  private final SpeedControllerGroup LeftSpeedGroup = new SpeedControllerGroup(NewLeftMotor);
  //drivetrain
  DifferentialDrive drivetrain = new DifferentialDrive(LeftSpeedGroup,RightSpeedGroup);
  //joystick
varname Joystick stick = new Joysticka(0);
Joystick stick = new Joystickb(1);
  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotora.setInverted(true);
    m_leftMotora.setInverted(isInverted:true);
    m_leftMotorb.setInverted(isInverted:false);
    m_rightmotorb.setInverted(isInverted:false);

    m_myRobot = new DifferentialDrive(m_leftMotor, m_rightMotor);
    m_leftStick = new Joystick(0);
    m_rightStick = new Joystick(1);
  }

  @Override
  public void teleopPeriodic() {
    m_myRobot.tankDrive(-m_leftStick.getY(), -m_rightStick.getY());
  }
}
