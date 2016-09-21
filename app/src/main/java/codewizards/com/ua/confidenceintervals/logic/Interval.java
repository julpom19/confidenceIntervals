package codewizards.com.ua.confidenceintervals.logic;

import java.math.BigDecimal;

/**
 * Created by Интернет on 02.09.2016.
 */

public class Interval extends Element {
    private String name;
    private double leftLimit;
    private double rightLimit;
    boolean isReflected;
    boolean isInversed;

    private static char nextName = 65;
    public static void decNextName() {
        nextName--;
    }

    public Interval(double leftLimit, double rightLimit, boolean isInversed, boolean isReflected) {
        this.name = String.valueOf(nextName++);
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
        this.isInversed = isInversed;
        this.isReflected = isReflected;
    }

    public Interval(double leftLimit, double rightLimit) {
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    public Interval(double number) {
        this.name = String.valueOf(nextName++);
        this.leftLimit = number;
        this.rightLimit = number;
    }

    public String getName() {
        return name;
    }

    public boolean isInversed() {
        return isInversed;
    }

    public boolean isReflected() {
        return isReflected;
    }

    @Override
    public String toString() {
        String str = "";
        if(isInversed && isReflected) {
            str = "(" + name + "⁻" + ")" + "⁻ⁱ";
        } else {
            str = name;
            if(isInversed) {
                str += "⁻ⁱ";
            }
            if(isReflected) {
                str += "⁻";
            }
        }
        return str;
    }

    public double getLeftLimit() {
        return leftLimit;
    }

    public double getRightLimit() {
        return rightLimit;
    }

    public String getLimitRepresentation() {
        String str = "";
        if(isInversed && isReflected) {
            str = "(" + "[" + roundUp(leftLimit, 2) + " " + roundUp(rightLimit, 2) + "]" + "⁻" + ")" + "⁻ⁱ";
        } else {
            str = "[" + roundUp(leftLimit, 2) + " " + roundUp(rightLimit, 2) + "]";
            if(isInversed) {
                str += "⁻ⁱ";
            }
            if(isReflected) {
                str += "⁻";
            }
        }
        return str;
    }

    public BigDecimal roundUp(double value, int digits){

        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }



}
