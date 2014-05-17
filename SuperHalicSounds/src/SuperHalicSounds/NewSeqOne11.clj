
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


;; create OSC client
(def PORT 4242)
(def client (osc-client "192.168.2.1" PORT))  ;; change localhost to viz IP




(definst kickA [freq 100 dur 0.4 width 0.5]
  (let [freq-env (* freq (env-gen (perc -0.03 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        drum (+ sqr (* env src))]
        (compander drum drum 0.4 1 0.1 0.01 0.01)))

(definst c-hat [amp 0.8 t 0.04]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 860 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(definst o-hat [amp 1.8 t 0.3]
  (let [env (env-gen (perc 0.008 t) 1 1 0 1 FREE)
        noise (pink-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 860 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(defsynth fm [carrier 440 divisor 4.0 depth 4.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin 1 0 6))
        amp-env (env-gen (lin 1 0 4.0) :action FREE)]
    (out out-bus (pan2 (* 0.15 amp-env
                          (sin-osc (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc modulator)))))))))

(definst dubstep [freq 200 wobble-freq 1]
  (let [sweep (lin-exp (lf-saw wobble-freq) -1 1 40 5000)
        son (mix (saw (* freq [0.99 1 1.01])))]
    (lpf son sweep)))





(def snareB (freesound 167968))
;; (def snareB (freesound 26903))

;; (def tik    (freesound 63799))
(def tik    (freesound 21175))
(def clap1 (freesound 196250))
(def blanket (freesound 111))
(def yea (freesound 110))



(def pats {clap1  [0 1 0 1 0 1 0 1]
           o-hat  [0 0 0 0 0 0 0 1]
           c-hat  [1 1 0 1 0 1 0 0]
           snareB [1 0 0 1 0 0 1 0 1]
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
       (at curr-t (apply sound v))
;;        (at curr-t (println v))
;;        (at curr-t (osc-send client "/liveseq" "v"))
       (apply-by curr-t (osc-send client "/live-seq" (str v)))
       )
     (let [new-t (+ curr-t sep-t)]
       (apply-by new-t #'live-sequencer [new-t sep-t live-patterns (inc beat)]))))

(def a {:rate 0.5 :amp 1.0})
(def b {:amp 0.5})
(def c {:amp 0.3})
(def d {:amp 2.00})

(live-sequencer (now) 200 live-pats)
(stop)


(swap! live-pats assoc clap1 [a a a b a a a a b])
(swap! live-pats assoc o-hat [a a a b a a a a b])
(swap! live-pats assoc c-hat [a a a b a a a a b])
(swap! live-pats assoc snareB  [a a a b a a a a b c [c a a]])
(swap! live-pats assoc kickA   [a a a b a a a a b [a a a b a a a a b]])



;; (metro)
(def metro (metronome 150))



;; FM chords
(defn fm1 [music-note]
  (fm (midi->hz (note music-note))))
;; (fm 440 (/ 8 3) 2)
(defn play-chord [a-chord]
  (doseq [note a-chord] (fm1 note)))


(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :A3 :minor)))
  (at (m (+ 8 beat-num)) (play-chord (chord :Bb3 :minor)))
  (at (m (+ 16 beat-num)) (play-chord (chord :A4 :minor)))
  (at (m (+ 26 beat-num)) (play-chord (chord :Bb3 :minor)))
  (apply-at (m (+ 64 beat-num)) chord-progression-beat m (+ 64 beat-num) [])
)
(chord-progression-beat metro (metro))


;; (defn chord-progression-beat [m beat-num]
;;   (at (m (+ 0 beat-num)) (play-chord (chord :A3 :minor)))
;;   (at (m (+ 8 beat-num)) (play-chord (chord :G4 :minor)))
;;   (at (m (+ 16 beat-num)) (play-chord (chord :A4 :minor)))
;; ;;   (at (m (+ 26 beat-num)) (play-chord (chord :Bb3 :minor)))
;;   (apply-at (m (+ 32 beat-num)) chord-progression-beat m (+ 32 beat-num) [])
;; )
;; (chord-progression-beat metro (metro))

(defn phat-beats [m beat-num]

    ; b1
  (at (m (+ 0 beat-num)) (tik)(yea))
  (at (m (+ 0.58 beat-num)) (tik))
  (at (m (+ 1 beat-num)) (tik))
  (at (m (+ 1.58 beat-num)) (tik))
  (at (m (+ 2 beat-num)) (tik))
  (at (m (+ 3 beat-num)) (tik))
  (at (m (+ 3.58 beat-num)) (tik))
;;     ; b2
  (at (m (+ 4 beat-num)) (tik))
  (at (m (+ 4.58 beat-num)) (tik))
  (at (m (+ 5 beat-num)) (tik))
  (at (m (+ 6 beat-num)) (tik))
  (at (m (+ 7 beat-num)) (tik))
    ; b3
  (at (m (+ 8 beat-num)) (tik))
  (at (m (+ 9 beat-num)) (tik))
  (at (m (+ 10 beat-num)) (tik))
  (at (m (+ 11 beat-num)) (tik))
    ; b4
  (at (m (+ 12 beat-num)) (tik))
  (at (m (+ 13 beat-num)) (tik))
  (at (m (+ 14 beat-num)) (tik))
  (at (m (+ 15 beat-num)) (tik))

  ;bar2

    ; b1
  (at (m (+ 16 beat-num)) (tik) (blanket))
  (at (m (+ 16.58 beat-num)) (tik))
  (at (m (+ 17 beat-num)) (tik))
  (at (m (+ 18 beat-num)) (tik))
  (at (m (+ 19 beat-num)) (tik))
  (at (m (+ 19.58 beat-num)) (tik))
    ; b2
  (at (m (+ 20 beat-num)) (tik))
  (at (m (+ 20.58 beat-num)) (tik))
  (at (m (+ 21 beat-num)) (tik))
  (at (m (+ 22 beat-num)) (tik))
  (at (m (+ 23 beat-num)) (tik))
    ; b3
  (at (m (+ 24 beat-num)) (tik))
  (at (m (+ 25 beat-num)) (tik))
  (at (m (+ 26 beat-num)) (tik))
  (at (m (+ 27 beat-num)) (tik))
    ; b4
  (at (m (+ 28 beat-num)) (tik))
  (at (m (+ 39 beat-num)) (tik))
  (at (m (+ 30 beat-num)) (tik))
  (at (m (+ 3.58 beat-num)) (tik))
  (at (m (+ 31 beat-num)) (tik))

  (apply-at (m (+ 32 beat-num)) phat-beats m (+ 32 beat-num) [])
  )
(phat-beats metro (metro))

; Bass function

(def notes (vec (map (comp midi->hz note)
                     [:g1 :a0 :a2  :a1 :a3 :bb0 :bb2 :a1 :a2 :g2 :g2 :a2 :a3 :g2 :g2]
                     )))


(defn bassE [m num notes]
  (at (m num)
      (ctl dubstep :freq (first notes)))
  (apply-at (m (inc num)) bassE m (inc num) (next notes) []))

(defn wobble [m num]
  (at (m num)
      (ctl dubstep :wobble-freq
           (choose [2 2 0 2 1 2])))
  (apply-at (m (+ 4 num)) wobble m (+ 2 num) [])
  )

(double-nested-allpass-l:ar
    (dubstep)
    (bassE metro (metro) (cycle notes))
    (wobble metro (metro))
    )



;; (player (metro))
;; (metro-bpm metro 150)





;; for sending the beatnumber over to quil!
(defn beat2quil [nome ]
    (let [beat (nome)]
        (apply-by (nome (inc beat)) (osc-send client "/beat" beat))
        (apply-by (nome (inc beat)) beat2quil nome [])
      ))

; turn on sender
(beat2quil metro)
(stop)

(stop)

