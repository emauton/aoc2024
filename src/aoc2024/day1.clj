(ns aoc2024.day1
  (:require [aoc2024.util :as util]))

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
       (util/distances)
       (reduce +)))

(defn similarity
  "Given a list of integer pairs, sum the first elements, weighted by number
   of times each appears as a second element (with weight = 0 if missing)"
  [pairs]
  (let [[a b] (ziplike pairs)
        freqs (frequencies b)
        weighted (map #(* % (get freqs % 0)) a)]
    (reduce + weighted)))

(defn main
  "Day 1 of Advent of Code 2024: Historian Hysteria
       lein run day1 <input>
   where <input> is a filename in resources/"
  [[filename]]
  (let [pairs (util/read-number-grid filename)
        dist (distance pairs)
        sim (similarity pairs)]
   (println "total distance:" dist)
   (println "similarity score:" sim)))