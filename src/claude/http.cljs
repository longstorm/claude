(ns claude.http
  (:refer-clojure :exclude [get])
  (:require-macros [redlobster.macros :refer [promise]])
  (:require [cljs.nodejs :as node]))

(def ^:private Http (memoize #(node/require "http")))

(defn get [url]
  (promise (.get (Http) url (fn [res]
                              (.log js/console (.-statusCode res))
                              (realise res)))))
