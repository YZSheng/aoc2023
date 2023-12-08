(ns aoc2023.day04.solution
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn parse-numbers [input]
  (set (map #(Integer/parseInt %) (re-seq #"\d+" input))))

(parse-numbers " 11 22 33 ")

(defn exp [x n]
  (reduce * (repeat n x)))

(exp 2 3)

(defn process-line [line]
  (let [[_ winning-str ticket-str] (str/split line #"[|:]")
        winning-numbers (parse-numbers winning-str)
        ticket-numbers (parse-numbers ticket-str)
        winning (set/intersection ticket-numbers winning-numbers)]
    (if (pos? (count winning))
      (exp 2 (dec (count winning)))
      0)))

(process-line "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53")
(process-line "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36")

(defn part-one [lines]
  (->> lines
       (str/split-lines)
       (map process-line)
       (apply +)))

(part-one (slurp "resources/day04/sample.txt"))
(part-one (slurp "resources/day04/input.txt"))