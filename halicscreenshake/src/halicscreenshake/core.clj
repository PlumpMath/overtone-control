(ns halicscreenshake.core
  (:require [overtone.live :as o])
  (:require [overtone.osc :as osc])
  (:require [quil.core :as q])
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

;;camera helpers


(defn defaultcam []
   (q/camera (/ (q/width) 2.0) (/ (q/height) 2.0) (/ (/ (q/height) 2.0) (q/tan (/ (* q/PI 60.0) 360.0))) (/ (q/width) 2.0) (/ (q/height) 2.0) 0 0 1 0)
)





;; (setcam 1 5)

(defn modrandom []
  (q/random-seed (mod16))
  )

;; (modrandom)



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




