(ns ext-sxf-gfx.core
  (:require [overtone.osc :as osc])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]]
))

;;bidirectional comms
(def GrPORT 4242)
;; ; start a server and create a client to talk with it
(def Gserver (osc/osc-server GrPORT))
;; (osc/osc-close Gserver)
(def GsPORT 4243)
(def Gclient (osc/osc-client "localhost" GsPORT))

(osc/osc-handle Gserver "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))

(osc/osc-handle Gserver "/vvv/a" (fn [msg]
                                (println (:args msg))
;;                                  (reset! beat {:beatnum (first (:args msg))})
                                ))



  @(:a ext-sxf-gfx.core/mytaps)
  @(:mod-env ext-sxf-gfx.core/qfm)
  (:taps ext-sxf-gfx.core/vvv)
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
;;    :qmod-env (@(get (:taps ext-sfx-gfx.core/qfm) :mod-env))
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
