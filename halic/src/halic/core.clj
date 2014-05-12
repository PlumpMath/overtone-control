;; (ns halic.core
;;   (:gen-class)
;; )

(ns halic.core
;;   (:refer-clojure :exclude [tan])
  (:require [overtone.live :as live])
  (:require [overtone.osc :as osc])
;;    (:require [quil.snippet :refer [defsnippet]])
  (:require [quil.core :refer :all])

;;   (:require [shadertone.tone :as t])
)


(def PORT 4242)

; start a server and create a client to talk with it
(def server (osc/osc-server PORT))
(def client (osc/osc-client "localhost" PORT))

(live/sc-osc-debug-off)



(live/definst tem [freq 440 depth 10 rate 6 length 3]
    (* 0.3
       (live/line:kr 0.5 1 length live/FREE)
       (live/saw (+ freq (* depth (live/sin-osc:kr rate))))))

(tem 400 4 6 3)
(tem 200 4 6 3)
;; (stop)

;; (t/start "shaders/disco.glsl"
;;          :width 800 :height 800
;;          :title "Quasicrystal")
