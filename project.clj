(defproject longstorm/claude "0.1.0"
  :description "Clojure meets node: the essentials wrapped in parens."
  :url "https://github.com/longstorm/claude"
  :author "Vic Goldfeld"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/longstorm/claude.git"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2202"]
                 [org.bodil/redlobster "0.2.1"]]
  :profiles {:dev {:dependencies [[org.bodil/cljs-noderepl "0.1.10"]]}})
