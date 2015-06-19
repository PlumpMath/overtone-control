(ns halic.core)

;; (q/perlin-noise-seq 1 0.1)
(defn draw [state]
  (q/background 255)
  (q/fill 255)
;;   (println (perlin-noise-seq 10 0.1))
  (dotimes [n 25]
;;     (let [ p (perlin-noise-seq 10 0.1)]
    (q/with-translation [0 (* n 10) 0]
      (dotimes [n 15]
;;         (q/with-translation [(* p 10) 0 (-  0 (* n 10))]
;;       (q/with-translation [(* n 10) (+ 50 (* (* 2 (:qbeat state)) (:step32 state))) 10]
;;       (q/box (* (buffer-get r n) 50))
           (q/box 10))))))
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
