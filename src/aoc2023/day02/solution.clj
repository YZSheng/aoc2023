(ns aoc2023.day02.solution
  (:require [clojure.string :as str]))

(defn make-regex [color]
  (re-pattern (str "(\\d+) " color)))

(defn find-color [segment color]
  (or (some->> segment
               (re-find (make-regex color))
               last
               (Integer/parseInt)) 0))

(defn extract-game-index [game-number]
  (Integer/parseInt (re-find #"\d+" game-number)))

(defn parse-line [line]
  (let [[game-info draws] (str/split line #": ")
        game-number (extract-game-index game-info)
        segments (str/split draws #";")]
    {:game game-number
     :results (mapv (fn [segment]
                      [(find-color segment "red")
                       (find-color segment "blue")
                       (find-color segment "green")]) segments)}))

(defn parse-input [input]
  (->> input
       slurp
       (str/split-lines)
       (map parse-line)))

(defn is-result-possible [[r b g]]
  (and
   (>= 12 r)
   (>= 14 b)
   (>= 13 g)))

(defn part-one [input]
  (let [parsed (parse-input input)
        valid (filter (fn [draw]
                        (every? is-result-possible (:results draw))) parsed)]
    (->> valid
         (map :game)
         (reduce +))))

(part-one "resources/day02/sample.txt")
(part-one "resources/day02/input.txt")


(defn find-max [n result]
  (apply max (map #(nth % n) result)))

(defn find-min-r-b-g [result]
  ((juxt (partial find-max 0)
         (partial find-max 1)
         (partial find-max 2))
   result))

(defn part-two [input]
  (let [parsed (parse-input input)
        results (map :results parsed)
        min-r-b-gs (map find-min-r-b-g results)
        products (map #(apply * %) min-r-b-gs)]
    (apply + products)))

(part-two "resources/day02/sample.txt")
(part-two "resources/day02/input.txt")

(comment
  (extract-game-index "Game 1")
  (re-find #"(\d+) red" "11 red, 2 green, 6 blue")
  (find-color "11 red, 2 green, 6 blue" "red")
  (find-color "2 green, 6 blue" "red")
  (parse-input "resources/day02/sample.txt")
  (parse-line "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green")
  (apply max (map #(nth % 0) [[4 3 0] [1 6 2] [0 0 2]]))
  (find-max 0 [[4 3 0] [1 6 2] [0 0 2]])
  (find-min-r-b-g [[4 3 0] [1 6 2] [0 0 2]]))