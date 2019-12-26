(ns sierpinski.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn equilateral-triangle [x y size]
  (let [x2 (- x (/ size 2))
        y2 (- y (* size (Math/sqrt 3) 0.5))
        x3 (- x size)]
    [x y x2 y2 x3 y]))

(defn sierpinski-iteration [triangles]
  (mapcat
    (fn [[x y size]]
      (let [bottom-triangle (equilateral-triangle x y (/ size 2))
            [bx by bx2 by2 bx3 by3] bottom-triangle]
        [[x y (/ size 2)]
         [bx2 by2 (/ size 2)]
         [bx3 by3 (/ size 2)]]))
    triangles))

(defn n-times [n f]
  (apply comp (repeat n f)))

(defn setup []
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state.
  {:color 0})

(defn draw-sierpinski [state n]
  (q/background 240)
  (q/fill (:color state) 255 255)
  (let [angle (:angle state)
        x 150
        y 150]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      (doseq [[xp yp s] ((n-times n sierpinski-iteration) [[x y 300]])]
        (apply q/triangle (equilateral-triangle xp yp s))))))

(defn draw-state [state]
  (draw-sierpinski state 6))

(q/defsketch sierpinski
  :title "Sierpinski"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
