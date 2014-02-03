(ns {{name}}.routes.api
  (:require [liberator.core :refer [resource defresource]]
            [compojure.core :as compojure :refer [ANY]]))

(defresource example []
  :available-media-types ["application/json" "text/html" "application/edn" "application/clojure"]
  :handle-ok (fn [_] {:super-duper ["okie-doke" 200 "fine"]}))

(defn routes [{:keys [db]}]
  (compojure/routes (ANY "/api/example" [] (example))))
