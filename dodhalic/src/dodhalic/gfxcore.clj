(ns dodhalic.gfxcore
  (:require [overtone.live :as o])
  (:require [overtone.osc :as osc])
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

(def beat (atom {:beatnum 0}))
(defn mod4 []
  (mod  (:beatnum @beat) 4)
  )

(defn mod8 []
  (mod  (:beatnum @beat) 8)
  )

(defn mod1 []
  (mod  (:beatnum @beat) 1)
  )


(osc/osc-handle Gserver "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))

;;   _           _          _                   _
;;  | |         | |        | |                 | |
;;  | |__   ___ | |__  _ __| |__  _   _  __ _  | |_ __ _ _ __
;;  | '_ \ / _ \| '_ \| '__| '_ \| | | |/ _` | | __/ _` | '_ \
;;  | |_) | (_) | | | | |  | |_) | |_| | (_| | | || (_| | |_) |
;;  |_.__/ \___/|_| |_|_|  |_.__/ \__,_|\__, |  \__\__,_| .__/
;;                                       __/ |          | |
;;                                      |___/           |_|

(o/defsynth tapper2
  []
  (let [source (o/sound-in 0 1)
        left (o/select 0 source)
        right (o/select 1 source)
        _ (o/tap :left 10 left)
        _ (o/tap :right 10 right)
        ]
       (o/out 0 left)))

   (def t (tapper2))

;;    @(get-in t [:taps :left])
;;     @(get-in t [:taps :right])
;;    (- @(get-in t [:taps :left])  @(get-in t [:taps :right]))

;; (defsynth tapper
;;   []
;;   (let [source (o/sound-in 0 1)
;;         left (select 0 source)
;;         right (select 1 source)]
;;     (tap :left 10 left)
;;     (tap :right 10 right)
;;     (tap :phase 10 (- left right))))


;;   (def mytaps (:taps (tapper)))
;;   @(:left mytaps)
;;   @(:right mytaps)
;;   @(:phase mytaps)

;;    ____  _    _ _____ _            _          __  __
;;   / __ \| |  | |_   _| |          | |        / _|/ _|
;;  | |  | | |  | | | | | |       ___| |_ _   _| |_| |_
;;  | |  | | |  | | | | | |      / __| __| | | |  _|  _|
;;  | |__| | |__| |_| |_| |____  \__ \ |_| |_| | | | |
;;   \___\_\\____/|_____|______| |___/\__|\__,_|_| |_|


(def width 1040)
(def height 1040)



(defn setup []
  (q/frame-rate 30)

)


(defn update [state]
  {; Update sketch state by changing circle color and position.
  ;;   {:color (mod (+ (:color state) 0.7) 255)
;; ;;   {:color (* 255 (get (get-taps) :left))
;;    :angle (+ (:angle state) 0.1)
;; ;;    :size (* 500 (get (get-taps) :left))
;;    :c100 (c100)
    })

(defn draw [state])

(q/defsketch tapdemo
  :title "lambdasonic"
  :size [width height]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )

