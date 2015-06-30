(ns halic.core
  (:use [clojure.core.match :only [match]])
  (:require [polynome.core :as poly])
  )

(def m (poly/init "/dev/tty.usbserial-m128-121"))



;; define timingsynths


(defn updateM []
  ;;(println (:bpm state))
  ;;(poly/led m (int (:bpm state)) 1 1)
  ;;(if (= (:bpm state) 0)
  (poly/led m (mod16) 0 1)

  (poly/led m (- (mod16) 1) 0 0)

  ;;  (poly/led m (- (:bpm state 1)) 1 0)

    (if (= 0 (mod (mod16) 4))
      ;;light up four LEDs
      (poly/led m (int  (/ ( mod16) 4)) 1 1)
      (poly/led m (int  (-  (/ ( mod16) 4) 1)) 1 0)
      )
  )


(poly/disconnect m)

(ctl b :bpm 1)

(def bpm1 (globalsaw 60))

(poly/led m 12 0 1)


(poly/on-press m (fn [x y s]
                   (match [x y]
                          [7 _] (println "stuff" y)
                          [_ 0] (println "otherstuff" x)
                          [_ 7] (println "othererstuff" x)
                          :else (println "anyother"))))
