package com.sbt.lazyservice.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;

/**
 * A History.
 */
@Document(collection = "history")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("start_dt")
    private Instant startDt;

    @Field("end_dt")
    private Instant endDt;

    @Field("risk_power")
    private Integer riskPower;

    @DBRef
    @Field("client")
    @JsonIgnoreProperties("histories")
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getStartDt() {
        return startDt;
    }

    public History startDt(Instant startDt) {
        this.startDt = startDt;
        return this;
    }

    public void setStartDt(Instant startDt) {
        this.startDt = startDt;
    }

    public Instant getEndDt() {
        return endDt;
    }

    public History endDt(Instant endDt) {
        this.endDt = endDt;
        return this;
    }

    public void setEndDt(Instant endDt) {
        this.endDt = endDt;
    }

    public Integer getRiskPower() {
        return riskPower;
    }

    public History riskPower(Integer riskPower) {
        this.riskPower = riskPower;
        return this;
    }

    public void setRiskPower(Integer riskPower) {
        this.riskPower = riskPower;
    }

    public Client getClient() {
        return client;
    }

    public History client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof History)) {
            return false;
        }
        return id != null && id.equals(((History) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "History{" +
            "id=" + getId() +
            ", startDt='" + getStartDt() + "'" +
            ", endDt='" + getEndDt() + "'" +
            ", riskPower=" + getRiskPower() +
            "}";
    }
}
