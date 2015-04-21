(ns claude.http
  (:refer-clojure :exclude [get])
  (:require [cljs.nodejs :as node]
            [cljs.core.async :as a]))

(defn get
  ([url]
   (let [chan (a/chan)
         request (.get (node/require "http") url
                       (fn [res] (a/put! chan res)))]
     (.on request "error"
          (fn [e]
            (a/put! chan {:error "GET" :msg (.-message e)})
            (a/close! chan)))
     chan))
  ([url callback] (.get (node/require "http") url callback)))
