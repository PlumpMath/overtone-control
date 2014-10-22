(ns dodhalic.gfxcore
  (:require [overtone.osc :as osc])
  (:require [quil.core :as q]
            [quil.middleware :as m]
;;             [newtonian.particle-system :as newt]
;;             [newtonian.utils :as utils]
;;             [quil.helpers.calc :refer [mul-add]]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between steps]]
            [quil.helpers.drawing :refer [line-join-points]]
            )
;;   (:import newtonian.utils.Vector2D)
            )


(def width 594)
(def height 840)

(defn setup []
  (q/frame-rate 30)

)


;;bidirectional comms
(def GrPORT 4242)
;; ; start a server and create a client to talk with it
(def Gserver (osc/osc-server GrPORT))
;; (osc/osc-close Gserver)
(def GsPORT 4243)
(def Gclient (osc/osc-client "localhost" GsPORT))

(def beat (atom {:beatnum 0}))
(defn mod4 []
  (mod  (:beatnum @beat) 4)
  )

(defn mod8 []
  (mod  (:beatnum @beat) 8)
  )

(defn mod1 []
  (mod  (:beatnum @beat) 1)
  )


(osc/osc-handle Gserver "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))
