(ns claude.http
  (:refer-clojure :exclude [get])
  (:require-macros [redlobster.macros :refer [promise]])
  (:require [cljs.nodejs :as node]
            [cljs.core.async :as a]))

(def ^:private Http (memoize #(node/require "http")))

(defn get
  ([url] (promise (.get (Http) url (fn [res] (realise res)))))
  ([url callback] (.get (Http) url callback)))
