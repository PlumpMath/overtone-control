(ns halic.core
  (:require [overtone.live :as live])
  (:require [overtone.osc :as osc])
  (:require [quil.core :refer :all])
  (:require [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]])
  (:require [quil.helpers.drawing :refer [line-join-points]])
)
;; object defs

(defn setup []
  (smooth)

)

(mod4)

(tr)

(def tr (seq->stream (cycle-between 0 100 1 100)))
(def s1 (seq->stream (cycle-between 0 10 0.1 10 )))

(def s (seq->stream (cycle-between 0 100 1 10 )))

(defn drawstuff [size]

  (box size)
  (with-translation [size size 0]
    (fill (tr) 1 98)
    (box (/ size 2))
    )
    (with-translation [0 size size]
      (fill 1 0 98)
    (box (/ size 4))
    )
    (with-translation [0 0 (* ( s) size)]
     (box (/ size 3))
      )

  )



(defn draw []
  (background 10 12 30)
  (ortho)
  (stroke-weight 1)
  (perspective)
  (with-translation [200  100]
    (fill 255 0 0)
;;     (box 100 100 100)
  )
  (with-translation [100 100]
;;     (box 100 100 (* (s) (mod8)))
  )
  (with-translation [100  100]
    (fill 12 110 255)
    (box 154 32 (tr))
  )

  (with-translation [600  100]
    (fill 255 0 0)
    (box (* 5 (s)) (tr) 150)
  )



    (with-translation [(* 100 (mod4)) 40]
    (fill (tr) 255 0)
;;     (box (s) (tr) 170)
  )
;;   (drawstuff 200)
;;     (drawstuff (* (mod8) (s)))

  (stroke-weight 2)
  (stroke (* (tr) 3) 100 (* (tr) 1))
;;   (line (* (s) 10) 100 600 600)
;;   (line (* (s) 10) 10 (* (s) 10) 200)

;;   (line (* (s) 40) 10 (* (s) 10) 200)
;;   (line (* (s) 90) 10 (* (s) 80) 200)
;;   (line (* (s) 90) 10 (* (s) 10) 200)
;;   (line (* (s) 60) 10 (* (s) 10) 200)
;;   (camera)
  (camera 400 (* (mod4) 3 ) 10  0 0 0 0 0 -1)
;;   (camera 375 375 0 375 375 1000 0 1 0)

)




