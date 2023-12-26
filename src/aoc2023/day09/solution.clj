(ns aoc2023.day09.solution
  (:require [aoc2023.utils :as u]))

(defn calculate-delta [nums]
  (->> nums
       (partition 2 1)
       (mapv (fn [[a b]] (- b a)))))

(defn calculate-delta-till-zero [input]
  (loop [inputs [input]]
    (let [next-input (calculate-delta (last inputs))
          updated (conj inputs next-input)]
      (if (every? zero? next-input)
        updated
        (recur updated)))))

(defn predict-next [inputs]
  (->> inputs
       (calculate-delta-till-zero)
       (map last)
       (apply +)))

(defn solve [input f]
  (->> input
       u/read-lines-of-numbers
       (map f)
       (map predict-next)
       (apply +)))

(defn solve-part-one [input]
  (solve input identity))

(solve-part-one "resources/day09/sample.txt")
(solve-part-one "resources/day09/input.txt")

(defn solve-part-two [input]
  (solve input reverse))

(solve-part-two "resources/day09/sample.txt")
(solve-part-two "resources/day09/input.txt")
