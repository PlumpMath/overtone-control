;; (ns halic.core
;;   (:gen-class)
;; )

(ns halic.core
;;   (:refer-clojure :exclude [tan])
  (:require [overtone.live :as live])
  (:require [overtone.osc :as osc])
;;   (:require [quil.snippet :refer [defsnippet]])
  (:require [quil.core :refer :all])
  (:require [quil.helpers.seqs :refer [range-incl]])
  (:require [quil.helpers.drawing :refer [line-join-points]])

;;   (:require [shadertone.tone :as t])
)


(def PORT 4242)

; start a server and create a client to talk with it
(def server (osc/osc-server PORT))
;; (def client (osc/osc-client "localhost" PORT))

(live/sc-osc-debug-off)

;; (osc-send)
;; (osc-msg)
(osc/osc-handle server "/test" (fn [msg] (println "MSG: " get(msg :args) )))


;; (osc/osc-recv)
;; osc/osc-listen


(live/definst tem [freq 440 depth 10 rate 6 length 3]
    (* 0.3
       (live/line:kr 0.5 1 length live/FREE)
       (live/saw (+ freq (* depth (live/sin-osc:kr rate))))))

;; (tem 400 4 6 3)
;; (tem 200 4 6 3)
;; (stop)

;; (t/start "shaders/disco.glsl"
;;          :width 800 :height 800
;;          :title "Quasicrystal")

(defn rand-y
  [border-y]
  (+ border-y (rand (- (height) (* 2 border-y)))))

(defn setup []
  (background 255)

  (smooth)
  (stroke 0 500)
  (line 20 50 480 50)
  (stroke 2 50 70)
)


(defn drawbox[cube]
  (with-translation [10 0 0]
    (box cube))
  )


(defn draw []
  (background 255)
  (camera 200 200 200 0 0 0 0 0 -1)
  (drawbox 100)


  (with-translation [0 100 0]
    (box 70 10 50))
  (stroke-weight 2)
;;   (let [step      10
;;         border-x  30
;;         border-y  10
;;         xs        (range-incl border-x (- (width) border-x) step)
;;         ys        (repeatedly #(rand-y border-y))
;;         line-args (line-join-points xs ys)]
;;     (dorun (map #(apply line %) line-args)))
  )


(defsketch example-5
  :title "Random Scribble"
  :setup setup
  :draw draw
  :renderer :p3d
  :size [500 500])



