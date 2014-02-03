(ns {{name}}.web-server
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as jetty]
            [taoensso.timbre :refer [info]]))

(defrecord WebServer [port server app handler-wrapper]
  component/Lifecycle

  (start [this]
    (info "Starting Web Server")
    (let [handler (:ring-handler app)
          wrapped (handler-wrapper handler)
          server  (jetty/run-jetty wrapped {:port port :join? false})]
      (info "Web Server running on port:" port)
      (assoc this :server server)))

  (stop [this]
    (info "Stopping Web Server")
    (when server
      (.stop server))
    (assoc this :server nil)))
