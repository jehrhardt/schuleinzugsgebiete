(ns schuleinzugsgebiete.endpoint.schools
  "Endpoint for schools. It serves the details page for each school."
  (:require [schuleinzugsgebiete.boundary.schools :as schools]
            [compojure.core :refer [GET]]
            [ring.util.response :refer [response]]
            [ring.util.codec :as codec]
            [selmer.parser :as templates]
            [integrant.core :as ig]))

(defn- render
  [school]
  (->> school
       (assoc {} :school)
       (templates/render-file "templates/school.html")))

(defn- schools-endpoint
  [{:keys [repo]}]
  (GET "/schulen/:school-name.html" [school-name]
    (->> (codec/url-decode school-name)
         (schools/by-name repo)
         (render)
         (response))))

(defmethod ig/init-key ::endpoint
  [_ options]
  (schools-endpoint options))