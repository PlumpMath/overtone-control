(ns dodhalic.gfxcore)

(mod4)
;; (newt/add-field (Vector2D. 280.0 305.0) 100.0)
(newt/add-emitter (Vector2D. 600.0 20.0) (Vector2D. 0.0 10.0))

;; (swap! newt/fields pop)
;; (swap! newt/emitters pop)
;; (def test (atom ()))

;; (swap! test conj :blaaah)

;; (swap! test pop)
;; test

newt/emitters
;; (nth @newt/emitters 0)
;; (swap! (get (nth @newt/emitters 0) :position) :x 200)
;; ;; (swap! newt/emitters update-in [0 :position] assoc :x 200)
;;  (get (nth @newt/emitters 0) :position)
;; newt/fields


;; (swap! newt/emitters (nth @newt/emitters 0)  )
;; (swap!)
;; (assoc-in @newt/emitters [0] {:position{:x 200}}))

;; @newt/draw-options
;; newt/draw-options

;; (swap! newt/draw-options assoc :accelerations true)

;; (swap! newt/emitters assoc :emission-rate 1 )

(swap! newt/emitters update-in [0] assoc :spread)

(swap! newt/emitters update-in [0 :position] assoc :x 500)
;; (swap!(nth @newt/emitters 0) assoc-in {:position {:x 100}})

(defn update [state]
  ;;update statemap here
  (swap! newt/emitters update-in [0] assoc :emission-rate (mod4))
  (newt/add-new-particles)
  (newt/update-particles width height)
;;   (swap! newt/emitters update-in [0 :position :x] + 0)
  {
   :left @(get-in t [:taps :left])
   })

(mod2)

@(get-in t [:taps :phase])

(defn draw-particle [{:keys [position velocity accel]} state]
  (let [x (:x position)
        y (:y position)
        a (utils/mag accel)
        s (utils/mag velocity)]
;; (    println s)
;;     (q/no-stroke)

;;     (q/fill-int (q/color 150 (/ (* 220 0.5) s) (* 76 s) 255))
;;     (q/fill 255 25 0)
    (q/no-fill)
    (q/stroke 255 255 255)
;;     (q/ellipse x y (* y (:left state)) (* y 0.05) )
    (q/with-translation [x y 50]
      (q/stroke 255 0 0 )
      (q/stroke-weight 5)
      (q/box 5 )
      (q/stroke-weight 3)
      (q/stroke 255 255 255)
      (q/box 50)

      )
;;     (q/line x y (+ x 200) (* (+ y 200) (mod4)))
    )
  )

(defn draw-field [{:keys [position]}]
  (let [x (:x position)
        y (:y position)]
    (q/no-stroke)
    (q/fill-int (q/color 255 (* (mod4) 64) 64))
    (q/ellipse x y 10.0 10.0)))



(defn draw [state]
  (q/perspective)
;;   (q/camera 250 800 10 20 2500 0 1 1 1)
  (q/camera 499 702 -1 500 514 104 0 -1 0 )
;;   (q/camera (/ width 2) 1000 50 (/ width (+ (mod4) 1)) 500 0 0 1 0)
  (q/background 0)
;;   draw stuff here
  (q/stroke 100 100 0 500)
  (dotimes [n 100] (q/line (* n 10) 0 (* n 10)  1000)
                   (q/line 0 (* n 10) 1000 (* n 10)))
  (q/line 10 10 1000 1000)
    (let [particles @newt/particles
        fields @newt/fields]
   (doseq [p particles]
     (draw-particle p state))
   (doseq [f fields]
     (draw-field f)))

  )



