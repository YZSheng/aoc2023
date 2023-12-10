(ns aoc2023.day04.solution-test
  (:require [aoc2023.day04.solution :as sut]
            [clojure.test :refer :all]))

(deftest two-cards-no-winning
  (is (= 2 (sut/part-two "Card 1: 41 48 83 86 17 | 93 53\nCard 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"))))

(deftest two-cards-one-winning
  (is (= 3 (sut/part-two "Card 1: 41 48 83 86 17 | 83 53\nCard 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19"))))

(deftest three-cards-two-winning
  (is (= 6 (sut/part-two "Card 1: 41 48 83 86 17 | 83 53\nCard 2: 13 32 20 16 61 | 13 14 15 17\nCard 3: 1 21 53 59 44 | 69 82 63 72 16 21 14  1"))))

(deftest three-cards-two-winning-first-card
  (is (= 5 (sut/part-two "Card 1: 41 48 83 86 17 | 83 53 41\nCard 2: 13 32 20 16 61 | 12 14 15 17\nCard 3: 1 21 53 59 44 | 69 82 63 72 16 21 14  1"))))

(deftest three-cards-two-winning-first-card-and-second-card
  (is (= 7 (sut/part-two "Card 1: 41 48 83 86 17 | 83 53 41\nCard 2: 13 32 20 16 61 | 13 14 15 16\nCard 3: 1 21 53 59 44 | 69 82 63 72 16 21 14  1"))))

(deftest sample-input
  (is (= 30 (sut/part-two "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"))))