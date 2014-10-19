(ns ext-sxf-gfx.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]]
))

  @(:a ext-sxf-gfx.core/mytaps)
  @(:mod-env ext-sxf-gfx.core/qfm)
  (:taps ext-sxf-gfx.core/v)
  (int @(get (:taps ext-sxf-gfx.core/v) :a))

(defn setup []
  (q/frame-rate 30)
)

(defn update [state]
  ; Update sketch state by changing circle color and position.
  ;;   {:color (mod (+ (:color state) 0.7) 255)
  {
   :qa  (- (int @(get (:taps ext-sxf-gfx.core/v) :a)) 255)
   :qb  (- (int @(get (:taps ext-sxf-gfx.core/v) :b)) 255)
    })

(defn draw [state]
  (q/fill 255  (int (:qa state)) 0)
  (q/rect 100 100 100 100)
  (q/fill 0  (int (:qb state)) 0)
  (q/rect 250 100 100 100)
  )


(q/defsketch tapdemo
  :title "tapdemo"
  :size [594 840]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )
