(ns claude.child-process
  (:require [cljs.nodejs :as node]
            [cljs.core.async :as a]))

(def ^:private Child_process (memoize #(node/require "child_process")))

(def ^:private get-exec
  (memoize (fn [] (.-exec (Child_process)))))

(defn exec [cmd]
  (let [chan (a/chan)]
    ((get-exec-file) cmd (clj->js {}) (fn [err res] (a/put! chan res)))
    chan))

(def ^:private get-exec-file
  (memoize (fn [] (.-execFile (Child_process)))))

(defn exec-file
  ([cmd args]
     (let [chan (a/chan)]
       ((get-exec-file) cmd (clj->js args) (fn [err res] (a/put! chan res)))
       chan))
  ([cmd args opts]
     (let [chan (a/chan)]
       ((get-exec-file) cmd (clj->js args) (clj->js opts)
        (fn [err res] a/put! chan res))
       chan)))

(def ^:private get-spawn
  (memoize (fn [] (.-spawn (Child_process)))))

(defn spawn
  ([cmd args] ((get-spawn) cmd (clj->js args)))
  ([cmd args opts] ((get-spawn) cmd (clj->js args) (clj->js opts))))
