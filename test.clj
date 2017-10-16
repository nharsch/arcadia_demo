(ns arcadia_demo.test
    (:require [arcadia.core :as a]
              [arcadia.linear :as l])
    (:import [UnityEngine 
                Application 
                GameObject 
                Vector3
                Component]))

(defn reset-world []
    (Application/LoadLevel Application/loadedLevel))

(defn ensure-uniquely-named-object! 
    "get or create uniquely named obj"
    [name type]
    (let [obj (first (a/objects-named name))]
      (if obj
        obj
        (set! (.. (a/create-primitive type) name) name))))

(defn setup-floor []
    ; why do I need to instantiate once?
    (let [floor (ensure-uniquely-named-object! "myplane" :plane)]
        (if (not= (type floor) UnityEngine.GameObject)
            ; sometimes floor is a string, in this case recur until it's a game obj
            (setup-floor)
            ; if we have a game obj, setup obj 
            (set! 
                (.. floor transform localScale) 
                (Vector3. 100 100 100)))
        floor))



(def sky-cube-vals {
    :position (l/v3 0 20 0)
    :scale (l/v3 1 1 1)
    :name "skycube"})

(defn build-cube [vals] 
    "vals: map containing :position :name :scale"
    (let [cube (a/create-primitive :cube (:name vals))]
        (do
            (set! (.. cube transform position) (:position vals))
            (set! (.. cube transform localScale) (:scale vals))
            (a/ensure-cmpt (.transform cube) UnityEngine.Rigidbody))))

(setup-floor)
(build-cube sky-cube-vals)

(defn reset-objs []
    (map a/destroy (concat (a/objects-named "myplane") (a/objects-named "skycube"))))

(reset-objs)
(reset-world)