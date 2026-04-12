package org.paybridge.dto;

public class ISOResponse {

    private String mti;

    private String pan;

    public String getReservedNational2() {
        return reservedNational2;
    }

    public void setReservedNational2(String reservedNational2) {
        this.reservedNational2 = reservedNational2;
    }

    public String getConversionDate() {
        return conversionDate;
    }

    public void setConversionDate(String conversionDate) {
        this.conversionDate = conversionDate;
    }

    public String getCaptureDate() {
        return captureDate;
    }

    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }

    public String getPanExtendedCountryCode() {
        return panExtendedCountryCode;
    }

    public void setPanExtendedCountryCode(String panExtendedCountryCode) {
        this.panExtendedCountryCode = panExtendedCountryCode;
    }

    public String getApplicationPanSequence() {
        return applicationPanSequence;
    }

    public void setApplicationPanSequence(String applicationPanSequence) {
        this.applicationPanSequence = applicationPanSequence;
    }

    public String getNetworkInternationalId() {
        return networkInternationalId;
    }

    public void setNetworkInternationalId(String networkInternationalId) {
        this.networkInternationalId = networkInternationalId;
    }

    public String getPosCaptureCode() {
        return posCaptureCode;
    }

    public void setPosCaptureCode(String posCaptureCode) {
        this.posCaptureCode = posCaptureCode;
    }

    public String getAuthIdResponseLength() {
        return authIdResponseLength;
    }

    public void setAuthIdResponseLength(String authIdResponseLength) {
        this.authIdResponseLength = authIdResponseLength;
    }

    public String getTransactionFeeAmount() {
        return transactionFeeAmount;
    }

    public void setTransactionFeeAmount(String transactionFeeAmount) {
        this.transactionFeeAmount = transactionFeeAmount;
    }

    public String getSettlementFeeAmount() {
        return settlementFeeAmount;
    }

    public void setSettlementFeeAmount(String settlementFeeAmount) {
        this.settlementFeeAmount = settlementFeeAmount;
    }

    public String getTransactionProcessingFee() {
        return transactionProcessingFee;
    }

    public void setTransactionProcessingFee(String transactionProcessingFee) {
        this.transactionProcessingFee = transactionProcessingFee;
    }

    public String getSettlementProcessingFee() {
        return settlementProcessingFee;
    }

    public void setSettlementProcessingFee(String settlementProcessingFee) {
        this.settlementProcessingFee = settlementProcessingFee;
    }

    public String getAcquiringInstitutionId() {
        return acquiringInstitutionId;
    }

    public void setAcquiringInstitutionId(String acquiringInstitutionId) {
        this.acquiringInstitutionId = acquiringInstitutionId;
    }

    public String getForwardingInstitutionId() {
        return forwardingInstitutionId;
    }

    public void setForwardingInstitutionId(String forwardingInstitutionId) {
        this.forwardingInstitutionId = forwardingInstitutionId;
    }

    public String getServiceRestrictionCode() {
        return serviceRestrictionCode;
    }

    public void setServiceRestrictionCode(String serviceRestrictionCode) {
        this.serviceRestrictionCode = serviceRestrictionCode;
    }

    public String getTrack1Data() {
        return track1Data;
    }

    public void setTrack1Data(String track1Data) {
        this.track1Data = track1Data;
    }

    public String getAdditionalDataIso() {
        return additionalDataIso;
    }

    public void setAdditionalDataIso(String additionalDataIso) {
        this.additionalDataIso = additionalDataIso;
    }

    public String getAdditionalDataNational() {
        return additionalDataNational;
    }

    public void setAdditionalDataNational(String additionalDataNational) {
        this.additionalDataNational = additionalDataNational;
    }

    public String getAdditionalDataPrivate() {
        return additionalDataPrivate;
    }

    public void setAdditionalDataPrivate(String additionalDataPrivate) {
        this.additionalDataPrivate = additionalDataPrivate;
    }

    public String getCurrencyCodeSettlement() {
        return currencyCodeSettlement;
    }

    public void setCurrencyCodeSettlement(String currencyCodeSettlement) {
        this.currencyCodeSettlement = currencyCodeSettlement;
    }

    public String getCurrencyCodeBilling() {
        return currencyCodeBilling;
    }

    public void setCurrencyCodeBilling(String currencyCodeBilling) {
        this.currencyCodeBilling = currencyCodeBilling;
    }

    public String getPinData() {
        return pinData;
    }

    public void setPinData(String pinData) {
        this.pinData = pinData;
    }

    public String getSecurityControlInfo() {
        return securityControlInfo;
    }

    public void setSecurityControlInfo(String securityControlInfo) {
        this.securityControlInfo = securityControlInfo;
    }

    public String getAdditionalAmounts() {
        return additionalAmounts;
    }

    public void setAdditionalAmounts(String additionalAmounts) {
        this.additionalAmounts = additionalAmounts;
    }

    public String getReservedIso() {
        return reservedIso;
    }

    public void setReservedIso(String reservedIso) {
        this.reservedIso = reservedIso;
    }

    public String getReservedNational() {
        return reservedNational;
    }

    public void setReservedNational(String reservedNational) {
        this.reservedNational = reservedNational;
    }

    public String getReservedNational3() {
        return reservedNational3;
    }

    public void setReservedNational3(String reservedNational3) {
        this.reservedNational3 = reservedNational3;
    }

    public String getReservedPrivate() {
        return reservedPrivate;
    }

    public void setReservedPrivate(String reservedPrivate) {
        this.reservedPrivate = reservedPrivate;
    }

    public String getReservedPrivate2() {
        return reservedPrivate2;
    }

    public void setReservedPrivate2(String reservedPrivate2) {
        this.reservedPrivate2 = reservedPrivate2;
    }

    public String getReservedPrivate3() {
        return reservedPrivate3;
    }

    public void setReservedPrivate3(String reservedPrivate3) {
        this.reservedPrivate3 = reservedPrivate3;
    }

    public String getReservedPrivate4() {
        return reservedPrivate4;
    }

    public void setReservedPrivate4(String reservedPrivate4) {
        this.reservedPrivate4 = reservedPrivate4;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    private String processingCode;

    private String amountTransaction;
    private String amountSettlement;
    private String amountCardholderBilling;

    private String transmissionDateTime;

    private String stan;

    private String localTransactionTime;
    private String localTransactionDate;

    private String expiryDate;
    private String settlementDate;
    private String conversionDate;
    private String captureDate;

    private String merchantType;

    private String acquiringCountryCode;
    private String panExtendedCountryCode;
    private String forwardingCountryCode;

    private String posEntryMode;
    private String applicationPanSequence;
    private String networkInternationalId;

    private String posConditionCode;
    private String posCaptureCode;

    private String authIdResponseLength;

    private String transactionFeeAmount;
    private String settlementFeeAmount;
    private String transactionProcessingFee;
    private String settlementProcessingFee;

    private String acquiringInstitutionId;
    private String forwardingInstitutionId;

    private String panExtended;

    private String track2Data;
    private String track3Data;

    private String retrievalReferenceNumber;

    private String authorizationId;

    private String responseCode;

    private String serviceRestrictionCode;

    private String terminalId;
    private String merchantId;

    private String cardAcceptorNameLocation;

    private String additionalResponseData;

    private String track1Data;

    private String additionalDataIso;
    private String additionalDataNational;
    private String additionalDataPrivate;

    private String currencyCodeTransaction;
    private String currencyCodeSettlement;
    private String currencyCodeBilling;

    private String pinData; // (you should mask in mapper)

    private String securityControlInfo;

    private String additionalAmounts;

    private String emvData;

    private String reservedIso;
    private String reservedNational;
    private String reservedNational2;
    private String reservedNational3;

    private String reservedPrivate;
    private String reservedPrivate2;
    private String reservedPrivate3;
    private String reservedPrivate4;

    private String mac; // (should be hidden or masked)

    // ---------------- GETTERS & SETTERS ----------------

    public String getMti() { return mti; }
    public void setMti(String mti) { this.mti = mti; }

    public String getPan() { return pan; }
    public void setPan(String pan) { this.pan = pan; }

    public String getProcessingCode() { return processingCode; }
    public void setProcessingCode(String processingCode) { this.processingCode = processingCode; }

    public String getAmountTransaction() { return amountTransaction; }
    public void setAmountTransaction(String amountTransaction) { this.amountTransaction = amountTransaction; }

    public String getAmountSettlement() { return amountSettlement; }
    public void setAmountSettlement(String amountSettlement) { this.amountSettlement = amountSettlement; }

    public String getAmountCardholderBilling() { return amountCardholderBilling; }
    public void setAmountCardholderBilling(String amountCardholderBilling) { this.amountCardholderBilling = amountCardholderBilling; }

    public String getTransmissionDateTime() { return transmissionDateTime; }
    public void setTransmissionDateTime(String transmissionDateTime) { this.transmissionDateTime = transmissionDateTime; }

    public String getStan() { return stan; }
    public void setStan(String stan) { this.stan = stan; }

    public String getTerminalId() { return terminalId; }
    public void setTerminalId(String terminalId) { this.terminalId = terminalId; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

    public String getResponseCode() { return responseCode; }
    public void setResponseCode(String responseCode) { this.responseCode = responseCode; }

    public String getEmvData() { return emvData; }
    public void setEmvData(String emvData) { this.emvData = emvData; }

    public String getCurrencyCodeTransaction() { return currencyCodeTransaction; }
    public void setCurrencyCodeTransaction(String currencyCodeTransaction) { this.currencyCodeTransaction = currencyCodeTransaction; }

    public String getRetrievalReferenceNumber() { return retrievalReferenceNumber; }
    public void setRetrievalReferenceNumber(String retrievalReferenceNumber) { this.retrievalReferenceNumber = retrievalReferenceNumber; }

    public String getAuthorizationId() { return authorizationId; }
    public void setAuthorizationId(String authorizationId) { this.authorizationId = authorizationId; }

    public String getTrack2Data() { return track2Data; }
    public void setTrack2Data(String track2Data) { this.track2Data = track2Data; }

    public String getAdditionalResponseData() { return additionalResponseData; }
    public void setAdditionalResponseData(String additionalResponseData) { this.additionalResponseData = additionalResponseData; }

    public String getCardAcceptorNameLocation() { return cardAcceptorNameLocation; }
    public void setCardAcceptorNameLocation(String cardAcceptorNameLocation) { this.cardAcceptorNameLocation = cardAcceptorNameLocation; }

    public String getPanExtended() { return panExtended; }
    public void setPanExtended(String panExtended) { this.panExtended = panExtended; }

    public String getTrack3Data() { return track3Data; }
    public void setTrack3Data(String track3Data) { this.track3Data = track3Data; }

    public String getLocalTransactionTime() { return localTransactionTime; }
    public void setLocalTransactionTime(String localTransactionTime) { this.localTransactionTime = localTransactionTime; }

    public String getLocalTransactionDate() { return localTransactionDate; }
    public void setLocalTransactionDate(String localTransactionDate) { this.localTransactionDate = localTransactionDate; }

    public String getExpiryDate() { return expiryDate; }
    public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }

    public String getSettlementDate() { return settlementDate; }
    public void setSettlementDate(String settlementDate) { this.settlementDate = settlementDate; }

    public String getMerchantType() { return merchantType; }
    public void setMerchantType(String merchantType) { this.merchantType = merchantType; }

    public String getAcquiringCountryCode() { return acquiringCountryCode; }
    public void setAcquiringCountryCode(String acquiringCountryCode) { this.acquiringCountryCode = acquiringCountryCode; }

    public String getPosEntryMode() { return posEntryMode; }
    public void setPosEntryMode(String posEntryMode) { this.posEntryMode = posEntryMode; }

    public String getPosConditionCode() { return posConditionCode; }
    public void setPosConditionCode(String posConditionCode) { this.posConditionCode = posConditionCode; }

    public String getForwardingCountryCode() { return forwardingCountryCode; }
    public void setForwardingCountryCode(String forwardingCountryCode) { this.forwardingCountryCode = forwardingCountryCode; }

}