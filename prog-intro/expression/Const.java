package expression;

import java.util.Objects;

public class Const extends MathExpression {
    private final Number c;
    public Const(int c) {
        super(100);
        this.c = Integer.valueOf(c);
    }
    public Const(double c) {
        super(100);
        this.c = Double.valueOf(c);
    }
    @Override
    public int evaluate(int x) {
        return c.intValue();
    }

    @Override
    public double evaluate(double x) {
        return c.doubleValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return c.intValue();
    }

    @Override
    public String toString() {
        return c.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(c);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == getClass() && ((Const) obj).c.equals(c);
    }
}
