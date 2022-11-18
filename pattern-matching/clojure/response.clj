(ns response)

; définition des records (équivalent à des "data class")
(defrecord Pending [])
(defrecord Failure [^String message])
(defrecord Success [result ^String comment])
; un "constructeur secondaire" doit être défini comme une fonction à part
(defn make-success [r & c]
  (->Success r c))

; on peut hiérarchiser les "data class" (records) mais ce n'est pas nécessaire
(defrecord Response [])
(derive ::Response ::Pending)
(derive ::Response ::Failure)
(derive ::Response ::Success)
; tout ce qui suit fonctionnerait très bien sans les 4 lignes au-dessus

; exemples pour les tests à suivre
(def tests [(->Pending)
            (make-success 42)
            (->Success 42 "quelle est la question?")
            (->Failure "catastrophe")
            (->Failure "tout va bien")
            ])

; une façon de "pattern-matcher" est en définissant une fonction dans un protocole
(defprotocol response-handling
  (process [r]))

; le protocole se spécialise ensuite par type
(extend-protocol response-handling
  Pending
  (process [_] "Rien reçu")
  Failure
  (process [r]
    (if (= (:message r) "tout va bien")
      "on a reçu un paradoxe!"
      (str "on a reçu une erreur: " (:message r))))
  Success
  (process [r]
    (if (nil? (:comment r))
      "ça a fonctionné!"
      (str "ça a fonctionné et on nous a raporté: " (:comment r)))))

(println "* Tests avec protocole *")
(doseq [c tests]
  (println (process c)))

;; alternative: une multi-method avec diverses implémentations a priori plus
;; puissant vu qu'on peut matcher sur plusieeurs objets et utiliser une fonction
;; arbitraire pour produire une ou plusieurs valeurs, mais "overkill" ici
(defmulti process-response (fn [r] [(class r)
                                    (or
                                        (= (:message r) "tout va bien")
                                        (and (= (class r) Success)
                                             (nil? (:comment r))))]))
(defmethod process-response [Pending false] [_] "rien reçu")
(defmethod process-response [Failure true] [_] "on a reçu un paradoxe!")
(defmethod process-response [Failure false] [r]
  (str "on a reçu une erreur: " (:message r)))
(defmethod process-response [Success true] [_] "ça a fonctionné!")
(defmethod process-response [Success false] [r]
  (str "ça a fonctionné et on nous a raporté: " (:comment r)))

(println "\n* Tests avec multi-method *")
(doseq [c tests]
  (println (process-response c)))
