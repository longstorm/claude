(ns claude.etc
  (:require-macros [redlobster.macros :refer [defer-node]])
  (:require [cljs.nodejs :as node]
            [claude.fs :refer [directory? mkdir list-dir-paths]]))

(defn windows? []
  (= "win32" (.-platform node/process)))

(def ^:private home-dir
  (memoize #(let [env-var (if (windows?) "USERPROFILE" "HOME")]
              (aget (.-env node/process) env-var))))

(defn home
  ([subpath] (str (home-dir) "/" subpath))
  ([] (home-dir)))

(defn expand-home-tilde [pattern]
  (if (= \~ (first pattern))
    (str (home) (subs pattern 1))
    pattern))

(defn expand-dir-blob [pattern]
  (let [pat (expand-home-tilde pattern)] 
    (try (cond (= \* (last pat)) (->> pat count
                                      dec dec
                                      (subs pat 0)
                                      list-dir-paths)
               (exists? pat) [pat]
               :else [])
         (catch js/Object e []))))

(defn cache-dir
  "Returns the full path of the cache directory (or a subdirectory if
  app-name is supplied.) Any directory along the path which didn't
  already exist will have been created when the function returns."
  ([] (cache-dir nil))
  ([app-name]
     (let [path (str (home) "/.cache" (when app-name (str "/" app-name)))]
       (try (mkdir path :p)
            (catch js/Object e path)))))
