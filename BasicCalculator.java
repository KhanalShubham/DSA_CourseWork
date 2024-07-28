import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BasicCalculator extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public BasicCalculator() {
        setTitle("Basic Calculator");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        inputField = new JTextField();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.ipady = 10; // Increase input field height
        add(inputField, constraints);

        JButton calculateButton = new JButton("Calculate");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipady = 0; // Reset button height
        add(calculateButton, constraints);

        resultLabel = new JLabel("Result: ");
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(resultLabel, constraints);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText();
                try {
                    double result = evaluateExpression(expression);
                    resultLabel.setText("Result: " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: Invalid Expression");
                }
            }
        });
    }

    private double evaluateExpression(String expression) throws Exception {
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();
        expression = expression.replaceAll(" ", "");

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                values.push(Double.parseDouble(sb.toString()));
                i--;
            } else if (ch == '(' || ch == '[' || ch == '{') {
                ops.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                char matchingOp = getMatchingBracket(ch);
                while (ops.peek() != matchingOp) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop();
                if (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '(' || expression.charAt(i + 1) == '[' || expression.charAt(i + 1) == '{')) {
                    ops.push('*');
                }
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                while (!ops.empty() && hasPrecedence(ch, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(ch);
            }
        }

        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == '[' || op2 == '{') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    private double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    throw new UnsupportedOperationException("Cannot divide by zero");
                }
                return a / b;
        }
        return 0;
    }

    private char getMatchingBracket(char ch) {
        switch (ch) {
            case ')':
                return '(';
            case ']':
                return '[';
            case '}':
                return '{';
        }
        return 0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BasicCalculator().setVisible(true);
            }
        });
    }
}
