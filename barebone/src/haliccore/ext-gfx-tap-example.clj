(ns barebone.core
  (:require [overtone.osc :as osc])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [newtonian.particle-system :as newt]
            [newtonian.utils :as utils]
;;             [quil.helpers.calc :refer [mul-add]]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between steps]]
            [quil.helpers.drawing :refer [line-join-points]]
            )
  (:import newtonian.utils.Vector2D)
            )
  (def width 594)
  (def height 840)

(defn setup []
  (q/frame-rate 30)

)


;;bidirectional comms
(def GrPORT 4242)
;; ; start a server and create a client to talk with it
(def Gserver (osc/osc-server GrPORT))
;; (osc/osc-close Gserver)
(def GsPORT 4243)
(def Gclient (osc/osc-client "192.168.7.191" GsPORT))

(def beat (atom {:beatnum 0}))
(defn mod4 []
  (mod  (:beatnum @beat) 4)
  )

(defn mod8 []
  (mod  (:beatnum @beat) 8)
  )

(defn mod1 []
  (mod  (:beatnum @beat) 1)
  )

(mod4)
(osc/osc-handle Gserver "/beat" (fn [msg]
                                (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))

(osc/osc-handle Gserver "/vvv/a" (fn [msg]
                                (println (:args msg))
;;                                  (reset! beat {:beatnum (first (:args msg))})
                                ))


;;  (osc/osc-rm-all-handlers Gserver)


;;   @(:a ext-sxf-gfx.core/mytaps)
;;   @(:mod-env ext-sxf-gfx.core/qfm)
;;   (:taps ext-sxf-gfx.core/vvv)
  (/ (int @(get (:taps ext-sxf-gfx.core/v) :a)) 255)


(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))

(def rfib16 (reverse (take 16 (fib 5 9))))
(def base (/ width (first rfib16)))
base

(defn dorect [size]
  (q/rect (-  (/ width 2) (* base size) ) (-  (/ height 2) (* base size) )  (* base size) (* base size))

  )
(defn draw-field [{:keys [position]}]
  (let [x (:x position)
        y (:y position)]
    (q/no-stroke)
    (q/fill-int (q/color 255 (* (mod4) 64) 64))
    (q/ellipse x y 10.0 10.0)))

(defn draw-particle [{:keys [position velocity accel]}]
  (let [x (:x position)
        y (:y position)
        a (utils/mag accel)
        s (utils/mag velocity)]
;; (    println s)
;;     (q/no-stroke)

;;     (q/fill-int (q/color 150 (/ (* 220 0.5) s) (* 76 s) 255))
    (q/fill 255 255 0)
    (q/ellipse x y 4 4)
;;     (q/line x y (+ x 200) (* (+ y 200) (mod4)))
    )
  )

;; (newt/add-field (Vector2D. 600 600) 900)

(newt/add-field (Vector2D. 280.0 285.0) 200.0)
(newt/add-emitter (Vector2D. 230.0 280.0) (Vector2D. 2.5 6.0))

(swap! newt/fields pop)
(swap! newt/emitters pop)



(defn mult-add
  "Generate a potential lazy sequence of values which is the result of
   multiplying each s by mul and then adding add. s mul and add may be
   seqs in which case the result will also be seq with the length
   being the same as the shortest input seq (similar to the behaviour
   of map). If all the seqs passed are infinite lazy seqs, the result
   will also be infinite and lazy..

   (mul-add 2 2 1)           ;=> 5
   (mul-add [2 2] 2 1)       ;=> [5 5]
   (mul-add [2 2] [2 4 6] 1) ;=> [5 9]
   (mul-add (range) 2 1)     ;=> [1 3 5 7 9 11 13...] ;; infinite seq
   (mul-add (range) [2 2] 1) ;=> [1 3]"
  [s mul add]
  (if (and (number? mul) (number? add) (number? s))
    (+ add (* mul s))
    (let [[mul nxt-mul] (if (sequential? mul)
                          [(first mul) (next mul)]
                          [mul mul])
          [add nxt-add] (if (sequential? add)
                          [(first add) (next add)]
                          [add add])
          [s nxt-s]     (if (sequential? s)
                          [(first s) (next s)]
                          [s s])]
      (lazy-seq
       (cons (+ add (* mul s)) (if (and nxt-mul nxt-add nxt-s)
                                 (mul-add  nxt-s nxt-mul nxt-add)
                                 []))))))

(defn noise100 []
  (q/stroke 255 0 0)
  (dotimes [_ 1]
    (let [radius      100
          cent-x      250
          cent-y      150
          start-angle (rand 360)
          end-angle   (+ 1440 (rand 1440))
          angle-step  (+ 5 (rand 3))
          rad-noise   (steps (rand 10) 0.05)
          rad-noise   (map #(* 200 (q/noise %)) rad-noise)
          rads        (map q/radians (range-incl start-angle end-angle angle-step))
          radii       (steps 10 0.5)
          radii       (map (fn [rad noise] (+ rad noise -100)) radii rad-noise)
          xs          (map (fn [rad radius] (mult-add (cos rad) radius cent-x)) rads radii)
          ys          (map (fn [rad radius] (mult-add (sin rad) radius cent-y)) rads radii)
          line-args   (line-join-points xs ys)
          ]
      (q/stroke (rand 20) (rand 50) (rand 70) 80)
;;       (println radii)
;;       (dorun (map #(apply q/line %) line-args))
         ))
  )


;;  (line-join-points [1 2 3] [4 5 6])

;; (line-join-points [[1 4] [2 5] [3 6]])


(defn update [state]
  (newt/add-new-particles)
  (newt/update-particles width height)
  ; Update sketch state by changing circle color and position.
  ;;   {:color (mod (+ (:color state) 0.7) 255)
  {

   :qa  (- (int @(get (:taps ext-sxf-gfx.core/v) :a)) 255)  ;; state needs to be updated, taps need to exist!
   :qb  (- (int @(get (:taps ext-sxf-gfx.core/v) :b)) 255)
;;    :qmod-env (@(get (:taps ext-sfx-gfx.core/qfm) :mod-env))
    })
@newt/particles


(defn draw [state]
  (q/background 255)
  (q/fill  0 (* (mod4) 55) 15)
;;   (q/fill 255  (int (:qa state)) 0)
;;   (q/rect 100 100 (* (mod4) 100) 100)
;;   (q/fill 0  (int (:qb state)) 0)
  (q/rect 250 100 (:qb state) 100)
  (q/stroke 0)
  (dotimes [n 16] (dorect (* (/ (:qa state) 25) (nth rfib16 n) )))

  (let [particles @newt/particles
        fields @newt/fields]
   (doseq [p particles]
     (draw-particle p))
   (doseq [f fields]
     (draw-field f)))

;;    (noise100)
  )


(q/defsketch tapdemo
  :title "tapdemo"
  :size [width height]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )
