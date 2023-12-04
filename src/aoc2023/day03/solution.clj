(ns aoc2023.day03.solution
  (:require [clojure.string :as str]))



(defn extract-number-in-line [line]
  (let [index 0
        nums (re-seq #"\d+" line)]
    (set (for [n nums
               :let [from-index (str/index-of line n)]]
           {:value (Integer/parseInt n)
            :from [index from-index]
            :to [index (+ from-index (dec (count n)))]}))))


(str/index-of "467..114.." "114")
(re-seq #"\d+" "467..114..")
(count "abc")