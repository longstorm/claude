(ns claude.process
  (:require [cljs.nodejs :as node]))

(defn exit
  ([] (exit 0))
  ([code] (.exit node/process code)))

(defn cwd []
  (.cwd node/process))

(defn writeln [& lines]
  (.write (.-stdout node/process)
          (apply str (concat (interpose "\n" lines) ["\n"]))))
