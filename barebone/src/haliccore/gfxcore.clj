(ns halic.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [range-incl perlin-noise-seq seq->stream cycle-between]]
))
  (def width 1440)
  (def height 580)

(defn setup []
  (q/frame-rate 30)

)


(defn update [state]
  {
   :qbeat @(get-in q [:taps :beat])
   :pbeat @(get-in p [:taps :beat])
   :asin @(get-in v [:taps :a])
   :step32 (int (* @(get-in mod16 [:taps :step]) 16))
;;   :saw16 (int (control-bus-get schoolbus))
;;   :r16  (dotimes [n 10]
;;           (assoc :r16 (buffer-get r n) n))
;;    :qa  (- (int @(get (:taps ext-sxf-gfx.core/v) :a)) 255)  ;; state needs to be updated, taps need to exist!
;;    :qb  (- (int @(get (:taps ext-sxf-gfx.core/v) :b)) 255)
;;    :qmod-env (@(get (:taps ext-sfx-gfx.core/qfm) :mod-env))
    })

(defn draw [state]
;;   (q/background (* (control-bus-get schoolbus) 255)
  (q/fill  (* (:qbeat state) 255))
;;   (q/box (* (control-bus-get schoolbus) 50))
;;   (q/fill  (* (:pbeat state) 255))
;;   (q/with-translation [(+ 150 (* 5 (/ (:step32 state) 5))) (+ 0 (/ (:asin state) 2)) 100]
    (q/box 100))
;;   (println (:asin state))
;;   )


(q/defsketch tapdemo
  :title "tapdemo"
  :size [width height]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )
