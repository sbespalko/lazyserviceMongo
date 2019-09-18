package com.sbt.lazyservice.server.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Client.
 */
@Document(collection = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(min = 3, max = 160)
    @Field("name")
    private String name;

    @DBRef
    @Field("product")
    private Set<Product> products = new HashSet<>();

    @DBRef
    @Field("history")
    private Set<History> histories = new HashSet<>();

    @DBRef
    @Field("address")
    @JsonIgnoreProperties("clients")
    private Address address;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Client name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Client products(Set<Product> products) {
        this.products = products;
        return this;
    }

    public Client addProduct(Product product) {
        this.products.add(product);
        product.setClient(this);
        return this;
    }

    public Client removeProduct(Product product) {
        this.products.remove(product);
        product.setClient(null);
        return this;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Set<History> getHistories() {
        return histories;
    }

    public Client histories(Set<History> histories) {
        this.histories = histories;
        return this;
    }

    public Client addHistory(History history) {
        this.histories.add(history);
        history.setClient(this);
        return this;
    }

    public Client removeHistory(History history) {
        this.histories.remove(history);
        history.setClient(null);
        return this;
    }

    public void setHistories(Set<History> histories) {
        this.histories = histories;
    }

    public Address getAddress() {
        return address;
    }

    public Client address(Address address) {
        this.address = address;
        return this;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
