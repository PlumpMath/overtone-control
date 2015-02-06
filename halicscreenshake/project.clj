(defproject halicscreenshake "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [quil "2.2.5"]
                 [overtone "0.9.1"]
                 [clj-glob "1.0.0"]
                 [org.clojure/core.match "0.3.0-alpha4"]
                 [polynome "0.2.2"]]

  :main ^:skip-aot halicscreenshake.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
