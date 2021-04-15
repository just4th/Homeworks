package expression;

import java.util.Objects;

public abstract class UnaryExpression extends MathExpression{
    private final MathExpression content;
    protected UnaryExpression (MathExpression content) {
        super(99);
        this.content = content;
    }

    abstract protected int f(int a);
    abstract protected double f(double a);

    protected abstract String getOperator();

    @Override
    public int evaluate(int x) {
        return f(content.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return f(content.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return f(content.evaluate(x, y, z));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        sb.append(getOperator());
        sb.append(content.toString());
        sb.append(")");
        return sb.toString();
    }

    @Override
    public String toMiniString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getOperator());
        if (content.priority < priority) {
            sb.append("(");
        }
        sb.append(content.toMiniString());
        if (content.priority < priority) {
            sb.append(")");
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, getOperator());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        } else {
            UnaryExpression be = ((UnaryExpression) obj);
            return content.equals(be.content) && getOperator().equals(be.getOperator());
        }
    }
}
