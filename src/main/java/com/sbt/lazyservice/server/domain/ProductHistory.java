package com.sbt.lazyservice.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

import com.sbt.lazyservice.server.domain.enumeration.OperationType;

/**
 * A ProductHistory.
 */
@Document(collection = "product_history")
public class ProductHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("operation_dt")
    private Instant operationDt;

    @NotNull
    @Field("opertation_type")
    private OperationType opertationType;

    @Field("sum")
    private BigDecimal sum;

    @DBRef
    @Field("product")
    @JsonIgnoreProperties("productHistories")
    private Product product;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getOperationDt() {
        return operationDt;
    }

    public ProductHistory operationDt(Instant operationDt) {
        this.operationDt = operationDt;
        return this;
    }

    public void setOperationDt(Instant operationDt) {
        this.operationDt = operationDt;
    }

    public OperationType getOpertationType() {
        return opertationType;
    }

    public ProductHistory opertationType(OperationType opertationType) {
        this.opertationType = opertationType;
        return this;
    }

    public void setOpertationType(OperationType opertationType) {
        this.opertationType = opertationType;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public ProductHistory sum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public Product getProduct() {
        return product;
    }

    public ProductHistory product(Product product) {
        this.product = product;
        return this;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductHistory)) {
            return false;
        }
        return id != null && id.equals(((ProductHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ProductHistory{" +
            "id=" + getId() +
            ", operationDt='" + getOperationDt() + "'" +
            ", opertationType='" + getOpertationType() + "'" +
            ", sum=" + getSum() +
            "}";
    }
}
