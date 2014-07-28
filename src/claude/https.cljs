(ns claude.https
  (:refer-clojure :exclude [get])
  (:require-macros [redlobster.macros :refer [promise]])
  (:require [cljs.nodejs :as node]
            [cljs.core.async :as a]))

(def ^:private Https (memoize #(node/require "https")))

(defn get
  ([url] (promise (.get (Https) url (fn [res] (realise res)))))
  ([url callback] (.get (Https) url callback)))
