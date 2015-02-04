(ns halicscreenshake.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [newtonian.particle-system :as newt]
            [newtonian.utils :as utils]
            [quil.helpers.calc :refer [mul-add]]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between steps]]
            [quil.helpers.drawing :refer [line-join-points]]
            )
  (:import newtonian.utils.Vector2D)
            )





;;    _____ ____  __  __ __  __  _____
;;   / ____/ __ \|  \/  |  \/  |/ ____|
;;  | |   | |  | | \  / | \  / | (___
;;  | |   | |  | | |\/| | |\/| |\___ \
;;  | |___| |__| | |  | | |  | |____) |
;;   \_____\____/|_|  |_|_|  |_|_____/




;;bidirectional comms
(def GrPORT 4242)
;; ; start a server and create a client to talk with it
(def Gserver (osc/osc-server GrPORT))
;; (osc/osc-close Gserver)
(def GsPORT 4243)
(def Gclient (osc/osc-client "localhost" GsPORT))


;;; various helpers
(def tr (seq->stream (cycle-between 1 1 16 0.1 15)))

(def pi2tr (seq->stream (cycle-between 0 0  6.28 0.01 0.01)))

(pi2tr)

(osc/osc-handle Gserver "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))


;;Camera

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






;;    ____  _    _ _____ _            _          __  __
;;   / __ \| |  | |_   _| |          | |        / _|/ _|
;;  | |  | | |  | | | | | |       ___| |_ _   _| |_| |_
;;  | |  | | |  | | | | | |      / __| __| | | |  _|  _|
;;  | |__| | |__| |_| |_| |____  \__ \ |_| |_| | | | |
;;   \___\_\\____/|_____|______| |___/\__|\__,_|_| |_|

(defn hex-str-to-dec
  "Convert hex RGB triples to decimal."
  [s]
  (map (comp #(Integer/parseInt % 16)
             (partial apply str))
       (partition 2 s)))


(def Width 1000)
(def Height 1000)
(def ratio (/ Width Height))


(hex-str-to-dec "cb4b16")

(def solar-yellow '(181 137 0))

solar-yellow
(vector solar-yellow)


(def solar-magenta '(211 54 130))

(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))
 (take 5 (fib 0 1))

(defn setup []
  (q/frame-rate 30)

)


(defn update [state]
    {
   :left @(get-in t [:taps :left])
   }
  )


(defn draw [state]
  (q/background 0)
  (q/stroke 255)
;;   (q/rect -500 50 (* 50 (+ 1(mod16))) 50)
  (q/rect 50 500  (* 1000 (:left state)) 1)
;;   (println "23")
;;   (showbeat)
;;   (drawdud)
  (stuff)
;;   (q/camera 0 0 (* 10000 (q/noise  (mod16)))  0 0 0 0 1 0)
;;   (q/camera 0 0 0  100 100 100   0 1 0)
(drawP state)

  )

(q/defsketch halic
  :title "halic screenshake"
  :size [Width Height]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )

;; (:beatnum @beat)
