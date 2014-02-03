(defproject {{name}} "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-core "1.2.0" :exclusions [org.clojure/tools.reader]]
                 [lib-noir "0.6.8"]
                 [compojure "1.1.5" :exclusions [org.clojure/tools.macro
                                                 org.clojure/core.incubator
                                                 ring/ring-core]]
                 [liberator "0.10.0"]
                 [ring-server "0.3.0" :exclusions [org.clojure/core.incubator]]
                 [com.taoensso/timbre "2.7.1"]
                 [log4j "1.2.17" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [hiccup "1.0.4"]
                 [com.stuartsierra/component "0.2.0"]
                 [org.clojure/tools.nrepl "0.2.3"]]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src-cljs"],
                :compiler {:pretty-print false
                           :output-to "resources/public/js/dev/{{sanitized}}.js"
                           :output-dir "resources/public/js/dev"
                           :optimizations :none
                           :source-map true}}
               {:id "production"
                :source-paths ["src-cljs"],
                :compiler {:pretty-print false
                           :output-to "resources/public/js/{{sanitized}}.js"
                           :optimizations :advanced
                           :externs ["externs/jquery-1.9.js"]}}]}

  :ring {:handler {{name}}.servlet-lifecycle/handler,
         :init    {{name}}.servlet-lifecycle/init,
         :destroy {{name}}.servlet-lifecycle/destroy
         :open-browser? false
         :auto-reload? false}

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}

  :profiles {:production {:ring {:stacktraces? false}
                          :dependencies [[org.clojure/tools.reader "0.7.10"]]}

             :dev {:ring {:stacktraces? true}
                   :source-paths ["dev"]
                   :dependencies [[org.clojure/clojurescript "0.0-2030"]
                                  [org.clojure/core.async "0.1.256.0-1bf8cf-alpha"]
                                  [org.clojure/tools.namespace "0.2.4"]
                                  [org.clojure/java.classpath "0.2.1"]
                                  [ring-mock "0.1.5"]
                                  [ring/ring-devel "1.2.0"]
                                  [midje "1.5.1"]
                                  [server-socket "1.0.0"]
                                  [prismatic/dommy "0.1.1"]]
                   :plugins      [[com.cemerick/austin "0.1.3"]]
                   :repositories {"sonatype-oss-public"
                                  "https://oss.sonatype.org/content/groups/public/"}}}

  :plugins [[lein-ring "0.8.3"]
            [lein-cljsbuild "1.0.0-alpha2"]
            [lein-release "1.0.4"]]

  :url "https://example.com/{{name}}/TODO"
  :description "Your place to shine..."

  :min-lein-version "2.0.0")
