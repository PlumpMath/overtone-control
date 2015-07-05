(ns halicgfx.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [range-incl perlin-noise-seq seq->stream cycle-between]]
))
  (def width 1440)
  (def height 980)

(defn setup []
  (q/frame-rate 30)
)


(defn update [state]
  {
  ;; :qbeat @(get-in q [:taps :beat])
  ;; :pbeat @(get-in p [:taps :beat])
  ;; :slowbeat (+ 16 (int (* @(get-in mod16 [:taps :step]) 16)))
  ;; :asin @(get-in v [:taps :a])
  ;; :step32 (int (* @(get-in mod16 [:taps :step]) 16))

   ;;if controller is up
  ;; :bpm (int (+ 0 (* @(get-in bpm [:taps :step]) 8 ) ))
   :mod16 (mod16)
;;   :saw16 (int (control-bus-get schoolbus))
;;   :r16  (dotimes [n 10]
;;           (assoc :r16 (buffer-get r n) n))

   }
  ;;(updateM)

  )

(defn draw [state]
  (q/background 255 255 0)
  (q/with-translation [ (+ 1000 (* 10 (mod16))) 100 100]
    (q/box 10))
 )

(q/defsketch tapdemo
  :title "tapdemo"
;;  :size :fullscreen
  :size [width height]
  ;;:features [:present]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )
