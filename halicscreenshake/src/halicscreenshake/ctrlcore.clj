(ns halicscreenshake.core
  (:use [clojure.core.match :only [match]]
;;         [overtone.inst sampled-piano]
        )
  (:require [polynome.core :as poly]))


(defonce m (poly/init "/dev/tty.usbserial-m128-121"))




(poly/on-press m (fn [x y s]
                   (match [x y]
                          [7 _] (println "stuff" y)
                          [_ 0] (println "otherstuff" x)
                          [_ 7] (println "othererstuff" x)
;;                           [_ 1] (defaultcam)
                          :else (println "anyother"))))
;; (setcam 1)

(poly/led m 12 0 1)
(poly/led-activation m 12 0)







(defn showbeat []
;;   (println (:beatnum @beat))

  (poly/led m (mod16) 0 1)
  (poly/led m (dec (mod16)) 0 0)
  )

(mod16)

;; (showbeat)

;; (sampled-piano 1)


;; (poly/col m 1 [0 0 0 ])

;; seq1

;; (poly/cols m)


;; (poly/led-state m)


(poly/callbacks m)
