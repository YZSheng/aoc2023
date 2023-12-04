(ns aoc2023.day03.solution
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn extract-number-in-line
  ([line] (extract-number-in-line line 0))
  ([line index]
   (let [index index
         nums (re-seq #"\d+" line)]
     (set (for [n nums
                :let [from-index (str/index-of line n)]]
            {:value (Integer/parseInt n)
             :from [index from-index]
             :to [index (+ from-index (dec (count n)))]})))))

(defn extract-numbers [lines]
  (apply set/union (for [index (range (count (str/split-lines lines)))
                         :let [line (nth (str/split-lines lines) index)]]
                     (extract-number-in-line line index))))

(str/index-of "467..114.." "114")
(re-seq #"\d+" "467..114..")


(count "abc")
(range 2)