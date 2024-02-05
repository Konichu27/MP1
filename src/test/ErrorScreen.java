/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import java.awt.Container;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author elizr
 */
public class ErrorScreen extends JFrame {
  
    Container c;
    JButton closeButton;
    JLabel errorMessageLabel; // Added JLabel for displaying error message

    public ErrorScreen() {
        setTitle("Error");
        setSize(305, 205);
        setLocation(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        errorMessageLabel = new JLabel("Incorrect Username/ Password");
        errorMessageLabel.setBounds(60, 40, 200, 30);
        c.add(errorMessageLabel);

        closeButton = new JButton("Close");
        closeButton.setBounds(110, 100, 70, 30);
        c.add(closeButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        ErrorScreen frame = new ErrorScreen();
    }
}
