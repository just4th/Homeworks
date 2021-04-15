package expression;

public class Minus extends UnaryExpression {

    public Minus(MathExpression content) {
        super(content);
    }

    @Override
    protected int f(int a) {
        return -a;
    }

    @Override
    protected double f(double a) {
        return -a;
    }

    @Override
    protected String getOperator() {
        return "-";
    }

}
