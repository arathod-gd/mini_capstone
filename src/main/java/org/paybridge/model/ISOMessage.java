package org.paybridge.model;

import org.paybridge.annotation.ISOField;
import org.paybridge.enums.LengthType;

public class ISOMessage {

    private String mti;

    ///internal parsing metadata
    public String primaryBitmap;


    @ISOField(fieldNumber = 2, length = 19, type = LengthType.LLVAR)
    private String pan;

    @ISOField(fieldNumber = 3, length = 6, type = LengthType.FIXED)
    private String processingCode;

    @ISOField(fieldNumber = 4, length = 12)
    private String amountTransaction;

    @ISOField(fieldNumber = 5, length = 12)
    private String amountSettlement;

    @ISOField(fieldNumber = 6, length = 12)
    private String amountCardholderBilling;

    @ISOField(fieldNumber = 7, length = 10)
    private String transmissionDateTime;

    @ISOField(fieldNumber = 8, length = 8)
    private String amountBillingFee;

    @ISOField(fieldNumber = 9, length = 8)
    private String conversionRateSettlement;

    @ISOField(fieldNumber = 10, length = 8)
    private String conversionRateBilling;

    @ISOField(fieldNumber = 11, length = 6)
    private String stan;

    @ISOField(fieldNumber = 12, length = 6)
    private String localTransactionTime;

    @ISOField(fieldNumber = 13, length = 4)
    private String localTransactionDate;

    @ISOField(fieldNumber = 14, length = 4)
    private String expiryDate;

    @ISOField(fieldNumber = 15, length = 4)
    private String settlementDate;

    @ISOField(fieldNumber = 16, length = 4)
    private String conversionDate;

    @ISOField(fieldNumber = 17, length = 4)
    private String captureDate;

    @ISOField(fieldNumber = 18, length = 4)
    private String merchantType;

    @ISOField(fieldNumber = 19, length = 3)
    private String acquiringCountryCode;

    @ISOField(fieldNumber = 20, length = 3)
    private String panExtendedCountryCode;

    @ISOField(fieldNumber = 21, length = 3)
    private String forwardingCountryCode;

    @ISOField(fieldNumber = 22, length = 3)
    private String posEntryMode;

    @ISOField(fieldNumber = 23, length = 3)
    private String applicationPanSequence;

    @ISOField(fieldNumber = 24, length = 3)
    private String networkInternationalId;

    @ISOField(fieldNumber = 25, length = 2)
    private String posConditionCode;

    @ISOField(fieldNumber = 26, length = 2)
    private String posCaptureCode;

    @ISOField(fieldNumber = 27, length = 1)
    private String authIdResponseLength;

    @ISOField(fieldNumber = 28, length = 8)
    private String transactionFeeAmount;

    @ISOField(fieldNumber = 29, length = 8)
    private String settlementFeeAmount;

    @ISOField(fieldNumber = 30, length = 8)
    private String transactionProcessingFee;

    @ISOField(fieldNumber = 31, length = 8)
    private String settlementProcessingFee;

    @ISOField(fieldNumber = 32, length = 11, type = LengthType.LLVAR)
    private String acquiringInstitutionId;

    @ISOField(fieldNumber = 33, length = 11, type = LengthType.LLVAR)
    private String forwardingInstitutionId;

    @ISOField(fieldNumber = 34, length = 28, type = LengthType.LLVAR)
    private String panExtended;

    @ISOField(fieldNumber = 35, length = 37, type = LengthType.LLVAR)
    private String track2Data;

    @ISOField(fieldNumber = 36, length = 104, type = LengthType.LLLVAR)
    private String track3Data;

    @ISOField(fieldNumber = 37, length = 12)
    private String retrievalReferenceNumber;

    @ISOField(fieldNumber = 38, length = 6)
    private String authorizationId;

    @ISOField(fieldNumber = 39, length = 2)
    private String responseCode;

    @ISOField(fieldNumber = 40, length = 3)
    private String serviceRestrictionCode;

    @ISOField(fieldNumber = 41, length = 8)
    private String terminalId;

    @ISOField(fieldNumber = 42, length = 15)
    private String merchantId;

    @ISOField(fieldNumber = 43, length = 40)
    private String cardAcceptorNameLocation;

    @ISOField(fieldNumber = 44, length = 25, type = LengthType.LLVAR)
    private String additionalResponseData;

    @ISOField(fieldNumber = 45, length = 76, type = LengthType.LLVAR)
    private String track1Data;

    @ISOField(fieldNumber = 46, length = 999, type = LengthType.LLLVAR)
    private String additionalDataIso;

    @ISOField(fieldNumber = 47, length = 999, type = LengthType.LLLVAR)
    private String additionalDataNational;

    @ISOField(fieldNumber = 48, length = 999, type = LengthType.LLLVAR)
    private String additionalDataPrivate;

    @ISOField(fieldNumber = 49, length = 3)
    private String currencyCodeTransaction;

    @ISOField(fieldNumber = 50, length = 3)
    private String currencyCodeSettlement;

    @ISOField(fieldNumber = 51, length = 3)
    private String currencyCodeBilling;

    @ISOField(fieldNumber = 52, length = 16)
    private String pinData;

    @ISOField(fieldNumber = 53, length = 16)
    private String securityControlInfo;

    @ISOField(fieldNumber = 54, length = 120, type = LengthType.LLLVAR)
    private String additionalAmounts;

    @ISOField(fieldNumber = 55, length = 255, type = LengthType.LLLVAR)
    private String emvData;

    @ISOField(fieldNumber = 56, length = 999, type = LengthType.LLLVAR)
    private String reservedIso;

    @ISOField(fieldNumber = 57, length = 999, type = LengthType.LLLVAR)
    private String reservedNational;

    @ISOField(fieldNumber = 58, length = 999, type = LengthType.LLLVAR)
    private String reservedNational2;

    @ISOField(fieldNumber = 59, length = 999, type = LengthType.LLLVAR)
    private String reservedNational3;

    @ISOField(fieldNumber = 60, length = 999, type = LengthType.LLLVAR)
    private String reservedPrivate;

    @ISOField(fieldNumber = 61, length = 999, type = LengthType.LLLVAR)
    private String reservedPrivate2;

    @ISOField(fieldNumber = 62, length = 999, type = LengthType.LLLVAR)
    private String reservedPrivate3;

    @ISOField(fieldNumber = 63, length = 999, type = LengthType.LLLVAR)
    private String reservedPrivate4;

    @ISOField(fieldNumber = 64, length = 16)
    private String mac;

    public String getMti() {
        return mti;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getReservedPrivate() {
        return reservedPrivate;
    }

    public void setReservedPrivate(String reservedPrivate) {
        this.reservedPrivate = reservedPrivate;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getAmountTransaction() {
        return amountTransaction;
    }

    public void setAmountTransaction(String amountTransaction) {
        this.amountTransaction = amountTransaction;
    }

    public String getAmountSettlement() {
        return amountSettlement;
    }

    public void setAmountSettlement(String amountSettlement) {
        this.amountSettlement = amountSettlement;
    }

    public String getAmountCardholderBilling() {
        return amountCardholderBilling;
    }

    public void setAmountCardholderBilling(String amountCardholderBilling) {
        this.amountCardholderBilling = amountCardholderBilling;
    }

    public String getTransmissionDateTime() {
        return transmissionDateTime;
    }

    public void setTransmissionDateTime(String transmissionDateTime) {
        this.transmissionDateTime = transmissionDateTime;
    }

    public String getAmountBillingFee() {
        return amountBillingFee;
    }

    public void setAmountBillingFee(String amountBillingFee) {
        this.amountBillingFee = amountBillingFee;
    }

    public String getConversionRateSettlement() {
        return conversionRateSettlement;
    }

    public void setConversionRateSettlement(String conversionRateSettlement) {
        this.conversionRateSettlement = conversionRateSettlement;
    }

    public String getConversionRateBilling() {
        return conversionRateBilling;
    }

    public void setConversionRateBilling(String conversionRateBilling) {
        this.conversionRateBilling = conversionRateBilling;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
    }

    public String getLocalTransactionTime() {
        return localTransactionTime;
    }

    public void setLocalTransactionTime(String localTransactionTime) {
        this.localTransactionTime = localTransactionTime;
    }

    public String getLocalTransactionDate() {
        return localTransactionDate;
    }

    public void setLocalTransactionDate(String localTransactionDate) {
        this.localTransactionDate = localTransactionDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
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

    public String getMerchantType() {
        return merchantType;
    }

    public void setMerchantType(String merchantType) {
        this.merchantType = merchantType;
    }

    public String getAcquiringCountryCode() {
        return acquiringCountryCode;
    }

    public void setAcquiringCountryCode(String acquiringCountryCode) {
        this.acquiringCountryCode = acquiringCountryCode;
    }

    public String getPanExtendedCountryCode() {
        return panExtendedCountryCode;
    }

    public void setPanExtendedCountryCode(String panExtendedCountryCode) {
        this.panExtendedCountryCode = panExtendedCountryCode;
    }

    public String getForwardingCountryCode() {
        return forwardingCountryCode;
    }

    public void setForwardingCountryCode(String forwardingCountryCode) {
        this.forwardingCountryCode = forwardingCountryCode;
    }

    public String getPosEntryMode() {
        return posEntryMode;
    }

    public void setPosEntryMode(String posEntryMode) {
        this.posEntryMode = posEntryMode;
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

    public String getPosConditionCode() {
        return posConditionCode;
    }

    public void setPosConditionCode(String posConditionCode) {
        this.posConditionCode = posConditionCode;
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

    public String getPanExtended() {
        return panExtended;
    }

    public void setPanExtended(String panExtended) {
        this.panExtended = panExtended;
    }

    public String getTrack2Data() {
        return track2Data;
    }

    public void setTrack2Data(String track2Data) {
        this.track2Data = track2Data;
    }

    public String getTrack3Data() {
        return track3Data;
    }

    public void setTrack3Data(String track3Data) {
        this.track3Data = track3Data;
    }

    public String getRetrievalReferenceNumber() {
        return retrievalReferenceNumber;
    }

    public void setRetrievalReferenceNumber(String retrievalReferenceNumber) {
        this.retrievalReferenceNumber = retrievalReferenceNumber;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getServiceRestrictionCode() {
        return serviceRestrictionCode;
    }

    public void setServiceRestrictionCode(String serviceRestrictionCode) {
        this.serviceRestrictionCode = serviceRestrictionCode;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCardAcceptorNameLocation() {
        return cardAcceptorNameLocation;
    }

    public void setCardAcceptorNameLocation(String cardAcceptorNameLocation) {
        this.cardAcceptorNameLocation = cardAcceptorNameLocation;
    }

    public String getAdditionalResponseData() {
        return additionalResponseData;
    }

    public void setAdditionalResponseData(String additionalResponseData) {
        this.additionalResponseData = additionalResponseData;
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

    public String getCurrencyCodeTransaction() {
        return currencyCodeTransaction;
    }

    public void setCurrencyCodeTransaction(String currencyCodeTransaction) {
        this.currencyCodeTransaction = currencyCodeTransaction;
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

    public String getEmvData() {
        return emvData;
    }

    public void setEmvData(String emvData) {
        this.emvData = emvData;
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

    public String getReservedNational2() {
        return reservedNational2;
    }

    public void setReservedNational2(String reservedNational2) {
        this.reservedNational2 = reservedNational2;
    }

    public String getReservedNational3() {
        return reservedNational3;
    }

    public void setReservedNational3(String reservedNational3) {
        this.reservedNational3 = reservedNational3;
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

    public void setMti(String mti) {
        this.mti = mti;
    }

    @Override
    public String toString() {
        return
                "mti='" + mti + '\'' +
                ", pan='" + pan + '\'' +
                ", processingCode='" + processingCode + '\'' +
                ", amountTransaction='" + amountTransaction + '\'' +
                ", amountSettlement='" + amountSettlement + '\'' +
                ", amountCardholderBilling='" + amountCardholderBilling + '\'' +
                ", transmissionDateTime='" + transmissionDateTime + '\'' +
                ", amountBillingFee='" + amountBillingFee + '\'' +
                ", conversionRateSettlement='" + conversionRateSettlement + '\'' +
                ", conversionRateBilling='" + conversionRateBilling + '\'' +
                ", stan='" + stan + '\'' +
                ", localTransactionTime='" + localTransactionTime + '\'' +
                ", localTransactionDate='" + localTransactionDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", settlementDate='" + settlementDate + '\'' +
                ", conversionDate='" + conversionDate + '\'' +
                ", captureDate='" + captureDate + '\'' +
                ", merchantType='" + merchantType + '\'' +
                ", acquiringCountryCode='" + acquiringCountryCode + '\'' +
                ", panExtendedCountryCode='" + panExtendedCountryCode + '\'' +
                ", forwardingCountryCode='" + forwardingCountryCode + '\'' +
                ", posEntryMode='" + posEntryMode + '\'' +
                ", applicationPanSequence='" + applicationPanSequence + '\'' +
                ", networkInternationalId='" + networkInternationalId + '\'' +
                ", posConditionCode='" + posConditionCode + '\'' +
                ", posCaptureCode='" + posCaptureCode + '\'' +
                ", authIdResponseLength='" + authIdResponseLength + '\'' +
                ", transactionFeeAmount='" + transactionFeeAmount + '\'' +
                ", settlementFeeAmount='" + settlementFeeAmount + '\'' +
                ", transactionProcessingFee='" + transactionProcessingFee + '\'' +
                ", settlementProcessingFee='" + settlementProcessingFee + '\'' +
                ", acquiringInstitutionId='" + acquiringInstitutionId + '\'' +
                ", forwardingInstitutionId='" + forwardingInstitutionId + '\'' +
                ", panExtended='" + panExtended + '\'' +
                ", track2Data='" + track2Data + '\'' +
                ", track3Data='" + track3Data + '\'' +
                ", retrievalReferenceNumber='" + retrievalReferenceNumber + '\'' +
                ", authorizationId='" + authorizationId + '\'' +
                ", responseCode='" + responseCode + '\'' +
                ", serviceRestrictionCode='" + serviceRestrictionCode + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", merchantId='" + merchantId + '\'' +
                ", cardAcceptorNameLocation='" + cardAcceptorNameLocation + '\'' +
                ", additionalResponseData='" + additionalResponseData + '\'' +
                ", track1Data='" + track1Data + '\'' +
                ", additionalDataIso='" + additionalDataIso + '\'' +
                ", additionalDataNational='" + additionalDataNational + '\'' +
                ", additionalDataPrivate='" + additionalDataPrivate + '\'' +
                ", currencyCodeTransaction='" + currencyCodeTransaction + '\'' +
                ", currencyCodeSettlement='" + currencyCodeSettlement + '\'' +
                ", currencyCodeBilling='" + currencyCodeBilling + '\'' +
                ", pinData='" + pinData + '\'' +
                ", securityControlInfo='" + securityControlInfo + '\'' +
                ", additionalAmounts='" + additionalAmounts + '\'' +
                ", emvData='" + emvData + '\'' +
                ", reservedIso='" + reservedIso + '\'' +
                ", reservedNational='" + reservedNational + '\'' +
                ", reservedNational2='" + reservedNational2 + '\'' +
                ", reservedNational3='" + reservedNational3 + '\'' +
                ", reservedPrivate='" + reservedPrivate + '\'' +
                ", reservedPrivate2='" + reservedPrivate2 + '\'' +
                ", reservedPrivate3='" + reservedPrivate3 + '\'' +
                ", reservedPrivate4='" + reservedPrivate4 + '\'' +
                ", mac='" + mac ;
    }
}