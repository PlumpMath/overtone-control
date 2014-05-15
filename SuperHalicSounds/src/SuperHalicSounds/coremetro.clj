
(ns dagocode.overtone
 (:use [overtone.live]
       [overtone.inst.piano]))

(piano)

(def kick1 (sample (freesound-path 2086)))
(def one-twenty-bpm (metronome 120))
(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-by (nome (inc beat)) looper nome sound [])))
(looper one-twenty-bpm kick)
;; (stop)

(definst saw-wave [freq 440 attack 0.08 sustain 0.4 release 0.8 vol 0.4]
  (* (env-gen(env-lin attack sustain release) 1 1 0 1 FREE)
  (saw freq)
  vol))

(defn saw2 [music-note]
  (saw-wave (midi->hz (note music-note))))
(saw2 :A4)

;; (stop)

(defonce metro (metronome 120))
(metro)
;; (defn chord-progression-beat [m beat-num]
;;   (at (m (+ 0 beat-num)) (play-chord (chord :C4 :major)))
;;   (at (m (+ 4 beat-num)) (play-chord (chord :G3 :major)))
;;   (at (m (+ 8 beat-num)) (play-chord (chord :A3 :minor)))
;;   (at (m (+ 14 beat-num)) (play-chord (chord :F3 :major)))
;;   (apply-at (m (+ 16 beat-num)) chord-progression-beat m (+ 16 beat-num) [])
;; )
;; (chord-progression-beat metro (metro))
(defn chord-progression-beat [m beat-num]
  (at (m (+ 0 beat-num)) (play-chord (chord :C4 :minor)))
  (at (m (+ 4 beat-num)) (play-chord (chord :G3 :major)))
  (at (m (+ 8 beat-num)) (play-chord (chord :A3 :sus4)))
  (at (m (+ 12 beat-num)) (play-chord (chord :F3 :minor)))
  (apply-at (m (+ 16 beat-num)) chord-progression-beat m (+ 16 beat-num) [])
)
(chord-progression-beat metro (metro))
(stop)

;; defn chord-progression-time []
;;   (let [time (now)]
;;     (at time (play-chord (chord :C4 :major)))
;;     (at (+ 2000 time) (play-chord (chord :G3 :major)))
;;     (at (+ 3000 time) (play-chord (chord :F3 :sus4)))
;;     (at (+ 4300 time) (play-chord (chord :F3 :major)))
;;     (at (+ 5000 time) (play-chord (chord :G3 :major))))

;; (defn play-chord [a-chord]
;;   (doseq [note a-chord] (saw2 note)))
;; (defn (chord-progression-beat [m beat-num]
;;                                (at (m (+ 0 beat-num)) (play-chord (chord :A4 :major)))
;;                                (at (m (+ 4 beat-num)) (play-chord (chord :C3 :minor)))
;;                                (at (m (+ 8 beat-num)) (play-chord (chord :D3 :sus4)))
;;                                (at (m (+ 12 beat-num)) (play-chord (chord :F3 :minor)))
;;                                (apply-at (m (+ 16 beat-num)) chord-progression-beat m (+16 beat-num) [])
;;                                          )
;;                                (chord-progression-beat metro (metro))




;; (stop)

    ;; (definst
    ;;     (out 0 (* v (clip2 (+ wob (* kick-vol kick) (* snare-vol snare)) 1)
    ;;               (line:kr 0 dur dur FREE)))
    ;; )


    ;; (defn beat2 [beat]
    ;;   (at (metro beat) (#'kick 220 0.3 0.5 1.5))
    ;;   (at (+ 0.5  (metro beat)) (#'kick 220 0.3 0.5 1.5))
    ;;   (if (= 0 (mod beat 2))
    ;;     (at (metro (+ 0.17 beat)) (#'kick)))
    ;;   (at (metro (+ 0.35 beat)) (c-hat))
    ;;   (if (even? beat) (at (metro (+ 0.45 beat)) (c-hat)))
    ;;   (at (metro (+ 0.5 beat)) (#'mew 8))
    ;;   (apply-at (metro (inc beat))
    ;;             (if (= 0 (mod beat 200)) #'beat1 #'beat2)
    ;;             (inc beat) []))
