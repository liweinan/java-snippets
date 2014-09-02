(def frame (java.awt.Frame.))

(for [mtd (.getMethods java.awt.Frame)                      ; iterate over class methods
      :let [name (.getName mtd)]                            ; bind a variable name
      :when (re-find #"Vis" name)]                          ; build a seq of matched names
  name)

(.isVisible frame)
(.setVisible frame true)
(.setSize frame (java.awt.Dimension. 200 200))

(def gfx (.getGraphics frame))

(defn xors [max-x max-y]
  (for [x (range max-x) y (range max-y)]
    [x y (rem (bit-xor x y) 256)]))

(doseq [[x y xor] (xors 500 500)]
  (.setColor gfx (java.awt.Color. xor xor xor))
  (.fillRect gfx x y 1 1))

(defn clear [g] (.clearRect g 0 0 200 200))