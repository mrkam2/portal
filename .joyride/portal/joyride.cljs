(ns portal.joyride
  (:require ["vscode" :as vscode]
            ["ext://djblue.portal$api" :as p]))

(defn ext-publics
  "Returns a map of the public intern mappings for the vs-code extension."
  [ext]
  (-> (.getExtension vscode/extensions ext)
      (.-exports)
      (js->clj :keywordize-keys true)))

(comment
  (p/set-defaults! {:launcher :vs-code})
  (p/open)
  (p/submit :hi)
  (p/clear)
  (ext-publics "djblue.portal")
  (ext-publics "betterthantomorrow.calva"))