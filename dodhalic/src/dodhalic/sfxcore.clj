(ns dodhalic.sfxcore
  (:require [dodhalic.core])
  (:use [overtone.live])
        )

;;; everything that is dependency for live code to come...

;; connect to the project using this namespace


;;    _____ ____  __  __ __  __  _____
;;   / ____/ __ \|  \/  |  \/  |/ ____|
;;  | |   | |  | | \  / | \  / | (___
;;  | |   | |  | | |\/| | |\/| |\___ \
;;  | |___| |__| | |  | | |  | |____) |
;;   \_____\____/|_|  |_|_|  |_|_____/




;;bidirectional comms
(def SrPORT 4243)
;; ; start a server and create a client to talk with it
(def Sserver (osc/osc-server SrPORT))
;; (osc/osc-close Sserver)
(def SsPORT 4242)
(def Sclient (osc/osc-client "localhost" SsPORT))




;; send the beat out!
(def beat (atom {:beatnum 0}))
(def metro (metronome 200))
;; for sending the beatnumber over to quil!
(defn beat2quil [nome ]
    (let [beat (nome)]
        (apply-by (nome (inc beat)) (osc/osc-send Sclient "/beat" beat))
        (apply-by (nome (inc beat)) beat2quil nome [])
      ))

(beat2quil metro)
(:beatnum @beat)


