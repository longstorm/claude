(ns claude.child-process
  (:require [cljs.nodejs :as node]
            [cljs.core.async :as a]))

(defn exec [cmd]
  (let [chan (a/chan)]
    ((.-exec (node/require "child_process"))
     cmd (clj->js {}) (fn [err res] (a/put! chan res)))
    chan))

(defn exec-file
  ([cmd args]
   (let [chan (a/chan)]
     ((.-execFile (node/require "child_process"))
      cmd (clj->js args) (fn [err res] (a/put! chan res)))
     chan))
  ([cmd args opts]
   (let [chan (a/chan)]
     ((.-execFile (node/require "child_process"))
      cmd (clj->js args) (clj->js opts)
      (fn [err res] (a/put! chan res)))
     chan)))

(defn spawn
  ([cmd args]
   ((.-spawn (node/require "child_process")) cmd (clj->js args)))
  ([cmd args opts]
   ((.-spawn (node/require "child_process"))
    cmd (clj->js args) (clj->js opts))))
