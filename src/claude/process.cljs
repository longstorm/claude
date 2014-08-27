(ns claude.process
  (:require [cljs.nodejs :as node]))

(defn output [& lines]
  (.on node/process "exit" #(.log js/console (clojure.string/join "\n" lines)))
  (.exit node/process 0))

(defn exit
  ([] (exit 0))
  ([code] (.exit node/process code)))

(defn on-exit [cb]
  (.on node/process "exit" cb)
  cb)

(defn cwd []
  (.cwd node/process))

(defn writeln [& lines]
  (.write (.-stdout node/process)
          (apply str (concat (interpose "\n" lines) ["\n"]))))
