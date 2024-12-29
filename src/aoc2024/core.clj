(ns aoc2024.core
  (:require aoc2024.day1 
            clojure.string)
  (:gen-class))

(defn -main
  "Dispatch to the different day routines for Advent of Code 2024"
  [day & args]
  (let [day-ns (symbol (clojure.string/join "." ["aoc2024" day]))
        day-main (ns-resolve day-ns 'main)]
    (day-main args)))
