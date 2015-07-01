(ns halic.core)



;;(def b (seq->stream (cycle-between 0 255)))
;;(b)



(def r [0 0 2 1 0 1 2 1 1 2])



;; ;; (q/perlin-noise-seq 1 0.1)
(defn draw [state]

 ;;  (q/background (* (:step32  state) 111) 20 200)
  (q/with-translation [(* 10 (:mod16 state)) 150 0 ]
    (dotimes [n 8]
      (if (= (r n) 1)
        (q/with-translation [(* n 55) 50 0]
          (q/box 50)
          )
        )
      )))




(dotimes [n 25]
  (q/fill (q/random 255) (q/random 2) 250)
  (q/with-translation [(* ( b) 10) (* n (b)) (* 0 (:step32 state))]
    ;;     (dotimes [n 15]
    ;;       (q/with-translation [(* (/ (:slowstep state) 1) (:step32 state)) (+ 50 (* (*  (:qbeat state)) (:step32 state))) (q/random 255)]
    ;;         (q/box (* (buffer-get r n) 50))
    ;;         (q/fill (q/random 255)))
    (q/box 10)
    (q/line (* 100 n) n 0 0)
    )
  ;;      ))
  )







(defn draw [state]

  (q/fill 255)
  ;; (q/with-translation [(* 50 (* n (:qbeat state))) 0 0 ]
  ;;   (q/box 100))
  ;; )
  ;; (println (:step32 state))


  (dotimes [n 80]
    ;; (if (= (:step32 state  ) n )
    ;;
    (q/with-translation [ (
                           * 3 100) 200 100]
      (q/box 100 10 10)
      (q/with-rotation [(* n  (:mod16 state)) 1 0  0]
        (q/box 10 100 10)
        )
      )
    ;;    )
    ))





  ;; (:step32 state


  ;; (control-bus 1 "tester")
  ;; (bus-monitor "my-bus")
  ;; (bus? )

  ;; (control-bus-set! 1 300000)

  ;; (control-bus-get 1)



  ;;stuff to test

  ;; share data
  ;;   busses
  ;;   buffer
  ;;   synths


  ;;audio
  ;;iedere synth op aparte bussen naar server sturen
  ;; pas op server mixen (POC objecten op scherm laten bewegen enkel als er audio op een bepaalde bus is)
