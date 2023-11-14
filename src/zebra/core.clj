(ns zebra.core
  (:require
    [zebra.charges :as charges]
    [zebra.customers :as customers]
    [zebra.ephemeral-keys :as ephemeral-keys]
    [zebra.invoice-items :as invoice-items]
    [zebra.invoices :as invoices]
    [zebra.payment-intents :as payment-intents]
    [zebra.payment-methods :as payment-methods]
    [zebra.prices :as prices]
    [zebra.products :as products]
    [zebra.refunds :as refunds]
    [zebra.sources :as sources]
    [zebra.subscriptions :as subscriptions])
  (:import
    (com.stripe
      Stripe)))

;; Stripe

(defn get-api-base
  []
  (Stripe/getApiBase))

;; Charges

(defn create-charge
  [params api-key]
  (charges/create params api-key))

(defn retrieve-charge
  [id api-key]
  (charges/retrieve id api-key))

(def charge-status-codes charges/status-codes)

;; Customers

(defn create-customer
  ([params api-key]
   (customers/create params api-key))
  ([api-key]
   (create-customer {} api-key)))

(defn retrieve-customer
  [id api-key]
  (customers/retrieve id api-key))

(defn attach-source-to-customer
  [customer-id source-id api-key]
  (customers/attach-source customer-id source-id api-key))

(defn attach-payment-method-to-customer
  [customer-id payment-method-id api-key]
  (customers/attach-payment-method customer-id payment-method-id api-key))

;; Sources

(defn create-source
  [params api-key]
  (sources/create params api-key))

(defn retrieve-source
  [id api-key]
  (sources/retrieve id api-key))

(def three-d-secure-requirements sources/three-d-secure-requirements)

(def source-status-codes sources/status-codes)

;; Ephemeral Keys

(defn create-ephemeral-key
  [params api-version api-key]
  (ephemeral-keys/create params api-version api-key))

;; Payment Methods

(defn create-payment-method
  [params api-key]
  (payment-methods/create params api-key))

(defn retrieve-payment-method
  [id api-key]
  (payment-methods/retrieve id api-key))

;; Payment Intents

(defn create-payment-intent
  [params api-key]
  (payment-intents/create params api-key))

(defn retrieve-payment-intent
  [id api-key]
  (payment-intents/retrieve id api-key))

(defn update-payment-intent
  [id params api-key]
  (payment-intents/update id params api-key))

(defn capture-payment-intent
  [id api-key]
  (payment-intents/capture id api-key))

(defn confirm-payment-intent
  [id api-key]
  (payment-intents/confirm id api-key))

;; Invoices

(defn create-invoice
  ([params api-key]
   (invoices/create params api-key))
  ([api-key]
   (create-invoice {} api-key)))

(defn retrieve-invoice
  [id api-key]
  (invoices/retrieve id api-key))

(defn finalise-invoice
  [id api-key]
  (invoices/finalise id api-key))

;; Invoice Items

(defn create-invoice-item
  ([params api-key]
   (invoice-items/create params api-key))
  ([api-key]
   (create-invoice-item {} api-key)))

(defn retrieve-invoice-item
  [id api-key]
  (invoice-items/retrieve id api-key))

;; Refunds

(defn create-refund
  [params api-key]
  (refunds/create params api-key))

(defn retrieve-refund
  [id api-key]
  (refunds/retrieve id api-key))

;; Prices

(defn create-price
  [params api-key]
  (prices/create params api-key))

(defn retrieve-price
  [id api-key]
  (prices/retrieve id api-key))

(defn search-price
  [params api-key]
  (prices/search params api-key))

;; Products

(defn create-product
  [params api-key]
  (products/create params api-key))

(defn retrieve-product
  [id api-key]
  (products/retrieve id api-key))

;; Subscriptions

(defn create-subscription
  [params api-key]
  (subscriptions/create params api-key))

(defn retrieve-subscription
  ([id params api-key]
   (subscriptions/retrieve id params api-key))
  ([id api-key]
   (retrieve-subscription id api-key)))

(defn list-subscriptions
  [params api-key]
  (subscriptions/list params api-key))
