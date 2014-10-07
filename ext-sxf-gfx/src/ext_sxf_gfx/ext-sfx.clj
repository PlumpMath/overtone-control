(ns ext-sxf-gfx.core
;;  (:use
;;         [overtone.core]  ;;if this is on, will use external server!!!
;;        )
  )


;; (boot-external-server)


(definst kickA [freq 100 dur 0.9 width 0.5 amp 1.4]
  (let [freq-env (* freq (env-gen (perc -0.03 (* 0.99 dur))))
        env (env-gen (perc 0.01 dur) 1 1 0 1 FREE)
        sqr (* (env-gen (perc 0 0.04)) (pulse (* 2 freq) width))
        src (sin-osc freq-env)
        drum (+ sqr (* env src))]
;;         (tap "kick-freq" 30 freq)
        (compander drum drum 0.4 0.5 0.1 0.01 0.01)))
(kickA)

;; (defsynth trem [freq 440 depth 10 rate 6 length 3]
;;     (tap "saw-freq" 30 freq)
;;     (out [0 1] (* 0.3
;;        (line:kr 0 1 length FREE)
;;        (saw (+ freq (* depth (sin-osc:kr rate))))
;;        ))

;;     )

(definst trem [freq 440 depth 10 rate 6 length 3]
;;     (tap "saw-freq" 30 freq)
    (out [0 1] (* 0.3
       (line:kr 0 1 length FREE)
       (saw (+ freq (* depth (sin-osc:kr rate))))
       ))

    )


(trem 250 3 7 3)


;; Define a synth we can use to tap into the stereo out.
(defsynth tapper
  "Tap into a stereo bus. Provides 3 taps: left, right, and phase."
  [bus 0]
  (let [source (in bus 2)
        left (select 0 source)
        right (select 1 source)]
    (tap :left 10 left)
    (tap :right 10 right)
    (tap :phase 10 (- left right))))

(tapper )

(comment
  (def mytaps (:taps (tapper)))
  @(:left mytaps)
  @(:right mytaps)
  @(:phase mytaps)
  )





(def pats {
           kickA  [1 0 0 0 0 0 0 0]
           })


(def live-pats (atom pats))

(swap! live-pats assoc kickA   [1 1 0 1[1 1 1 1 0 1 1 1]])

(defn flatten1
  "Takes a map and returns a seq of all the key val pairs:
      (flatten1 {:a 1 :b 2 :c 3}) ;=> (:b 2 :c 3 :a 1)"
  [m]
  (reduce (fn [r [arg val]] (cons arg (cons val r))) [] m))

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

;; (live-sequencer (now) 250 live-pats)

;; (stop)
