(ns leiningen.new.gyag
  (:require [leiningen.new.templates :refer [renderer name-to-path ->files]]
            [leiningen.core.main :as main]
            [clojure.string :as str]))

(def render (renderer "gyag"))

(defn gyag
  [name]
  (let [data {:name          name
              :sanitized     (name-to-path name)
              :capitalized   (str/capitalize name)
              :system-record (str/capitalize name)}]
    (main/info "Generating fresh 'lein new' gyag project.")
    (->files data
             ["src/{{sanitized}}/system.clj" (render "src/system.clj" data)]
             ["src/{{sanitized}}/app.clj" (render "src/app.clj" data)]
             ["src/{{sanitized}}/repl.clj" (render "src/repl.clj" data)]
             ["src/{{sanitized}}/web_server.clj" (render "src/web_server.clj" data)]

             ["src/{{sanitized}}/routes/home.clj" (render "src/routes/home.clj" data)]
             ["src/{{sanitized}}/routes/api.clj" (render "src/routes/api.clj" data)]

             ["src-cljs/{{sanitized}}/browser.cljs" (render "src-cljs/browser.cljs" data)]

             ["src/{{sanitized}}/views/layout.clj" (render "src/views/layout.clj" data)]
             ["src/{{sanitized}}/views/home.clj" (render "src/views/home.clj" data)]

             ["resources/public/css/bootstrap.min.css" (render "resources/bootstrap.min.css")]
             ["resources/public/js/bootstrap.min.js" (render "resources/bootstrap.min.js")]
             ["resources/public/js/jquery.min.js" (render "resources/jquery.min.js")]

             ["externs/jquery-1.9.js" (render "externs/jquery-1.9.js")]

             ["dev/user.clj" (render "dev/user.clj" data)]
             ["dev/dev_cljs_repl.clj" (render "dev/dev_cljs_repl.clj")]
             ["project.clj" (render "project.clj" data)])))
