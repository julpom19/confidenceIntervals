package codewizards.com.ua.confidenceintervals.logic;

/**
 * Created by Интернет on 02.09.2016.
 */

public class Operation extends Element{
    private String name;

    public Operation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        String str = "";
        switch (name) {
            case "SUM":
                str = "(+)";
                break;
            case "SUB":
                str = "(-)";
                break;
            case "DIV":
                str = "(:)";
                break;
            case "DIV_HIP":
                str = "(:)";
                break;
            case "MULT":
                str = "(*)";
                break;
            case "MAX":
                str = "(∨)";
                break;
            case "MIN":
                str = "(∧)";
                break;
        }
        return str;
    }

    public int getPriority() {
        int priority = 1;
        switch (name) {
            case "SUM":
            case "SUB":
            case "MIN":
            case "MAX":
                priority = 0;
                break;
        }
        return priority;
    }
}
