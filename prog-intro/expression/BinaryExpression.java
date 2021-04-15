package expression;

import java.util.Objects;

public abstract class BinaryExpression extends MathExpression{
    private final MathExpression left, right;
    private final boolean rf;
    protected BinaryExpression (MathExpression left, MathExpression right, int priority, boolean rf) {
        super(priority);
        this.left = left;
        this.right = right;
        this.rf = rf;
    }

    abstract protected int f(int a, int b);
    abstract protected double f(double a, double b);

    protected abstract String getOperator();

    @Override
    public int evaluate(int x) {
        return f(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return f(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return f(left.evaluate(x, y, z), right.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(left.toString());
        sb.append(" " + getOperator() + " ");
        sb.append(right.toString());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        if (left.priority < priority) {
            sb.append("(");
        }
        sb.append(left.toMiniString());
        if (left.priority < priority) {
            sb.append(")");
        }
        sb.append(" " + getOperator() + " ");
        boolean b = right.priority < priority || right.priority == priority && (rf || right instanceof Divide);
        if (b) {
            sb.append("(");
        }
        sb.append(right.toMiniString());
        if (b) {
            sb.append(")");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, getOperator());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            BinaryExpression be = ((BinaryExpression) obj);
            return left.equals(be.left) && right.equals(be.right) && getOperator().equals(be.getOperator());
        }
    }
}
