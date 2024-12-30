(ns aoc2024.day4
  (:require [aoc2024.util :as util]))

(defn count-xmas
  "Count occurances of XMAS in the grid"
  [grid]
  (->> (util/all-coords grid)
       (map #(util/all-rays grid 4 %))
       ; NB using (reduce concat) here builds a deep stack of
       ; (concat a (concat b (concat ...))) calls and overflows
       (apply concat)
       (map #(util/grid-values grid %))
       (filter #(= '(\X \M \A \S) %))
       (count)))

(defn x-rays
  "Return a pair of coordinate seqs of length 3, intersecting at coord"
  [coord]
  ; the inner map just adds vector pairs
  [(map #(map + coord %) '([-1 -1] [0 0] [1 1]))
   (map #(map + coord %) '([-1 1] [0 0] [1 -1]))])

(defn count-x-mas
  "Count occurances of X-MAS in the grid"
  [grid]
  (->> (util/all-coords grid)
       (map #(x-rays %))
       ; remove any x-ray pairs that have elements out of bounds
       (filter (fn [pairs] (every? #(util/in-bounds? grid %)
                                   (partition 2 2 (flatten pairs)))))
       (map #(map (fn [coords] (util/grid-values grid coords)) %))
       (filter (fn [pairs] (every? #(or (= '(\M \A \S) %)
                                        (= '(\S \A \M) %))
                                   pairs)))
       (count)))

(defn main
  "Day 4 of Advent of Code 2024: Ceres Search
       lein run day4 <input>
   where <input> is a filename in resources/"
  [[filename]]
  (let [grid (util/read-grid filename seq)]
    (println "XMAS appearances:" (count-xmas grid))
    (println "X-MAS appearances:" (count-x-mas grid))))