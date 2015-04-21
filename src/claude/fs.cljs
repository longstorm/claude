(ns claude.fs
  (:refer-clojure :exclude [slurp exists?])
  (:require [cljs.nodejs :as node]))

(defn slurp
  ([path] (.readFileSync (node/require "fs") path))
  ([path cb]
   (.readFile (node/require "fs")
              path #(if %1
                      (throw (js/Error. %1))
                      (cb %2)))))

(defn list-file-names
  ([path] (.readdirSync (node/require "fs") path))
  ([path cb]
   (.readdir (node/require "fs") path #(if %1
                                         (throw (js/Error. %1))
                                         (cb %2)))))

(defn list-file-paths
  ([path] (map #(str path "/" %) (list-file-names path)))
  ([path cb]
   (list-file-names
    path (fn [err res]
           (if err
             (throw (js/Error. err))
             (cb (map #(str path "/" %) res)))))))

(defn exists? [path]
  (.existsSync (node/require "fs") path))

(defn directory? [path]
  (.isDirectory (.statSync (node/require "fs") path)))

(defn list-dir-names
  ([path]
   (filter directory? (map #(str path "/" %) (list-file-names path))))
  ([path cb]
     (list-file-names
      path (fn [err res]
             (if err
               (throw (js/Error. err))
               (cb (filter directory? (map #(str path "/" %) res))))))))

(defn list-dir-paths
  ([path] (filter directory? (list-file-paths path)))
  ([path cb]
   (list-file-paths
    path (fn [err res]
           (if err
             (throw (js/Error. err))
             (cb (filter directory? res)))))))

(defn mkdir
  ([path] (.mkdirSync (node/require "fs") path) path)
  ([path & opts]
     (if (some #{:p} opts)
       (.sync (node/require "mkdirp") path)
       (mkdir path))
     path))

;;; FILES

(defn file? [path]
  (.isFile (.statSync (node/require "fs") path)))

(defn write-file-and-forget [s path]
  (.writeFile (node/require "fs") path s))

(defn write-file
  ([s path] (.writeFileSync (node/require "fs") path s))
  ([s path callback] (.writeFile (node/require "fs") path s callback)))

(defn append-file-and-forget [s path]
  (.appendFile (node/require "fs") path s))

(defn append-file
  ([s path] (.appendFileSync (node/require "fs") path s))
  ([s path callback] (.appendFile (node/require "fs") path s callback)))
