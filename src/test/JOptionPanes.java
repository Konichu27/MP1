package test;

import javax.swing.JOptionPane;

public class JOptionPanes {

    public static void main(String[] args) {
        // Placeholder for user login success check
        boolean loginSuccessful = true; // Replace this with your actual login logic

        // Placeholder for error login counter
        int errorCounter = 0;

        if (loginSuccessful) {
            JOptionPane.showMessageDialog(null, "Successfully logged in.");
            // Dispose the login frame with .dispose() and then run
            // Replace the following line with the actual code to dispose the login frame
            // loginFrame.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect Username/Password", "Error", JOptionPane.ERROR_MESSAGE);
            // Increment the error login counter
            errorCounter++;

            // Create a nested if to check if the current error is already 3
            if (errorCounter >= 3) {
                JOptionPane.showMessageDialog(null, "Sorry, You have reached the limit of 3 tries. Good bye!", 
                                                "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }
}
