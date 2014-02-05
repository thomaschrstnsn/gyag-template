# gyag

1. Tibetan for yak *(or so I am told)*.
2. An opinionated Leiningen template for developing web applications using [Clojure](http://clojure.org/)
and [ClojureScript](https://github.com/clojure/clojurescript).

## Features
* Reliable and fast code reloads for Clojure code during development
* Based on the Clojure *de facto standard* [Ring](https://github.com/ring-clojure/ring)
* [Hiccup](https://github.com/weavejester/hiccup) server-side rendered HTML pages
* Easy REST Api using [Liberator](http://clojure-liberator.github.io/liberator/)
* Fast, debuggable, ClojureScript builds with source maps
* In-browser ClojureScript REPL via [Austin](https://github.com/cemerick/austin)

## Usage

### Prerequisite

Install [Leiningen](http://leiningen.org).

### Initializing the template
Initialize your new Leiningen project: `lein new gyag $your-project`

### ClojureScript compilation (browser-side)

Contains two ClojureScript build profiles:
* `dev` : Source maps and no optimizations, multiple js-files
* `production` : Advanced optimization to a single js-file

Build command examples:
* Continuous builds of `dev` profile (occupies one terminal) and will rebuild the
js-files from all changes in `src-cljs`: `lein cljsbuild auto dev`
* Building `production` profile once: `lein cljsbuild once production`

### Clojure compilation / dev cycle (server-side)

The project uses the excellent `component` and `tools.nrepl` libraries to enable fast and reliable
code reloads. See [component on github](https://www.github.com/stuartsierra/component/) and
[REPL-driven development workflow](http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded).

#### Example:

1. Start a nRepl for your project:
 * `M-x cider-jack-in` or `M-x nrepl-jack-in` in Emacs
 * `lein repl` from the command-line
2. In the REPL start the server-side by issuing `(go)` which should produce something similar to this:
```
user> (go)
2014-feb-05 21:52:00 +0100 Aero.local INFO [myone.app] - Starting Myone App
2014-feb-05 21:52:00 +0100 Aero.local INFO [myone.web-server] - Starting Web Server
2014-feb-05 21:52:00 +0100 Aero.local INFO [myone.web-server] - Web Server running on port: 8080
2014-feb-05 21:52:00 +0100 Aero.local INFO [myone.repl] - Starting Hosted REPL
2014-feb-05 21:52:00 +0100 Aero.local INFO [myone.repl] - REPL running on port 12345
:ok
```
3. The server-side is split into multiple components, which is what is logged out in the above:
 * The app: Ring handler and route compositions
 * The web-server: a Jetty-instance serving the app on HTTP
 * The in-app hosted REPL: live code changes everywhere, *whats not to like?* (**caveat emptor**)
4. You can now open http://localhost:8080 to connect to your running application.
5. During development, to reload code changes, perform the following in the REPL which launched
to app: `(user/reset)`. This performs the following steps:
 1. Stops the currently running components, releasing acquired resources (bound ports etc)
 2. Uses `tools.nrepl` to reload code under `src/`
 3. Restarts the components
 4. **NOTE:** In the case of problems reloading code, eg. compilation errors, first resolve these
and then perform `(clojure.tools.namespace.repl/refresh)` followed by `(user/reset)`

## License

Copyright © 2014 Thomas Christensen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
