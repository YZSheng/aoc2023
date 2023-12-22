(ns aoc2023.day07.solution
  (:require [clojure.string :as str]))

(def hand-strength {[5] 70000000
                    [1 4] 60000000
                    [2 3] 50000000
                    [1 1 3] 40000000
                    [1 2 2] 30000000
                    [1 1 1 2] 20000000
                    [1 1 1 1 1] 10000000})

(def card-to-number {"2" 2
                     "3" 3
                     "4" 4
                     "5" 5
                     "6" 6
                     "7" 7
                     "8" 8
                     "9" 9
                     "T" 10
                     "J" 11
                     "Q" 12
                     "K" 13
                     "A" 14})


(defn card-to-decimal [card-digits]
  (reduce + (map (fn [digit index] (* digit (Math/pow 15 index)))
                 (reverse card-digits)
                 (range))))

(defn calculate-cards-value [hand]
  (->> hand
       (#(str/split % #""))
       (map card-to-number)
       card-to-decimal))

(defn calculate-hand-score [hand]
  (-> hand
      (str/split #"")
      frequencies
      vals
      sort
      vec
      hand-strength
      (+ (calculate-cards-value hand))))


(defn parse-input [input]
  (->> input
       slurp
       str/split-lines
       (map #(str/split % #"\s"))))


(defn calculate-total-winnings [winnings]
  (reduce + (map (fn [winning index]
                   (* winning (inc index))) winnings (range))))


(defn part-1 [input]
  (->> input
       parse-input
       (sort-by (comp calculate-hand-score first))
       (map last)
       (map parse-long)
       (calculate-total-winnings)))

(part-1 "resources/day07/sample.txt")
(part-1 "resources/day07/input.txt")


(comment

  (calculate-cards-value "32T3K")
  (calculate-cards-value "77888")
  (calculate-cards-value "77788")

  (calculate-hand-score "32T3K")
  (calculate-hand-score "T55J5")
  (calculate-hand-score "KK677")
  (calculate-hand-score "KTJJT")
  (calculate-hand-score "AAAAA")

  (sort-by calculate-hand-score ["32T3K" "T55J5" "KK677" "KTJJT" "QQQJA"]))