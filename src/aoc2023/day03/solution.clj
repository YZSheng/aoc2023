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

(defn find-gears-by-line [line row max-height]
  (set (for [i (range (count line))
             :let [ch (nth line i)
                   max-w (dec (count line))]
             :when (= \* ch)]
         (let [all-neighbours  (set [[(max 0 (dec row)) (max 0 (dec i))]
                                     [(max 0 (dec row)) i]
                                     [(max 0 (dec row)) (min max-w (inc i))]
                                     [row (max 0 (dec i))]
                                     [row i]
                                     [row (min max-w (inc i))]
                                     [(min (inc row) max-height) (max 0 (dec i))]
                                     [(min (inc row) max-height) i]
                                     [(min (inc row) max-height) (min max-w (inc i))]])
               location [row i]
               neighbours (set/difference all-neighbours #{location})]
           {:location location
            :neighbours neighbours}))))

(defn find-gears [lines]
  (let [split-lines (str/split-lines lines)]
    (apply set/union (for [row (range (count split-lines))
                           :let [line (nth split-lines row)]]
                       (find-gears-by-line line row (dec (count split-lines)))))))

(defn is-adjacent [gear number]
  (not-empty (set/intersection (:locations number) (:neighbours gear))))

(defn find-gears-with-numbers [lines]
  (let [gears (find-gears lines)
        numbers (extract-numbers lines)]
    (set (map (fn [gear]
                (let [adjacent-numbers (set (filter #(is-adjacent gear %) numbers))]
                  (assoc gear :numbers adjacent-numbers))) gears))))

(defn part-two [lines]
  (let [gears-with-numbers (find-gears-with-numbers lines)
        gears-with-two-numbers (set (filter (fn [gears-with-number]
                                              (= 2 (count (:numbers gears-with-number)))) gears-with-numbers))]
    (->> gears-with-two-numbers
         (map (fn [gears-with-two-number]
                (apply * (map :value (:numbers gears-with-two-number)))))
         (apply +))))

(part-two (slurp "resources/day03/sample.txt"))
(part-two (slurp "resources/day03/input.txt"))