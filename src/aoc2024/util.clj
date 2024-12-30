(ns aoc2024.util
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.pprint]))

(defn slurp-resource
  "Slurp all data from a file in project root's resources/"
  [filename]
  (->  filename io/resource slurp))

(defn read-lines
  "Read line-by-line from a file in project root's resources/"
  [filename]
  (-> filename io/resource io/reader line-seq))

(defn read-numbers [filename]
  "Read one number per line into a seq"
  (map #(Integer/parseInt %) (read-lines filename)))

(defn read-number-grid [filename]
  "Read lists of space-separated numbers per line into a seq of seqs"
  (map (fn [line](map #(Integer/parseInt %)
                      (string/split (string/trim line) #"\s+")))
       (read-lines filename)))

(defn break-commas
  "Break up a line on commas"
  [line]
  (string/split (string/trim line) #","))

(defn in?
  "Test if a is in coll"
  [coll a]
  (some #(= a %) coll))

(defn distances
  "Given a seq of integer pairs, return a seq of distances between them"
  [pairs]
  (map (fn [[a b]] (abs (- a b))) pairs))

(def not-nil? (complement nil?))

(def pprint clojure.pprint/pprint)