(ns aoc2023.day03.solution
  (:require [clojure.set :as set]
            [clojure.string :as str]))

(defn regex-match-index-value-pairs
  [pattern text]
  (let [matcher (re-matcher (re-pattern pattern) text)]
    (loop [pairs []]
      (if (.find matcher)
        (recur (conj pairs [(.start matcher) (.group matcher)]))
        pairs))))


(defn extract-number-in-line
  ([line] (extract-number-in-line line 0))
  ([line index]
   (let [index index
         nums-match (regex-match-index-value-pairs #"\d+" line)]
     (set (for [[from-index n] nums-match]
            {:value (Integer/parseInt n)
             :locations (set (for [i (range from-index (+ from-index (count n)))]
                               [index i]))})))))

(defn extract-numbers [lines]
  (apply set/union (for [index (range (count (str/split-lines lines)))
                         :let [line (nth (str/split-lines lines) index)]]
                     (extract-number-in-line line index))))

(defn non-digit-dot-indexes [s]
  (keep-indexed (fn [idx ch]
                  (when (and (not= \. ch)
                             (not (Character/isDigit ch)))
                    idx))
                s))

(defn extract-symbol-neighbours-by-line [line index max-height]
  (let [index index
        max-count (dec (count line))
        max-height max-height
        symbol-indices (non-digit-dot-indexes line)]
    (apply set/union (for [symbol-index symbol-indices]
                       (set [[index (max 0 (dec symbol-index))]
                             [index symbol-index]
                             [index (min max-count (inc symbol-index))]
                             [(max 0 (dec index)) (max 0 (dec symbol-index))]
                             [(max 0 (dec index)) symbol-index]
                             [(max 0 (dec index)) (min max-count (inc symbol-index))]
                             [(min max-height (inc index)) (max 0 (dec symbol-index))]
                             [(min max-height (inc index)) symbol-index]
                             [(min max-height (inc index)) (min max-count (inc symbol-index))]])))))

(defn extract-symbol-neighbours [lines]
  (let [split-lines (str/split-lines lines)
        max-height (dec (count split-lines))]
    (apply set/union (for [index (range (count split-lines))
                           :let [line (nth split-lines index)]]
                       (extract-symbol-neighbours-by-line line index max-height)))))

(defn part-one [lines]
  (let [numbers (extract-numbers lines)
        symbols-neighbours (extract-symbol-neighbours lines)
        part-numbers (filter (fn [num]
                               (not-empty (set/intersection (:locations num) symbols-neighbours))) numbers)]
    (->> part-numbers
         (map :value)
         (apply +))))


(part-one (slurp "resources/day03/sample.txt"))
(part-one (slurp "resources/day03/input.txt"))
