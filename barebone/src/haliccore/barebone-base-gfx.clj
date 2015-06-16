(ns barebone.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]]
))

;; (connect-external-server 57110)

;; ('ext-sxf-gfx.core/tapper :left)

;; (def
;;   ^:private
;;   ^:dynamic
;;   *taps* nil)


;; ;; start tapping the main stereo output bus.
;; (defn init-taps
;;   [] (when-not *taps*
;; ;;        (boot-server-and-mixer)
;;        (alter-var-root (var *taps*)
;;                        (constantly (:taps (tapper))))))

;; (init-taps)





;; (defn get-taps
;;   "Deref and return all of our taps as a plain old map."
;;   [] (when *taps*
;;        (into {} (map (fn [[k v]] [k @v]) *taps*))))

;; (get-taps)


(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  (def c100 (seq->stream (cycle-between 0 100 1 100)))
  (def ratio (q/width) (q/hei))
  {:color 0
   :angle 0})






(defn update [state]
  ; Update sketch state by changing circle color and position.
  ;;   {:color (mod (+ (:color state) 0.7) 255)
  {:color (* 255 (get (get-taps) :left))
   :angle (+ (:angle state) 0.1)
   :size (* 500 (get (get-taps) :left))
   :c100 (c100)
    })


(defn draw [state]
  (q/background 255)

  )


(q/defsketch hello-quil
  :title "You spin my circle right round"
  :size [594 840]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )
