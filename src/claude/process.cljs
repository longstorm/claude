(ns claude.process
  (:require [cljs.nodejs :as node]
            [clojure.string :as strng]))

(defn output [& lines]
  (.on node/process "exit"
       #(.log js/console (strng/join "\n" lines)))
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
