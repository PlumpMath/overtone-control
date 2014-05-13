;; (ns halic.core
;;   (:gen-class)
;; )

(ns halic.core
;;   (:refer-clojure :exclude [tan])
  (:require [overtone.live :as live])
  (:require [overtone.osc :as osc])
;;   (:require [quil.snippet :refer [defsnippet]])
  (:require [quil.core :refer :all])
  (:require [quil.helpers.seqs :refer [seq->stream range-incl]])
  (:require [quil.helpers.drawing :refer [line-join-points]])

;;   (:require [shadertone.tone :as t])
)


(def PORT 4242)

; start a server and create a client to talk with it
(def server (osc/osc-server PORT))
;; (def client (osc/osc-client "localhost" PORT))

(live/sc-osc-debug-off)

;; object defs

(def cube1 (atom {:cubesize 10}))
(reset! cube1 {:cubesize 100})
(:cubesize @cube1)
;; (:z @cube1)

;; //osc handling

;; (osc/osc-handle server "/test" (fn [msg] (println "MSG: " get(msg :args) )))
;;   (osc/osc-handle server "/test" (fn [msg] (get (msg :args) )))

;; (osc/osc-handle server "/test" (defn data [] (fn [msg] msg )))
;;   (osc/osc-handle server "/test" (println (fn [msg] (get (msg :args) ) )))


;;   (osc/osc-msg ["/test" :args])

;; (osc/osc-handle server "/test" (fn [msg] (defn data [] (get(msg :args))) ))

;; (osc/osc-handle server "/test" (fn [msg] ( data [msg]  )))


;; (osc/osc-handle server "/test" (fn [msg] (println "handler for /foo/bar: " msg)))




(osc/osc-handle server "/test" (fn [msg]
                            (let [x (first (:args msg)) y (rest(:args msg))]
                              (reset! cube1 {:cubesize x})
                              )))



(defn drawbox [cube]
  (with-translation [0 1 0]
    (box cube))

  )





(defn setup []
  (background 255)

  (smooth)
  (stroke 0 500)
  (line 20 50 480 50)
  (stroke 2 50 70)
;;   (set-state! :oscmsg (atom [0 0]))
;;   (set-state! :cubesize 10)



)


(defn draw []
  (background 255)
  (camera 200 200 200 0 0 0 0 0 -1)
;;   (drawbox 10)
;;   (with-translation [0 100 0]
;;  (drawbox 10)
;;   )
;;   (with-translation [110 100 0]
;;     (box 70 10 50))
  (stroke-weight 20)
;;   (let [[x y] @(state :oscmsg)]
;;     (drawbox x)
;;     (drawbox y)
;;     )
;;   (let [c (state [:cubesize])]
;;     (drawbox c))
(:cubesize @cube1)
  (drawbox (:cubesize @cube1))
;;   (let [step      10
;;         border-x  30
;;         border-y  10
;;         xs        (range-incl border-x (- (width) border-x) step)
;;         ys        (repeatedly #(rand-y border-y))
;;         line-args (line-join-points xs ys)]
;;     (dorun (map #(apply line %) line-args)))
  )


(defsketch example-5
  :title "Random Scribble"
  :setup setup
  :draw draw
  :renderer :p3d
  :size [500 500])



