package com.datj.mobile.data.remote.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CaptureResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("status")
    private String status;

    @SerializedName("payment_source")
    private PaymentSource paymentSource;

    @SerializedName("purchase_units")
    private List<PurchaseUnit> purchaseUnits;

    @SerializedName("payer")
    private Payer payer;

    @SerializedName("links")
    private List<Link> links;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public PaymentSource getPaymentSource() {
        return paymentSource;
    }

    public List<PurchaseUnit> getPurchaseUnits() {
        return purchaseUnits;
    }

    public Payer getPayer() {
        return payer;
    }

    public List<Link> getLinks() {
        return links;
    }

    // -------- Nested Classes -------- //

    public static class PaymentSource {
        @SerializedName("paypal")
        private Paypal paypal;

        public Paypal getPaypal() {
            return paypal;
        }

        public static class Paypal {
            @SerializedName("email_address")
            private String emailAddress;

            public String getEmailAddress() {
                return emailAddress;
            }
        }
    }

    public static class PurchaseUnit {
        @SerializedName("reference_id")
        private String referenceId;

        public String getReferenceId() {
            return referenceId;
        }
    }

    public static class Payer {
        @SerializedName("email_address")
        private String emailAddress;

        @SerializedName("payer_id")
        private String payerId;

        public String getEmailAddress() {
            return emailAddress;
        }

        public String getPayerId() {
            return payerId;
        }
    }

    public static class Link {
        @SerializedName("href")
        private String href;

        @SerializedName("rel")
        private String rel;

        @SerializedName("method")
        private String method;

        public String getHref() {
            return href;
        }

        public String getRel() {
            return rel;
        }

        public String getMethod() {
            return method;
        }
    }
}