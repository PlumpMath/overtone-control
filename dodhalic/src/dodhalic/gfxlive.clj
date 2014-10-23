(ns dodhalic.gfxcore)

(mod4)
(newt/add-field (Vector2D. 280.0 305.0) 100.0)
(newt/add-emitter (Vector2D. 200.0 280.0) (Vector2D. 0.0 10.0))

(swap! newt/fields pop)
(swap! newt/emitters pop)
(def test (atom ()))

(swap! test conj :blaaah)

(swap! test pop)
test

newt/emitters
(nth @newt/emitters 0)
(swap! (get (nth @newt/emitters 0) :position) :x 200)
 (get (nth @newt/emitters 0) :position)
newt/fields


(swap! newt/emitters (nth @newt/emitters 0)  )
;; (swap!)
;; (assoc-in @newt/emitters [0] {:position{:x 200}}))

@newt/draw-options
newt/draw-options

(swap! newt/draw-options assoc :accelerations false)



(swap!(nth @newt/emitters 0) assoc-in {:position {:x 200}})

(defn update [state]
  ;;update statemap here
  (newt/add-new-particles)
  (newt/update-particles width height)
  {})

(defn draw-particle [{:keys [position velocity accel]}]
  (let [x (:x position)
        y (:y position)
        a (utils/mag accel)
        s (utils/mag velocity)]
;; (    println s)
;;     (q/no-stroke)

;;     (q/fill-int (q/color 150 (/ (* 220 0.5) s) (* 76 s) 255))
    (q/fill 255 255 0)
    (q/ellipse x y 2 23.0 )
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
  (q/background 28)
;;   draw stuff here
  (q/line 420 209 342 239)
    (let [particles @newt/particles
        fields @newt/fields]
   (doseq [p particles]
     (draw-particle p))
   (doseq [f fields]
     (draw-field f)))

  )

(rest '(100))
