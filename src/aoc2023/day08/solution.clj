(ns aoc2023.day08.solution
  (:require [clojure.string :as str]))

(defn parse-to-map [network]
  (->> network
       (str/split-lines)
       (map #(re-seq #"\w+" %))
       (map (fn [[node l r]]
              [node [l r]]))
       (into {})))

(defn parse-input [input]
  (-> input
      slurp
      (str/split #"\n\n")))

(defn part-1 [input]
  (let [[commands map-input] (parse-input input)
        map-input (parse-to-map map-input)]
    (loop [counter 0
           node "AAA"]
      (let [command (nth (cycle commands) counter)
            [l r] (map-input node)
            next-node (if (= \L command)
                        l
                        r)]
        (if (= next-node "ZZZ")
          (inc counter)
          (recur (inc counter) next-node))))))

(part-1 "resources/day08/sample_one.txt")
(part-1 "resources/day08/sample_two.txt")
(part-1 "resources/day08/input.txt")

(comment
  (nth (cycle "ab") 4))