(ns dodhalic.gfxcore)

 (def width 594)
  (def height 840)

(defn setup []
  (q/frame-rate 30)

)


;;bidirectional comms
(def GrPORT 4242)
;; ; start a server and create a client to talk with it
(def Gserver (osc/osc-server GrPORT))
;; (osc/osc-close Gserver)
(def GsPORT 4243)
(def Gclient (osc/osc-client "192.168.7.191" GsPORT))

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
