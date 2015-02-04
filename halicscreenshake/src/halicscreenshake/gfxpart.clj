(ns halicscreenshake.core)


(newt/add-field (Vector2D. 500 500) -1000)
(newt/add-emitter (Vector2D. 650 50) (Vector2D. 0 100))
(swap! newt/emitters update-in [0 :position] assoc :x 300)


newt/emitters



(defn draw-particle [{:keys [position velocity accel]} state]
  (let [x (:x position)
        y (:y position)]
    (q/no-fill)
    (q/stroke 255 0 0 )
    (q/rect x y -65 65))
  )


