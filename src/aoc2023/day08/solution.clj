(ns aoc2023.day08.solution
  (:require [clojure.string :as str]
            [clojure.math.numeric-tower :as math]))

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

(defn get-command [commands n]
  (let [l (count commands)
        m (mod n l)]
    (nth commands m)))

(defn get-ghost-step-count [node commands map-input]
  (loop [counter 0
         node node]
    (let [command (get-command commands counter)
          [l r] (map-input node)
          next-node (if (= \L command)
                      l
                      r)]
      (if (str/ends-with? next-node "Z")
        (inc counter)
        (recur (inc counter) next-node)))))

(defn lcm [numbers]
  (reduce (fn [acc n]
            (let [gcd (math/gcd acc n)]
              (* acc (/ n gcd))))
          1
          numbers))

(defn part-2 [input]
  (let [[commands map-input] (parse-input input)
        map-input (parse-to-map map-input)
        start-nodes (filter #(str/ends-with? % "A") (keys map-input))]
    (lcm (for [node start-nodes]
      (get-ghost-step-count node commands map-input)))))

(part-2 "resources/day08/sample_three.txt")
(part-2 "resources/day08/input.txt")
