package com.telusmikolaj.calc.calculatorfx;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MathOperationsController {

    public String solveEquation(String equation) {
        Expression expression = new ExpressionBuilder(equation).build();
        double result = expression.evaluate();
        return String.valueOf(result);
    }

    public String solveSqrt(String equation) {
        return String.valueOf(Math.sqrt(Double.parseDouble(equation)));
    }

    public String solvePower(String equation) {
        int operandIndex = equation.indexOf("^");
        double base = Double.parseDouble(equation.substring(0, operandIndex));
        double exponent = Double.parseDouble(equation.substring(operandIndex + 1, equation.length()));

        return String.valueOf(Math.pow(base, exponent));
    }

    public String solveNegation(String equation) {
        return String.valueOf(-Double.parseDouble(equation));
    }


}
