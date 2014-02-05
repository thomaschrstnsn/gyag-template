# gyag

Gyag is Tibetan for yak (or so I am told).
An opinionated Leiningen template for web applications.

## Usage

### First
* Initialize your new Leiningen project: `lein new gyag $your-project`

### ClojureScript compilation (browser-side)

Contains two ClojureScript build profiles:
* `dev` : Source maps and no optimizations, multiple js-files
* `production` : Advanced optimization to a single js-file

Build command examples:
* Continouos builds of `dev` profile (occupies one terminal) and will rebuild the js-files from all changes in `src-cljs`: `lein cljsbuild auto dev`
* Building `production` profile once: `lein cljsbuild once production`

### Clojure compilation / dev cycle (server-side)

The project uses the excellent `component` and `tools.nrepl` libraries to enable fast and reliable
code reloads. See [component on github](https://www.github.com/stuartsierra/component/) and
[REPL-driven development workflow](http://thinkrelevance.com/blog/2013/06/04/clojure-workflow-reloaded).

#### Example:

* Start a nRepl for your project, eg. `M-x nrepl-jack-in` in emacs or `lein repl` from the command-line
* In the start the server-side: `(go)` which should produce something similar to this:
```
2014-feb-05 07:36:40 +0100 Aero.local INFO [myone.app] - Starting Myone App
2014-feb-05 07:36:40 +0100 Aero.local INFO [myone.web-server] - Starting Web Server
2014-02-05 07:36:40.437:INFO:oejs.Server:jetty-7.6.8.v20121106
2014-02-05 07:36:40.486:INFO:oejs.AbstractConnector:Started SelectChannelConnector@0.0.0.0:8080
2014-feb-05 07:36:40 +0100 Aero.local INFO [myone.web-server] - Web Server running on port: 8080
2014-feb-05 07:36:40 +0100 Aero.local INFO [myone.repl] - Starting Hosted REPL
2014-feb-05 07:36:40 +0100 Aero.local INFO [myone.repl] - REPL running on port 12345
:ok
```
* The server-side is split into multiple components, which is what is logged out in the above:
 * The app: Ring handler and route compositions
 * The web-server: a Jetty-instance serving the app on HTTP
 * The in-app hosted REPL: live code changes everywhere, whats not to like? (*caveat emptor*)
* You can now open [localhost](http://localhost:8080) to connect to your running application.
* During development, to reload code changes, perform the following in the REPL which launched
to app: `(user/reset)`. This performs the following steps:
 * Stops the currently running components, releasing acquired resources (ports etc)
 * Uses `tools.nrepl` to reload code under `src/`
 * Restarts the components
 * *NOTE:* In the case of problems reloading code, eg. compilation errors, first resolve these
and then perform `(clojure.tools.namespace.repl/refresh)` followed by `(user/reset)`

## License

Copyright © 2014 Thomas Christensen

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
