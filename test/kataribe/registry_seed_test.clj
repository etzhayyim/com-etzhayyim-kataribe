(ns kataribe.registry-seed-test
  (:require [clojure.edn :as edn]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]))

(def seed (edn/read-string (slurp "registry/channels.seed.edn")))
(def channels (get seed "channels"))
(def channel-kinds
  #{"official-gazette" "legal-publication" "press-freedom-org"
    "open-access-archive" "translation-resource"})
(def timestamp-pattern #"^\d{4}-\d{2}-\d{2}T\d{2}:\d{2}:\d{2}(?:\.\d+)?Z$")

(deftest registry-shape-and-unique-ids
  (is (map? seed))
  (is (seq channels))
  (let [ids (map #(get % "channelId") channels)]
    (is (every? seq ids))
    (is (= (count ids) (count (set ids))))))

(deftest channels-ship-fail-closed
  (is (every? #(= "unverified-seed" (get % "verificationStatus")) channels)))

(deftest provenance-is-complete
  (doseq [channel channels]
    (testing (get channel "channelId")
      (doseq [field ["accessUrl" "provenance"]]
        (is (re-find #"^https?://" (get channel field ""))))
      (is (re-matches timestamp-pattern (get channel "lastVerified" ""))))))

(deftest registry-has-worldwide-coverage
  (is (every? #(seq (get % "jurisdiction")) channels))
  (is (<= 12 (count (set (map #(get % "jurisdiction") channels))))))

(deftest channel-kinds-remain-closed
  (is (every? #(contains? channel-kinds (get % "channelKind")) channels)))

(deftest notes-preserve-boundary
  (doseq [channel channels]
    (let [notes (get channel "notes" "")]
      (is (str/includes? notes "NOT commercial journalism") (get channel "channelId"))
      (is (str/includes? notes "OBSERVATIONAL") (get channel "channelId")))))

(deftest freshness-window-is-positive-integer
  (let [days (get seed "freshnessWindowDays")]
    (is (integer? days))
    (is (pos? days))))
