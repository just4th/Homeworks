; func

(defn variable [x] (fn [m] (m x)))

(def constant constantly)

(defn double_divide [num & den] (if (> (count den) 0) (/ num (double (apply * den))) (/ 1.0 num)))
(defn arithMean_ [& args] (/ (apply + args) (double (count args))))
(letfn [(expr [f] (fn [& args] (fn [m] (apply f (map #(% m) args)))))

        (square [x] (* x x))]
  (def add (expr +))
  (def subtract (expr -))
  (def multiply (expr *))
  (def divide (expr double_divide))
  (def mean (expr arithMean_))
  (def varn (expr (fn [& args1] (- (apply arithMean_ (map square args1)) (square (apply arithMean_ args1)))))))


(def negate subtract)

(def funcOps {'+ add, '- subtract, '* multiply, '/ divide, 'negate negate, 'mean mean, 'varn varn})

(defn parseAbstract [ops const_ var_]
  (fn [s] (letfn [(check [op] (cond
                                (list? op) (apply (ops (first op)) (map check (rest op)))
                                (symbol? op) (var_ (name op))
                                :else (const_ op)))]
            (check (read-string s)))))

(def parseFunction (parseAbstract funcOps constant variable))

; obj

(load-file "proto.clj")

(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))

(defn CreateExpression [toString evaluate diff]
  (constructor (fn [this & args] (assoc this :args args)) {:toString toString, :evaluate evaluate, :diff diff}))

(let [args (field :args)]
  (defn CreateOperation [operation name diff_]
    (CreateExpression
      (fn [this] (str "(" name " " (clojure.string/join " " (map toString (args this))) ")"))
      (fn [this vals] (apply operation (map #(evaluate % vals) (args this))))
      (fn [this x] (diff_ (args this) (map #(diff % x) (args this))))))

  (def Constant
    (CreateExpression
      #(str (first (args %)))
      (fn [this _] (first (args this)))
      (fn [_ _] (Constant 0))))

  (def Variable
    (CreateExpression
      #(first (args %))
      (fn [this vals] (vals (first (args this))))
      (fn [this x] (if (= x (first (args this))) (Constant 1) (Constant 0))))))


(def Negate (CreateOperation - 'negate (fn [_ args'] (Negate (first args')))))

(def Add (CreateOperation + '+ (fn [_ args'] (apply Add args'))))

(def Subtract (CreateOperation - '- (fn [_ args'] (apply Subtract args'))))

(declare Multiply)

(defn multiplyDiff [args args']
  (if (zero? (count args))
    (Constant 0)
    (Add (Multiply (first args) (multiplyDiff (rest args) (rest args')))
         (apply Multiply (first args') (rest args)))))

(def Multiply (CreateOperation * '* multiplyDiff))

(defn pow [a b]
  (cond
    (zero? b) (Constant 1)
    (== b 1) a
    (even? b) (recur (Multiply a a) (quot b 2))
    :else (Multiply a (pow a (dec b)))))

(def Divide (CreateOperation
              double_divide
              '/
              (fn [args args']
                (let [num (first args)
                      num' (first args')
                      denom (rest args)
                      denom' (rest args')]
                  (if (empty? denom)
                    (Negate (Divide num' (Multiply num num)))
                    (Divide
                      (Subtract (apply Multiply num' denom) (Multiply num (multiplyDiff denom denom')))
                      (pow (apply Multiply denom) 2)))))))

(def ArithMean (CreateOperation
                 arithMean_
                 'arith-mean
                 (fn [_ args'] (Divide (apply Add args') (Constant (count args'))))))

(def Sign (CreateOperation
            (fn [a] (cond
                      (> a 0) 1
                      (< a 0) -1
                      :else 0))
            'sign
            (fn [_ _] (Constant 0))))

(def GeomMean (CreateOperation (fn [& args] (Math/pow (Math/abs (apply * args)) (/ 1 (count args))))
                               'geom-mean
                               (fn [args args']
                                 (Divide
                                   (Multiply (Sign (apply Multiply args)) (multiplyDiff args args'))
                                   (Multiply (Constant (count args)) (pow (apply GeomMean args) (- (count args) 1)))))))

(def HarmMean (CreateOperation (fn [& args] (double_divide (count args) (apply + (map double_divide args))))
                               'harm-mean
                               (fn [args args']
                                 (let [denom (apply Add (map #(Divide (Constant 1) %) args))
                                       denom' (apply Add (map (fn [a b] (Divide b (Multiply a a))) args args'))]
                                   (Multiply (Constant (count args)) denom denom denom')))))

(def objOps {'+ Add, '- Subtract, '* Multiply, '/ Divide, 'negate Negate, 'arith-mean ArithMean, 'geom-mean GeomMean, 'harm-mean HarmMean})

(def parseObject (parseAbstract objOps Constant Variable))
