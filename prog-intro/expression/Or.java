package expression;

public class Or extends BinaryExpression {
    public Or (MathExpression left, MathExpression  right) {
        super(left, right, -2, false);
    }

    @Override
    public String getOperator() {
        return "|";
    }

    @Override
    public int f(int a, int b) {
        return a | b;
    }

    @Override
    public double f(double a, double b) {
        return f((int) a, (int) b);
    }
}
