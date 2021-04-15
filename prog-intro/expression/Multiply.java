package expression;

public class Multiply extends BinaryExpression {
    public Multiply (MathExpression left, MathExpression  right) {
        super(left, right,  2, false);
    }

    @Override
    protected String getOperator() {
        return "*";
    }

    @Override
    protected int f(int a, int b) {
        return a * b;
    }

    @Override
    protected double f(double a, double b) {
        return a * b;
    }
}
