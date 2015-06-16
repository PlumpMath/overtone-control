(ns halic.core
  (:require [overtone.osc :as osc])
  (:use [overtone.core]  ;;if this is core, will use external server!!! live will use internal server
       ))
(def superServerIP "192.168.1.228")

(connect-external-server superServerIP 57110)

;;shared defs
(def gfxOSCserverPort 4242)
(def sndOSCserverPort 4243)


;; gfx helpers
(defn startgfxOSC [gfxIP ]
  "bidirectional comms for halic gfx-sfx sync over OSC. e.g. (startgfxOSC '127.0.0.1')"
  ;; ; start a server and create a client to talk with it
  (def Sserver (osc/osc-server gfxOSCserverPort))
  ;; (osc/osc-close Sserver)
  (def Sclient (osc/osc-client gfxIP sndOSCserverPort))

)



;; sfx helpers
(defn startsndOSC [gfxIP ]
  "bidirectional comms for halic gfx-sfx sync over OSC. e.g. (startsndOSC '127.0.0.1')"
  ;; ; start a server and create a client to talk with it
  (def Sserver (osc/osc-server sndOSCserverPort))
  ;; (osc/osc-close Sserver)
  (def Sclient (osc/osc-client gfxIP gfxOSCserverPort))

)


(defn sendTick [bpm]
  "starts a tick clock at bpm"

  )


;; ;; define a synth
;; (defsynth vvv
;;   []
;;   (let [a (+ 300 (* 50 (sin-osc:kr (/ 1 3))))
;;         b (+ 300 (* 100 (sin-osc:kr (/ 1 5))))
;;         _ (tap:kr :a 60 (a2k a))
;;         _ (tap:kr :b 60 (a2k b))]
;;     (out 0 (pan2 (+ (sin-osc a)
;;                     (sin-osc b))))))

;; ;; run an instance of the synth

;; (vvv)
;; ;; kill it if you have to
;; (kill vvv)


;; (def v (vvv))
;; @(get-in v [:taps :a])
;; (kill v)



;; ;; start tapping

;;   (def mytaps (:taps (vvv)))

;;   @(:a mytaps)

;; (def bpm 150)
;;    (int (/ 1000 (/ bpm 60)))

(defsynth qqq
  [bpm 120]
  (let [a (/ bpm 60)
        _ (tap:kr :beat 100 (lf-pulse:kr a))]
    (out 0 (lf-pulse:kr a))))

(qqq)
 (kill qqq)
 (def q (qqq))
@(get-in q [:taps :beat])
;; (kill v)


;; (definst sq [freq 120]
;;    (* (env-gen (perc 0.1 4.8) :action FREE)
;;      (square freq)))
;; (sq 320)

;; (server-connected?)



;; init algorave


