"use strict";

const func = {};
const cnt = {};

function CreateExpression(toString, evaluate, diff) {
    const constructor = function (...args) {
        this.args = args;
    }
    constructor.prototype._argsToString = function () {
        return this.args.map(a => a.toString()).join(" ");
    }
    constructor.prototype.toString = toString;
    constructor.prototype.evaluate = evaluate;
    constructor.prototype.diff = diff;
    return constructor;
}

const CreateOperation = (op, c, f, diff) => {
        cnt[op] = c;
        return func[op] = CreateExpression(
            function () {
                return this._argsToString() + " " + op;
            },
            function (...vars) {
                return f(...(this.args.map(a => a.evaluate(...vars))));
            },
            diff
        );
}
const Const = CreateExpression(
    function() {
        return this.args[0].toString();
    },
    function () {
        return +this.args[0];
    },
    function () {
        return new Const(0);
    }
)

const variables = ['x', 'y', 'z'];

const Variable = CreateExpression(
    function () {
        return this.args[0];
    },
    function (...vars) {
        return +vars[variables.indexOf(this.args[0])];
    },
    function (x) {
        return new Const(x === this.args[0] ? 1 : 0);
    }
);

const Add = CreateOperation(
    '+',
    2,
    (a, b) => (a + b),
    function (x) {
        return new Add(this.args[0].diff(x), this.args[1].diff(x));
    }
);

const Subtract = CreateOperation(
    '-',
    2,
    (a, b) => (a - b),
    function (x) {
        return new Subtract(this.args[0].diff(x), this.args[1].diff(x));
    }
);

const Multiply = CreateOperation(
    '*',
    2,
    (a, b) => (a * b),
    function (x) {
        return new Add(new Multiply(this.args[0], this.args[1].diff(x)), new Multiply(this.args[0].diff(x), this.args[1]));
    }
);

const Divide = CreateOperation(
    '/',
    2,
    (a, b) => (a / b),
    function (x) {
        return new Divide(
            new Subtract(new Multiply(this.args[0].diff(x), this.args[1]), new Multiply(this.args[0], this.args[1].diff(x))),
            new Multiply(this.args[1], this.args[1])
        );
    }
);

const Negate = CreateOperation(
    "negate",
    1,
    a => -a,
    function (x) {
        return new Negate(this.args[0].diff(x));
    }
);

const Hypot = CreateOperation(
    "hypot",
    2,
    (a, b) => (a * a + b * b),
    function (x) {
        return new Add(new Multiply(this.args[0], this.args[0]), new Multiply(this.args[1], this.args[1])).diff(x);
    }
);

const HMean = CreateOperation(
    "hmean",
    2,
    (a, b) => 2 / (1 / a + 1 / b),
    function (x) {
        return new Divide(new Const(2),
            new Add(
                new Divide(new Const(1), this.args[0]),
                new Divide(new Const(1), this.args[1])
            )
        ).diff(x);
    }
);

const parse = s => {
    const operators = s.trim().split(/\s+/);
    const deque = [];
    for (const a of operators) {
        if (func[a] !== undefined) {
                deque.push(new func[a](...deque.splice(-cnt[a])));
        } else if (variables.indexOf(a) !== -1) {
            deque.push(new Variable(a));
        } else {
            deque.push(new Const(+a));
        }
    }
    return deque.pop();
};
