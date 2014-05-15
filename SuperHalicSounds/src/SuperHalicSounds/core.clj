
(ns dagocode.overtone
 (:use [overtone.live]
       [overtone.inst.piano]))

;(piano)
(piano 48)


(demo (sin-osc))

()

    (ns muzik.core
      (:gen-class)
    )


    (ns muzik.overtone
      (:use [overtone.live]
            [overtone.inst.piano]
            [overtone.inst.synth]

            ))


    (definst bar [freq 220] (saw 110))
    (definst baz [freq 440] (* 0.3 (saw freq)))
    (definst quux [freq 440] (* 0.3 (saw freq)))


    (definst tem [freq 440 depth 10 rate 6 length 3]
        (* 0.3
           (line:kr 0.5 1 length FREE)
           (saw (+ freq (* depth (sin-osc:kr rate))))))

    (tem 400 4 6 3)


    (definst sawtem [freq 440 length 0.3]
        (* 0.3
           (line:kr 0 1 length FREE)
           (saw (+ freq(* 50 (sin-osc:kr 22)) ))))

    (sawtem)


    (definst both []
        (+
          (sawtem)
          (tem 400 4 4 0.4)))

    (both)


    (definst detunedsaw [freq 300]
      (mix (saw (* freq[0.99 1 1.3]))))

    (detunedsaw 100)

    (stop)

    (definst wobbled-sin [pitch-freq 440 wobble-freq 5 wobble-depth 5]
      (let [wobbler (* wobble-depth (sin-osc wobble-freq))
            freq (+ pitch-freq wobbler)]
        (sin-osc freq)))


    ;; (wobbled-sin 423)

    (comment
      (wobbled-sin)
      ;; you can try it with deeper, slower wobbles:
      (wobbled-sin 440 2 50)
      ;; if you make the wobble much faster, strange sounds emerge:
      (wobbled-sin 440 100 50)
      )

    ;; Combining the previous two ideas, we have the start of a dubstep
    ;; oscillator:

    (definst dubstep [freq 100 wobble-freq 2]
      (let [sweep (lin-exp (lf-saw wobble-freq) -1 1 40 400)
            son   (mix (saw (* freq [0.99 1 1.01])))]
        (lpf son sweep)))

    (dubstep 230 10)
    (kill dubstep)



    (piano 32)
    (piano 34)

    ;; (baz 110)
    ;; (baz 220)
    ;; (baz 660)
    ;; (kill baz )

    ;; (quux)
    ;; (ctl quux :freq 40)

    ;; (stop)

    ;; (demo 7 (lpf (mix (saw [50 (line 100 1600 5) 101 100.5]))
    ;;                   (lin-lin (lf-tri (line 2 20 5)) -1 1 400 4000)))



    ;; overtone.live


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
(defn play-chord [a-chord]
  (doseq [note a-chord] (piano note)))

;; play a chord progression on our piano
(let [time (now)]
  (at time (play-chord (chord :D3 :major7)))
  (at (+ 2000 time) (play-chord (chord :A3 :major)))
  (at (+ 3000 time) (play-chord (chord :A3 :major7)))
  (at (+ 4300 time) (play-chord (chord :F3 :major7))))


