package expression;

public class XOR extends BinaryExpression {
    public XOR (MathExpression left, MathExpression  right) {
        super(left, right, -1, false);
    }

    @Override
    public String getOperator() {
        return "^";
    }

    @Override
    public int f(int a, int b) {
        return a ^ b;
    }

    @Override
    public double f(double a, double b) {
        return f((int) a, (int) b);
    }
}
