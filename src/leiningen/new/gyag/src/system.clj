(ns {{name}}.system
  (:require [com.stuartsierra.component :as component]
            [{{name}}.app :as app]
            [{{name}}.repl :as repl]
            [{{name}}.web-server :as web]))

(def dev-system-components  [:repl :app :web-server])
(def prod-system-components [:repl :app])

(defrecord {{system-record}} [components config-options repl web-server app]
  component/Lifecycle
  (start [this]
    (component/start-system this components))
  (stop [this]
    (component/stop-system this components)))

(def default-config-options
  {:cljs-optimized?     true
   :repl-port           4321
   :web-port            8080
   :handler-wrapper     identity
   :cljs-repl-script-fn (constantly nil)})

(defn system [components & {:as config-overrides}]
  (let [config-options (merge default-config-options config-overrides)]
    (map->{{system-record}}
     {:components     components
      :config-options config-options
      :repl           (repl/map->HostedRepl
                       {:port (:repl-port config-options)})
      :app            (component/using (app/map->App {}) {:config-options :config-options})
      :web-server     (component/using
                       (web/map->WebServer
                        {:port            (:web-port config-options)
                         :handler-wrapper (:handler-wrapper config-options)})
                       {:app :app})})))
