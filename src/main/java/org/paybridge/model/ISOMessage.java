package org.paybridge.model;
import org.paybridge.annotation.ISOField;

public class ISOMessage {

    private String mti;

    @ISOField(fieldNumber = 2, length = 16)
    private String pan;

    @ISOField(fieldNumber = 3, length = 6)
    private String processingCode;

    @ISOField(fieldNumber = 4, length = 12)
    private String amount;

    @ISOField(fieldNumber = 7, length = 10)
    private String transmissionDateTime;

    @ISOField(fieldNumber = 11, length = 6)
    private String stan;

    @ISOField(fieldNumber = 41, length = 8)
    private String terminalId;

    @ISOField(fieldNumber = 42, length = 15)
    private String merchantId;

    public String getMti() {
        return mti;
    }

    public void setMti(String mti) {
        this.mti = mti;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getProcessingCode() {
        return processingCode;
    }

    public void setProcessingCode(String processingCode) {
        this.processingCode = processingCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransmissionDateTime() {
        return transmissionDateTime;
    }

    public void setTransmissionDateTime(String transmissionDateTime) {
        this.transmissionDateTime = transmissionDateTime;
    }

    public String getStan() {
        return stan;
    }

    public void setStan(String stan) {
        this.stan = stan;
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

    @Override
    public String toString() {
        return
                "mti='" + mti + '\'' +
                "pan='" + pan + '\'' +
                ", processingCode='" + processingCode + '\'' +
                ", amount='" + amount + '\'' +
                ", transmissionDateTime='" + transmissionDateTime + '\'' +
                ", stan='" + stan + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", merchantId='" + merchantId;
    }
}