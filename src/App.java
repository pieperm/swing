import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    private JPanel contentPanel;
    private JTextField firstNumberTextField;
    private JTextField secondNumberTextField;
    private OperationsPanel operationsPanel;
    private JButton equalsButton;
    private JLabel resultLabel;

    public App() {
        this.setSize(400, 300);
        this.setTitle("Calculator");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JFrame.setDefaultLookAndFeelDecorated(true);
        createAppContent();
    }

    private void createAppContent() {
        firstNumberTextField = new JTextField(20);
        secondNumberTextField = new JTextField(20);
        operationsPanel = new OperationsPanel();
        equalsButton = new JButton("=");
        resultLabel = new JLabel("0");

        contentPanel = new JPanel();
        createAppLayout();
        this.add(contentPanel);
        createListeners();
    }

    private void createAppLayout() {
        GridBagLayout layout = new GridBagLayout();
        contentPanel.setLayout(layout);
        GridBagConstraints gridConstraints = new GridBagConstraints();

        gridConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridConstraints.gridx = 0;
        gridConstraints.gridy = 0;
        gridConstraints.gridwidth = 2;
        contentPanel.add(firstNumberTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 1;
        gridConstraints.gridwidth = 2;
        contentPanel.add(operationsPanel, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 2;
        gridConstraints.gridwidth = 2;
        contentPanel.add(secondNumberTextField, gridConstraints);

        gridConstraints.gridx = 0;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 1;
        contentPanel.add(equalsButton, gridConstraints);

        gridConstraints.gridx = 1;
        gridConstraints.gridy = 3;
        gridConstraints.gridwidth = 2;
        contentPanel.add(resultLabel, gridConstraints);
    }

    private void createListeners() {
        equalsButton.addActionListener((event) -> calculate());
    }

    private void displayError(String message, Object... args) {
        SwingUtilities.invokeLater(() -> {
            resultLabel.setForeground(Color.RED);
            resultLabel.setText(String.format(message, args));
        });
    }

    private void calculate() {
        Operation selectedOperation = operationsPanel.getSelectedOperation();

        // artificial delay to simulate a longer calculation and to expose a problem...
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get numbers from input fields
        double firstNumber;
        double secondNumber;
        try {
            firstNumber = Double.parseDouble(firstNumberTextField.getText());
            secondNumber = Double.parseDouble(secondNumberTextField.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                resultLabel.setForeground(Color.RED);
                //TODO which field has the error?  what is nature of error?
                resultLabel.setText("Error");
            });
            return;
        }

        double result;
        switch (selectedOperation) {
            case ADD -> result = firstNumber + secondNumber;
            case SUBTRACT -> result = firstNumber - secondNumber;
            case MULTIPLY -> result = firstNumber * secondNumber;
            case DIVIDE -> {
                if (secondNumber == 0) {
                    displayError("Cannot divide by 0");
                    return;
                }
                result = firstNumber / secondNumber;
            }
            default -> {
                displayError("Invalid operation %s", selectedOperation);
                return;
            }
        }

        SwingUtilities.invokeLater(() -> {
            resultLabel.setForeground(Color.BLACK);
            resultLabel.setText(String.valueOf(result));
        });
    }

    public static void main(String[] args) {
        System.out.println("Running Swing application...");
        App app = new App();
        SwingUtilities.invokeLater(() -> app.setVisible(true));
    }

}