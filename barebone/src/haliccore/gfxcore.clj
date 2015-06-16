(ns halic.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            ))
  (def width 594)
  (def height 840)

(defn setup []
  (q/frame-rate 30)

)


(defn update [state]
  {
  :qbeat @(get-in q [:taps :beat])
  :pbeat @(get-in p [:taps :beat])
;;    :qa  (- (int @(get (:taps ext-sxf-gfx.core/v) :a)) 255)  ;; state needs to be updated, taps need to exist!
;;    :qb  (- (int @(get (:taps ext-sxf-gfx.core/v) :b)) 255)
;;    :qmod-env (@(get (:taps ext-sfx-gfx.core/qfm) :mod-env))
    })

(defn draw [state]
  (q/background 255)
  (q/fill  (* (:qbeat state) 255))
  (q/box 50)
  (q/fill  (* (:pbeat state) 255))
  (q/with-translation [150 100 100]
    (q/box 100))
  )


(q/defsketch tapdemo
  :title "tapdemo"
  :size [width height]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )
