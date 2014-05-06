
(ns dagocode.overtone
 (:use [overtone.live]
       [overtone.inst.piano]
       [overtone.inst.drum]
       ))

(piano)

;; (def kick (sample (freesound-path 2086)))
(def one-twenty-bpm (metronome 120))
(defn looper [nome sound]
  (let [beat (nome)]
    (at (nome beat) (sound))
    (apply-by (nome (inc beat)) looper nome sound [])))
(looper one-twenty-bpm kick)
;; (stop)


(definst c-hat [amp 0.8 t 0.4]
  (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
        noise (white-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 880 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))
