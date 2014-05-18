;; (ns halic.core
;;   (:gen-class)
;; )

(ns halic.core
;;   (:refer-clojure :exclude [tan])
  (:require [overtone.live :as live])
  (:require [overtone.osc :as osc])
;;   (:require [quil.snippet :refer [defsnippet]])
  (:require [quil.core :refer :all])
  (:require [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]])
  (:require [quil.helpers.drawing :refer [line-join-points]])

;;   (:require [shadertone.tone :as t])
)




(def PORT 4242)
;; ; start a server and create a client to talk with it
(def server (osc/osc-server PORT))
;; ;; (def client (osc/osc-client "localhost" PORT))

;; (live/sc-osc-debug-on)


;; (osc/osc-close server)


(def beat (atom {:beatnum 0}))
;; (osc/osc-handle server "/live-seq" (fn [msg]
;; ;;                                 (if (empty? (first (:args msg))) (println "not good"))
;; ;;                             (let [x (first (:args msg)) y (first(rest(:args msg)))]
;; ;;                               (reset! cube1 {:cubesize x})
;; ;;                               (println y)
;;                               ))

(osc/osc-rm-listener server "/live-seq")

(:beatnum @beat)

(osc/osc-handle server "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))


;; (osc/osc-handle server "/wobble" (fn [msg]
;;                                 (println "weird "(:args msg))
;; ;;                                  (reset! beat {:beatnum (first (:args msg))})
;;                                 ))
;; (def cube1 (atom {:cubesize 10}))


;; (defn drawbox [cube]
;;   (box cube)
;;   (with-translation [0 (* (+ 1 (mod4)) cube) 0]
;;     (box cube)
;;     (with-translation [cube 0 0]
;; ;;       (sphere (/ cube 2) )
;;       (box 10)
;;       )
;;     (with-translation [(- 0 cube) 0 0]
;; ;;       (sphere (/ cube 2) )
;;       (box 10)
;;       )
;;   )
;;   (begin-shape :triangles )
;;                (vertex 0 live/b-low-pass4 0)
;;                (vertex 200 0 0)
;;                (vertex 0 200 0)
;;                (vertex 200 200 0)

;;   (end-shape :close)

;;   )


(defn mod4 []
  (mod  (:beatnum @beat) 4)
  )

(defn mod8 []
  (mod  (:beatnum @beat) 8)
  )

(defn mod1 []
  (mod  (:beatnum @beat) 1)
  )








(defn setup []
  (background 255)

  (smooth)


)

;; (live/lf-pulse:kr 0.5 0 3)

;; (* (live/sin-osc:kr 10.0 0) 1)

(defn draw []
  (background 245  45 0)
  (camera 500 300 200 0 0 0 0 0 -1)

;;   (spot-light 255, 255, 0
;;               100, -40, 200
;;               0, 0.5, 0.5
;;               1.5, 2)

  (point-light 150, 150, 0
               1, 0, 0)

;;   (camera)
  (stroke-weight 1)
  (stroke 255 255 255)
  (fill 45  45 0)
;;   (no-fill)
  (ortho 0 1000 0 1000)
;;   (perspective)
;;   (line 0 (/ 500 (mod8)) 100 500)



  (sin 45)
;;   (live/line [0.0 1.0 1.0 0])
;;   (mod4 beat :beatnum)

;;   (with-translation [(* (mod4) 100) 0 0]

;;   (with-translation [10 100 0])

;;   (drawscore )

;;   (drawbox (kube1))
  (drawbox 100)

;;   (drawbox (:    cubesize @cube1))
    )
;;   (let [step      10
;;         border-x  30
;;         border-y  10
;;         xs        (range-incl border-x (- (width) border-x) step)
;;         ys        (repeatedly #(rand-y border-y))
;;         line-args (line-join-points xs ys)]
;;     (dorun (map #(apply line %) line-args)))



(defsketch kaosbeat
  :title "[]<>"
  :setup setup
  :draw draw
  :renderer :p3d
  :size [750 750])

;; (:cubesize @cube1)





