package expression;

public class Subtract extends BinaryExpression {
    public Subtract (MathExpression left, MathExpression  right) {
        super(left, right, 1, true);
    }

    @Override
    protected String getOperator() {
        return "-";
    }

    @Override
    protected int f(int a, int b) {
        return a - b;
    }

    @Override
    protected double f(double a, double b) {
        return a - b;
    }
}
