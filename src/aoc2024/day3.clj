(ns aoc2024.day3
  (:require [aoc2024.util :as util]))

(defn filter-onoff
  "Filter a sequence by testing for 'on/off' markers in the stream.
   All markers are removed."
  [off-pred on-pred coll]
  (loop [input coll
         acc '()]
    (if (empty? input)
      (filter #(not (or (off-pred %) (on-pred %))) acc) ; remove any left-over no-ops
      (let [filtered (take-while (complement off-pred) input)
            remainder (rest (drop-while (complement on-pred) (nthrest input (count filtered))))]
        (recur remainder (concat acc filtered))))))

(defn multiply
  "Given a list of [mul, arg1, arg2] matches returned by re-seq, return a
   sequence of mul results"
  [instructions]
  (map (fn [[_ a b]] (* (Integer/parseInt a) (Integer/parseInt b))) instructions))

(defn mul-results
  "Return all mul(x,y) results in the input"
  [input]
  (let [pattern (re-pattern #"mul\((\d+),(\d+)\)")]
    (multiply (re-seq pattern input))))

(defn mul-results-enabled
  "Return all mul(x,y) results in the input where enabled"
  [input]
  (let [pattern (re-pattern #"mul\((\d+),(\d+)\)|don't\(\)|do\(\)")
        muls (filter-onoff (fn [[op & _]] (= "don't()" op))
                           (fn [[op & _]] (= "do()" op))
                           (re-seq pattern input))]
    (multiply muls)))

(defn main
  "Day 3 of Advent of Code 2024: Mull It Over
       lein run day3 <input>
   where <input> is a filename in resources/"
  [[filename]]
  (let [input (util/slurp-resource filename)]
    (println "sum of mul(x,y):" (reduce + (mul-results input)))
    (println "sum of enabled mul(x,y):" (reduce + (mul-results-enabled input)))))