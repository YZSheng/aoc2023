(ns aoc2023.utils
  (:require [clojure.string :as str]))

(defn read-lines-of-numbers [input]
  (->> input
       slurp
       str/split-lines
       (map #(re-seq #"-?\d+" %))
       (map #(map parse-long %))))