(ns claude.path
  (:require [cljs.nodejs :as node]
            [claude.etc :refer [windows?]]))

(defn extension [path]
  (let [i (.lastIndexOf path ".")]
    (when-not (= -1 i)
      (subs path (inc i)))))

(defn sans-extension
  ([path]
     (let [i (.lastIndexOf path ".")]
       (if (= -1 i)
         path
         (subs path 0 i))))
  ([ext path]
     (let [i (.lastIndexOf path ".")]
       (if (and (not= -1 i) (= ext (subs path (inc i))))
         (subs path 0 i)
         path))))

(defn basename
  ([path] (.basename (node/require "path") path))
  ([path ext] (.basename (node/require "path") path ext)))

(defn parent-path [path]
  (.dirname (node/require "path") path))

(def dirname parent-path)

(defn parent-name [path]
  (basename (parent-path path)))

(if (windows?)
  (defn absolute? [path] (re-find #"^\w:\\" path))
  (defn absolute? [path] (= \/ (first path))))

(defn ^:private walk-up-origin [origin pred path overstep]
  (print (count path) (.lastIndexOf path \/))
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
