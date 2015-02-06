(ns halicscreenshake.core
  (:require [overtone.live :as o])
  (:require [overtone.osc :as osc])
  (:require [quil.core :as q]
            [quil.helpers.calc :refer [mul-add]]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between steps]]
            [quil.helpers.drawing :refer [line-join-points]]
            )
;;   (:use [overtone.live];;if this is core, will use external server!!! live will use internal server
;;         [clojure.core]
;;        )
  )
;;start core
;;start ctrl-core
;;start gfx-core
;;start sfx-core

;; (connect-external-server 57110)






;; gfx helpers


;; timing helpers
(def beat (atom {:beatnum 0}))
(defn mod4 []
  (mod  (:beatnum @beat) 4)
  )

(defn mod8 []
  (mod  (:beatnum @beat) 8)
  )

(defn mod2 []
  (mod  (:beatnum @beat) 2)
  )

(defn mod16 []
  (mod  (:beatnum @beat) 16)
  )

;;number helpers

(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))
 (nth  (fib 0 1) 13) ;; use with care





;;; various helpers
(def tr (seq->stream (cycle-between 1 1 16 0.1 15)))

(def pi2tr (seq->stream (cycle-between 0 0  6.28 0.01 0.01)))

(pi2tr)



;; (setcam 1 5)

(defn modrandom []
  (q/random-seed (mod16))
  )

;; (modrandom)

;;

;;Camera
;;   ___ __ _ _ __ ___   ___ _ __ __ _
;;  / __/ _` | '_ ` _ \ / _ \ '__/ _` |
;; | (_| (_| | | | | | |  __/ | | (_| |
;;  \___\__,_|_| |_| |_|\___|_|  \__,_|


(defn defaultcam []
   (q/camera (/ (q/width) 2.0) (/ (q/height) 2.0) (/ (/ (q/height) 2.0) (q/tan (/ (* q/PI 60.0) 360.0))) (/ (q/width) 2.0) (/ (q/height) 2.0) 0 0 1 0)
)


(defn setcam [x y]
     (q/camera    (/ (q/width) x)
                  (/ (q/height) y)
                  (/ (/ (q/height) 2.0) (q/tan (/ (* q/PI 60.0) 360.0)))

                  (/ (q/width) x)
                  (/ (q/height) 2.0)
                  0

                  0
                  1
                  0
                  )

  )

;; (setcam 1 5)





;;                   _   _      _
;;                  | | (_)    | |
;;  _ __   __ _ _ __| |_ _  ___| | ___  ___
;; | '_ \ / _` | '__| __| |/ __| |/ _ \/ __|
;; | |_) | (_| | |  | |_| | (__| |  __/\__ \
;; | .__/ \__,_|_|   \__|_|\___|_|\___||___/
;; | |
;; |_|



;; enable the particle updater in the update routine



;;   _           _          _                   _
;;  | |         | |        | |                 | |
;;  | |__   ___ | |__  _ __| |__  _   _  __ _  | |_ __ _ _ __
;;  | '_ \ / _ \| '_ \| '__| '_ \| | | |/ _` | | __/ _` | '_ \
;;  | |_) | (_) | | | | |  | |_) | |_| | (_| | | || (_| | |_) |
;;  |_.__/ \___/|_| |_|_|  |_.__/ \__,_|\__, |  \__\__,_| .__/
;;                                       __/ |          | |
;;                                      |___/           |_|

;; (o/odoc o/sound-in)

(o/defsynth tapper2
  []
  (let [source (o/sound-in 0 1)
        left (o/select 0 source)
        right (o/select 1 source)
        _ (o/tap :left 10 left)
        _ (o/tap :right 10 right)
        _ (o/tap :phase 10 (- left right))
        ]
       (o/out [0 1] [left right])
    ))

(def t (tapper2))




