(ns aoc2023.day03.solution-test
  (:require [aoc2023.day03.solution :as sut]
            [clojure.test :refer :all]))

(deftest extract-number-in-line-test
  (testing "extracting a number in a line"
    (is (= #{{:value 35
              :from [0 2]
              :to [0 3]}
             {:value 633
              :from [0 6]
              :to [0 8]}} (sut/extract-number-in-line "..35..633.")))
    (is (= #{{:value 467
              :from [0 0]
              :to [0 2]}
             {:value 114
              :from [0 5]
              :to [0 7]}} (sut/extract-number-in-line "467..114..")))))


(deftest extract-numbers-in-multiline
  (is (= #{{:value 467
            :from [0 0]
            :to [0 2]}
           {:value 114
            :from [1 5]
            :to [1 7]}}
         (sut/extract-numbers "467.......\n.....114.."))))