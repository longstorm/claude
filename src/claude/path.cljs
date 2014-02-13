(ns claude.path
  (:require [cljs.nodejs :as node]
            [claude.etc :refer [windows?]]))

(def ^:private Path (memoize #(node/require "path")))

(defn basename
  ([path] (.basename (Path) path))
  ([path ext] (.basename (Path) path ext)))

(defn parent-path [path]
  (.dirname (Path) path))

(defn parent-name [path]
  (basename (parent-path path)))

(if (windows?)
  (defn absolute? [path] (re-find #"^\w:\\" path))
  (defn absolute? [path] (= \/ (first path))))

(defn ^:private walk-up-origin [origin pred path overstep]
  (print (count path) (last-index-of \/ path))
  (if-not (pred path)
    (walk-up-origin origin pred (parent-path path) overstep)
    (if (zero? overstep)
      (subs origin (inc (count path)))
      (walk-up-origin
       origin (constantly true) (parent-path path) (dec overstep)))))

(defn walk-up
  "Walks up the directory structure checking pred for each path
  segment, passing it the absolute path segment. Stops as soon as pred
  turns true, returning a vector of the relative path segments walked,
  including the one for which pred was true, in reverse order.
  Example: (clojure.string/join
             \"/\" (walk-up \"/home/monet/wip/lillies\" wip-folder?))
           ;=> \"wip/lillies\"

  You can use the predicate for side-effects to do something to each
  folder along the way."
  ([pred path] (walk-up pred path 0))
  ([pred path overstep] (walk-up-origin path pred path overstep))) 
