(println (+ 1 2 3))

(ns joy.java
  (:import [java.util HashMap]
           [java.util.io.alchemystudio.concurrent.atomic AtomicLong]))

(HashMap. {"happy?" true})
(AtomicLong. 42)

(def ultimate (AtomicLong. 42))
(println ultimate)

(def fullname ["Guy" "Lewis" "Steele"])
(let [[f m l] fullname]
     (println (str l ", " f " " m)))

(println {1 "one", 2 "two", 3 "three"})

(def xyz {:x "one", :y "two", :z "three"})
(let [{:keys [x y z]} xyz]                                  ; key name must match
     (println x y z))

(println (range 5))
(println (bit-xor 1 2))