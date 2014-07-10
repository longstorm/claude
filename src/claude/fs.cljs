(ns claude.fs
  (:require [cljs.nodejs :as node]))

(def ^:private Fs (memoize #(node/require "fs")))
(def ^:private Mkdirp (memoize #(node/require "mkdirp")))

(defn slurp [path]
  (.readFileSync (Fs) path))

(defn list-file-names [path]
  (.readdirSync (Fs) path))

(defn list-file-paths [path]
  (map #(str path "/" %) (list-file-names path)))

(defn exists? [path]
  (.existsSync (Fs) path))

(defn directory? [path]
  (.isDirectory (.lstatSync (Fs) path)))

(defn list-dir-names [path]
  (filter #(directory? (str path "/" %)) (list-file-names path)))

(defn list-dir-paths [path]
  (filter directory? (list-file-paths path)))

(defn mkdir
  ([path] (.mkdirSync (Fs) path) path)
  ([path & opts]
     (if (some #{:p} opts)
       (.sync (Mkdirp) path)
       (mkdir path))
     path))

;;; FILES

(defn file? [path]
  (.isFile (.lstatSync (Fs) path)))

(defn write-file-and-forget [s path]
  (.writeFile (Fs) path s))

(defn write-file
  ([s path] (.writeFileSync (Fs) path s))
  ([s path callback] (.writeFile (Fs) path s callback)))

(defn append-file-and-forget [s path]
  (.appendFile (Fs) path s))

(defn append-file
  ([s path] (.appendFileSync (Fs) path s))
  ([s path callback] (.appendFile (Fs) path s callback)))
