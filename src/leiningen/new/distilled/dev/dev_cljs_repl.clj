(ns dev-cljs-repl
  (:require [cemerick.austin.repls :as austin]))

(defn script-tag-fn []
  (when @austin/browser-repl-env
    [:script (austin/browser-connected-repl-js)]))

(defn set-repl! [repl]
  (def repl-env (reset! austin/browser-repl-env repl)))

(defn setup
  "Setup the app to use a Austin browser hosted cljs-repl"
  []
  (set-repl! (cemerick.austin/repl-env)))

(defn connect
  "Connect to the browser hosted cljs-repl"
  []
  (austin/cljs-repl repl-env))
