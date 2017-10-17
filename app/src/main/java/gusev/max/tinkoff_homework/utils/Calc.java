package gusev.max.tinkoff_homework.utils;

import java.io.Serializable;

/**
 * Created by v on 17/10/2017.
 */

public class Calc implements Serializable {

    private Double number1;
    private Double number2;
    private String symbol;

    public Calc(Double number1, Double number2, String symbol) {
        this.number1 = number1;
        this.number2 = number2;
        this.symbol = symbol;
    }

    public Double getNumber1() {
        return number1;
    }

    public void setNumber1(Double number1) {
        this.number1 = number1;
    }

    public Double getNumber2() {
        return number2;
    }

    public void setNumber2(Double number2) {
        this.number2 = number2;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    String getExpression(){
        Double value;

        switch (symbol){
            case "+":
                value = number1 + number2;
                return buildStringExpression(value);
            case "-":
                value = number1 - number2;
                return buildStringExpression(value);
            case "*":
                value = number1 * number2;
                return buildStringExpression(value);
            case "/":
                value = number1 / number2;
                return buildStringExpression(value);
            default:
                break;
        }
        return null;
    }

    private String buildStringExpression(Double value){
        return String.valueOf(number1) + " " + symbol + " " + String.valueOf(number2) + " = " + String.valueOf(value);
    }
}
