(ns halicscreenshake.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [newtonian.particle-system :as newt]

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
(def Gclient (osc/osc-client "192.168.2.22" GsPORT))



(osc/osc-handle Gserver "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))

(mod4)




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


  (def Width 1280)
  (def Height 720)
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
  (newt/add-new-particles)
  (newt/update-particles Width Height)

    {
   :left @(get-in t [:taps :left])
   }
  )


(defn draw [state]
  (q/background 0)
  (q/stroke 255 255 0)
;;   (drawdud state)
;;   (drawP state)
;;   (space state)
;;   (stuff)


  )

(q/defsketch halic
  :title "halic screenshake"
  :size [Width Height]
;;   :size [1000 1000]
;;   :size :fullscreen
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )

;; (:beatnum @beat)
