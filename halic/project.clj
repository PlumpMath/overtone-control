(defproject halic "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [overtone "0.9.1"]
                 [shadertone "0.2.3"]
                 [quil "2.0.0-SNAPSHOT"]
                 [quil/processing-core "2.1.2"]
                 [quil/processing-pdf "2.1.2"]
                 [quil/processing-dxf "2.1.2"]
;;                  [overtone/osc-clj "0.8.1"]
;;                  [polynome "0.2.2"] ;;for monome
                 ]
  :main ^:skip-aot halic.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
