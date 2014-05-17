(ns halic.core
  (:require [overtone.live :as live])
  (:require [overtone.osc :as osc])
  (:require [quil.core :refer :all])
  (:require [quil.helpers.seqs :refer [seq->stream range-incl cycle-between tally ]])
  (:require [quil.helpers.drawing :refer [line-join-points]])
)

(defn setup []
  (background 255)
  (smooth)
  ;; communication with bohrbug
;;   (def PORT 4243)
;;   (def server (osc/osc-server PORT))

)
;; object defs
(def beat (atom {:beatnum 0}))
(:beatnum @beat)

;;message handling
(osc/osc-handle server "/beat" (fn [msg]
                                 ;;                                 (println msg)
                                 (reset! beat {:beatnum (first (:args msg))})
                                 ))

(defn mod4 []
  (mod  (:beatnum @beat) 4)
  )

(mod4)

(range-incl 100)
(tally)
(beat)
(if (= 0 (mod4)) )
(def s (seq->stream (cycle-between 10 100 4 40)) )
(def s4 (seq->stream (cycle-between 10 100)) )
(s)
(defn draw []
  (background 120 120 30)
;;   (camera (* (mod4) 100) (* (mod4) 100) (* (mod4) 100) 0 0 0 1 1 1)
;;   (take 5(repeatedly (

  (line 110 100 10 (s))
  (line 210 100 10 (s4))
;;                       )))
;;   (camera)
;;   (perspective)


)


(defsketch example-5
  :title "[]<>"
  :setup setup
  :draw draw
  :renderer :p3d
  :size [500 500])






