package codewizards.com.ua.confidenceintervals;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import codewizards.com.ua.confidenceintervals.logic.Element;
import codewizards.com.ua.confidenceintervals.logic.Interval;
import codewizards.com.ua.confidenceintervals.logic.Operation;

/**
 * Created by Интернет on 05.09.2016.
 */

public class ExpressionCalculator {
    private static ExpressionCalculator expressionCalculator = new ExpressionCalculator();
    private ExpressionCalculator() {}
    public static ExpressionCalculator getInstance() {
        return expressionCalculator;
    }
    StringBuilder result;
    int opCounter = 0;
    private List<Interval> lastIntervals;

    public String calculateExpression() {
        result = new StringBuilder();
        opCounter = 0;
        List<Element> resultList = new ArrayList<>();
        resultList.addAll(DataContainer.getInstance().getElementList());
        if(resultList.size() == 0) {
            return "";
        }
        //не враховуємо останню операцію, якщо після неї не стоїть інтервал
        if(resultList.get(resultList.size() - 1) instanceof Operation) {
            resultList.remove(resultList.size() - 1);
        }

        //перший рядок
        result.append("Expr: ");
        for(Element element : resultList) {
            if(element instanceof Interval) {
                Interval lastInterval = (Interval) element;
                result.append(lastInterval.getLimitRepresentation());
            } else {
                Operation lastOperation = (Operation) element;
                result.append(lastOperation);
            }
        }
        result.append("\n");
        result.append("\n");

        //виконати відображення та інверсію
        for(int i = 0; i < resultList.size(); i++) {
            if(resultList.get(i) instanceof Interval) {
                Interval interval = (Interval) resultList.get(i);
                boolean isInversed = interval.isInversed();
                boolean isReflected = interval.isReflected();
                interval = new Interval(interval.getLeftLimit(), interval.getRightLimit());
                if(isReflected) {
                    result.append(++opCounter + ") " + interval.getLimitRepresentation() + "⁻" + " = ");
                    interval = reflect(interval);
                    result.append(interval.getLimitRepresentation());
                    result.append("\n");
                }
                if(isInversed) {
                    result.append(++opCounter + ") " + interval.getLimitRepresentation() + "⁻ⁱ" + " = ");
                    interval = inverse(interval);
                    result.append(interval.getLimitRepresentation());
                    result.append("\n");
                }
                resultList.set(i, interval);
            }
        }

        //виконати всі пріоритетні операції
        boolean proceedNewCycle = false;
        do {
            proceedNewCycle = false;
            for(int i = 0; i < resultList.size(); i++) {
                if(resultList.get(i) instanceof Operation) {
                    Operation operation = (Operation) resultList.get(i);
                    if(operation.getPriority() == 1) {
                        try {
                            calculateOperation(resultList, i);
                        } catch (ArithmeticException ex) {
                            return "Ділення на нуль!";
                        } catch (NumberFormatException ex) {
                            return "Ділення за скороченою формулою не може відбуватись в множині R";
                        }
                        proceedNewCycle = true;
                        break;
                    }
                }
            }
        } while (proceedNewCycle);


        //виконати всі інші операції
        do {
            proceedNewCycle = false;
            for(int i = 0; i < resultList.size(); i++) {
                if(resultList.get(i) instanceof Operation) {
                    try {
                        calculateOperation(resultList, i);
                    } catch (ArithmeticException ex) {
                        return "Ділення на нуль!";
                    } catch (NumberFormatException ex) {
                        return "Ділення за скороченою формулою не може відбуватись в множині R";
                    }
                    proceedNewCycle = true;
                    break;
                }
            }
        } while (proceedNewCycle);


        result.append("\n");
        result.append("Result: " + ((Interval)resultList.get(0)).getLimitRepresentation());
        return result.toString();
    }



    private void calculateOperation(List<Element> elements, int operationIndex) {
        Interval resultInterval = null;
        Operation operation = (Operation) elements.get(operationIndex);
        Interval operand1 = (Interval) elements.get(operationIndex - 1);
        Interval operand2 = (Interval) elements.get(operationIndex + 1);
        lastIntervals = new ArrayList<>();
        lastIntervals.add(operand1);
        lastIntervals.add(operand2);
        switch (operation.getName()) {
            case "SUM":
                resultInterval = sum(operand1, operand2);
                break;
            case "SUB":
                resultInterval = sub(operand1, operand2);
                break;
            case "DIV":
                try {
                    resultInterval = div(operand1, operand2);
                    if(Double.isInfinite(resultInterval.getLeftLimit()) || Double.isNaN(resultInterval.getLeftLimit()) ||
                            Double.isInfinite(resultInterval.getRightLimit()) || Double.isNaN(resultInterval.getRightLimit())) {
                        throw new ArithmeticException();
                    }
                } catch (ArithmeticException ex) {
                    throw ex;
                }
                break;
            case "DIV_HIP":
                if((operand1.getLeftLimit() <= 0 && operand1.getRightLimit() <=0 && operand2.getLeftLimit() <= 0 && operand2.getRightLimit() <= 0) ||
                        (operand1.getLeftLimit() >= 0 && operand1.getRightLimit() >=0 && operand2.getLeftLimit() >= 0 && operand2.getRightLimit() >= 0)) {
                    try {
                        resultInterval = divHip(operand1, operand2);
                        if(Double.isInfinite(resultInterval.getLeftLimit()) || Double.isNaN(resultInterval.getLeftLimit()) ||
                                Double.isInfinite(resultInterval.getRightLimit()) || Double.isNaN(resultInterval.getRightLimit())) {
                            throw new ArithmeticException();
                        }
                    } catch (ArithmeticException ex) {
                        throw ex;
                    }
                } else {
                    throw new NumberFormatException();
                }
                break;
            case "MULT":
                resultInterval = mult(operand1, operand2);
                break;
            case "MAX":
                resultInterval = max(operand1, operand2);
                break;
            case "MIN":
                resultInterval = min(operand1, operand2);
                break;
        }
        result.append(++opCounter + ") " + operand1.getLimitRepresentation() + operation + operand2.getLimitRepresentation()
                                + " = " + resultInterval.getLimitRepresentation());
        result.append("\n");
        lastIntervals.add(resultInterval);
        DataContainer.getInstance().setLastIntervals(lastIntervals);
        elements.set(operationIndex, resultInterval);
        elements.remove(operationIndex + 1);
        elements.remove(operationIndex - 1);
    }

    private Interval sum(Interval operand1, Interval operand2) {
        double leftLimit = operand1.getLeftLimit() + operand2.getLeftLimit();
        double rightLimit = operand1.getRightLimit() + operand2.getRightLimit();
        return new Interval(leftLimit, rightLimit);
    }

    private Interval sub(Interval operand1, Interval operand2) {
        double leftLimit = operand1.getLeftLimit() - operand2.getRightLimit();
        double rightLimit = operand1.getRightLimit() - operand2.getLeftLimit();
        return new Interval(leftLimit, rightLimit);
    }

    private Interval mult(Interval operand1, Interval operand2) {
        if(operand1.getRightLimit() == operand1.getLeftLimit() ||
                operand2.getLeftLimit() == operand2.getRightLimit()) {
            double leftLimit = operand1.getLeftLimit() * operand2.getLeftLimit();
            double rightLimit = operand1.getRightLimit() * operand2.getRightLimit();
            return new Interval(leftLimit, rightLimit);
        }
        double val[] = new double[4];
        val[0] = operand1.getLeftLimit() * operand2.getLeftLimit();
        val[1] = operand1.getLeftLimit() * operand2.getRightLimit();
        val[2] = operand1.getRightLimit() * operand2.getLeftLimit();
        val[3] = operand1.getRightLimit() * operand2.getRightLimit();
        double min = val[0];
        double max = val[0];
        for(int i = 1; i < val.length; i++) {
            if(val[i] > max) {
                max = val[i];
            }
            if(val[i] < min) {
                min = val[i];
            }
        }
        return new Interval(min, max);
    }

    private Interval div(Interval operand1, Interval operand2) throws ArithmeticException {
        double leftLimit = operand1.getLeftLimit() / operand2.getRightLimit();
        double rightLimit = operand1.getRightLimit() / operand2.getLeftLimit();
        return new Interval(leftLimit, rightLimit);
    }

    private Interval divHip(Interval operand1, Interval operand2) throws ArithmeticException {
        if(operand1.getRightLimit() == operand1.getLeftLimit() ||
                operand2.getLeftLimit() == operand2.getRightLimit()) {
            double leftLimit = operand1.getLeftLimit() / operand2.getLeftLimit();
            double rightLimit = operand1.getRightLimit() / operand2.getRightLimit();
            return new Interval(leftLimit, rightLimit);
        }
        double val[] = new double[4];
        val[0] = operand1.getLeftLimit() / operand2.getLeftLimit();
        val[1] = operand1.getLeftLimit() / operand2.getRightLimit();
        val[2] = operand1.getRightLimit() / operand2.getLeftLimit();
        val[3] = operand1.getRightLimit() / operand2.getRightLimit();
        double min = val[0];
        double max = val[0];
        for(int i = 1; i < val.length; i++) {
            if(val[i] > max) {
                max = val[i];
            }
            if(val[i] < min) {
                min = val[i];
            }
        }
        return new Interval(min, max);
    }

    private Interval max(Interval operand1, Interval operand2) {
        double leftLimit = findMax(operand1.getLeftLimit(), operand2.getLeftLimit());
        double rightLimit = findMax(operand1.getRightLimit(), operand2.getRightLimit());
        return new Interval(leftLimit, rightLimit);
    }

    private Interval min(Interval operand1, Interval operand2) {
        double leftLimit = findMin(operand1.getLeftLimit(), operand2.getLeftLimit());
        double rightLimit = findMin(operand1.getRightLimit(), operand2.getRightLimit());
        return new Interval(leftLimit, rightLimit);
    }

    private Interval inverse(Interval interval) {
        double leftLimit = 1/interval.getRightLimit();
        double rightLimit = 1/interval.getLeftLimit();
        return new Interval(leftLimit, rightLimit);
    }

    private Interval reflect(Interval interval) {
        double leftLimit = -interval.getRightLimit();
        double rightLimit = -interval.getLeftLimit();
        return new Interval(leftLimit, rightLimit);
    }

    private double findMin(double a1, double a2) {
        if(a1 > a2) {
            return a2;
        } else {
            return a1;
        }
    }

    private double findMax(double a1, double a2) {
        if(a1 > a2) {
            return a1;
        } else {
            return a2;
        }
    }
}
