(ns {{name}}.app
  (:use [compojure.core :only [defroutes]])
  (:use [taoensso.timbre :only [trace debug info warn error fatal spy]])
  (:require [{{name}}.routes.home :as home]
            [{{name}}.routes.api :as api]
            [noir.util.middleware :as middleware]
            [compojure.route :as route]
            [com.stuartsierra.component :as component]))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(defrecord App [ring-handler config-options]
  component/Lifecycle

  (start [this]
    (info "Starting {{capitalized}} App")
    (let [route-fns    [api/routes
                        home/routes]
          app-routes   (-> (map (fn [route-fn] (route-fn {:config-options config-options}))
                                route-fns)
                           (concat [app-routes])
                           (vec))
          ring-handler (middleware/app-handler app-routes)]
      (assoc this :ring-handler ring-handler)))

  (stop [this]
    (info "Stopping {{capitalized}} App")
    (merge this {:app-handler nil :war-handler nil})))
