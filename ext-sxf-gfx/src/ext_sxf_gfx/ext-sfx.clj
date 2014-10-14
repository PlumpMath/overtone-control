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
        drum (+ sqr (* env src))
        ]
        (compander drum drum 0.4 0.5 0.1 0.01 0.01)
;;     (tap :kick-freq 30 freq)
    )
  )
(kickA)


(defsynth fm [carrier 880 divisor 5.0 depth 4.0 out-bus 0]
  (let [modulator (/ carrier divisor)
        mod-env (env-gen (lin 1.9 3.8 -2.8))
        amp-env (env-gen (lin 1 0 4.0 1) :action FREE)
        filt (rlpf (+ carrier modulator ) 100 0.1)]
    (out out-bus  (pan2 (* 0.15 amp-env
                          (sin-osc (+ carrier
                                      (* mod-env (* carrier depth) (sin-osc modulator))))))
;;          (tap :pan :kr (pan2 (* 0.15 amp-env
;;                           (sin-osc (+ carrier
;;                                       (* mod-env (* carrier depth) (sin-osc modulator)))))))
    )))


(fm 300 2 4 0)


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

(fmtones)

(definst trem [freq 440 depth 10 rate 6 length 3]
;;     (tap :saw-freq 30 freq)
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

(swap! live-pats assoc kickA   [1 1 0 1 1 1 0 1 1 0 1[1 1 1 1 0 1 1 1]])

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

;; (live-sequencer (now) 2050 live-pats)

;; (kill 1)
;; (stop)
