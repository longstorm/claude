(ns claude.https
  (:refer-clojure :exclude [get])
  (:require-macros [redlobster.macros :refer [promise]])
  (:require [cljs.nodejs :as node]))

(def ^:private Https (memoize #(node/require "https")))

(defn get [url]
  (promise (.get (Https) url (fn [res]
                               (.log js/console (.-statusCode res))
                               (realise res)))))
