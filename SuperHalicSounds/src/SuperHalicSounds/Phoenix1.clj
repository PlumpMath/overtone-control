(ns dagocode.overtone
 (:use [overtone.live]
       [overtone.inst.drum]
       [overtone.inst.piano]
       [overtone.inst.io]
       [overtone.inst.synth]
       [overtone.osc.util]
       [overtone.osc.peer]
       [overtone.osc.dyn-vars]
       ))

(def PORT 4242)
;; (def client (osc-client "192.168.2.1" PORT))
;; change localhost to viz IP



(definst kickA [freq 100 dur 0.9 width 0.5 amp 1.4]
  (let [freq-env (* freq (env-gen (perc -0.03 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.04)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        drum (+ sqr (* env src))]
        (compander drum drum 0.4 1 0.1 0.01 0.01)))

(definst snareA [freq 500 dur 0.1 width 0.5 pan 0.5 amp -1.0]
  (let [freq-env (* freq (env-gen (perc -0.03 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0 0.03)) (pulse (* 2 freq) width))
        src (c-osc freq-env)
        drum (+ sqr (* env src))]
        (compander drum drum 0.4 1 0.1 0.01 0.01)))

(definst c-hat [amp 0.7 t 0.03]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
             noise (white-noise)
             sqr (* (env-gen (perc 0.07 0.04)) (pulse 880 0.8))
             filt (rhpf  (+ sqr noise) 300 0.5)
       ]
             (* amp env filt)
    ))


(defsynth fm [carrier 440 divisor 7.0 depth 4.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin-rand [0.9 1.8 -2.8]))
        amp-env (env-gen (lin 1 0 4.0 1) :action FREE)
        filt (bpf (+ carrier modulator ) 300 0.2)]
    (out out-bus (pan2 (* 0.15 amp-env
                          (sin-osc (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc modulator)))))))))

;; (fm)
;; (kill fm)

;; (stop)

(swap! live-pats assoc clap    [0 b 1 0 0 a 1 0 0 b 0 0])
(swap! live-pats assoc c-hat   [1 1 1 b 1 a])
(swap! live-pats assoc snareA  [0 1 0 0 1 0 0 a 1 0 c 0 1 0 0 0 0 b b])
(swap! live-pats assoc kickA   [1 0 1 1 0 0 1 1 0 0[1 1 1 1 1 1 1 1]])

(def blanket (freesound 111))

(def pats {clap   [0 0 1 0]
           c-hat  [1 1 1 0 1 0 1 0]
           snareA [0 1 0 0 1 0 0 1]
           kickA  [1 0 0 0 0 0 0 1]
           })

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

(def a {:rate 0.5 :amp -0.03})
(def b {:rate -0.8 :amp 0.5})
(def c {:amp -0.3})
(def d {:amp 2.00})
(live-sequencer (now) 200 live-pats)
(stop)

(defn fm1 [music-note]
  (fm (midi->hz (note music-note))))
;; (fm 440 (/ 8 3) 2)
(defn play-chord [a-chord]
  (doseq [note a-chord] (fm1 note)))


(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :G4 :minor)))
  (at (m (+ 8 beat-num)) (play-chord (chord :F4 :major7)))
  (at (m (+ 16 beat-num)) (play-chord (chord :A4 :minor7)))
  (at (m (+ 26 beat-num)) (play-chord (chord :Bb4 :9sus4)))
  (at (m (+ 32 beat-num)) (play-chord (chord :B4 :minor7)))
  (at (m (+ 40 beat-num)) (play-chord (chord :F4 :minor7)))
  (at (m (+ 48 beat-num)) (play-chord (chord :A4 :minor)))
  (at (m (+ 56 beat-num)) (play-chord (chord :F4 :7sus4)))

  (apply-at (m (+ 64 beat-num)) chord-progression-beat m (+ 64 beat-num) [])
)
(chord-progression-beat metro (metro))

;; for sending the beatnumber over to quil!
;; (defn beat2quil [nome ]
;;     (let [beat (nome)]
;;         (apply-by (nome (inc beat)) (osc-send client "/beat" beat))
;;         (apply-by (nome (inc beat)) beat2quil nome [])
;;       ))

(def metro (metronome 100))

; turn on sender
;; (beat2quil metro)
;; (stop)

;; (recording-start "~/desktop/foo1i.wav")
;; (recording-stop)
