(ns halic.core
  (:require [overtone.osc :as osc])

  ;;(:use [overtone.polynome] :reload)
  (:use [overtone.core]  ;;if this is core, will use external server!!! live will use internal server
        ))
;; (def superServerIP "78.22.74.30")
;; (def superServerIP "192.168.1.228")
;; (def superServerIP "162.252.242.33")
(def superServerIP "localhost")
(def superServerPort 57110)
;; (def superServerPort 4555)
 (connect-external-server superServerIP  superServerPort)
;; (defonce (connect-external-server "192.168.1.228"  4555))

;; (server-disconnected?)
;; ;;shared defs
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


;; (defn sendTick [bpm]
;;   "starts a tick clock at bpm"

;;   )


;; (sin PI


;; define a synth
(defsynth vvv
  []
  (let [a (+ 300 (* 50 (sin-osc:kr (/ 1 3))))
        b (+ 300 (* 100 (sin-osc:kr (/ 1 5))))
        _ (tap:kr :a 60 (a2k a))
        _ (tap:kr :b 60 (a2k b))]
    (out 0 (pan2 (+ (sin-osc a)
                    (sin-osc b))))))

;; ;; run an instance of the synth

(vvv)
;; kill it if you have to
(kill vvv)


(def v (vvv))
@(get-in v [:taps :a])
;; (kill v)

(defsynth foo [freq 200 dur 0.5]
  (let [src (saw [freq (* freq 1.01) (* 0.99 freq)])
        low (sin-osc (/ freq 2))
        filt (lpf src (line:kr (* 10 freq) freq 10))
        env (env-gen (perc 0.1 dur) :action FREE)]
    (out 0 (pan2 (* 0.8 low env filt)))))

(foo 200)

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
;;  (kill qqq)
 (def q (qqq))
@(get-in q [:taps :beat])
; (kill v)



(def p (qqq 240))
(def s (qqq 15))
(volume 0)
 (defsynth saw16
   [bpm 120]
   (let  [a (/ bpm 60)
          _ (tap:kr :step 32 (lf-saw:kr a))]
     (out 0 (lf-saw:kr a))))

(def ss (saw16 15))

(ctl ss  :bpm 1)
(ctl mod16 :bpm 10 )
;;  (scope-out 0)
;;  (show-graphviz-synth saw16)
 (def mod16 (saw16))
(println  (int (* @(get-in mod16 [:taps :step]) 16)))
;; (definst sq [freq 120]
;;    (* (env-gen (perc 0.1 4.8) :action FREE)
;;      (square freq)))
;; (sq 320)

;; (server-connected?)

(defsynth beep3 [freq 440 amp 0.1]
  (let [a (* amp (sin-osc freq))
        b (* amp (sin-osc freq))]
    (out 0 [a b])))

(beep3)

(volume 0.0)
;; init algorave


;; (def r (buffer 16))

;; (take 16 (range 16))

;; (map #(buffer-set! r %1 %2) (take 16 (range 16)) [1/3 2/2 1/4 2/4 1/2 1/2 1/2 2/9 1/8 2/4 1/4 2/5 4/8 5/8 4/7 9/9 1/7 2/4 8/3 9/9])

;; (buffer-get r 1)
;; (buffer-get r 12)
;; r


;; (buffer-read r)
;;busses


;;pseudocode
;; ;; define control bus
;; (def schoolbus (control-bus 1 "schoolbus"))

;; ;; define control synth with controlbus output

;; (defsynth controlsaw16
;;    [bpm 120]
;;    (let  [a (/ bpm 60)
;;           _ (tap:kr :step 32 (lf-saw:kr a))]
;;      (out:kr schoolbus (lf-saw:kr a))))


;; ;; define audio synth, with control bus as input and audio out

;; (defsynth ddd
;;   [bpm 120 bpm2 120]
;;   (let [a (+ 300 (* 50 bpm))
;;         b (+ 300 (* 100  bpm2))]
;;     (out 0 (pan2 (+ (sin-osc 440)
;;                     (sin-osc b))))))

;; ;; start audio synth and control synth

;; (ddd 120 60)
;;  (ctl 47 :bpm (controlsaw16 20))
;; visualise control synth
;; (stop)
;; (getcon schoolbus)

;;         (audio-bus-monitor 0)

;; 0 - setup environment
(defn setupenv []
  (def superServerIP "localhost")
  (def superServerPort 57110)
  )
(setupenv)

;; 1 - connect to supercollider
( connect-external-server superServerIP  superServerPort)

(connected)
;; 2 - define & start central bpm-counter
(defsynth bpm
   [bpm 120]
   (let  [a (/ bpm 60)
          _ (tap:kr :step 60 (lf-saw:kr a))]
     (out 100 (lf-saw:kr a)))) ;; put the control synths on channel 100 & on

(def b (bpm 120))
(defn mod16 []
  (int (* 16 (/ (+ @(get-in b [:taps :step]) 1) 2 ))))


(println  ( mod16))



 ;; 3 - check if monome attached

;;if monome connected
;;eval all fucntions in ctrl.core namespace


 ;; 4 - prompt to start gfxcore

;;start gfxcore, update state




;; 5 - switch to gfxlive
