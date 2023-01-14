package com.telusmikolaj.calc.calculatorfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DisplayController {

    private static final String OPERANDS_REGEX = "[×+√∼÷*/^-]";

    private static final String EQUALS_SING_BTN_ID = "equalsSign";

    private static final String NUM_BTN_ID = "Num";

    private static final String COMMA_BTN_ID = "commaBtn";

    private static final String OPERAND_BTN_ID = "Opr";

    private static final String ZERO_BTN_ID = "Zero";
    private final MathOperationsController mathOperationsController = new MathOperationsController();

    private final StringBuilder memory = new StringBuilder();

    @FXML
    private TextField display;
    @FXML
    private GridPane mainPanel;
    private boolean isPowerMode = false;

    private Pattern pattern;

    private Matcher matcher;

    public void getInputCharacter(ActionEvent e) {
        String input = ((Button) e.getSource()).getText();

        if (isContainsOperands(input)) {
            validateOperand(input);
        } else if (input.equals("=")) {
            validateEqualsSing(input);
        } else {
            validateDigit(input);
        }
    }

    private String solveEquation() {
        String equation = display.getText();
        return mathOperationsController.solveEquation(equation);
    }

    private boolean isCommaBtnEnabled() {
        String equation = display.getText();
        long commaCounter = equation.chars().filter(ch -> ch == '.').count();

        if (commaCounter == 0) {
            return true;
        }
        int operandIndex = 0;
        int commaIndex = 0;
        if (commaCounter == 1 && isContainsOperands(display.getText())) {
            pattern = Pattern.compile(OPERANDS_REGEX);
            matcher = pattern.matcher(display.getText());
            while (matcher.find()) {
                operandIndex = matcher.start();
            }

            pattern = Pattern.compile("[.]");
            matcher = pattern.matcher(display.getText());
            while (matcher.find()) {
                commaIndex = matcher.start();
            }

            return operandIndex > commaIndex;

        }

        return false;
    }

    private void validateEqualsSing(String input) {
            if (isLastEquationCharacter('.')) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid operation", ButtonType.CLOSE);
                alert.show();
            } else if (isPowerMode) {
                solvePower();
            } else {
                String result = solveEquation();
                displayCharacter(input);
                displayCharacter(result);
                addToMemory(display.getText());
                disableOperationsButtons();
            }

    }

    private boolean isContainsOperands(String text) {
        pattern = Pattern.compile(OPERANDS_REGEX);
        matcher = pattern.matcher(text);
        return matcher.find();
    }
    private void validateDigit(String input) {


        if (display.getText().isEmpty()) {
            enableOrDisableSelectedButton(OPERAND_BTN_ID, false);
        }

        enableOrDisableSelectedButton(COMMA_BTN_ID, !isCommaBtnEnabled() || isLastEquationCharacter('.'));
        displayCharacter(input);

        if (isContainsOperands(display.getText())) {
            enableOrDisableSelectedButton(EQUALS_SING_BTN_ID, false);
        }
    }

    private void validateOperand(String input) {
        displayCharacter(input);
        enableOrDisableSelectedButton(OPERAND_BTN_ID,true);

    }
    @FXML
    private void clearDisplay(ActionEvent e) {
        display.setText("");
        enableOrDisableSelectedButton(EQUALS_SING_BTN_ID, true);
        enableOrDisableSelectedButton(OPERAND_BTN_ID, true);
        enableOrDisableSelectedButton(NUM_BTN_ID, false);

    }

    private void displayCharacter(String input) {
        if (input.equals("×")) {
            display.appendText("*");
        } else if (input.equals("÷")) {
            enableOrDisableSelectedButton(ZERO_BTN_ID,true);
            display.appendText("/");
        } else {
            display.appendText(input);
        }

        if (input.equals(".")) {
            enableOrDisableSelectedButton(COMMA_BTN_ID, true);
        }
    }


    private boolean isLastEquationCharacter(char character) {
        if (display.getText().isEmpty()) {
            return false;
        }

        return display.getText().charAt(display.getText().length() - 1) == character;
    }

    private void addToMemory(String line) {
        memory.append(line);
        memory.append("\n");
    }

    @FXML
    private void showMemory(ActionEvent e) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, memory.toString(), ButtonType.OK);
        alert.show();
    }

    private void enableOrDisableSelectedButton(String id, boolean flag) {
        mainPanel.getChildren().forEach(element -> {
            if (element instanceof Button && element.getId() != null && element.getId().contains(id)) {
                element.setDisable(flag);
            }
        });
    }

    @FXML
    private void solveSqrt(ActionEvent e) {
        String number = display.getText();
        String result = mathOperationsController.solveSqrt(number);
        display.setText("");
        display.appendText("√" + number + "=" + result);
        addToMemory(display.getText());
        disableOperationsButtons();
    }

    @FXML
    private void initalPower(ActionEvent e) {
        String base = display.getText();
        display.setText("");
        display.appendText(base + "^");
        isPowerMode = true;
        enableOrDisableSelectedButton(OPERAND_BTN_ID, true);
        enableOrDisableSelectedButton(EQUALS_SING_BTN_ID, true);
    }

    private void solvePower() {
        String result = mathOperationsController.solvePower(display.getText());
        display.appendText("=" + result);
        addToMemory(display.getText());
        disableOperationsButtons();
        isPowerMode = false;
    }

    @FXML
    private void solveNegation(ActionEvent e) {
        String number = display.getText();
        String result = mathOperationsController.solveNegation(number);
        display.setText("");
        display.setText(" ¬" + number + "=" + result);
        addToMemory(display.getText());
        disableOperationsButtons();

    }

    private void disableOperationsButtons() {
        enableOrDisableSelectedButton(NUM_BTN_ID, true);
        enableOrDisableSelectedButton(EQUALS_SING_BTN_ID, true);
        enableOrDisableSelectedButton(OPERAND_BTN_ID, true);
        enableOrDisableSelectedButton(COMMA_BTN_ID, true);
    }




}
