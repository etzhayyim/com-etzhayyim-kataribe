(require '[clojure.test :as t])

(doseq [ns-sym '[kataribe.methods.test-charter-gates
                  kataribe.registry-seed-test
                  kataribe.murakumo-test
                  kataribe.repository-contract-test]]
  (require ns-sym))

(let [result (apply t/run-tests
                    '[kataribe.methods.test-charter-gates
                      kataribe.registry-seed-test
                      kataribe.murakumo-test
                      kataribe.repository-contract-test])]
  (System/exit (if (zero? (+ (:fail result) (:error result))) 0 1)))
