(ns barebone.core
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

(defn savef []
  (q/save-frame "pretty-pic-####.jpg"))

;; start tapping the main stereo output bus.
(defn init-taps
  [] (when-not *taps*
;;        (boot-server-and-mixer)
       (alter-var-root (var *taps*)
                       (constantly (:taps (tapper))))))

(init-taps)


(defn show-frame-rate [options]
  (let [; retrieve existing draw function or use empty one if not present
        draw (:draw options (fn []))
        ; updated-draw will replace draw
        updated-draw (fn []
                       (draw) ; call user-provided draw function
                       (q/fill 0)
                       (q/text-num (q/current-frame-rate) 10 10))]
    ; set updated-draw as :draw function
    (assoc options :draw updated-draw)))



(defn get-taps
  "Deref and return all of our taps as a plain old map."
  [] (when *taps*
       (into {} (map (fn [[k v]] [k @v]) *taps*))))

(get-taps)


(defn- scale-in
  "Scale a tap value by 'max'."
  [taps key max]
  (abs (* (get taps key 0) max)))


(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))



(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
;;   (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0})

(def c100 (seq->stream (cycle-between 0 100 1 100)))
(c100)



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

    (let [[x1 y1 x2 y2] (repeatedly 4 (fn [] (q/random size)))]
      (q/stroke 255 )
      (q/line x1 y1 x2 y2))


 )
(defn shp [x1 y1 z1 x2 y2 z2 x3 y3 z3 state]
  (def noisy (* 0.1 (:color state)))
  (q/fill (.intValue 0xffffBC03))

  (q/begin-shape :triangle-fan)
  (q/vertex (+ x1 noisy) (+ y1 noisy) (+ z1 noisy))
  (q/vertex (+ x2 noisy) (+ y2 noisy) (+ z2 noisy))
  (q/vertex (+ x3 noisy) (+ y3 noisy) (+ z3 noisy))
  (q/end-shape :close)
  )

(def vx1 (vec (drop 12 (take 15 (fib-seq)))) )
(def vx2 (map (fn [x] (* x 0.5)) vx1))
(def vx3 (map (fn [x] (/ x 15))(vec (drop 18 (take 21 (fib-seq))))))


(def widthmap [0 2 5 6 9])



(defn randlines [state size]

    (let [[x1 y1 x2 y2] [150 100 250 500]]
      (q/stroke 255 )
      (q/line x1 y1 x2 y2))
)





(defn vertexseed [size]
  (take size (inc 1  ))
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

  (flatten [vx1 vx2 vx3])



;; (defn divs (/ (c100) 4))


;;   ////DRAW - DRAW - DRAW /////

  (drop 14 (take 15 (fib 0 1)))

;;   (q/width)
  (def repsize 8)
  repsize


  (defn dorect [size]
    (q/rect size size size size)
    (q/line  )

    q/state

;;     q/sin  q/radians
;;     (q/curve 5 26 73 24 73 61 15 65 )
     (q/curve-tightness 2)
;;     :curve-tightness 5
    )

(defn draw [state]
  (q/background 255)
;;   (randline state 100)
;;   (dotimes [n 10] (reduce (fn [a b] (q/line (* a 5) (* b 10) (* a 10) (* b 3)))  [50 10 3 4]))

;;   (circlebounce state)
;;   (drawline state (c100))
;;   (for [a (take 20 (fib-seq))]
;; ;;     [(q/stroke 125 0 0)
;; ;;      (q/line 0 50 100 100)])
;;       [(drawline state a)])
  (q/fill 255 255 255 18)
;;   (q/rect 0 0 100 100)
;;   (q/rect 50 50 100 100)

  (q/stroke 255 0 0 )
  (dotimes [n repsize]  (dorect (nth (take repsize (fib 0 1)) n) ) )
  (q/stroke 0 0 255)
  (dotimes [n repsize]  (dorect (nth (take repsize [2 25 66 88 45 48 445 255 12 ]) n) ) )


  (dotimes [n (int  (/  (c100) 10))] (q/rect (* (/ (q/width) (+ (c100) 1)) n) 0 (* (:color state) (/ (q/width) 80)) (q/height) ))
  (dotimes [n 4] (q/rect  0 (* (/ (q/height) 4) n)  (q/width) (* (:color state) (/ (q/height) 80))  ))


  (q/stroke 255 128 2)
;;   (dotimes [n 15] (q/line (drop (+ n 1)(take (+ n 2) (fib 0 1))   )0 ))

;;   (for [x (take 15 (fib 0 1))
;;              :let [drawline [state x]]]
;;          x)

;;   (reduce linereduce (take 15 (fib 2 1)) )
;;   (dotimes [n (:size state)](randline state (* 50 n)))



  (q/with-translation [(/ (q/width) 3) (/ (q/height) 2)]
    (q/fill 153)
  (q/stroke 255 255 255 )
  (q/box 2 100 20)
;;    (draw-plot f 0 100 0.01))
   (q/with-translation [(- (/ (q/width) 5) 100) (- (/ (q/height) 5) 500)]
    (q/stroke 255 1 255)
      (dotimes [n 50] (q/line  (* n (:color state)) (* n 10) (* n 40) (* n 100)))

    )

;;   (q/with-translation [(/ (q/width) 5) (/ (q/height) 5)]
;;     (q/stroke 255)
;;     (dotimes [n 500] (q/line  (* n (:color state)) (* n 10) (* n 40) (* n 100)))
;;     (shp 144 233 3 72.0 116.5 1.5 258/15 4181/15 4 state)
;;     (q/with-rotation [(:color state)]

;;       (q/with-fill [0 0 255 ]
;;         (q/box 20 100 120)
;;         (q/rect -75 -50 75 100)
;;         (draw-plot f 0 100 0.01)
;;         ))
;;   )

;;   (shp (flatten [vx1 vx2 vx3]))


;;   (into vx1 vx2 vx3)
;;   (dotimes [n 4] (q/rect (* n (/ q/width n)) 0 (* n (/ q/width n)) q/height))
   (q/stroke 0 0 0)
;;   (doseq [t (range 0 (* 10(:color state)) 0.01)]
    (doseq [t (range 0 100 0.01)]
    (q/point (* t (q/tan t))
             (* (* 2 t) (q/cos t)))

    )

  )



  )



(defn key-press [a b]
  (savef)
  )


;; (take 5 (range 10))
(q/defsketch hello-quil
  :title "You spin my circle right round"
  :size [594 840]
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
  :middleware [m/fun-mode]
;;   :key-pressed   key-press
;;   :middleware [m/fun-mode m/navigation-3d]
;;   :middleware [show-frame-rate]
  )
