;; (ns halic.core
;;   (:gen-class)
;; )

(ns halic.core
;;   (:refer-clojure :exclude [tan])
  (:require [overtone.live :as live])
  (:require [overtone.osc :as osc])
;;   (:require [quil.snippet :refer [defsnippet]])
  (:require [quil.core :refer :all])
  (:require [quil.helpers.seqs :refer [seq->stream range-incl]])
  (:require [quil.helpers.drawing :refer [line-join-points]])

;;   (:require [shadertone.tone :as t])
)


(def PORT 4242)

; start a server and create a client to talk with it
(def server (osc/osc-server PORT))
;; (def client (osc/osc-client "localhost" PORT))

(live/sc-osc-debug-off)

;; object defs

(def cube1 (atom {:cubesize 10}))
(def beat (atom {:beatnum 0}))
;; (reset! cube1 {:cubesize 100})
;; (:cubesize @cube1)


(osc/osc-handle server "/test" (fn [msg]
                            (let [x (first (:args msg)) y (first(rest(:args msg)))]
                              (reset! cube1 {:cubesize x})
;;                               (println y)
                              )))



(osc/osc-handle server "/beat" (fn [msg]
;;                                 (println msg)
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))


(defn drawbox [cube]
  (box cube)
  (with-translation [0(* (+ 1 (mod4)) cube) 0]
    (box cube)
    )
  )

(defn mod4 []
  (mod  (:beatnum @beat) 4)
  )

(defn kube1 []
  (* 1 (:cubesize @cube1))
  )

(kube1)



(defn setup []
  (background 255)

  (smooth)
  (stroke 0 500)
  (line 20 50 480 50)
  (stroke 2 255 170)

)


(defn draw []
  (background 45  45 255)
  (camera 0 200 200 0 0 0 0 0 -1)

;;   (spot-light 255, 255, 0
;;               100, -40, 200
;;               0, 0.5, 0.5
;;               1.5, 2)

  (point-light 150, 150, 0
               1, 0, 0)

;;   (camera)
  (stroke-weight 1)
  (stroke 0 255 0)
  (fill 121)
  (ortho 0 1000 0 1000)
;;   (mod4 beat :beatnum)

  (with-translation [(* (mod4) 100) 0 0]

;;   (with-translation [10 100 0])

  (drawbox (kube1))

;;   (drawbox (:    cubesize @cube1))
    )
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

;; (:cubesize @cube1)


