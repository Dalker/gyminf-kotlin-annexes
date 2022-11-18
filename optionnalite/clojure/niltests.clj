(ns niltests
  (:require [clojure.string :as str])
  (:import java.util.HashMap))

(def tests ["minuscule", "   indent", "", nil])

(defn get-reply [r]
  (some-> r
     str/trim
     (#(when (> (count %) 0) %))
     str/upper-case
     ))

(defn get-value [k]
  (let [m (doto (new HashMap) (.put 1 "A"))
        v (.get m k)]
    (if (some? v) (str "Found " v) "Not found")))

(doseq [test tests]
  (println (get-reply test)))

(doseq [key [1 2]]
  (println (get-value key)))
