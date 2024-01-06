(ns zebra.payment-intents
  (:refer-clojure :exclude [update])
  (:require [zebra.utils :refer [transform-params]])
  (:import
   [com.stripe.model PaymentIntent PaymentIntent$NextAction]
   [com.stripe.net RequestOptions]
   [java.util Map]))

(defn- ^RequestOptions request-options [api-key]
  (->
    (RequestOptions/builder)
    (.setApiKey api-key)
    (.build)))

(defn next-action->map [^PaymentIntent$NextAction next-action]
  (merge
    {:type (.getType next-action)}
    (when-let [redirect-to-url (.getRedirectToUrl next-action)]
      {:redirect_to_url {:return_url (.getReturnUrl redirect-to-url)
                         :url        (.getUrl redirect-to-url)}})))

(defn payment-intent->map [^PaymentIntent x]
  (merge
    {:id                   (.getId x)
     :object               (.getObject x)
     :status               (.getStatus x)
     :description          (.getDescription x)
     :statement_descriptor (.getStatementDescriptor x)
     :confirmation_method  (.getConfirmationMethod x)
     :payment_method_types (vec (.getPaymentMethodTypes x))
     :amount               (.getAmount x)
     :currency             (.getCurrency x)
     :payment_method       (.getPaymentMethod x)
     :client_secret        (.getClientSecret x)}
    (when-let [next-action (.getNextAction x)]
      {:next_action
       (next-action->map next-action)})))

(defn create
  [params api-key]
  (payment-intent->map
    (PaymentIntent/create ^Map (transform-params params)
      (request-options api-key))))

(defn retrieve
  [id api-key]
  (payment-intent->map
    (PaymentIntent/retrieve id
      (request-options api-key))))

(defn update
  [id params api-key]
  (let [request-options (request-options api-key)
        payment-intent (PaymentIntent/retrieve id request-options)]
    (payment-intent->map
      (.update payment-intent ^Map (transform-params params) request-options))))

(defn capture
  [id api-key]
  (let [request-options (request-options api-key)
        payment-intent
        (PaymentIntent/retrieve id request-options)]
    (payment-intent->map (.capture payment-intent request-options))))

(defn confirm
  [id api-key]
  (let [request-options (request-options api-key)
        params {}
        payment-intent (PaymentIntent/retrieve id request-options)]
    (payment-intent->map
      (.confirm
        ^PaymentIntent payment-intent
        ^Map params
        ^RequestOptions request-options))))
