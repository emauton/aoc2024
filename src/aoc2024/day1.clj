(ns aoc2024.day1
  (:require [aoc2024.util :as util]
            [clojure.string :as string]))

(defn ziplike
  "Given a sequence of lists, return a sequence of lists made of all first
   elements, then all second elements, and so on"
  [lists]
  (apply map list lists)) ; cf. https://clojuredocs.org/clojure.core/map

(defn distance
  "Given a list of integer pairs, re-pair them in sorted order and sum
   distances between each pair"
  [pairs]
  (->> (ziplike pairs) ; unzip the pairs
       (map sort)      ; sort resulting sub-lists
       (ziplike)       ; zip 'em up again
       (map (fn [[a b]] (abs (- a b))))
       (reduce +)))

(defn similarity
  "Given a list of integer pairs, sum the first elements, weighted by number
   of times each appears as a second element (with weight = 0 if missing)"
  [pairs]
  (let [[a b] (ziplike pairs)
        freqs (frequencies b)
        weighted (map #(* % (get freqs % 0)) a)]
    (reduce + weighted)))

(defn parse-pairs
  "Parse the input into a seq of pairs"
  [input]
  (map (fn [line](map #(Integer/parseInt %) (string/split (string/trim line) #"\s+"))) input))

(defn main
  "Day 1 of Advent of Code 2024: Historian Hysteria
       lein run day1 <input>
   where <input> is a filename in resources/"
  [[filename]]
  (let [pairs (parse-pairs (util/read-lines filename))
        dist (distance pairs)
        sim (similarity pairs)]
   (println "total distance:" dist)
   (println "similarity score:" sim)))