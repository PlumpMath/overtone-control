(ns ext-sxf-gfx.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]]
))

;; (connect-external-server 57110)

('ext-sxf-gfx.core/tapper :left)

(def
  ^:private
  ^:dynamic
  *taps* nil)



;; start tapping the main stereo output bus.
(defn init-taps
  [] (when-not *taps*
;;        (boot-server-and-mixer)
       (alter-var-root (var *taps*)
                       (constantly (:taps (tapper))))))

(init-taps)



(defn get-taps
  "Deref and return all of our taps as a plain old map."
  [] (when *taps*
       (into {} (map (fn [[k v]] [k @v]) *taps*))))

;; (get-taps)


(defn- scale-in
  "Scale a tap value by 'max'."
  [taps key max]
  (abs (* (get taps key 0) max)))


(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))



(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0})

(def c100 (seq->stream (cycle-between 0 100 1 100)))

(defn update [state]
  ; Update sketch state by changing circle color and position.
  ;;   {:color (mod (+ (:color state) 0.7) 255)
  {:color (* 255 (get (get-taps) :left))
   :angle (+ (:angle state) 0.1)
   :size (* 500 (get (get-taps) :left))
   :c100 (c100)
    })


(defn linereduce [x y]
  (q/line y (* y y) 100 100))

(defn drawline [state y]
   (q/stroke (:color state) 0 0)
   (q/line (:size state) y 100 100)
  )

(defn fib-seq []
  ((fn rfib [a b]
       (cons a (lazy-seq (rfib b (+ a b)))))
    0 1))

(defn randline [state size]
;;     (q/stroke (.intValue 0xffffBC03))
  (q/stroke 255 0 0 )
    (let [[x1 y1 x2 y2] (repeatedly 4 (fn [] (q/random size)))]
    (q/line x1 y1 x2 y2))
  )



(defn circlebounce [state]
   ; Set circle color.
  (q/fill (:color state) 255 255)
  ; Calculate x and y coordinates of the circle.
  (let [angle (:angle state)
        x (* 150 (q/cos angle))
        y (* 150 (q/sin angle))]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      ; Draw the circle.
;;       (q/ellipse (:size state) 0 (:size state) (:size state)))))
      (q/ellipse (:size state) 0 (:size state) (:size state)))))

(defn f [t]
  [(* t (q/sin t))
   (* t (q/cos t))])

(defn draw-plot [f from to step]
  (doseq [two-points (->> (range from to step)
                          (map f)
                          (partition 2 1))]
    ; we could use 'point' function to draw a point
    ; but let's rather draw a line which connects 2 points of the plot
    (apply q/line two-points)))



(defn draw [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  (circlebounce state)
  (drawline state (c100))
;;   (for [a (take 20 (fib-seq))]
;; ;;     [(q/stroke 125 0 0)
;; ;;      (q/line 0 50 100 100)])
;;       [(drawline state a)])

;;   (for [x (take 15 (fib 0 1))
;;              :let [drawline [state x]]]
;;          x)

  (reduce linereduce (take 15 (fib 2 1)) )
  (dotimes [n (:size state)](randline state (* 50 n)))

;;   (q/fill 153)
;;   (q/stroke 0 0 0 )
;;   (q/box 20 100 20)

;;   (q/with-translation [(/ (q/width) 3) (/ (q/height) 2)]
;;    (draw-plot f 0 100 0.01))

  (q/with-translation [(/ (q/width) 3) (/ (q/height) 2)]

    (q/rect -75 -50 75 100)

    )



 )

;; (take 5 (range 10))
(q/defsketch hello-quil
  :title "You spin my circle right round"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update is called on each iteration before draw is called.
  ; It updates sketch state.
  :update update
  :draw draw
  :renderer :p3d
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
