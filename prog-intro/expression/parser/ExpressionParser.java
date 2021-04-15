package expression.parser;

import expression.*;

public class ExpressionParser extends BaseParser implements Parser{
    // :NOTE: Map
    private static int getPriority(char op) {
        switch (op) {
            case '*':
            case '/':
                return 2;
            case '-':
            case '+':
                return 1;
            case '&':
                return 0;
            case '^':
                return -1;
            case '|':
                return -2;
            default:
                return -100;
        }
    }

    private BinaryExpression newBinaryExpression(char op, MathExpression left, MathExpression right) {
        if (op == '*') {
            return new Multiply(left, right);
        } else if (op == '/') {
            return new Divide(left, right);
        } else if (op == '-') {
            return new Subtract(left, right);
        } else if (op == '+') {
            return new Add(left, right);
        } else if (op == '&') {
            return new And(left, right);
        } else if (op == '^') {
            return new XOR(left, right);
        } else if (op == '|') {
            return new Or(left, right);
        } else {
            throw error("Unsupported binary operation");
        }
    }

    // :NOTE: Магические числа
    private MathExpression parse(int minPriority) {

        MathExpression left;
        skipWhitespaces();
        if (test('-')) {
            if (!between('0', '9')) {
                left = new Minus(parse(99));
            } else {
                StringBuilder num = new StringBuilder("-");
                while (between('0', '9')) {
                    num.append(ch);
                    nextChar();
                }
                left = new Const(Integer.parseInt(num.toString()));
            }
        } else if (test('f')) {
            expect("lip");
            left = new Flip(parse(99));
        } else if (test('l')) {
            expect("ow");
            left = new Low(parse(99));
        } else if (test('(')) {
            left = parse (-99);
            expect(')');
        } else if (between('x', 'z')) {
            left = new Variable(String.valueOf(ch));
            nextChar();
        } else {
            StringBuilder num = new StringBuilder();
            while (between('0', '9')) {
                num.append(ch);
                nextChar();
            }
            if (num.length() == 0) {
                throw error("Unsupported unary operation");
            }
            left = new Const(Integer.parseInt(num.toString()));
        }
        // :NOTE: Упростить
        skipWhitespaces();
        while (!eof() && getPriority(ch) >= minPriority) {
            char op = ch;
            nextChar();
            left = newBinaryExpression(op, left, parse(getPriority(op) + 1));
            skipWhitespaces();
        }
        return left;
    }

    @Override
    public TripleExpression parse(String expression) {
        changeSource(new StringSource(expression));
        return parse(Integer.MIN_VALUE);
    }
}