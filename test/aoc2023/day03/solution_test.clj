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
    (is (= #{{:value 1
              :locations #{[0 0]}}
             {:value 1
              :locations #{[0 3]}}}
           (sut/extract-number-in-line "1.@1")))
    (is (= #{{:value 35
              :locations #{[0 3] [0 4]}}
             {:value 633
              :locations #{[0 6] [0 7] [0 8]}}}
           (sut/extract-number-in-line "...35.633.")))
    (is (= #{{:value 467
              :locations #{[0 0] [0 1] [0 2]}}
             {:value 114
              :locations #{[0 5] [0 6] [0 7]}}}
           (sut/extract-number-in-line "467..114..")))))

(deftest extract-numbers-in-multiline
  (is (= #{{:value 467
            :locations #{[0 0] [0 1] [0 2]}}
           {:value 114
            :locations #{[1 5] [1 6] [1 7]}}}
         (sut/extract-numbers "467.......\n.....114..")))
  (is (= #{{:value 633
            :locations #{[2 0] [2 1] [2 2]}}}
         (sut/extract-numbers "...\n...\n633")))
  (is (= #{{:value 633
            :locations #{[2 3] [2 1] [2 2]}}}
         (sut/extract-numbers "....\n....\n.633")))
  (is (= #{{:value 467
            :locations #{[0 0] [0 1] [0 2]}}
           {:value 114
            :locations #{[1 7] [1 8] [1 9]}}}
         (sut/extract-numbers "467.......\n.......114")))
  (is (= #{{:value 467
            :locations #{[0 0] [0 1] [0 2]}}
           {:value 114
            :locations #{[1 5] [1 6] [1 7]}}}
         (sut/extract-numbers "467....$..\n.....114.."))))


(deftest extract-symbol-neighbours
  (is (= #{[0 2] [0 3] [0 4] [1 2] [1 3] [1 4]}
         (sut/extract-symbol-neighbours "467..114..\n...*......")))
  (is (= #{[0 0] [0 1]}
         (sut/extract-symbol-neighbours "*.........")))
  (is (= #{[0 2] [0 3] [0 4]}
         (sut/extract-symbol-neighbours "...*......")))
  (is (= #{[0 0] [0 1]
           [1 0] [1 1]}
         (sut/extract-symbol-neighbours "#..\n...\n...")))
  (is (= #{[1 0] [1 1]
           [2 0] [2 1]}
         (sut/extract-symbol-neighbours "...\n...\n/..")))
  (is (= #{[1 1] [1 2]
           [2 1] [2 2]}
         (sut/extract-symbol-neighbours "...\n...\n..!")))
  (is (= #{[0 0] [0 1] [0 2]
           [1 0] [1 1] [1 2]
           [2 0] [2 1] [2 2]}
         (sut/extract-symbol-neighbours "...\n.*.\n...")))
  (is (= #{[0 1] [0 2]
           [1 1] [1 2]
           [2 1] [2 2]}
         (sut/extract-symbol-neighbours "...\n..&\n...")))
  (is (= #{[0 0] [0 1] [0 2]
           [1 0] [1 1] [1 2]
           [2 0] [2 1] [2 2]}
         (sut/extract-symbol-neighbours "...\n/.&\n...")))
  (is (= #{[0 0] [0 1]
           [1 0] [1 1]
           [2 0] [2 1]}
         (sut/extract-symbol-neighbours "...\n/..\n...")))
  (is (= #{[0 0] [0 1] [0 2]
           [1 0] [1 1] [1 2]
           [2 0] [2 1] [2 2]}
         (sut/extract-symbol-neighbours "...\n.=&\n...")))
  (is (= #{[0 2] [0 3] [0 4] [1 2] [1 3] [1 4] [2 2] [2 3] [2 4]}
         (sut/extract-symbol-neighbours "467..114..\n...*......\n..35..633."))))

(deftest extract-multiple-symbol-neighbours
  (is (= #{[0 2] [0 3] [0 4] [0 5] [0 6]
           [1 2] [1 3] [1 4] [1 5] [1 6]
           [2 2] [2 3] [2 4] [2 5] [2 6]} (sut/extract-symbol-neighbours "......755.\n...$.*....\n.664.598.."))))

(deftest find-indexes-of-symbols
  (is (= '(0 1 2 3 4 5 6 7 8 9) (sut/non-digit-dot-indexes "!@#$%^&*=/.123....3333"))))

(deftest part-one
  (is (= 0 (sut/part-one "1.\n..")))
  (is (= 1 (sut/part-one "1.\n.-")))
  (is (= 0 (sut/part-one "")))
  (is (= 0 (sut/part-one "\n")))
  (is (= 1 (sut/part-one ".1\n.-")))
  (is (= 932 (sut/part-one "....-932...")))
  (is (= 932 (sut/part-one ".......932\n........$.")))
  (is (= 1 (sut/part-one "..1\n..-\n...")))
  (is (= 0 (sut/part-one "..1\n...\n.$.")))
  (is (= 1 (sut/part-one "..1\n1..\n.$.")))
  (is (= 35 (sut/part-one "..1#@$@#$34")))
  (is (= 1 (sut/part-one "1111.$#@%$#@%#$@1")))
  (is (= 1 (sut/part-one "1.@1")))
  (is (= 123 (sut/part-one "..1\n123\n.$.")))
  (is (= 0 (sut/part-one "467..114..\n.........&")))
  (is (= 4361 (sut/part-one "467..114.
...*.....
..35..633
......#..
617*.....
.....+.58
..592....
......755
...$.*...
.664.598."))))