(ns claude.process
  (:require [cljs.nodejs :as node]))

(defn exit
  ([] (exit 0))
  ([code] (.exit node/process code)))

(defn cwd []
  (.cwd node/process))
