(ns aoc2023.day10.solution
  (:require [clojure.string :as str]))

(defn parse-input-into-chars [input]
  (->> input
       slurp
       (str/split-lines)
       (mapv #(str/split % #""))))

(defn parse-map [parsed]
  (into {} (for [row (range (count parsed))
                 col (range (count (first parsed)))]
             [[row col]
              (get-in parsed [row col])])))

(defn find-starting-point [m]
  (some #(when (= "S" (second %)) %) m))

(defn valid-neighbour? [m [coord-row coord-col] [neighbour-row neighbour-col]]
  (let [coord-v (m [coord-row coord-col])
        neighbour-v (m [neighbour-row neighbour-col])]
    (cond
      (= coord-row neighbour-row)
      (case coord-v
        "F" (and (#{"-" "7" "J"} neighbour-v) (= (inc coord-col) neighbour-col))
        "L" (and (#{"-" "7" "J"} neighbour-v) (= (inc coord-col) neighbour-col))
        "7" (and (#{"-" "F" "L"} neighbour-v) (= (dec coord-col) neighbour-col))
        "J" (and (#{"-" "F" "L"} neighbour-v) (= (dec coord-col) neighbour-col))
        "-" (cond
              (= (inc coord-col) neighbour-col)
              (#{"-" "J" "7"} neighbour-v)

              (= (dec coord-col) neighbour-col)
              (#{"-" "F" "L"} neighbour-v)

              :else false)
        "S" (cond
              (= (inc coord-col) neighbour-col)
              (#{"-" "J" "7"} neighbour-v)

              (= (dec coord-col) neighbour-col)
              (#{"-" "F" "L"} neighbour-v)

              :else false)
        false)

      (= coord-col neighbour-col)
      (case coord-v
        "F" (and (#{"|" "L" "J"} neighbour-v) (= (inc coord-row) neighbour-row))
        "7" (and (#{"|" "L" "J"} neighbour-v) (= (inc coord-row) neighbour-row))
        "L" (and (#{"|" "F" "7"} neighbour-v) (= (dec coord-row) neighbour-row))
        "J" (and (#{"|" "F" "7"} neighbour-v) (= (dec coord-row) neighbour-row))
        "|" (cond
              (= (inc coord-row) neighbour-row)
              (#{"|" "J" "L"} neighbour-v)

              (= (dec coord-row) neighbour-row)
              (#{"|" "F" "7"} neighbour-v)

              :else false)
        "S" (cond
              (= (inc coord-row) neighbour-row)
              (#{"|" "J" "L"} neighbour-v)

              (= (dec coord-row) neighbour-row)
              (#{"|" "F" "7"} neighbour-v)

              :else false)
        false)

      :else false)))

(defn find-neighbours [chars-map [row col]]
  (let [max-row (dec (count chars-map))
        max-col (dec (count (first chars-map)))
        left [row (max 0 (dec col))]
        down [(min max-row (inc row)) col]
        right [row (min max-col (inc col))]
        up [(max 0 (dec row)) col]
        m (parse-map chars-map)]
    (->> [left down right up]
         (filter #(not= % [row col]))
         (filter (fn [[neighbour-row neighbour-col]]
                   (valid-neighbour? m [row col] [neighbour-row neighbour-col])))
         (map (fn [coord]
                [coord (m coord)]))
         (into {}))))

(def chars-map (parse-input-into-chars "resources/day10/sample_one.txt"))

(defn part-one [input]
  (let [chars-map (parse-input-into-chars input)
        parsed-map (parse-map chars-map)
        [starting-coord _] (find-starting-point parsed-map)]
    (loop [coords (map first (find-neighbours chars-map starting-coord))
           visited #{starting-coord}]
      (if (empty? coords)
        (/ (count visited) 2)
        (let [[coord & rest-coords] coords
              rest-coords-v (vec rest-coords)
              all-neighbours (find-neighbours chars-map coord)
              neighbours (filter (fn [[neighbour-coord _]]
                                   (not (visited neighbour-coord))) all-neighbours)
              updated-visited (conj visited coord)]
          (if (= starting-coord coord)
            (do
              (println "find starting coord in neighbour")
              updated-visited)
            (let [updated-coords (vec (concat rest-coords-v (map first neighbours)))]
              (recur updated-coords updated-visited))))))))

(part-one "resources/day10/sample_one.txt")
(part-one "resources/day10/sample_two.txt")
(part-one "resources/day10/input.txt")

(comment

  chars-map
  (find-neighbours chars-map [3 3])
  (find-neighbours chars-map [3 1])
  (find-neighbours chars-map [1 3])

  (valid-neighbour? (parse-map chars-map) [3 3] [2 3]))