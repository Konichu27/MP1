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
public class ErrorScreen2 extends JFrame {
  
    Container c;
    JButton closeButton;
    JLabel errorMessageLabel; // Added JLabel for displaying error message

    public ErrorScreen2() {
        setTitle("Error");
        setSize(505, 205);
        setLocation(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        errorMessageLabel = new JLabel("Sorry, You have reached the limit of 3 tries. Good bye!");
        errorMessageLabel.setBounds(100, 45, 400, 30);
        c.add(errorMessageLabel);

        closeButton = new JButton("Close");
        closeButton.setBounds(195, 105, 70, 30);
        c.add(closeButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        ErrorScreen2 frame = new ErrorScreen2();
    }
}
