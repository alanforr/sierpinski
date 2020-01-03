(ns sierpinski.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [sierpinski.squares :as sq]
            [sierpinski.triangles :as tr]))

(defn n-times [n f]
  (apply comp (repeat n f)))

(defn setup []
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state.
  {:color 0})

(defn draw-sierpinski [state iter-fn translator shape-render n]
  (q/background 240)
  (q/fill (:color state) 255 255)
  (let [x -400
        y -400]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
                        (doseq [[xp yp s] ((n-times n iter-fn) [[x y 800]])]
                          (apply shape-render (translator xp yp s))))))

(defn draw-state [state]
  (draw-sierpinski state sq/square-iteration sq/square-to-rect q/rect 6))

(q/defsketch sierpinski
  :title "Sierpinski"
  :size [1000 1000]
  ; setup function called only once, during sketch initialization.
  :setup setup
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
