"use strict";

const cnst = a => () => a;

const variables = ['x', 'y', 'z'];

const variable = v => {
    const i = variables.indexOf(v);
    return (...args) => args[i];
    };


const operation = f => (...args) => (...vars) => f(...args.map(a => a(...vars)));

const add = operation((a, b) => a + b);
const subtract = operation((a, b) => a - b);
const multiply = operation((a, b) => a * b);
const divide = operation((a, b) => a / b);
const negate = operation(a => -a);
const madd = operation((a, b, c) => a * b + c);
const floor = operation(a => Math.floor(a));
const ceil = operation(a => Math.ceil(a));
const one = cnst(1);
const two = cnst(2);

const func = {
    "+" : add,
    "-" : subtract,
    "*" : multiply,
    "/" : divide,
    "negate": negate,
    "*+": madd,
    "madd": madd,
    "_": floor,
    "floor": floor,
    "^": ceil,
    "ceil": ceil,
    "one": one,
    "two": two
};
const cnt = {
    "+" : 2,
    "-" : 2,
    "*" : 2,
    "/" : 2,
    "negate": 1,
    "*+": 3,
    "madd": 3,
    "_": 1,
    "floor": 1,
    "^": 1,
    "ceil": 1,
    "one": 0,
    "two": 0
};


const parse = s => {
    const operators = s.trim().split(/\s+/);
    const deque = [];
    for (const a of operators) {
        if (func[a] !== undefined) {
            if (cnt[a] === 0) {
                deque.push(func[a]);
            } else {
                deque.push(func[a](...deque.splice(-cnt[a])));
            }
        } else if (variables.indexOf(a) !== -1) {
            deque.push(variable(a));
        } else {
            deque.push(cnst(+a));
        }
    }
    return deque.pop();
};
