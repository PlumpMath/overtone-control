(ns halic.core
  (:require  [overtone.osc :as osc])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [range-incl perlin-noise-seq seq->stream cycle-between]]
            )
  ;;(:use [overtone.polynome])
  (:use [overtone.core]  ;;if this is core, will use external server!!! live will use internal server
        ))
;; 0 - setup environment
(def superServerIP "localhost")
(def superServerPort 57110)
(def width 1440)
(def height 980)

;; 1 - connect to supercollider
( connect-external-server superServerIP  superServerPort)

;; 2 - define & start central bpm-counter
(defsynth bpm [bpm 120]
  (let  [a   (/ bpm 120)
          _ (tap:kr :step 60 (lf-saw:kr a))]
     (out 100 (lf-saw:kr a)))) ;; put the control synths on channel 100 & on

(def b (bpm 60))

(defn mod16 []
  (int (* 16 (/ (+ @(get-in b [:taps :step]) 1) 2 ))))

 ;; 3 - check if monome attached



 ;; 4 -  start gfxcore

;;start gfxcore, update state
(defn setup []
  (q/frame-rate 30)
)
(defn update [state]
  {
  :mod16 (mod16)
   }
  ;; (updateM)
  )

(defn draw [state]
  (q/background 255 255 0)
  (q/with-translation [ (+ 100 (* 10 (mod16))) 100 0]
    (q/box 100))
 )

(q/defsketch tapdemo
  :title "halic"
;;  :size :fullscreen
  :size [width height]
  ;;:features [:present]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )


;; 5 - switch to gfxlive
