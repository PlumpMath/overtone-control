
(ns dagocode.overtone
 (:use [overtone.live]
       [overtone.inst.piano]
       ))

;;     (piano)
;; (piano 48)

(defn play-chord [a-chord]
  (doseq [note a-chord] (piano note)))

;; play a chord progression on our piano
(let [time (now)]
  (at time (play-chord (chord :D3 :major7)))
  (at (+ 2000 time) (play-chord (chord :A3 :major)))
  (at (+ 3000 time) (play-chord (chord :A3 :diminished)))
  (at (+ 4300 time) (play-chord (chord :F3 :minor)))
)

