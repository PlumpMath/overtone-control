(ns ext-sxf-gfx.core
  (:require [overtone.osc :as osc])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]]
))

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

(mod4)
(osc/osc-handle Gserver "/beat" (fn [msg]
;;                                 (println (:args msg))
                                 (reset! beat {:beatnum (first (:args msg))})
                                ))

(osc/osc-handle Gserver "/vvv/a" (fn [msg]
                                (println (:args msg))
;;                                  (reset! beat {:beatnum (first (:args msg))})
                                ))


 (osc/osc-rm-all-handlers Gserver)


  @(:a ext-sxf-gfx.core/mytaps)
  @(:mod-env ext-sxf-gfx.core/qfm)
  (:taps ext-sxf-gfx.core/vvv)
  (/ (int @(get (:taps ext-sxf-gfx.core/v) :a)) 255)


(defn fib [a b] (cons a (lazy-seq (fib b (+ b a)))))

(def rfib16 (reverse (take 16 (fib 5 9))))
(def base (/ width (first rfib16)))
base

(defn dorect [size]
  (q/rect (-  (/ width 2) (* base size) ) (-  (/ height 2) (* base size) )  (* base size) (* base size))

  )


(defn setup []
  (q/frame-rate 30)
  (def width (q/width))
  (def height (q/height))
)

(defn update [state]
  ; Update sketch state by changing circle color and position.
  ;;   {:color (mod (+ (:color state) 0.7) 255)
  {

   :qa  (- (int @(get (:taps ext-sxf-gfx.core/v) :a)) 255)  ;; state needs to be updated, taps need to exist!
   :qb  (- (int @(get (:taps ext-sxf-gfx.core/v) :b)) 255)
;;    :qmod-env (@(get (:taps ext-sfx-gfx.core/qfm) :mod-env))
    })

(defn draw [state]
  (q/background 255)
  (q/fill  255 55 255)
;;   (q/fill 255  (int (:qa state)) 0)
;;   (q/rect 100 100 (* (mod4) 100) 100)
;;   (q/fill 0  (int (:qb state)) 0)
  (q/rect 250 100 100 100)
  (dotimes [n 16] (dorect (* (/ (:qa state) 25) (nth rfib16 n) )))
  )


(q/defsketch tapdemo
  :title "tapdemo"
  :size [594 840]
  :setup setup
  :update update
  :draw draw
  :renderer :p3d
  :middleware [m/fun-mode]

  )
