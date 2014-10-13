(ns ext-sxf-gfx.core
  (:use [overtone.core]  ;;if this is on, will use external server!!!
       ))

(connect-external-server 57110)



;; gfx helpers

(defn show-frame-rate [options]
  (let [; retrieve existing draw function or use empty one if not present
        draw (:draw options (fn []))
        ; updated-draw will replace draw
        updated-draw (fn []
                       (draw) ; call user-provided draw function
                       (q/fill 0)
                       (q/text-num (q/current-frame-rate) 10 10))]
    ; set updated-draw as :draw function
    (assoc options :draw updated-draw)))

