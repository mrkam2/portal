# portal

A clojure tool to navigate through your data.

[![Clojars Project](https://img.shields.io/clojars/v/djblue/portal?color=380036&style=flat-square)](https://clojars.org/djblue/portal)
[![VS Code Extension](https://vsmarketplacebadge.apphb.com/version-short/djblue.portal.svg?color=007ACC&label=vs-code&logo=vs&style=flat-square)](https://marketplace.visualstudio.com/items?itemName=djblue.portal)
[![Version](https://img.shields.io/jetbrains/plugin/v/18467?style=flat-square&label=intellij)](https://plugins.jetbrains.com/plugin/18467-portal)
[![Get help on Slack](https://img.shields.io/badge/slack-clojurians%20%23portal-4A154B?color=63B132&style=flat-square)](https://clojurians.slack.com/channels/portal)

[![screenshot](https://user-images.githubusercontent.com/1986211/129153169-4018d586-d747-48f9-8193-d267ea5e288a.png)](https://djblue.github.io/portal/)

The portal UI can be used to inspect values of various shapes and sizes. The UX
will probably evolve over time and user feedback is welcome!

For an in-depth explanation of the UI, you can jump to the [UI](https://cljdoc.org/d/djblue/portal/0.30.0/doc/ui-concepts) docs.

## Demo

To get an overview of the Portal UI and workflow, checkout the following recording of a [live demo](https://www.youtube.com/watch?v=Tj-iyDo3bq0) I gave for [London Clojurians](https://www.youtube.com/channel/UC-pYfofTyvVDMwM4ttfFGqw).

<a href="https://www.youtube.com/watch?v=Tj-iyDo3bq0">
<img src="https://img.youtube.com/vi/Tj-iyDo3bq0/hqdefault.jpg" alt="London Clojurians Demo" />
</a>

## Usage

To start a repl with portal, run the **clojure >= 1.10.0** cli with:

```bash
clj -Sdeps '{:deps {djblue/portal {:mvn/version "0.30.0"}}}'
```

or for a **web** **clojurescript >= 1.10.773** repl, do:

```bash
clj -Sdeps '{:deps {djblue/portal {:mvn/version "0.30.0"}
                    org.clojure/clojurescript {:mvn/version "1.10.844"}}}' \
    -m cljs.main
```

or for a **node** **clojurescript >= 1.10.773** repl, do:

```bash
clj -Sdeps '{:deps {djblue/portal {:mvn/version "0.30.0"}
                    org.clojure/clojurescript {:mvn/version "1.10.844"}}}' \
    -m cljs.main -re node
```

or for a **babashka >=0.2.4** repl, do:

```bash
bb -cp `clj -Spath -Sdeps '{:deps {djblue/portal {:mvn/version "0.30.0"}}}'`
```

or for examples on how to integrate portal into an existing project, look
through the [examples](./examples) directory.

**NOTE:** Portal can also be used without a runtime via the [standalone
version](./doc/guides/standalone.md).

### API

Try the [portal api](./src/portal/api.cljc) with the following commands:

```clojure
;; for node and jvm
(require '[portal.api :as p])

;; for web
;; NOTE: you might need to enable popups for the portal ui to work in the
;; browser.
(require '[portal.web :as p])


(def p (p/open)) ; Open a new inspector

;; or with an extension installed, do:
(def p (p/open {:launcher :vs-code}))  ; jvm / node only
(def p (p/open {:launcher :intellij})) ; jvm / node only

(add-tap #'p/submit) ; Add portal as a tap> target

(tap> :hello) ; Start tapping out values

(p/clear) ; Clear all values

(tap> :world) ; Tap out more values

(prn @p) ; bring selected value back into repl

(remove-tap #'p/submit) ; Remove portal from tap> targetset

(p/close) ; Close the inspector when done
```

**NOTE:** portal will keep objects from being garbage collected until they are
cleared from the UI.

### Options

Options for `portal.api/open`:

| Option          | Description                                 | If not specified    | Spec                  |
|-----------------|---------------------------------------------|---------------------|-----------------------|
| `:window-title` | Custom window title for UI                  | "portal"            | string?               |
| `:theme`        | Default theme for UI                        | :portal.colors/nord |                       |
| `:value`        | Root value of UI                            | (atom (list))       |                       |
| `:app`          | Launch UI in Chrome app window              | true                | boolean?              |
| `:launcher`     | Launch UI using this editor                 |                     | #{:vs-code :intellij} |
| `:editor`       | Enable editor commands, but use separate UI |                     | #{:vs-code :intellij} |
| `:port`         | Http server port for UI                     | 0                   | int?                  |
| `:host`         | Http server host for UI                     | "localhost"         | string?               |

For more documentation, take a look through the [docs](https://cljdoc.org/d/djblue/portal/0.30.0/doc/ui-concepts).
