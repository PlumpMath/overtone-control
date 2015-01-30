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

