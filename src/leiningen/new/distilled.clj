(ns leiningen.new.distilled
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "distilled"))

(defn distilled
  [name]
  (let [data {:name name
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' distilled project.")
    (->files data
             ["src/{{sanitized}}/foo.clj" (render "foo.clj" data)]
             ["project.clj" (render "project.clj" data)])))
