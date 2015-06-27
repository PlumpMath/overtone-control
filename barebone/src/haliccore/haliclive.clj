(ns halic.core)



(def b (seq->stream (cycle-between 0 255)))
(b)


(def r [0 0 2 1 0 1 2 1 1 2])



;; ;; (q/perlin-noise-seq 1 0.1)
(defn draw [state]
;;   () println
;;   ;;   (q/background (b))


;;   (q/background (* (:step32 state) 16) 20 200)

;;   (dotimes [n 8]
;;     (if (= (:slowstep state) n)

 ;;      (if (= (r n) 1)
        (q/with-translation [(* n 55) 50 0]
          (q/box 100)
        )

;;   (println (perlin-noise-seq 10 0.1))
  (dotimes [n 25]
;;    (let [ p (perlin-noise-seq 10 0.1)]
    (q/with-translation [0 (* n 10) 0]
      (dotimes [n 15]
;;         (q/with-translation [(* p 10) 0 (-  0 (* n 10))]
      (q/with-translation [(* (/ (:slowstep state) 1) (:step32 state)) (+ 50 (* (*  (:qbeat state)) (:step32 state))) (q/random 255)]
;;       (q/box (* (buffer-get r n) 50))
         (q/fill (q/random 255))
            (q/box 10)
        )))
    )
)
;;     )



(defn draw [state]
  (q/background (:pbeat state ))
  (q/fill 255)
          ;; (q/with-translation [(* 50 (* n (:qbeat state))) 0 0 ]
          ;;   (q/box 100))
          ;; )
          ;; (println (:step32 state))

  (dotimes [n 8]
     ;; (if (= (:step32 state  ) n )
    ;;
    (q/with-translation [ (
                              * 3 100) 100 100]
         (q/box 10)
            (q/with-rotation [0.57 1 0 0]
           (q/box 10)
                    )
         )
       ;;    )
   )





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

)
