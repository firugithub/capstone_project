package capstone;

import java.beans.Transient;
import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss")
  private Timestamp transactionDate;
  
  @JsonIgnore
  private String score;
  
  @JsonIgnore
  private String ucl;
  
  @JsonIgnore
  private String status;

  @JsonIgnore
  private Long lastPostCode;
  
  @JsonIgnore
  private Timestamp lastTransDate;
  
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

  public Timestamp getTransactionDate() {
    return transactionDate;
  }

  public void setTransactionDate(Timestamp transactionDate) {
    this.transactionDate = transactionDate;
  }

  @Override public String toString(){return "TransactionData [cardId=" + cardId + ", memberId=" + memberId + ", amont=" + amont + ", postCode=" + postCode + ", posId=" + posId + ", transactionDate=" + transactionDate + ", score=" + score + ", ucl=" + ucl + ", status=" + status + ", lastPostCode=" + lastPostCode + ", lastTransDate=" + lastTransDate + "]";}

public String getScore() {
return score;}

public void setScore(String score) {
this.score = score;}

public String getUcl() {
return ucl;}

public void setUcl(String ucl) {
this.ucl = ucl;}

public String getStatus() {
return status;}

public void setStatus(String status) {
this.status = status;}

public Long getLastPostCode() {
return lastPostCode;}

public void setLastPostCode(Long lastPostCode) {
this.lastPostCode = lastPostCode;}

public Timestamp getLastTransDate() {
return lastTransDate;}

public void setLastTransDate(Timestamp lastTransDate) {
this.lastTransDate = lastTransDate;}
}
