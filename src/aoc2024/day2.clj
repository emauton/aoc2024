(ns aoc2024.day2
  (:require [aoc2024.util :as util]))

(defn safe?
  "Test whether a report is safe"
  [levels]
  (let [pairs (partition 2 1 levels)
        distances (util/distances pairs)]
    (and (or (apply <= levels) (apply >= levels))       ; levels are monotonic
         (every? #(and (>= % 1) (<= % 3)) distances)))) ; any two differ by 1..3

(defn dampened
  "Generate sub-sequences with the 1st level removed, then 2nd, and so on"
  [levels]
  (let [len (count levels)]
    (map #(concat (take % levels)
                  (take-last (- len (inc %)) levels))
         (range len))))

(defn safe-dampened?
  "Test whether a report is safe, but allow for one bad level"
  [levels]
  (some safe? (dampened levels)))

(defn main
  "Day 2 of Advent of Code 2024: Red-Nosed Reports
       lein run day1 <input>
   where <input> is a filename in resources/"
  [[filename]]
  (let [reports (util/read-number-grid filename)]
    (println "safe reports:" (count (filter safe? reports)))
    (println "dampened:" (count (filter safe-dampened? reports)))))