(defproject aoc2024 "0.1.0-SNAPSHOT"
  :description "Advent of Code 2024"
  :url "https://github.com/emauton/aoc2024"
  :dependencies [[org.clojure/clojure "1.12.0"]]
  :main ^:skip-aot aoc2024.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
