(ns ext-sxf-gfx.core
  (:require [overtone.osc :as osc])
;; connect to the project using this namespace
  )

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


;;    _______     ___   _ _______ _    _  _____
;;   / ____\ \   / / \ | |__   __| |  | |/ ____|
;;  | (___  \ \_/ /|  \| |  | |  | |__| | (___
;;   \___ \  \   / | . ` |  | |  |  __  |\___ \
;;   ____) |  | |  | |\  |  | |  | |  | |____) |
;;  |_____/   |_|  |_| \_|  |_|  |_|  |_|_____/




;; define a synth
(defsynth vvv
  []
  (let [a (+ 300 (* 50 (sin-osc:kr (/ 1 3))))
        b (+ 300 (* 100 (sin-osc:kr (/ 1 5))))
        _ (tap:kr :a 60 (a2k a))
        _ (tap:kr :b 60 (a2k b))]
    (out 0 (pan2 (+ (sin-osc a)
                    (sin-osc b))))))

;; run an instance of the synth

(vvv)
;; kill it if you have to
(kill vvv)


(def v (vvv))
@(get-in v [:taps :a])
(kill v)



;; start tapping

  (def mytaps (:taps (vvv)))


;;   @(:a mytaps)




  (defsynth fm [carrier 880 divisor 5.0 depth 4.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin 1.9 3.8 -2.8))
        amp-env (env-gen (lin 1 0 4.0 1) :action FREE)
        filt (rlpf (+ carrier modulator ) 100 0.1)
        _ (tap :mod-env 30 mod-env)]
    (out out-bus  (pan2 (* 0.15 amp-env
                          (sin-osc (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc modulator))))))
;;          (tap :pan :kr (pan2 (* 0.15 amp-env
;;                           (sin-osc (+ carrier
;;                                       (* mod-env (* carrier depth) (sin-osc modulator)))))))
    )))

@(:mod-env (:taps (fm)))

(def qfm (fm 300 0 10 10))
qfm

;; (get (:taps (qfm)))
@(get-in qfm [:taps :mod-env])

;; @(:mod-env qfm )

;; (def qfm (:taps (fm 300 0 10 10)))
;; @(:mod-env qfm )


;;    _____ ______ ____
;;   / ____|  ____/ __ \
;;  | (___ | |__ | |  | |___
;;   \___ \|  __|| |  | / __|
;;   ____) | |___| |__| \__ \
;;  |_____/|______\___\_\___/



;; (def pats {
;;            fm  [1 0 0 0 0 0 0 0]
;;            })


;; (def live-pats (atom pats))

;; (swap! live-pats assoc fm   [1 1 0 1 1 1 0 [1 1 1 1 0 1 1 1]])
;; (swap! live-pats assoc fm   [0])

;; (defn flatten1
;;   "Takes a map and returns a seq of all the key val pairs:
;;       (flatten1 {:a 1 :b 2 :c 3}) ;=> (:b 2 :c 3 :a 1)"
;;   [m]
;;   (reduce (fn [r [arg val]] (cons arg (cons val r))) [] m))

;; (defn live-sequencer
;;   ([curr-t sep-t live-patterns] (live-sequencer curr-t sep-t live-patterns 0))
;;   ([curr-t sep-t live-patterns beat]
;;      (doseq [[sound pattern] @live-patterns
;;              :let [v (nth pattern (mod beat (count pattern)))
;;                    v (cond
;;                       (= 1 v)
;;                       []

;;                       (map? v)
;;                       (flatten1 v)

;;                       :else
;;                       nil)]
;;              :when v]
;;         (at curr-t (apply sound v)))
;;      (let [new-t (+ curr-t sep-t)]
;;        (apply-by new-t #'live-sequencer [new-t sep-t live-patterns (inc beat)]))))



;; (live-sequencer (now) 250 live-pats)