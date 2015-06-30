(ns halic.core
  (:require [overtone.osc :as osc])
  ;;(:use [overtone.polynome] :reload)
  (:use [overtone.core]  ;;if this is core, will use external server!!! live will use internal server
        ))
;; 0 - setup environment

;; (def superServerIP "78.22.74.30")
;; (def superServerIP "192.168.1.228")
;; (def superServerIP "162.252.242.33")
(def superServerIP "localhost")
(def superServerPort 57110)
;; (def superServerPort 4555)


;; 1 - connect to supercollider
( connect-external-server superServerIP  superServerPort)


;; 2 - define & start central bpm-counter
(defsynth bpm [bpm 120]
  (let  [a   (/ bpm 120)
          _ (tap:kr :step 60 (lf-saw:kr a))]
     (out 100 (lf-saw:kr a)))) ;; put the control synths on channel 100 & on

(def b (bpm 10))

(defn mod16 []
  (int (* 16 (/ (+ @(get-in b [:taps :step]) 1) 2 ))))



 ;; 3 - check if monome attached

;;if monome connected
;;eval all fucntions in ctrl.core namespace


 ;; 4 - prompt to start gfxcore

;;start gfxcore, update state




;; 5 - switch to gfxlive
