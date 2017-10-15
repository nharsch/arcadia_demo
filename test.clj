(ns arcadia_demo.test
    (:use arcadia.core)
    (:import [UnityEngine Application GameObject Vector2 Vector3]))

(defn reset-world []
    (Application/LoadLevel Application/loadedLevel))


(defn setup []
    (let [myplane (create-primitive :plane) 
          mycube (create-primitive :cube)]
        (set! (.. (object-named "Plane") name) "myplane")
        (set! (.. (object-named "Cube") name) "mycube"))
    (set! (.. (object-named "myplane") transform localScale) (Vector3. 2.0 2.0 2.0))
    (set! (.. (object-named "mycube") transform position) (Vector3. 0.0 1.0 0.0))
)
(setup)

(.. (object-named "myplane") transform localScale)

; naive move
(defn move-obj [obj]
    (let [cpos (.. obj transform position)]
        (set! (.. obj transform position) 
            (Vector3. (.. cpos x) (+ 1 (.. cpos y)) (.. cpos z)))
    ))

(move-obj mycube)


(reset-world)
(destroy (object-named "Cube"))
(destroy (object-named "plane"))