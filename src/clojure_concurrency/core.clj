(ns clojure-concurrency.core
  (:gen-class))

;; Atom
(def counter-atom (atom 0))

; Add number m to value of atom counter
(defn add-counter [m] (swap! counter-atom (fn [n] (+ n m))))

; Increment atom counter
(defn inc-counter [] (swap! counter-atom inc))

;; Refs
(def first-name (ref "Johan"))
(def last-name (ref "Astborg"))
(def age (ref 28))

;; Agent

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  ;; work around dangerous default behaviour in Clojure
  (alter-var-root #'*read-eval* (constantly false))
  (println "Hello, World!")

  ;; Use atom
  
  ; Detects changes in the atom
  (add-watch counter-atom :key (fn [k r os ns] (println (format "Change of atom, old: %d, new: %d"  os ns))))
  
  (add-counter 4)
  (add-counter 7)
  (inc-counter)

  ;; Use refs
  
  ; Update all refs using a transaction
  (dosync
   (ref-set first-name "Joohaan")
   (ref-set last-name "Astburg")
   (alter age (partial + 1)))

  ; Inspect values in refs
  (println @first-name @last-name @age)

  ;; Simple use of futures
  (defn long-calc [n] n)

  ; Create future
  (def res (future (long-calc 10000)))

  ; Use future, ie get result
  (println (deref res))

  ;; Use Agent
)
