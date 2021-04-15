package expression;


public class Flip extends UnaryExpression {

    public Flip(MathExpression content) {
        super(content);
    }

    @Override
    protected int f(int a) {
        int ans = 0;
        while (a != 0) {
            ans <<= 1;
            ans += (a & 1);
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
        return "flip ";
    }


}
