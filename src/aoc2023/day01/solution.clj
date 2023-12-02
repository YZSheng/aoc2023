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

(defn clean-input [s]
  (reduce (fn [acc [old new]]
            (string/replace acc old new))
          s
          {"one" "one1one",
           "two" "two2two",
           "three" "three3three",
           "four" "four4four",
           "five" "five5five",
           "six" "six6six",
           "seven" "seven7seven",
           "eight" "eight8eight",
           "nine" "nine9nine"}))

(clean-input "eightwothree")

(defn part-two [input]
  (->> input
       slurp
       (string/split-lines)
       (map clean-input)
       (map #(re-seq #"\d" %))
       (map (juxt first last))
       (map #(string/join %))
       (map #(Integer/parseInt %))
       (reduce +)))

(part-two "resources/day01/sample_part2.txt")
(part-two "resources/day01/input.txt")