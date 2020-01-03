(ns sierpinski.squares)

(defn square-to-rect [x y size]
  [x y size size])

(defn square-iteration [squares]
  (mapcat
    (fn [[x y size]]
      (let [smaller (/ size 3)
            adds [0 smaller (* smaller 2)]]
        (for [addx adds
              addy adds
              :when (not= smaller addx addy)]
          [(+ x addx) (+ y addy) smaller])))
    squares))