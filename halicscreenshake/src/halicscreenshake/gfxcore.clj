(ns halicscreenshake.core
  (:require [quil.core :as q]
            [quil.middleware :as m]

))





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



(osc/osc-handle Gserver "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))






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


(defn setup []
  (q/frame-rate 30)

)


(defn update [state]
;;   (newt/add-new-particles)
;;   (newt/update-particles Width Height)

    {
   :left @(get-in t [:taps :left])
   }
  )


(defn draw [state]
  (q/background 0)
  (q/stroke 255 255 0)
  (q/line 50 50 500 500)
;;   (defaultcam)
;;   (changespeed 0 0 (+ 200 (q/random 10)))

;;   (doseq [pos [seq1]]
;;               (q/with-translation [0 (* (q/random 500) pos) 0]
;;                 (q/box 50)
;;                 )
;;               )

;;   (setcam 500 0)
;;   (q/ortho)
;;   (q/perspective)
;;   (changeemitspeed (mod4) 0)
;;   (changeemitspeed (+ (mod4) 1) 1)

;;   (changeemitspread 2 1)
;;   (draw1 (q/abs (:left state)))

;;   (setcam 100 100)
;;   (q/rect -500 50 (* 50 (+ 1(mod16))) 50)
;;   (q/rect 50 500  (* 10000 (q/abs (:left state))) 10)
;;   (println "23")
;;   (drawdud)
;;   (stuff)
;;   (q/camera 0 0 (* 10000 (q/noise  (mod16)))  0 0 0 0 1 0)
;;   (q/camera 500 500 (* (pi2tr) 500)  500 500 0   (pi2tr) 1 0)

  (q/camera 1000 0 10 500 500 100 0 1 0)

    (q/with-translation [ (/ (q/width) 2) (/ (q/height) 2) 0]
    (q/with-rotation [(* 3.14 0.5) 1 0 0 ]
     (q/box 50)

;;       (drawP state)
    )
      )

  (if (= 3 (mod2))
    (q/display-filter :blur 1)
    )

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
