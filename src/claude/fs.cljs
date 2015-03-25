(ns claude.fs
  (:require [cljs.nodejs :as node]))

(def ^:private Fs (memoize #(node/require "fs")))
(def ^:private Mkdirp (memoize #(node/require "mkdirp")))

(defn slurp
  ([path] (.readFileSync (Fs) path))
  ([path cb]
     (.readFile (Fs) path #(if %1
                             (throw (js/Error. %1))
                             (cb %2)))))

(defn list-file-names
  ([path] (.readdirSync (Fs) path))
  ([path cb]
     (.readdir (Fs) path #(if %1
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
  (.existsSync (Fs) path))

(defn directory? [path]
  (.isDirectory (.statSync (Fs) path)))

(defn list-dir-names
  ([path] (filter directory? (map #(str path "/" %) (list-file-names path))))
  ([path cb]
     (list-file-names
      path (fn [err res]
             (if err
               (throw (js/Error. err))
               (cb (filter directory? (map #(str path "/" %) res))))))))

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
  (.isFile (.statSync (Fs) path)))

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
