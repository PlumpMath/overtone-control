(ns dodhalic.sfxcore
  )


;;    _______     ___   _ _______ _    _  _____
;;   / ____\ \   / / \ | |__   __| |  | |/ ____|
;;  | (___  \ \_/ /|  \| |  | |  | |__| | (___
;;   \___ \  \   / | . ` |  | |  |  __  |\___ \
;;   ____) |  | |  | |\  |  | |  | |  | |____) |
;;  |_____/   |_|  |_| \_|  |_|  |_|  |_|_____/



;; define a synth
(defsynth vvv
  []
  (let [a (+ 300 (* 50 (sin-osc:kr (/ 1 3))))
        b (+ 300 (* 100 (sin-osc:kr (/ 1 5))))
        _ (tap:kr :a 60 (a2k a))
        _ (tap:kr :b 60 (a2k b))]
    (out 0 (pan2 (+ (sin-osc a)
                    (sin-osc b))))))

;; run an instance of the synth

(vvv)
;; kill it if you have to
(kill vvv)
