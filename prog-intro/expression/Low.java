package expression;

public class Low extends UnaryExpression {

    public Low(MathExpression content) {
        super(content);
    }

    @Override
    protected int f(int a) {
        if (a == 0) return a;
        int ans = 1;
        while ((a & 1) != 1) {
            ans <<= 1;
            a >>>= 1;
        }
        return ans;
    }

    @Override
    protected double f(double a) {
        return f((int) a);
    }

    @Override
    protected String getOperator() {
        return "low ";
    }

}
