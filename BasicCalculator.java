import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class BasicCalculator extends JFrame {

    // Declaring the input field and result label as instance variables
    private JTextField inputField;
    private JLabel resultLabel;

    public BasicCalculator() {
        // Setting up the JFrame properties
        setTitle("Basic Calculator");
        setSize(500, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Creating GridBagConstraints to manage the layout
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        // Setting up the input field where users can enter the expression
        inputField = new JTextField();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.ipady = 10; // Increase input field height
        add(inputField, constraints);

        // Setting up the Calculate button
        JButton calculateButton = new JButton("Calculate");
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.ipady = 0; // Reset button height
        add(calculateButton, constraints);

        // Setting up the label to display the result
        resultLabel = new JLabel("Result: ");
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(resultLabel, constraints);

        // Adding an ActionListener to the Calculate button
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetching the expression entered by the user
                String expression = inputField.getText();
                try {
                    // Evaluating the expression
                    double result = evaluateExpression(expression);
                    // Displaying the result
                    resultLabel.setText("Result: " + result);
                } catch (Exception ex) {
                    // Displaying an error message if the expression is invalid
                    resultLabel.setText("Error: Invalid Expression");
                }
            }
        });
    }

    /**
     * This method evaluates a mathematical expression passed as a string.
     * It uses two stacks: one for numbers (values) and another for operators (ops).
     *
     * @param expression The mathematical expression to evaluate
     * @return The result of the evaluated expression
     * @throws Exception if the expression is invalid
     */
    private double evaluateExpression(String expression) throws Exception {
        // Stacks to hold numbers (values) and operators (ops)
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        // Removing spaces from the expression
        expression = expression.replaceAll(" ", "");

        // Parsing the expression character by character
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // If the current character is a digit or a decimal point, parse the number
            if (Character.isDigit(ch) || ch == '.') {
                StringBuilder sb = new StringBuilder();
                // Accumulate the number (including decimals)
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                // Push the parsed number to the values stack
                values.push(Double.parseDouble(sb.toString()));
                i--; // Adjust the index to continue parsing correctly
            }
            // If the current character is an opening bracket, push it to the ops stack
            else if (ch == '(' || ch == '[' || ch == '{') {
                ops.push(ch);
            }
            // If the current character is a closing bracket, evaluate the expression within the brackets
            else if (ch == ')' || ch == ']' || ch == '}') {
                char matchingOp = getMatchingBracket(ch);
                // Evaluate until the matching opening bracket is found
                while (ops.peek() != matchingOp) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.pop(); // Remove the matching opening bracket

                // Handle implicit multiplication (e.g., 2(3) -> 2 * 3)
                if (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '(' || expression.charAt(i + 1) == '[' || expression.charAt(i + 1) == '{')) {
                    ops.push('*');
                }
            }
            // If the current character is an operator, evaluate the expression based on precedence
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                // Evaluate higher precedence operators before pushing the current operator
                while (!ops.empty() && hasPrecedence(ch, ops.peek())) {
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));
                }
                ops.push(ch); // Push the current operator to the ops stack
            }
        }

        // Evaluate the remaining operators in the ops stack
        while (!ops.empty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()));
        }

        // The final value on the values stack is the result
        return values.pop();
    }

    /**
     * Determines if op2 has higher or equal precedence than op1.
     *
     * @param op1 The current operator
     * @param op2 The operator on top of the ops stack
     * @return true if op2 has higher or equal precedence, false otherwise
     */
    private boolean hasPrecedence(char op1, char op2) {
        // Brackets always have the lowest precedence
        if (op2 == '(' || op2 == '[' || op2 == '{') {
            return false;
        }
        // Multiplication and division have higher precedence than addition and subtraction
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    /**
     * Applies the operator on two operands.
     *
     * @param op The operator to apply
     * @param b The second operand
     * @param a The first operand
     * @return The result of the operation
     */
    private double applyOp(char op, double b, double a) {
        // Perform the operation based on the operator
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

    /**
     * Returns the matching opening bracket for a given closing bracket.
     *
     * @param ch The closing bracket
     * @return The corresponding opening bracket
     */
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
        // Running the calculator in the Swing event dispatch thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BasicCalculator().setVisible(true);
            }
        });
    }
}
