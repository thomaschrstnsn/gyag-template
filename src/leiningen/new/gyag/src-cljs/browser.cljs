(ns {{name}}.browser
    (:require [clojure.browser.repl :as repl]))

(defn ^:export clojurescript-main []
  (.log js/console "Hello world! From ClojureScript!"))
