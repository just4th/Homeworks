package expression;

public abstract class MathExpression implements Expression, DoubleExpression, TripleExpression{
    public final int priority;
    protected MathExpression (int priority) {
        this.priority = priority;
    }
}
