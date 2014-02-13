(ns claude.child-process
  (:require [cljs.nodejs :as node]))

(def ^:private Child_process (memoize #(node/require "child_process")))

(def ^:private get-exec
  (memoize (fn [] (.-exec (Child_process)))))

(defn exec
  ([cmd] ((get-exec) cmd))
  ([cmd cb] (get-exec) cmd (clj->js {}) cb))

(def ^:private get-exec-file
  (memoize (fn [] (.-execFile (Child_process)))))

(defn exec-file
  ([cmd args cb] ((get-exec-file) cmd (clj->js args) cb))
  ([cmd args opts cb] ((get-exec-file) cmd (clj->js args) (clj->js opts) cb)))

(def ^:private get-spawn
  (memoize (fn [] (.-spawn (Child_process)))))

(defn spawn [bin & args]
  ((get-spawn) bin (clj->js args)))
