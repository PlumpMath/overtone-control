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
  (let [x (:x position)
        y (:y position)]


;;     (println x)
;;     (q/no-fill)
    (q/fill 255 (* 1 (q/random 255) ) 255 18)
;;     (q/fill (* y 0.5) (* y 0.3) 0 0.5)
;;     (q/stroke-weight (* y 0.003))
;;     (q/stroke 255 0 0 )
    (q/no-stroke)
    (q/rect x y (* 1 100) (* (:left state) 500))
;;     (q/rect x y -65 65)
    (q/with-translation [ x y ]
;;        (changepos 0 (/ 1000 (+ (mod16) 1)) 600)
;;         (q/box 50)
    )

;;     (q/line 0 y (* (+ 1 (mod4)) y)  y)


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



 (nth  @newt/particles 0)

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

(changepos 2 600 0)
(changespeed 0 0 (+ 200 (q/random 10)))
(changeemitspeed 0 1)
(changeemitspread 0 0)


(get (nth @newt/emitters 0) :position)
