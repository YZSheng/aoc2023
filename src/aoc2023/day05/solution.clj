(ns aoc2023.day05.solution
  (:require [clojure.string :as str]))

(defn construct-range [dest-start source-start count]
  [[source-start (dec (+ source-start count))] [dest-start (dec (+ dest-start count))]])

(defn get-destination [range-map source]
  (let [match-range (filter (fn [r]
                              (let [[source-start source-end] (first r)]
                                (and (<= source-start source) (<= source source-end))))
                            range-map)]
    (if (seq match-range)
      (let [r (first match-range)
            [s d] r
            [s-start _] s
            [d-start _] d
            delta (- source s-start)]
        (+ delta d-start))
      source)))

(defn parse-line [line]
  (->> line
       (str/split-lines)
       (drop 1)
       (map #(str/split % #"\s+"))
       (mapv #(mapv parse-long %))))

(defn parse-line-to-map [line]
  (->> line
       parse-line
       (reduce (fn [acc cur]
                 (conj acc (apply construct-range cur)))
               [])))

(defn part-one [input]
  (let [parsed (->> input
                    (slurp)
                    (#(str/split % #"\n\n")))
        [seeds
         seed-to-soil
         soil-to-fertilizer
         fertilizer-to-water
         water-to-light
         light-to-temperature
         temperature-to-humidity
         humidity-to-location] parsed
        seed-numbers (map parse-long (re-seq #"\d+" seeds))
        seed-to-soil-map (parse-line-to-map seed-to-soil)
        soil-to-fertilizer-map (parse-line-to-map soil-to-fertilizer)
        fertilizer-to-water-map (parse-line-to-map fertilizer-to-water)
        water-to-light-map (parse-line-to-map water-to-light)
        light-to-temperature-map (parse-line-to-map light-to-temperature)
        temperature-to-humidity-map (parse-line-to-map temperature-to-humidity)
        humidity-to-location-map (parse-line-to-map humidity-to-location)]
    (->> seed-numbers
         (map #(get-destination seed-to-soil-map %))
         (map #(get-destination soil-to-fertilizer-map %))
         (map #(get-destination fertilizer-to-water-map %))
         (map #(get-destination water-to-light-map %))
         (map #(get-destination light-to-temperature-map %))
         (map #(get-destination temperature-to-humidity-map %))
         (map #(get-destination humidity-to-location-map %))
         (apply min))))


(->> "seeds: 79 14 55 13"
     (re-seq #"\d+")
     (map parse-long)
     (partition 2)
     (map (fn [[start count]]
            (range start (+ start count))))
     (apply concat))

(defn seed-from-range [input]
  (->> input
       (re-seq #"\d+")
       (map parse-long)
       (partition 2)
       (map (fn [[start count]]
              (range start (+ start count))))
       (apply concat)))

(defn part-two [input]
  (let [parsed (->> input
                    (slurp)
                    (#(str/split % #"\n\n")))
        [seeds
         seed-to-soil
         soil-to-fertilizer
         fertilizer-to-water
         water-to-light
         light-to-temperature
         temperature-to-humidity
         humidity-to-location] parsed
        seed-numbers (seed-from-range seeds)
        seed-to-soil-map (parse-line-to-map seed-to-soil)
        soil-to-fertilizer-map (parse-line-to-map soil-to-fertilizer)
        fertilizer-to-water-map (parse-line-to-map fertilizer-to-water)
        water-to-light-map (parse-line-to-map water-to-light)
        light-to-temperature-map (parse-line-to-map light-to-temperature)
        temperature-to-humidity-map (parse-line-to-map temperature-to-humidity)
        humidity-to-location-map (parse-line-to-map humidity-to-location)]
    (->> seed-numbers
         (map #(get-destination seed-to-soil-map %))
         (map #(get-destination soil-to-fertilizer-map %))
         (map #(get-destination fertilizer-to-water-map %))
         (map #(get-destination water-to-light-map %))
         (map #(get-destination light-to-temperature-map %))
         (map #(get-destination temperature-to-humidity-map %))
         (map #(get-destination humidity-to-location-map %))
         (apply min))))

(part-one "resources/day05/sample.txt")
(part-one "resources/day05/input.txt")

(part-two "resources/day05/sample.txt")
(part-two "resources/day05/input.txt")

(comment
  (get-destination [[[98 99] [50 51]]] 99)
  (get-destination [[[98 99] [50 51]] [[50 97] [52 99]]] 50)

  (->> "50 98 2\n52 50 48"
       (str/split-lines)
       (map #(str/split % #"\s+"))
       (mapv #(mapv parse-long %))
       (reduce (fn [acc cur]
                 (conj acc (apply construct-range cur)))
               []))

  (construct-range 50 98 2)

  (parse-line "seed-to-soil map:\n50 98 2\n52 50 48")
  (parse-line-to-map "seed-to-soil map:\n50 98 2\n52 50 48")
  (->> "resources/day05/sample.txt"
       (slurp)
       (#(str/split % #"\n\n")))

  (->> "seed-to-soil map:\n50 98 2\n52 50 48"
       (str/split-lines)
       (drop 1)
       (map #(str/split % #"\s+"))
       (mapv #(mapv parse-long %))))