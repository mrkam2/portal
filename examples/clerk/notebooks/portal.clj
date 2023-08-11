(ns portal
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [nextjournal.clerk :as clerk]
            [examples.data :refer [data] :as d]
            [portal.clerk :as p]
            [portal.api :as portal]))

(def dev (portal/url (portal/open {#_#_:mode :dev :launcher false :theme :portal.colors/nord-light})))

(add-tap #'portal/submit)

(def url (let [[host query] (str/split dev #"\?")]
           (str host "/main.js?" query)))

(def inline-viewer
  {:name :portal/inline
   :nextjournal.clerk/width :full
   :transform-fn
   (fn [value]
     (pr-str {:url url
              :id (str (random-uuid))
              :text (binding [*print-meta* true]
                      (pr-str (:nextjournal/value value)))}))
   :render-fn '(fn [value #_{:keys [id value] :as arg}]
                 (js/console.log (pr-str nextjournal.ui.components/theme))
                 (let [{:keys [id text url]} (clojure.edn/read-string value)
                       portal (nextjournal.clerk.render.hooks/use-d3-require url)
                       ref  (nextjournal.clerk.render.hooks/use-ref)
                       [api set-api!] (nextjournal.clerk.render.hooks/use-state nil)]
                   (nextjournal.clerk.render.hooks/use-effect
                    (fn []
                      (when-let [el (and portal (.-current ref))]
                        (let [api (js/portal.extensions.vs_code_notebook.activate)]
                          (.renderOutputItem api
                                             (clj->js {:mime "x-application/edn"
                                                       :text (fn [] text)})
                                             el)
                          (fn []
                            (try
                              (.disposeOutputItem api id)
                              (catch :default _))))
                        #_(set-api! (js/portal.extensions.vs_code_notebook.activate))))
                    [portal (.-current ref)])
                   [:div {:ref ref :id id}]))})


(defn inspect [value]
  (clerk/with-viewer inline-viewer {:nextjournal.clerk/width :wide} value))

#_(clerk/with-viewer inline-viewer data)

(defn viewer
  "Set default portal viewer for a given value."
  [v default]
  (with-meta v {:portal.viewer/default default}))

(inspect (::d/hacker-news d/data))

(inspect d/basic-data)

(inspect d/platform-data)

;; (:portal.colors/nord c/themes)

(inspect d/diff-data)

(inspect d/prepl-data)

(inspect d/log-data)

(inspect (viewer d/log-data :portal.viewer/table))

(inspect d/hiccup)

(inspect (viewer d/hiccup :portal.viewer/tree))

(inspect
 (viewer
  [:portal.viewer/markdown (::d/markdown d/string-data)]
  :portal.viewer/hiccup))

(inspect d/exception-data)

(inspect d/test-report)

(inspect d/line-chart)
