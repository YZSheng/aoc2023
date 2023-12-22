(ns aoc2023.day06.solution
  (:require [clojure.string :as string]))

(defn get-race-results [[t distance]]
  (->> (for [hold-t (range 1 t)
             :let [speed hold-t
                   remaining-t (- t hold-t)
                   travel-distance (* speed remaining-t)]]
         [hold-t travel-distance])
       (filter #(> (second %) distance))
       count))

(defn part-1 [input]
  (->> input
       slurp
       string/split-lines
       (map #(re-seq #"\d+" %))
       (map (fn [nums-str]
              (map parse-long nums-str)))
       (apply map vector)
       (map get-race-results)
       (apply *)))

(part-1 "resources/day06/sample.txt")
(part-1 "resources/day06/input.txt")


(defn part-2 [input]
  (->> input
       slurp
       string/split-lines
       (map #(re-seq #"\d+" %))
       (map (fn [nums-str]
              (apply str nums-str)))
       (map parse-long)
       vec
       (get-race-results)))

(part-2 "resources/day06/sample.txt")
(part-2 "resources/day06/input.txt")
