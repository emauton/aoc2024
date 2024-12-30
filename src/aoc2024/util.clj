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

; Grid functions
(defn read-grid
  "Read a grid into a 2D vector, splitting each line by split-fn"
  [filename split-fn]
  (mapv #(vec (split-fn %)) (read-lines filename)))

(defn all-coords
  "Return 2D vector of coords in grid"
  [grid]
  (partition 2 2 (flatten (for [y (range (count grid))]
                            (for [x (range (count (first grid)))]
                              [y x])))))

(defn in-bounds?
  "True if coordinates are in the grid"
  [grid [y x]]
  (let [rows (count grid)
        cols (count (first grid))]
    (cond
      (or (neg? y) (neg? x)) false
      (or (>= y rows) (>= x cols)) false
      :else true)))

(defn ray
  "Return a list of coordinates in the grid on a 'ray' starting at
   origin [y x], changing each step by [dy dx], maximum of 'steps'"
  [grid [y x] [dy dx] steps]
  (take-while #(in-bounds? grid %)
              (for [i (range steps)]
                [(+ y ( * i dy)) (+ x (* i dx))])))

(defn all-rays
  "Return a seq of 'ray' coordinate sets - north, northeast, east, etc. -
   within the grid starting from coord; return only rays of length 'steps'" 
  [grid steps coord]
  (let [dyx (for [i (range -1 2)
                  j (range -1 2)
                  :when (not= [0 0] [i j])]
              [i j])]
    (filter #(= (count %) steps) (map #(ray grid coord % steps) dyx))))

(defn grid-values
  "Get all values in grid for a sequence of coordinates"
  [grid coords]
  (map #(get-in grid %) coords))