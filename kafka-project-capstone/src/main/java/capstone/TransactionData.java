package capstone;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionData {

  @JsonProperty("card_id")
  private Long cardId;

  @JsonProperty("member_id")
  private Long memberId;

  @JsonProperty("amount")
  private Long amont;

  @JsonProperty("postcode")
  private Long postCode;

  @JsonProperty("pos_id")
  private Long posId;

  @JsonProperty("transaction_dt")
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd")
  private Date transactionDate;

  public Long getCardId() {
    return cardId;
  }

  public void setCardId(Long cardId) {
    this.cardId = cardId;
  }

  public Long getMemberId() {
    return memberId;
  }

  public void setMemberId(Long memberId) {
    this.memberId = memberId;
  }

  public Long getAmont() {
    return amont;
  }

  public void setAmont(Long amont) {
    this.amont = amont;
  }

  public Long getPostCode() {
    return postCode;
  }

  public void setPostCode(Long postCode) {
    this.postCode = postCode;
  }

  public Long getPosId() {
    return posId;
  }

  public void setPosId(Long posId) {
    this.posId = posId;
  }

  public Date getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Date transactionDate) {
    this.transactionDate = transactionDate;
  }

  @Override
  public String toString() {
    return "TransactionData [cardId="
        + cardId
        + ", memberId="
        + memberId
        + ", amont="
        + amont
        + ", postCode="
        + postCode
        + ", posId="
        + posId
        + ", transactionDate="
        + transactionDate
        + "]";
  }
}
