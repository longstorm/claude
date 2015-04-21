(defproject longstorm/claude "0.1.10"
  :description "Clojure meets node: the essentials wrapped in parens."
  :url "https://github.com/longstorm/claude"
  :author "Vic Goldfeld"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :scm {:name "git"
        :url "https://github.com/longstorm/claude.git"}
  :plugins [[lein-cljsbuild "1.0.5"]]
  :source-paths ["src"]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-3165"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]]
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:target :nodejs
                                   :output-to "target/claude.js"
                                   :optimizations :simple}}]}
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.0"]
                                  [org.clojure/tools.nrepl "0.2.10"]]
                   :repl-options {:nrepl-middleware
                                  [cemerick.piggieback/wrap-cljs-repl]}}})
