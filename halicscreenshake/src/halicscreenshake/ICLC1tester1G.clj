(ns dagocode.overtone
 (:use [overtone.live]
       [overtone.inst.drum]
       [overtone.inst.io]
       [overtone.inst.synth]
       [overtone.osc.util]
       [overtone.osc.peer]
       [overtone.osc.dyn-vars]
       ))



(def PORT 4242)
(def client (osc-client "192.168.2.1" PORT))
;; change localhost to viz IP


(def bus1 (audio-bus))
(def bus2 (audio-bus))




(definst kickA [freq 105 dur 1.2 width 0.5 amp -20 out-bus 0]
  (let [freq-env (* freq (env-gen (perc 0.02 (* 0.49 dur))))
        env (env-gen (perc 0.019 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.0800)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        src2 (sin-osc-fb freq-env)
        filt (lpf (+ sqr src src2) 100)
        drum (+ sqr (* env filt))]
        (compander drum drum 0.2 1 0.1 0.01 0.01)
))

(definst kick6 [freq 105 dur 1.2 width 0.5 amp -20 out-bus 0]
  (let [freq-env (* freq (env-gen (perc 0.020 (* 0.49 dur))))
        env (env-gen (perc 0.19 dur) 1 1 0 1 dur)
        sqr (* (env-gen (perc 0 0.800)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        src2  (sin-osc-fb freq-env)
        filt (lpf (+ sqr src src2) 100)
        drum (+ sqr (* env filt))]
        (compander drum drum 0.2 1 0.1 0.01 0.01)
    ))

(swap! live-pats assoc kickA [1 0 0 0 0 0 x 0 0 0 0 0 1 0 0 0])
(swap! live-pats assoc kickA [1 0 0 0])
(swap! live-pats assoc kickA [0])

(definst kickB [freq 100 dur 1.2 width 0.5 amp -20 out-bus 1]
  (let [freq-env (* freq (env-gen (perc 0.02 (* 0.49 dur))))
        env (env-gen (perc 0.019 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.800)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        src2 (sin-osc-fb freq-env)
        filt (lpf (+ sqr src src2) 100)
        drum (+ sqr (* env filt))]
    (compander drum drum 0.2 1 0.1 0.01 0.01)
    ))
(def player (midi-poly-player kickB))
(midi-player-stop)



(def kickdisto (inst-fx! kickA fx-distortion2))
    (ctl kickdisto :amount 0.70)


(def kickcompress (inst-fx! kickA fx-compressor))
    (ctl kickcompress :amount 0.10)
(def kickChorus (inst-fx! kickA fx-chorus))
     (ctl kickChorus :rate 0.2 :depth 0.4)
(def kickfilter (inst-fx! kickA fx-rlpf))
    (ctl kickfilter :cutoff (ranged-rand 200 7000) :res 0.8)


(clear-fx kickA)

;q x





(definst snareA [freq 400 dur 0.20 width 0.5 pan 0.5 amp -1.0]
  (let [freq-env (* freq (env-gen (perc -0.4 (* 0.24 dur))))
        env (env-gen (perc 0.006 dur) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0 0.04)) (pulse (* 2.9 freq) width))
        src (c-osc freq-env)
        src2 (sin-osc freq-env)
        filt (glitch-rhpf (+ sqr noise src src2) 400)
        clp (clip2 filt 0.6)
        drum (+ sqr (* env clp))]
        (compander drum drum 0.4 1 0.02 0.01 0.01)
    ))

(swap! live-pats assoc snareA [0 0 1 0 0 0 1 0 0 0 0])

(swap! live-pats assoc clap [0 0 0 0 1 0 0 0])
(swap! live-pats assoc snareA [0])
(inst-fx! clap fx-freeverb)
(clear-fx clap)


(inst-fx! snareA fx-chorus)
(inst-fx! snareA fx-distortion2)
(def snareverb (inst-fx! snareA fx-freeverb))


(ctl snareverb :mix 0.1)
(ctl snareverb :room 0.5)
;; ctl werkt hier niet


(inst-fx! snareA fx-compressor)
(inst-fx! snareA fx-noise-gate)

(def lowpass (inst-fx! snareA fx-rlpf))
(ctl lowpass :cutoff (ranged-rand 500 2000))
(ctl lowpass :res 2.6)
;werkt

(clear-fx snareA)




(definst c-hat [amp 0.7 t 0.03]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
             noise (white-noise)
             sqr (* (env-gen (perc 0.07 0.04)) (pulse 880 0.8))
             filt (rhpf  (+ sqr noise) 300 0.5)
       ]
             (* amp env filt)
    ))

(swap! live-pats assoc c-hat [1 0 p o 1 0 p o 1 0 p o 1 0 p o s 0 0 0 0 0 s 0])

(def hhrev (inst-fx! c-hat fx-freeverb))
(ctl hhrev :wet-dry 0.1)
(ctl hhrev :room-size 0.6)
(def hhecho (inst-fx! c-hat fx-echo ))
(ctl hhecho :delay-time 1.50)
(def hhchor (inst-fx! c-hat fx-chorus))
(ctl hhchor :depth 0.4 :rate 0.8)
(clear-fx c-hat)
; o p



(def clapverb (inst-fx! clap fx-freeverb))
(ctl clapverb :wet-dry 0.15)
(clear-fx clap)

(swap! live-pats assoc clap [0 0 0 0 1 0 0 0])

(defsynth fmchord [carrier 440 divisor 4.0 depth 2.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin 1.9 3.8 -2.8))
        amp-env (env-gen (lin 1 0 4.0 1) :action FREE)
        filt (rlpf (+ carrier modulator ) 100 0.1)]
    (out bus2  (pan2 (* 0.15 amp-env
                          (sin-osc (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc modulator)))))))
    ))


;;                              1       2       3       4       5       6       7       8
(swap! live-pats assoc fmchord [1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
                                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 d 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
                                1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0
                                0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 e 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0])
(swap! live-pats assoc fmchord [0])

(defsynth chordreverb [mix 0.5 room 0.6 damp 0.1]
  (out 0 (free-verb (in-feedback bus2 2) mix room damp)))

(def chordreverb-ctrl (chordreverb))
; ___  __  __   _____
;| __||  \/  | |_   _|___  _ _   ___  ___
;| _| | |\/| |   | | / _ \| ' \ / -_ (_-<
;|_|  |_|  |_|   |_| \___/|_||_|\___|/__/


(defsynth fmtones [carrier 440 divisor 8.0 depth 8.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin-rand -0.2 0.4 -2.8))
        amp-env (env-gen (lin 0 -0.2 0.1 1 ) :action FREE)
        filt (glitch-rhpf:ar (+ carrier modulator ) 500 2.6)
             ]
      (out bus1 (pan2 (* 0.60 amp-env
                          (lf-saw (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc  modulator)))))))))

(swap! live-pats assoc fmtones [-a -a -a -a 0 0 0 0 1 1 1 1 d d e e])
(swap! live-pats assoc fmtones [-a -b -c -d a b c d e f])
(swap! live-pats assoc fmtones [0])

(defsynth phasemod [car-freq 440.00, mod-freq 441.00, pm-index 4.5, mod-phase 1.8]
  (let [modulator (/ car-freq mod-freq)
         mod-env (env-gen (lin-rand 1.3 0.4 0.8))
        amp-env (env-gen (lin 0.2 0.2 0.1 1 ) :action FREE)
        filt (rlpf (+ car-freq modulator ) 500 2.6)
             ]
      (out bus2 (pan2 (* 0.30 amp-env
                          (sin-osc (+ car-freq
                                      (* mod-env (* pm-index) (sin-osc modulator)))))))))

(swap! live-pats assoc phasemod [1 0 0 0 0 0 d 0 0 0 0 0 0 0])
(swap! live-pats assoc phasemod [0])


;; car-freq - Carrier frequency
;; mod-freq - Modulation frequency
;; pm-index - Phase modulation index
;; mod-phase - Modulation phase

;; Please add some docs!

;; Categories: Composite Ugen
;; Rates: [ :ar, :kr ]
;; Default rate: :ar





(swap! live-pats assoc fmtones [1 1 1 1 0 0 0 0 -a a a a h h d d])
(swap! live-pats assoc fmtones [a b c d e])
(swap! live-pats assoc fmtones [0])
;; (ctl fmtones-ctrl :carrier 220)

(defsynth fmreverb [mix 0.5 room 0.6 damp 0.1]
  (out 0 (free-verb (in-feedback bus1 2) mix room damp)))

(def fmreverb-ctrl (fmreverb))
(ctl fmreverb-ctrl :room 0.4)
(ctl fmreverb-ctrl :mix (ranged-rand 0.1 0.8))
(ctl fmreverb-ctrl :damp 0.4)
(kill fmreverb)

(defsynth fmfilter [cutoff (ranged-rand 500 8000) res 0.8]
  (out 0 (rlpf (in-feedback bus1 2) cutoff res)))
(def fmfilter-ctrl (fmfilter))
(ctl fmfilter-ctrl :cutoff (ranged-rand 200 5000))
(kill fmfilter)
;  pulse, p-sin-grain, v-osc (clean), lf-par, var-saw
;  Chaos  ()
;  Line   (amp-comp, amp-comp-a, k2a, line )
;  Random (rand-seed, lonrenz-trig )
;  Noise  (lf-noise, hasher , mantissa-mask)

;; (fmtones :depth 8.0)
;; (fmtones :inst-volume 8.0)
(def clapverb)
;; (stop-all)




(def pats {clap   [0 0 0 0 0 0 0]
           c-hat  [0 0 0 0 0 0]
           snareA [0 0 0 0 0 0 0 0]
           kickA  [ 0 0 0 0 0 0 0 0 0 0 0 0 0 0]
           fmchord [0]
           fmtones   [0]
           phasemod [0]

           })




;kick control
(def q {:amp 0.5 :dur 1.0})
(def x {:amp 2.0 :dur 4.0})
(def y {:freq 75})
;; (def z {:amp})

;hh control
(def o {:amp 0.2})
(def p {:amp 0.5})
(def s {:t 0.18})

;fmtones control
 (def -a {:carrier 261.63 :car-freq 261.63})
 (def -b {:carrier 277.18})
 (def -c {:carrier 293.66})
 (def -d {:carrier 311.13})
 (def -e {:carrier 329.63})
 (def -f {:carrier 349.23})
 (def -g {:carrier 369.99})
 (def -h {:carrier 392.00})
 (def -i {:carrier 415.00})
 (def -j {:carrier 440.00})
 (def -k {:carrier 466.16})
 (def -l {:carrier 493.88})
 (def a {:carrier 523.25
         :divisor (ranged-rand 5.0 9.0) :depth (ranged-rand 0.1 9.0)
         })
 (def b {:carrier 554.37})
 (def c {:carrier 587.33 :car-freq 587.33})
 (def d# {:carrier 622.25 :car-freq 622.25})
 (def e {:carrier 659.25})
 (def f {:carrier 698.46 :depth (ranged-rand 0.2 0.8)})
 (def g {:carrier 739.99})
 (def h {:carrier 783.99
         :depth 9.0
         :mix 0.5
         })
 (def i {:carrier 830.61})
 (def j {:carrier 880.00})
 (def k {:carrier 923.33})
 (def l {:carrier 987.77})
 (def +a {:carrier 1046.50})
 (def +b {:carrier 1108.73})
 (def +c {:carrier 1174.66})
 (def +d {:carrier 1244.51})


 (def allecho(fx-echo 1 0.2 0.2))
 (def allechor (fx-echo 0 0.2 0.3))
 (ctl allecho :delay-time 0.1)
 (ctl allecho :decay-time 2.0)
 (kill fx-echo)


;sequencer
(defn flatten1

  [m]
  (reduce (fn [r [arg val]] (cons arg (cons val r))) [] m))

(def live-pats (atom pats))


(defn live-sequencer
  ([curr-t sep-t live-patterns] (live-sequencer curr-t sep-t live-patterns 0))
  ([curr-t sep-t live-patterns beat]
     (doseq [[sound pattern] @live-patterns
             :let [v (nth pattern (mod beat (count pattern)))
                   v (cond
                      (= 1 v)
                      []

                      (map? v)
                      (flatten1 v)

                      :else
                      nil)]
             :when v]
       (at curr-t (apply sound v)))
     (let [new-t (+ curr-t sep-t)]
       (apply-by new-t #'live-sequencer [new-t sep-t live-patterns (inc beat)]))))

(live-sequencer (now) 120 live-pats)
                                        (stop)



;; for sending the beatnumber over to quil!
(def beat (atom {:beatnum 0}))
;; (def metro (metronome 200))
;; (metro :bpm 240)
;; for sending the beatnumber over to quil!
(defn beat2quil [nome ]
    (let [beat (nome)]
        (println beat)
        (apply-by (nome (inc beat)) (osc-send client "/beat" beat))
        (apply-by (nome (inc beat)) beat2quil nome [])
      ))


(def metro (metronome 150))
(metro 150)

(beat2quil metro)
;; (:beatnum @beat)



; turn on sender
;; (beat2quil metro)

;(recording-start "~/desktop/ICLC1b.wav")
;(recording-stop)
(recording?)
(midi-connected-devices)
(midi-connected-receivers)

(inst-volume! kickA 1.5)

;(inst-volume! c-hat 0.75)
;(inst-volume! snareA 1.00)

(inst-pan! c-hat 0.5)

(volume 0.5)



;(fx-freeverb 0 0.5 0.9 0.5)
;(fx-freeverb 1 0.5 0.9 0.5)
;; (fx-bitcrusher 0.04)
;(fx-limiter)

;(kill fx-limiter)
(kill fx-freeverb)
(kill fx-bitcrusher)
(kill fx-compressor)
(kill fx-distortion)

;(stop-all)
