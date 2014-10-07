(ns ext-sxf-gfx.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

;; (connect-external-server 57110)

('ext-sxf-gfx.core/tapper :left)

(def
  ^:private
  ^:dynamic
  *taps* nil)



;; start tapping the main stereo output bus.
(defn init-taps
  [] (when-not *taps*
;;        (boot-server-and-mixer)
       (alter-var-root (var *taps*)
                       (constantly (:taps (tapper))))))

(init-taps)



(defn get-taps
  "Deref and return all of our taps as a plain old map."
  [] (when *taps*
       (into {} (map (fn [[k v]] [k @v]) *taps*))))

;; (get-taps)


(defn- scale-in
  "Scale a tap value by 'max'."
  [taps key max]
  (abs (* (get taps key 0) max)))


(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0})

(defn update [state]
  ; Update sketch state by changing circle color and position.
;;   {:color (mod (+ (:color state) 0.7) 255)
  { :color (* 255 (get (get-taps) :left))
   :angle (+ (:angle state) 0.1)
    :size (* 500 (get (get-taps) :left))

    })


(defn draw [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/fill (:color state) 255 255)
  ; Calculate x and y coordinates of the circle.
  (let [angle (:angle state)
        x (* 150 (q/cos angle))
        y (* 150 (q/sin angle))]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      ; Draw the circle.
;;       (q/ellipse (:size state) 0 (:size state) (:size state)))))
      (q/ellipse (:size state) 0 (:size state) (:size state)))))


(q/defsketch hello-quil
  :title "You spin my circle right round"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update is called on each iteration before draw is called.
  ; It updates sketch state.
  :update update
  :draw draw
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])
