(ns aoc2023.day01.solution
  (:require [clojure.string :as string]))

(defn part-one [input]
  (->> input
       slurp
       (string/split-lines)
       (map #(re-seq #"\d" %))
       (map (juxt first last))
       (map #(string/join %))
       (map #(Integer/parseInt %))
       (reduce +)))

(part-one "resources/day01/sample.txt")
(part-one "resources/day01/input.txt")

