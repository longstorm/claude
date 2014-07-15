(defproject longstorm/claude "0.1.3"
  :description "Clojure meets node: the essentials wrapped in parens."
  :url "https://github.com/longstorm/claude"
  :author "Vic Goldfeld"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/longstorm/claude.git"}
  :source-paths ["src"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2202"]
                 [org.bodil/redlobster "0.2.1"]]
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:target :nodejs
                                   :output-to "target/claude.js"
                                   :optimizations :simple}}]})
