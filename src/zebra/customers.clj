(ns zebra.customers
  (:refer-clojure :exclude [list update])
  (:require [zebra.sources :refer [source->map]]
            [zebra.payment-methods :refer [payment-method->map]])
  (:import [com.stripe.model Customer PaymentMethod PaymentSourceCollection]
           [com.stripe.net RequestOptions]
           [java.util Map]))
(defn- ^RequestOptions request-options [api-key]
  (->
    (RequestOptions/builder)
    (.setApiKey api-key)
    (.build)))

(defn customer->map [^Customer customer]
  {:id       (.getId customer)
   :metadata (.getMetadata customer)
   :sources  (.getSources customer)})

(defn create
  ([api-key]
   (create api-key {}))
  ([api-key ^Map params]
   (customer->map
     (Customer/create params
       (request-options api-key)))))

(defn retrieve
  [id api-key]
  (customer->map
    (Customer/retrieve id
      (request-options api-key))))

(defn attach-source
  [customer-id source-id api-key]
  (let [params {"source" source-id}
        request-options (request-options api-key)
        customer (Customer/retrieve customer-id
                   request-options)
        sources (.getSources customer)]
    (source->map
      (.create
        ^PaymentSourceCollection sources
        ^Map params
        ^RequestOptions request-options))))

(defn attach-payment-method
  [customer-id payment-method-id api-key]
  (let [params {"customer" customer-id}
        request-options (request-options api-key)
        payment-method (PaymentMethod/retrieve payment-method-id
                         request-options)]
    (payment-method->map
      (.attach
        ^PaymentMethod payment-method
        ^Map params
        ^RequestOptions request-options))))
