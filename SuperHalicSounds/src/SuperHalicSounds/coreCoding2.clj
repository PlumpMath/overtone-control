
(ns dagocode.overtone
 (:use [overtone.live]
       [overtone.inst.drum]))

;; (definst kickA [freq 120 dur 0.3 width 0.5]
;;   (let [freq-env (* freq (env-gen (perc 0 (* 0.99 dur))))
;;         env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
;;         sqr (* (env-gen (perc 0 0.01)) (pulse (* 2 freq) width))
;;         src (sin-osc freq-env)
;;         drum (+ sqr (* env src))]
;;         (compander drum drum 0.2 1 0.1 0.01 0.01)))

;; (definst c-hat [amp 0.8 t 0.04]
;;   (let [env (env-gen (perc 0.001 t) 1 1 0 1 FREE)
;;         noise (white-noise)
;;         sqr (* (env-gen (perc 0.01 0.04)) (pulse 860 0.2))
;;         filt (bpf (+ sqr noise) 9000 0.5)]
;;     (* amp env filt)))

(definst o-hat [amp 0.8 t 0.3]
  (let [env (env-gen (perc 0.002 t) 1 1 0 1 FREE)
        noise (pink-noise)
        sqr (* (env-gen (perc 0.01 0.04)) (pulse 860 0.2))
        filt (bpf (+ sqr noise) 9000 0.5)]
    (* amp env filt)))

(def metro (metronome 120))

(metro)
(metro 100)

(defn player [beat]
  (at (metro beat) (kickA))
  (at (metro (+ 0.5 beat)) (c-hat))
  (at (metro (+ 3.8 beat)) (o-hat))
  (apply-by (metro (inc beat)) #'player (inc beat) []))

(player (metro))
;; (metro-bpm metro 120)

;; (stop)

