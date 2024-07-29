/*
 * NumberGuesser program that generates a random number for a user to guess.
 * Banele Mdluli
 * 2023-07-29
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NumberGuesserGUI extends JFrame {

    private int number;
    private int trials;
    private boolean guessed;

    private JTextField guessField;
    private JLabel messageLabel;
    private JButton guessButton;
    private JButton restartButton;

    public NumberGuesserGUI() {
        // Set up the frame
        setTitle("Number Guesser");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(60, 63, 65));

        // Initialize the game
        initializeGame();

        // Set up the components
        Font font = new Font("SansSerif", Font.BOLD, 18);
        Font messageFont = new Font("SansSerif", Font.PLAIN, 20);

        messageLabel = new JLabel("A number is chosen between 1 to 100. Guess the number.", SwingConstants.CENTER);
        messageLabel.setFont(messageFont);
        messageLabel.setForeground(Color.WHITE);
        add(messageLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(60, 63, 65));
        JLabel guessLabel = new JLabel("Enter your guess: ");
        guessLabel.setFont(font);
        guessLabel.setForeground(Color.WHITE);
        guessField = new JTextField(10);
        guessField.setFont(font);
        guessButton = new JButton("Guess");
        guessButton.setFont(font);
        guessButton.setBackground(new Color(28, 184, 65));
        guessButton.setForeground(Color.WHITE);
        inputPanel.add(guessLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);
        add(inputPanel, BorderLayout.CENTER);

        restartButton = new JButton("Restart");
        restartButton.setFont(font);
        restartButton.setBackground(new Color(219, 68, 55));
        restartButton.setForeground(Color.WHITE);
        restartButton.setEnabled(false);
        add(restartButton, BorderLayout.SOUTH);

        // Add action listeners
        guessButton.addActionListener(new GuessButtonListener());
        guessField.addKeyListener(new EnterKeyListener());
        restartButton.addActionListener(new RestartButtonListener());
    }

    private void initializeGame() {
        number = 1 + (int) (100 * Math.random());
        trials = 0;
        guessed = false;
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            processGuess();
        }
    }

    private class EnterKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                processGuess();
            }
        }
    }

    private class RestartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            initializeGame();
            messageLabel.setText("A number is chosen between 1 to 100. Guess the number.");
            guessField.setText("");
            guessButton.setEnabled(true);
            restartButton.setEnabled(false);
        }
    }

    private void processGuess() {
        if (!guessed) {
            String userInput = guessField.getText();
            if (userInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int guess = Integer.parseInt(userInput);
                trials++;
                if (guess == number) {
                    messageLabel.setText("Congratulations! You have guessed the number within " + trials + " attempts.");
                    guessed = true;
                    guessButton.setEnabled(false);
                    restartButton.setEnabled(true);
                } else if (guess < number) {
                    messageLabel.setText("The number is greater than " + guess + ". Try again.");
                } else {
                    messageLabel.setText("The number is less than " + guess + ". Try again.");
                }
                guessField.setText(""); // Clear the text field after each guess
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Input Error", JOptionPane.ERROR_MESSAGE);
                guessField.setText(""); // Clear the text field after an invalid input
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            NumberGuesserGUI frame = new NumberGuesserGUI();
            frame.setVisible(true);
        });
    }
}
