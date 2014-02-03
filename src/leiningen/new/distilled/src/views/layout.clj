(ns {{name}}.views.layout
  (:require [hiccup.element :refer [link-to]]
            [hiccup.page :refer [html5 include-css include-js]]))

(defn header []
  [:div.navbar.navbar-static-top.navbar-default
   [:a.navbar-brand {:href "/"} "{{capitalized}}"]
   [:ul.nav.navbar-nav.pull-left
    [:li#nav-api (link-to "/api" "API")]]])

(defn footer [{:keys [cljs-repl-script-fn cljs-optimized?]}]
  (list
   (let [cljs-path (if cljs-optimized? "/js/" "/js/dev/")
         scripts   (filter identity ["/js/jquery.min.js"
                                     "/js/bootstrap.min.js"
                                     (when-not cljs-optimized? (str cljs-path "goog/base.js"))
                                     (str cljs-path "{{sanitized}}.js")])]
     (apply include-js scripts))
   (when-not cljs-optimized?
     [:script {:type "text/javascript"} "goog.require(\"{{name}}.browser\");"])
   [:script {:type "text/javascript"} "{{name}}.browser.register_document_ready();"]
   (cljs-repl-script-fn)))

(defn base [config-options & content]
  (html5
   [:head
    [:title "{{capitalized}}"]
    [:link {:rel "icon" :type "image/x-icon" :href "/favicon.ico"}]
    (include-css "/css/bootstrap.min.css")]
   [:body content]))

(defn common [config-options & {:keys [content]}]
  (base config-options (header) [:div.container content] (footer config-options)))
