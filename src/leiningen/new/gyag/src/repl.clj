(ns {{name}}.repl
  (:require [clojure.tools.nrepl.server :as nrepl]
            [com.stuartsierra.component :as component]
            [taoensso.timbre :refer [info]]))

(defrecord HostedRepl [port server]
  component/Lifecycle

  (start [component]
    (info "Starting Hosted REPL")
    (let [repl-server (nrepl/start-server :port port)]
      (info "REPL running on port" port)
      (assoc component :server repl-server)))

  (stop [component]
    (info "Stopping Hosted REPL")
    (when server
      (nrepl/stop-server server))
    (assoc component :server nil)))
