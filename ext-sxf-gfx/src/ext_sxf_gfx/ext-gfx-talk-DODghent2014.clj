(ns ext-sxf-gfx.core
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between]]
))

;; (connect-external-server 57110)

;; ('ext-sxf-gfx.core/tapper :left)

;; (def
;;   ^:private
;;   ^:dynamic
;;   *taps* nil)


;; ;; start tapping the main stereo output bus.
;; (defn init-taps
;;   [] (when-not *taps*
;; ;;        (boot-server-and-mixer)
;;        (alter-var-root (var *taps*)
;;                        (constantly (:taps (tapper))))))

;; (init-taps)





;; (defn get-taps
;;   "Deref and return all of our taps as a plain old map."
;;   [] (when *taps*
;;        (into {} (map (fn [[k v]] [k @v]) *taps*))))

;; (get-taps)


(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 3)
  (def c100 (seq->stream (cycle-between 0 100 1 100)))
  (def Width (q/width))
  (def Height (q/height))
  (def ratio (/ Width Height))

  {:color 0
   :angle 0})





(defn update [state]
  {; Update sketch state by changing circle color and position.
  ;;   {:color (mod (+ (:color state) 0.7) 255)
;;   {:color (* 255 (get (get-taps) :left))
   :angle (+ (:angle state) 0.1)
;;    :size (* 500 (get (get-taps) :left))
   :c100 (c100)
    })


(def blah (seq->stream (cycle-between 0 10 1 0.1)))
(blah)

(defn heading [h]
  (q/fill 0)
  (q/rect 100 100 400 -50)
  (q/fill 255 128 128)
  (q/text-size 50)
  (q/text h 100 100)
  )
(defn imgcredit [ic]
  (q/fill 255)
  (q/text-size 12)
  (q/text (str "image credit " ic) 10 800)
  )

(defn twittername [t]
  (q/fill 255)
  (q/text-size 20)
  (q/text t 10 800)

  )


(def img (ref nil))
(defn fitimage [src]
  (dosync (ref-set img (q/load-image src)))
  (if (<= (/ (. @img width) (. @img height)) ratio)
    (q/image @img (/ (- Width (* (/ Height (. @img height)) (. @img width))) 2) 0 (* (/ Height (. @img height)) (. @img width)) (* (/ Height (. @img height)) (. @img height)))
    (q/image @img 0 (/ (- Height (* (/ Width (. @img width)) (. @img height))) 2) (* (/ Width (. @img width)) (. @img width)) (* (/ Width (. @img width)) (. @img height)) )
   )
)

(defn draw [state]
  (q/background 0)

;; ====== Introduction =======

;; === HALIC ===
  (q/fill 255 255 0)
  (fitimage "resources/control_room.jpg")
  (q/text-size 50)
  (q/text "H.euristic" 100 100)
  (q/text "AL.gorithmic" 100 200)
  (q/text "I.nteractive" 100 300)
  (q/text "C.omputing" 100 400)
;;   (def keywords ["humans", "machines", "interaction"])
;; Ralph McQuarrie concept art for the original Battlestar Galactica series


;; ;; while explaining halic, make some sound, and map a tap on the color, showing the interactive part

;; ==== H ====
;;   stands for human

;;   (q/text-size 500)
;;   (q/text "H" 100 600)

;; ;; === humans: kaosbeat & bohrbug ===
;;   (fitimage "resources/halic.jpg")

;; = kaosbeat =
;; (fitimage "resources/kaos.png")
;; (heading "Hi There!")
;; (twittername "@kaosbeat")
;; (def keywords ["Architect" "@kaosbeat" "creative technologist"])

;; = bohrbug =
;; (fitimage somepic.jpg)
;; (def keywords ["describe" "yourself" "in" "keywords" ])

;; ==== AL ====
;;   stands for alien / algorithmic

;; "for $50 you can buy a home PC program that will crush most grandmasters."
;;   (fitimage "resources/Fincollo.jpg")
;;   (imgcredit "http://culprittech.blogspot.be/")
;;   (q/text-size 400)
;; ;;   (q/text "AL" 50 550)
;;   (def keywords (["alien" "data" "unknown/untrusted source" "machines"]))
;;   (def keywords ["goldorak" "ijzeren reus"])

;; === philosophy ===
;;   (fitimage "resources/control_room.jpg")
;;   (heading "Control the galaxy")


;; === livecoding ===
;; (fitimage "resources/moog.jpg")
;; (def keywords ["live" "tool" "or" "instrument"])


;; === example ===
;;  ==sine wave ==

  ;; draw (def function already loaded)
  ;; sound (sin-osc 440 with tap)

  ;;viz and sound




;; ;; == clojure ==
;; (def keywords ["lambda-calculus" "functional programming"])

  )




=== lambdasonic promo ===
=== halic === (D + K)
stands for
halic is ons project.
Heuristic, Algorithmic, interactive, computing
a search for music and sound synesthesia

=== humans ===
kaosbeat/bohrbug (D + K)

=== why even consider humans, they are weak === (K)
Kasparov => human + computers = good

=== HAL is not (good) enough === (K)
it's not intuition
aesthaetics

=== HAL is not interactive computation (K)
computer as an instrument, not a tool
deep blue is not an instrument, but a tool

=== the moog is an instrument (compare) (K -> D)
interactive music making, analog live coding

=== waarom dan geen moog? computers kunnenm nog net iets meer?  (D)
geluid = heel goed, maar compositie was nog pover

=== composing? -> symbolic composer (D)
not good enough (geen improvisatie)

=== composing and improvising with machines (D)

=== tools (K)
Pure data / max -> Lighttable / Emacs
dichter bij de machine

=== because bytecode, because JVM inject, lambda-calculus


=== go R














(q/defsketch hello-quil
  :title "Devops Days Ghent"
  :size [594 840]
  :setup setup
  :update update
  :draw draw
;;   :renderer :p3d
  :middleware [m/fun-mode]

  )
