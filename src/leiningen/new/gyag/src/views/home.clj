(ns {{name}}.views.home
  (:require [hiccup.page :refer [html5]]))

(defn page []
  (html5
   [:div.jumbotron
    [:h1 "{{capitalized}}"]
    [:h2 "Does amazing things to You and those around you"]
    [:p.lead "This is where the magic happens."]]))
