
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
(def client (osc-client "localhost" PORT))  ;; change localhost to viz IP


;; osc-send
(osc-send client "/test" "i" 42)



;;  (piano)
 (mic)
 (kill mic)

;;  (tb303 100)
;;  (mooger)
 (bubbles)
  (kill bubbles)
;;  (overpad)
 (buzz 45)
 (kill buzz)

 (supersaw 100)
 (kill supersaw)

 (whoahaha)
 (kill whoahaha)

 (rise-fall-pad)


(definst kickA [freq 100 dur 0.6 width 0.5]
  (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        drum (+ sqr (* env src))]
        (compander drum drum 0.2 1 0.2 0.01 0.01)))
;; (kill kickA) ;werkt niet

(def snareB (freesound 26903))
(def zoomer (freesound 9087))
(def pwords (freesound 9088))
(def kickD (freesound 41155))
(def noiseN (freesound 2050))
(def blanket (freesound 1011))




(definst snareA [freq 400 dur 0.3 width 0.5]
  (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        noise (pink-noise)
        sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        drum (+ sqr (* env noise src))]
        (compander drum drum 0.2 1 0.1 0.01 0.01)))

(definst c-hat [amp 0.4 t 0.04]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 860 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))
(c-hat)


(definst o-hat [amp 0.9 t 0.3]
  (let [env (env-gen (perc 0.002 t) 1 1 0 1 FREE)
        noise (pink-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 860 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(defsynth fm [carrier 440 divisor 4.0 depth 4.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin 1 0 6))
        amp-env (env-gen (lin 1 0 0.01) :action FREE)]
    (out out-bus (pan2 (* 0.10 amp-env
                          (sin-osc (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc modulator)))))))))


(definst dubstep [freq 100 wobble-freq 5]
  (let [sweep (lin-exp (lf-saw wobble-freq) -1 1 40 5000)
        son (mix (saw (* freq [0.99 1 1.01])))]
    (lpf son sweep)))



(defn fm1 [music-note]
  (fm (midi->hz (note music-note))))
;; (fm 440 (/ 8 3) 2)
(defn play-chord [a-chord]
  (doseq [note a-chord] (pad note)))


(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :A5 :minor)))
  (at (m (+ 8 beat-num)) (play-chord (chord :G4 :aug)))
  (at (m (+ 16 beat-num)) (play-chord (chord :A#4 :minor)))
  (at (m (+ 24 beat-num)) (play-chord (chord :Bb4 :minor)))
  (apply-at (m (+ 32 beat-num)) chord-progression-beat m (+ 32 beat-num) [])
)
(chord-progression-beat metro (metro))
;; (stop chord-progression-beat); werkt niet


(defn phat-beats [m beat-num]

    ; b1
  (at (m (+ 0 beat-num)) (kickD) (c-hat))
  (at (m (+ 0.5 beat-num)) (open-hat))
  (at (m (+ 1 beat-num)) (kickA) (snareB))
  (at (m (+ 1.65 beat-num)) (c-hat))
  (at (m (+ 2 beat-num)) (kickD)(c-hat))
  (at (m (+ 3 beat-num)) (kickA) (snareB)(c-hat))
  (at (m (+ 3.5 beat-num)) (o-hat))
;;     ; b2
  (at (m (+ 4 beat-num)) (kickD) (c-hat))
  (at (m (+ 4.65 beat-num)) (c-hat))
  (at (m (+ 5 beat-num)) (kickA) (c-hat))
  (at (m (+ 6 beat-num)) (kickA) (c-hat))
  (at (m (+ 7 beat-num)) (kickA) (snareB)(c-hat))
    ; b3
  (at (m (+ 8 beat-num)) (kickD) (noiseN))
  (at (m (+ 9 beat-num)) (kickD) (o-hat))
  (at (m (+ 10 beat-num)) (kickD) (c-hat))
  (at (m (+ 11 beat-num)) (kickD) (c-hat))
    ; b4
  (at (m (+ 12 beat-num)) (kickD)(o-hat))
  (at (m (+ 13 beat-num)) (kickD) (c-hat))
  (at (m (+ 14 beat-num)) (kickD) (c-hat))
  (at (m (+ 15 beat-num)) (kickD) (c-hat))

    ; b5
  (at (m (+ 16 beat-num)) (kickD) (c-hat))
  (at (m (+ 16.65 beat-num)) (c-hat))
  (at (m (+ 17 beat-num)) (kickA) (snareB))
  (at (m (+ 18 beat-num)) (kickA))
  (at (m (+ 19 beat-num)) (kickA) (c-hat) (snareA))
  (at (m (+ 19.65 beat-num)) (kickD) (c-hat))
    ; b6
  (at (m (+ 20 beat-num)) (kickD) (c-hat))
  (at (m (+ 20.65 beat-num)) (c-hat))
  (at (m (+ 21 beat-num)) (kickA)(c-hat)) (snareA)
  (at (m (+ 22 beat-num)) (kickA) (c-hat))
  (at (m (+ 23 beat-num)) (kickD) (snareB)(c-hat))
    ; b7
  (at (m (+ 24 beat-num)) (kickD))
  (at (m (+ 25 beat-num)) (kickD) (snareB)(o-hat))
  (at (m (+ 26 beat-num)) (kickD) (c-hat))
  (at (m (+ 27 beat-num)) (kickD) (snareB)(c-hat))
    ; b8
  (at (m (+ 28 beat-num)) (kickD) (o-hat))
  (at (m (+ 39 beat-num)) (kickD) (o-hat))
  (at (m (+ 30 beat-num)) (kickD) (c-hat))
  (at (m (+ 3.65 beat-num)) (c-hat))
  (at (m (+ 31 beat-num)) (kickD) (c-hat))

  (apply-at (m (+ 32 beat-num)) phat-beats m (+ 32 beat-num) [])
  )

;; (kill phat-beats)


(def notes (vec (map (comp midi->hz note)
                     [:a1 :a0 :a2  :a1 :a3 :bb0 :bb2 :a1 :a2 :g2 :g2 :a2 :a3 :g4 :g4]
                     )))
(def notes2 (vec (map (comp midi->hz note)
                     [:a1 :a0 :a2]
                     )))

 ;(def scale)

; Bass function
(defn bassE [m num notes]
  (at (m num)
      (ctl dubstep :freq (first notes)))
  (apply-at (m (inc num)) bassE m (inc num) (next notes) []))
(defn wobble [m num]
  (at (m num)
;;       (println (num))
      (ctl dubstep :wobble-freq ((fn [x]
                                   (osc-send client "/wobble" x)
                                   x
                                   ) (choose [16 2 8 2 3 2]))

;;            (choose [16 2 8 2 3 2])
;;               (choose [1 2 1 2 2 2])
           ))
  (apply-at (m (+ 4 num)) wobble m (+ 2 num) [])
  )

(double-nested-allpass-l:ar
    (dubstep)
    (bassE metro (metro) (cycle notes))
    (wobble metro (metro))
    )
(kill dubstep)


  (phat-beats metro (metro))


;metro
  (metro)
  (metro 4)
  (def metro (metronome 120))
  (metro-bpm metro 120)


(metro-beat metro)




(stop)


