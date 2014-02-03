(ns leiningen.new.distilled
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as str]))

(def render (renderer "distilled"))

(defn distilled
  [name]
  (let [data {:name          name
              :sanitized     (name-to-path name)
              :capitalized   (str/capitalize name)
              :system-record (str/capitalize name)}]
    (main/info "Generating fresh 'lein new' distilled project.")
    (->files data
             ["src/{{sanitized}}/system.clj" (render "src/system.clj" data)]
             ["src/{{sanitized}}/app.clj" (render "src/app.clj" data)]
             ["src/{{sanitized}}/repl.clj" (render "src/repl.clj" data)]
             ["src/{{sanitized}}/web_server.clj" (render "src/web_server.clj" data)]

             ["src/{{sanitized}}/routes/home.clj" (render "src/routes/home.clj" data)]
             ["src/{{sanitized}}/routes/api.clj" (render "src/routes/api.clj" data)]

             ["src/{{sanitized}}/views/layout.clj" (render "src/views/layout.clj" data)]
             ["src/{{sanitized}}/views/home.clj" (render "src/views/home.clj" data)]

             ["dev/user.clj" (render "dev/user.clj" data)]
             ["dev/dev_cljs_repl.clj" (render "dev/dev_cljs_repl.clj")]
             ["project.clj" (render "project.clj" data)])))
