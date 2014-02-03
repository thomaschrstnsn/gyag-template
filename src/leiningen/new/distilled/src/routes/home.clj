(ns {{name}}.routes.home
  (:require [compojure.core :as compojure :refer [GET]]
            [{{name}}.views.home :as home]
            [{{name}}.views.layout :as layout]))

(defn front-page [{:keys [config-options]}]
  (layout/common config-options :content (home/page)))

(defn routes [deps]
  (compojure/routes
   (GET "/" [] (front-page deps))))
