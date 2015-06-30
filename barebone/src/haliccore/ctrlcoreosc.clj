(ns halic.core
  (:require [monome-osc.core :as mono])
  )


(def m (mono/connect { :id "m128-121" :port 12001 :prefix "/manager"}))

(m)
m
(mono/disconnect "m128-121")


(mono/Device :monome)


(mono/get-device :monome)


(mono/get-info)


(mono/Monome)
