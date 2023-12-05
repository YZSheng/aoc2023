(ns aoc2023.day03.solution-test
  (:require [aoc2023.day03.solution :as sut]
            [clojure.test :refer :all]))

(deftest extract-number-in-line-test
  (testing "extracting a number in a line"
    (is (= #{{:value 35
              :locations #{[0 2] [0 3]}}
             {:value 633
              :locations #{[0 6] [0 7] [0 8]}}}
           (sut/extract-number-in-line "..35..633.")))
    (is (= #{{:value 467
              :locations #{[0 0] [0 1] [0 2]}}
             {:value 114
              :locations #{[0 5] [0 6] [0 7]}}} (sut/extract-number-in-line "467..114..")))))


(deftest extract-numbers-in-multiline
  (is (= #{{:value 467
            :locations #{[0 0] [0 1] [0 2]}}
           {:value 114
            :locations #{[1 5] [1 6] [1 7]}}}
         (sut/extract-numbers "467.......\n.....114.."))))


(deftest extract-symbol-neighbours
  (is (= #{[0 2] [0 3] [0 4] [1 2] [1 3] [1 4]}
         (sut/extract-symbol-neighbours "467..114..\n...*......")))
  (is (= #{[0 0] [0 1]}
         (sut/extract-symbol-neighbours "*.........")))
  (is (= #{[0 2] [0 3] [0 4]}
         (sut/extract-symbol-neighbours "...*......")))
  (is (= #{[0 2] [0 3] [0 4] [1 2] [1 3] [1 4] [2 2] [2 3] [2 4]}
         (sut/extract-symbol-neighbours "467..114..\n...*......\n..35..633."))))

(deftest extract-multiple-symbol-neighbours
  (is (= #{[0 2] [0 3] [0 4] [0 5] [0 6]
           [1 2] [1 3] [1 4] [1 5] [1 6]
           [2 2] [2 3] [2 4] [2 5] [2 6]} (sut/extract-symbol-neighbours "......755.\n...$.*....\n.664.598.."))))