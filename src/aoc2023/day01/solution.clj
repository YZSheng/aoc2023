(ns aoc2023.day01.solution
  (:require [clojure.string :as string]))

(defn solution
  ([input]
   (solution input identity))
  ([input clean-fn]
   (->> input
        slurp
        (string/split-lines)
        (map clean-fn)
        (map #(re-seq #"\d" %))
        (map (juxt first last))
        (map #(string/join %))
        (map #(Integer/parseInt %))
        (reduce +))))


(solution "resources/day01/sample.txt")
(solution "resources/day01/input.txt")

;; Replaces number words in the input string with number digits  
;; and additional digits, e.g. "one" becomes "one1one". This transforms
;; the input to make calculating the priority easier.
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

(solution "resources/day01/sample_part2.txt" clean-input)
(solution "resources/day01/input.txt" clean-input)