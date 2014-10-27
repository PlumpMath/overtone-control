(ns dodhalic.gfxcore)
(defn update [state]
 {
   :left @(get-in t [:taps :left])
   })


;;       _ _     _       __                  _   _
;;      | (_)   | |     / _|                | | (_)
;;   ___| |_  __| | ___| |_ _   _ _ __   ___| |_ _  ___  _ __  ___
;;  / __| | |/ _` |/ _ \  _| | | | '_ \ / __| __| |/ _ \| '_ \/ __|
;;  \__ \ | | (_| |  __/ | | |_| | | | | (__| |_| | (_) | | | \__ \
;;  |___/_|_|\__,_|\___|_|  \__,_|_| |_|\___|\__|_|\___/|_| |_|___/



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
  (q/text (str "image credit " ic) 10 950)
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

(def parts ["Introduction" "why livecoding" "tools" "posibilities" "showcase"])
(defn slide [title part]
  (q/background 0)
;;   (q/fill)
;;   (q/rect )
)

(defn setbackground []
  (q/background 0 43 54))

(defn setfillmagenta []
  (q/fill 211 54 130))

(defn setfillyellow []
  (q/fill 181 130 0))

(defn setfillorange []
  (q/fill 203 75 22))




(defn draw [state]


;; ====== Introduction =======
  (setbackground)
  (setfillorange)
  (q/text-size 100)
  (q/text "livecoding:" 30 130)
  (q/text-size 40)
  (q/text "closing the human-machine feedbackloop" 60 260 )

;; === lambdasonic promo ===


;; === halic === (D + K)

  (setbackground)
  (fitimage "resources/control_room.jpg")
  (imgcredit  "Ralph McQuarrie concept art for the original Battlestar Galactica series")
  (setfillyellow)
  (q/text-size 70)
  (q/text "HALIC" 30 100)
;; stands for

  (q/text-size 50)
  (q/text "H.euristic" 630 150)
  (q/text "AL.gorithmic" 630 225)
  (q/text "I.nteractive" 630 300)
  (q/text "C.omputing" 630 375)

;; our project
  (q/text-size 30)
  (q/text "a search for music and sound synesthesia" 10 880)

;; === humans ===
;; ;; kaosbeat/bohrbug (D + K)
  (setbackground)
  (q/no-stroke)
  (fitimage "resources/stadslimiet1.jpg")
  (setfillyellow)
  (q/text-size 70)
  (q/text "H = humans" 30 400)
  (setfillorange)
  (q/text-size 30)
  (q/text "@kaosbeat" 400 300 )
  (q/text "@bohrbug" 480 400 )

;;@kaosbeat
  (setbackground)
  (fitimage "resources/teletext.jpg" )
  (q/text "analog tools/digital output" 190 50)
  (setbackground)
  (fitimage "resources/kaos.png")
  (q/text "analog tools/analog output" 190 50)
  (setbackground)
  (fitimage "resources/delvoye.jpg")
  (q/text "digital tools/analog output" 190 50)
  (imgcredit "Kasper Jordaens/ 'Delvoye corten steel tower Guggenheim Venice'")
  (setbackground)
  (fitimage "resources/nglitchlogo.jpg")
  (imgcredit "netglitch at HAR2009 by @0xtosh/@kaosbeat")
  (q/text "digital tools/digital output" 190 50)
  ;; digital tools, digital output is kinda the point of this talk

;;@bohrbug
  (setbackground)
  (fitimage "resources/577523_10200978702328212_558341331_n.jpg")
  (imgcredit "Tom Van Ghent")
  (setbackground)
  (fitimage "resources/atari_mega-ste_2.jpg")
  (q/text "atari mega" 190 50)
  (setbackground)
  (fitimage "resources/atariM.l.gif")
  (q/text "atari M" 190 50)



;;AL stands for algorithms
;;   Deep blue was an algorhitm (K)
  (setbackground)
  (fitimage "resources/Deep_Blue.jpg")
  (imgcredit "James the photographer - http://flickr.com/photos/22453761@N00/592436598/")
  (setfillyellow)
  (q/text-size 70)
  (q/text "AL = Algorithmic" 30 400)
  (setfillorange)
  (q/text-size 50)
  (q/text "supercomputer" 250 100)
  ;;it's even Deep Blue

;; === why even consider humans, they are weak === (K)
;; Kasparov => human + computers = good
  (setbackground)
  (fitimage "resources/kasparov_1-021110_jpg_600x722_q85.jpg")
  (imgcredit "Steve Honda/AFP/Getty Images")
;;   1997 Kasparov lost from Deep blue
  (setfillorange)
  (q/text-size 50)
  (q/text "human VS. computer" 250 100)

;; === HAL is not (good) enough === (K)
;; it's not intuition
;; aesthaetics
  (setbackground)
  (fitimage "resources/hal9000.png")
  (setfillyellow)
  (q/text-size 70)
  (q/text "(H)AL is not (good) enough" 30 400)


;; === HAL is not interactive computation (K)
;; computer as an instrument, not a tool
;; deep blue is not an instrument, but a tool
  (setbackground)
  (fitimage "resources/computer-operator1.jpg")
  (imgcredit "the internet")
  (setfillorange)
  (q/text-size 50)
  (q/text "interactive tool/instrument" 50 50)


;; === the moog is an instrument (compare) (K -> D)
;; interactive music making, analog live coding
  (setbackground)
  (fitimage "resources/moog.jpg")
  (imgcredit "the internet")
  (setfillorange)
  (q/text-size 50)
  (q/text "moog: analog love coding" 50 50)

;; === waarom dan geen moog? computers kunnen nog net iets meer?  (D)
;; geluid = heel goed, maar compositie was nog pover


;; === composing? -> symbolic composer (D)
;; not good enough (geen improvisatie)
  (setbackground)
  (fitimage "resources/SymbolicComposer.jpg")
  (q/text "symbolic composer" 190 50)

;; === composing and improvising with machines (D)
  (setbackground)
  (fitimage "resources/overtone.mp4-3.png")
  (q/text "overtone" 190 50)

;; === tools (K)
;; Pure data / max -> Lighttable / Emacs
;; dichter bij de machine
  (setbackground)
  (fitimage "resources/kaosslicer_pd.jpg")
  (imgcredit "kaosbeat kaosslicer")

  (setbackground)
  (q/text "texteditor: Lighttable / Emacs" 190 50)


;; === because bytecode, because JVM inject, lambda-calculus
;; livecoding can take many forms, like I mentioned pure data or analog live coding like the moog.
;; But what you have just seen/heard is clojure. Clojure is a jar file. A set of classes that gets
;; loaded and can be executed in a JVM. The REPL is one of these and is an interface to hand of bytecode.
;; prabably with some way of pointing to exactly where it has to go, but I'm not fully aware of that.
;; Main thing is it goes inside the running program

;; another thing in Clojure and functional languages like a lot of livecoding languages (Haskell, Tidal, ...)
;; is the concept of  λ-calculus

;; immutable data parsing big data streams (think logs or IoT datastores where history is important)
;; you want to pick up where you left of, run your algorithms on ALL the data. Datomic is a clojure
;; implementation of this concept integrating tools like Lucene and briging the concept  of live
;; calculated data to production environments.


;; === go R


)

(def foo (fn [e] (* 2 e)))

(foo 2)




;; scrubbing example
;; make if fibonaciable

