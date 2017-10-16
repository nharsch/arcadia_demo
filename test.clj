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
        (set! 
            (.. (ensure-uniquely-named-object! "myplane" :plane)
                transform localScale) 
            (Vector3. 100 100 100))
    )
)

(setup-floor)

(def sky-cube-vals {
    :position (l/v3 0 20 0)
    :scale (l/v3 1 1 1)
    :name "skycube"})

(defn build-cube [vals] 
    "vals: map containing :position :name :scale"
    (let [cube (ensure-uniquely-named-object! (:name vals) :cube)]
        (do
            (set! (.. 
                    cube
                    transform
                    position)
                (:position vals))
            (set! (..
                    cube
                    transform
                    localScale
                    )
                (:scale vals))
            (a/ensure-cmpt
                (.transform cube)
                UnityEngine.Rigidbody))))

(build-cube sky-cube-vals)



(:name sky-cube-vals)
(:position sky-cube-vals)
(ensure-uniquely-named-object! (:name sky-cube-vals) :cube)

(map a/destroy (a/objects-named "skycube"))
(reset-world)