(ns portal.clerk
  (:require [clojure.java.io :as io]
            [nextjournal.clerk :as clerk]
            [portal.api :as p]))

(def app-viewer
  {:name :portal/app
   :transform-fn
   (fn [value]
     (p/url
      (p/open {:launcher false
               :value    (:nextjournal/value value)
               :theme    :portal.colors/nord-light})))
   :render-fn '#(nextjournal.clerk.viewer/html
                 [:iframe
                  {:src %
                   :style {:width "100%"
                           :height "50vh"
                           :border-left "1px solid #d8dee9"
                           :border-right "1px solid #d8dee9"
                           :border-bottom "1px solid #d8dee9"
                           :border-radius 2}}])})

(defn open
  "Open portal with the value of `x` in current notebook."
  ([x] (open {} x))
  ([viewer-opts x]
   (clerk/with-viewer app-viewer viewer-opts x)))

(defn inspect
  ([x] (inspect x {}))
  ([viewer-opts x]
   (clerk/with-viewer app-viewer viewer-opts x)))