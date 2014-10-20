(ns dodhalic.gfxcore
  (:require [overtone.osc :as osc])
  (:require [quil.core :as q]
            [quil.middleware :as m]
            [newtonian.particle-system :as newt]
            [newtonian.utils :as utils]
;;             [quil.helpers.calc :refer [mul-add]]
            [quil.helpers.seqs :refer [seq->stream range-incl cycle-between steps]]
            [quil.helpers.drawing :refer [line-join-points]]
            )
  (:import newtonian.utils.Vector2D)
            )


