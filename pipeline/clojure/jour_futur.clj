(ns jour-futur
  (:import (java.util Calendar Locale)))

(def LOCALE (Locale. "fr" "CH"))

(defn jour-futur [annees]
  (let [YEAR (. Calendar YEAR)]
    (-> (. Calendar getInstance)
        (doto (#(.set % YEAR (+ (.get % YEAR) annees))))
        (.getDisplayName (. Calendar DAY_OF_WEEK) (. Calendar LONG_FORMAT) LOCALE))))

(let [PHRASE "Dans précisément %d ans nous serons un %s"
      DELTA-ANNEES 10]
  (println (format PHRASE DELTA-ANNEES (jour-futur DELTA-ANNEES))))
