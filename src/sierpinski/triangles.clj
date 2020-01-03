(ns sierpinski.triangles)

(defn equilateral-triangle [x y size]
  (let [x2 (- x (/ size 2))
        y2 (- y (* size (Math/sqrt 3) 0.5))
        x3 (- x size)]
    [x y x2 y2 x3 y]))

(defn triangle-iteration [triangles]
  (mapcat
    (fn [[x y size]]
      (let [bottom-triangle (equilateral-triangle x y (/ size 2))
            [bx by bx2 by2 bx3 by3] bottom-triangle]
        [[bx by (/ size 2)]
         [bx2 by2 (/ size 2)]
         [bx3 by3 (/ size 2)]]))
    triangles))