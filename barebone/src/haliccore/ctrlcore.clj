(ns halic.core
   (:require [polynome.core :as poly])
  )

(defonce m (poly/init "/dev/tty.usbserial-m128-121"))


;; define timingsynths

(defsynth globalsaw
   [bpm 120]
   (let  [a (/ bpm 60)
          _ (tap:kr :step 32 (lf-saw:kr a))]
     (out 0 (lf-saw:kr a))))

(def bpm (globalsaw 120))



(defn updateM [state]
  (println (:bpm state))
  (poly/led m (int (:bpm state)) 1 1)
  (if (= (:bpm state) 0)
    (poly/led m 0 1 0)
    (poly/led m (- (:bpm state 1)) 1 0)))



(ctl bpm :bpm 15)

(def bpm1 (globalsaw 60))

(poly/led m 12 0 1)
