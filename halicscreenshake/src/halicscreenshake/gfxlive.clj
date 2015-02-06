(ns halicscreenshake.core)



;; (setcam)

;; (q/noise 1 1) ;; needs to be inside draw

(tr)
(def seq1 [0 0 1 0 0 0 1 0 0 0 0 1 0 0 0 0 0])


(nth seq1 15)

(defn drawdud []
  (dotimes [n 100]
    (q/with-translation [0 (* n 100) 0]
      (q/stroke 255 20 0)
;;       (q/box (* 1 50))
      )
  )
  )


(defn stuff []
  (setcam (+ 1 (mod16)) 160)
  (if (even? (mod16))
             (q/fill 0 0 (* (pi2tr) (/ 255 6.28)))
;;              (q/fill 255 255 0)
             )
;;   (setcam 1 1)

;;     (q/stroke (into [] solar-yellow))
    (dotimes [n 100]
      (q/line 0 (* n 10) 640 (* n 20))
;;       (q/line (* 3 (nth (take 103 (fib 0 1)) n)) 0 (nth (take 100 (fib 0 1)) n) 1000)
;;       (q/line (* 5000 (:left state)) (* n 10) 1000 (* n 10))
    )

    (dotimes [n 16]
;;       (q/with-rotation [(/ q/PI 2) 0 1 0]
;;       (q/fill 24 1 88)
;;         (q/with-translation [0 0 (* n (/ (q/width) 16))]
;;       (q/rect (* n (/ (q/width) 16)) (/ (q/height) 2) 50 (* (* (* (q/noise (mod16) ) (tr) ) 10)  (pi2tr)))
;;           (q/rect 0 0 50 50 )
;;         )
;;       )
;;       (q/rect (* n (/ (q/width) 16)) (/ (q/height) 2) 50 100)
        (q/rect
           (* n (/ (q/width) 2))
           500
           (/ (q/width) 32)
           (* (/ (q/width) -12) n)
         )
        (q/with-translation [(* 1000 n) (* 200 (pi2tr)) 0]
          (q/sphere (* (* 1 1)  50))
          )

      )
  )



(defn draw1 [x]
  (q/with-translation [(* x (/ (q/width) 2)) 0 0]
      (dotimes [n 16]
        (q/rect (* 500 (* n x)) 0 50 100)
      )
    )
  (q/stroke 255)
  (dotimes [n 100]
    (q/line 0 (* n (mod4)) 1000 (* n 100))

    )

  )







