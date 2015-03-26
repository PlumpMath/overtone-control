(ns halicscreenshake.core)


(defn space [state]
  (changepos 0 (- (/ Width 2) (* (pi2tr) 10)) (- 200 100))
  (changespeed 0 (/ (tr) 5) 2)
  (drawP state)
  (q/with-translation [(/ (q/width) 2) (/ (q/height) 2) (* (:left state) 10000)]

      (q/box 100)
  )


)



(changespeed 0 0 10)
(changeemitspread 0 0)


