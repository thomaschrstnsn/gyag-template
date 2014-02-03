(ns user
  "nRepl development setup alá http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded"
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :refer (pprint)]
            [clojure.repl :refer :all]
            [clojure.test :as test]
            [clojure.tools.namespace.repl :refer (refresh refresh-all set-refresh-dirs)]
            [ring.middleware.stacktrace :as stacktrace]
            [{{name}}.system :as system]
            [dev-cljs-repl :as cljs-repl]
            [taoensso.timbre :as timbre]
            [taoensso.timbre.appenders (socket :as socket-appender)]
            [com.stuartsierra.component :as component]))

(def system nil)

(defn init
  "Constructs the current development system."
  []
  (alter-var-root #'system (constantly (system/system system/dev-system-components
                                        :handler-wrapper stacktrace/wrap-stacktrace
                                        :cljs-optimized? false
                                        :repl-port 12345
                                        :cljs-repl-script-fn cljs-repl/script-tag-fn))))

(defn socket-logger []
  (timbre/set-config! [:appenders :socket] socket-appender/socket-appender)
  (timbre/set-config! [:shared-appender-config :socket]
                      {:listen-addr :all :port 9000}))

(defn start
  "Starts the current development system."
  []
  (socket-logger)
  (alter-var-root #'system component/start))

(defn stop
  "Shuts down and destroys the current development system."
  []
  (alter-var-root #'system
                  (fn [s] (when s (component/stop s))))
  :ok)

(defn go
  "Initializes the current development system and starts it running."
  []
  (init)
  (start)
  :ok)

(defn reset []
  (stop)
  (set-refresh-dirs "src/" "dev/")
  (refresh :after 'user/go))
