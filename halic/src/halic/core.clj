;; (ns halic.core
;;   (:gen-class)
;; )

(ns halic.core
  (:use [overtone.live]
;;         [overtone.osc]
        )
   (:require [quil.snippet :refer [defsnippet]]
            [quil.core :refer :all])
;;   (:require [shadertone.tone :as t])
)

(def PORT 4242)

; start a server and create a client to talk with it
(def server (osc-server PORT))
(def client (osc-client "localhost" PORT))

(sc-osc-debug-off)


(definst bar [freq 220] (saw 110))
(definst baz [freq 440] (* 0.3 (saw freq)))
(definst quux [freq 440] (* 0.3 (saw freq)))


(definst tem [freq 440 depth 10 rate 6 length 3]
    (* 0.3
       (line:kr 0.5 1 length FREE)
       (saw (+ freq (* depth (sin-osc:kr rate))))))

(tem 400 4 6 3)
(tem 200 4 6 3)
;; (stop)

(t/start "shaders/disco.glsl"
         :width 800 :height 800
         :title "Quasicrystal")
