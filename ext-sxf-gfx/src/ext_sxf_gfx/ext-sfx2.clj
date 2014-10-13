(ns ext-sxf-gfx.core
 (:use [overtone.inst.drum]
       [overtone.inst.piano]
       [overtone.inst.io]
       [overtone.inst.synth]
       [overtone.osc.util]
       [overtone.osc.peer]
       [overtone.osc.dyn-vars]
       ))

; check machinery voor hierboven
;; (connect-external-server 57110)


;; (def PORT 4242)
;; (def client (osc-client "192.168.2.1" PORT))
;; change localhost to viz IP


(mic -8)
(kill mic)


(definst kickA [freq 105 dur 1.2 width 0.5 amp -20 out-bus 1]
  (let [freq-env (* freq (env-gen (perc 0.02 (* 0.49 dur))))
        env (env-gen (perc 0.009 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.0800)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        src2 (sin-osc-fb freq-env)
        filt (lpf (+ sqr src src2) 100)
        drum (+ sqr (* env filt))]
        (compander drum drum 0.2 1 0.1 0.01 0.01)
))
;; (inst-fx! kickA fx-distortion2)
;; (clear-fx kickA)

;; (stop)

(definst snareA [freq 400 dur 0.40 width 0.5 pan 0.5 amp -1.0]
  (let [freq-env (* freq (env-gen (perc -0.4 (* 0.24 dur))))
        env (env-gen (perc 0.006 dur) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0 0.04)) (pulse (* 2.9 freq) width))
        src (c-osc freq-env)
        src2 (lf-tri freq-env)
        filt (glitch-rhpf (+ sqr noise src src2) 800)
        clp (clip2 filt 0.6)
        drum (+ sqr (* env clp))]
        (compander drum drum 0.4 1 0.02 0.01 0.01)
    ))

;; (inst-fx! snareA fx-chorus)
;; (inst-fx! snareA fx-freeverb)
;; (inst-fx! snareA fx-compressor)
;; (clear-fx snareA)








;; (inst-fx! clap fx-freeverb)

(defsynth fm [carrier 880 divisor 5.0 depth 4.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin 1.9 3.8 -2.8))
        amp-env (env-gen (lin 1 0 4.0 1) :action FREE)
        filt (rlpf (+ carrier modulator ) 100 0.1)]
    (out out-bus  (pan2 (* 0.15 amp-env
                          (sin-osc (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc modulator)))))))
    ))


(defsynth fmtones [carrier 880 divisor 10.0 depth 8.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin -0.2 0.4 0.8))
        amp-env (env-gen (lin 0 -0.2 0.1 1 ) :action FREE)
        filt (glitch-rhpf:ar (+ carrier modulator ) 600 0.6)
             ]
      (out out-bus (pan2 (* 0.50 amp-env
                          (formant (+ carrier
                                      (* mod-env (* carrier depth) (line modulator)))))))))
;  Chaos  ()
;  Line   (amp-comp, amp-comp-a, k2a, line )
;  Random (rand-seed, lonrenz-trig )
;  Noise  (lf-noise, hasher , mantissa-mask)



(swap! live-pats assoc fmtones [0 0 f a g 0 0 0 0 0 0 0 k 0 k 0 0 0 e e e c d 0 0])
(swap! live-pats assoc fmtones [0])


(fmtones :depth 8.0)
(fmtones :inst-volume 8.0)


;; (swap! live-pats assoc clap    [0 0 0 0 1 0 0 0])
;; (swap! live-pats assoc c-hat   [0])
(swap! live-pats assoc snareA  [0])
(swap! live-pats assoc kickA   [1 0 0 0 ])


;; (stop-all)

(def pats {
;;            c-hat  [1 0 0 0 0 0 1 0]
           snareA [0 0 0 0 0 0 0 0]
           kickA  [1 0 0 0 0 0 0 0 0 0 0 0 0 0 0 1]
           fmtones   [0]
           })

;kick control
(def q {:rate 0.5 :amp 1.0})
(def x {:freq 85})
(def y {:freq 75})
(def z {:freq 120 :depth 2.0})

;hh control
(def o {:amp 0.2})
(def p {:amp 0.5})

;fmtones control
 (def a {:carrier 523.25})
 (def b {:carrier 554.37})
 (def c {:carrier 587.33})
 (def d {:carrier 622.25})
 (def e {:carrier 659.25})
 (def f {:carrier 698.46})
 (def g {:carrier 739.99})
 (def h {:carrier 783.99})
 (def i {:carrier 830.61})
 (def j {:carrier 880.00})
 (def k {:carrier 923.33})

;;  (fx-echo 1 0.2 0.2)
;;  (fx-echo 0 0.2 0.3)
;;  (kill fx-echo)
;;  (fx-noise-gate 2 0.2 0.2)
;;  (kill fx-noise-gate)



;sequencer hart
(defn flatten1
  "Takes a map and returns a seq of all the key val pairs:
      (flatten1 {:a 1 :b 2 :c 3}) ;=> (:b 2 :c 3 :a 1)"
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

(live-sequencer (now) 100 live-pats)
;; (stop)

(piano)


(defn fm1 [music-note]
  (fm (midi->hz (note music-note))))
;; (fm 440 (/ 8 3) 2)
(defn piano1 [music-note]
  (piano (midi->hz (note music-note))))

(defn play-chord [a-chord]
  (doseq [note a-chord] (piano1 note)))

(defn play-chord [a-chord]
  (doseq [note a-chord] (fm1 note)))


(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :G4 :minor)))
  (at (m (+ 8 beat-num)) (play-chord (chord :F4 :major7)))
  (at (m (+ 16 beat-num)) (play-chord (chord :A4 :minor7)))
  (at (m (+ 26 beat-num)) (play-chord (chord :Bb4 :9sus4)))
  (at (m (+ 32 beat-num)) (play-chord (chord :B4 :major)))
  (at (m (+ 40 beat-num)) (play-chord (chord :F4 :minor7)))
  (at (m (+ 48 beat-num)) (play-chord (chord :A4 :minor)))
  (at (m (+ 56 beat-num)) (play-chord (chord :F4 :7sus4)))

  (apply-at (m (+ 64 beat-num)) chord-progression-beat m (+ 64 beat-num) [])
)

(def metro (metronome 200))


(chord-progression-beat metro (metro))

;; (kill 1)
;; (stop)

;; for sending the beatnumber over to quil!
;; (defn beat2quil [nome ]
;;     (let [beat (nome)]
;;         (apply-by (nome (inc beat)) (osc-send client "/beat" beat))
;;         (apply-by (nome (inc beat)) beat2quil nome [])
;;       ))


; turn on sender
;; (beat2quil metro)
;; (stop)

;; (recording-start "~/desktop/LamdaSonic5a.wav")
;; (recording-stop)
(midi-connected-devices)
(midi-connected-receivers)
;; (stop)
;; (stop-all)
(scope-out 1)
;; (ugen-doc distort)
;; (ugen-doc beat-track)

(sound-in 0)
;; (__BUS-MIXERS__)
;; (out-bus-mixer)
;; (kill out-bus-mixer)


;; (fx-freeverb 0 0.5 0.9 0.5)
;; (fx-freeverb 1 0.5 0.9 0.5)
;; (fx-limiter 0)
;; (fx-limiter 1)
;; (kill fx-limiter)
;; (kill fx-freeverb)
