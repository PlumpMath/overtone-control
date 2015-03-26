(ns halicscreenshake.core
    (:require [newtonian.particle-system :as newt]
              [newtonian.utils :as utils]
             )
    (:import newtonian.utils.Vector2D)
  )


;; (newt/add-field (Vector2D. 100 500) 1000)
(newt/add-emitter (Vector2D. 200 500) (Vector2D. 0 100))


;; (newt/add-emitter (Vector2D. 650 50) (Vector2D. 0 100))


@newt/emitters
;; (swap! newt/fields pop)


(defn draw-particle [{:keys [position velocity accel]} state]
 (q/with-translation [ 0 100  ]
  (q/with-rotation [1 1 0 0]
  (let [x (:x position)
        y (:y position)
        z 5]


;;     (println x)
;;     (q/no-fill)
    (q/fill 0 (* 1 (q/random 255) ) 255 188)
;;     (q/fill (* y 0.5) (* y 0.3) 0 0.5)
;;     (q/stroke-weight (* y 0.003))
;;     (q/stroke 255 0 0 )
    (q/no-stroke)
    (q/rect x y (* 1 100) (* (:left state) 5000))


    (q/with-translation [ x y z ]
      (q/with-rotation [(/ 3.14 2) 1 0 0]
      (q/no-fill)
      (q/stroke (* 10 25))
      (q/stroke-weight 2)
      (q/rect 0 0 250 250)
      )
;;        (changepos 0 (/ 1000 (+ (mod16) 1)) 600)
        (q/box 5)
    )

;;     (q/line 0 y (* (+ 1 (mod4)) y)  y)


    )

    )
   )
)
(defn draw-particle2 [{:keys [position velocity accel]} state]
    (let [x (:x position)
        y (:y position)]
       (q/fill 255 255 0)
       (q/rect x y 50 50)

      )
  )
@newt/emitters

(int 3.4)

(defn drawP [state]
;;   (println "drawingP")
;;   (changespeed 0 1 (* 100 (:left state)))

  (let [particles @newt/particles]
    (doseq [p particles]
      (draw-particle p state)))
)



;;  (nth  @newt/particles 0)

(defn changespeed [emitterindex  speedX speedY]
;;   (swap! newt/emitters update-in [emitterindex :position] assoc :x 200)
  (swap! newt/emitters update-in [emitterindex :velocity] assoc :x speedX )
  (swap! newt/emitters update-in [emitterindex :velocity] assoc :y speedY )
  )


(defn changepos [emitterindex x y]
(swap! newt/emitters update-in [emitterindex :position] assoc :x x )
(swap! newt/emitters update-in [emitterindex :position] assoc :y y )
  )

(defn changeemitspeed [emitterindex speed]
;; (swap! newt/emitters update-in [emitterindex :emission-rate] assoc :x x )
  (swap! newt/emitters update-in [emitterindex] assoc :emission-rate speed)
  )

(defn changeemitspread [emitterindex spread]
;; (swap! newt/emitters update-in [emitterindex :emission-rate] assoc :x x )
  (swap! newt/emitters update-in [emitterindex] assoc :spread spread)
  )

;; (changepos 2 600 0)
;; (changespeed 0 0 (+ 200 (q/random 10)))
;; (changeemitspeed 0 1)
;; (changeemitspread 0 0)


;; (get (nth @newt/emitters 0) :position)
