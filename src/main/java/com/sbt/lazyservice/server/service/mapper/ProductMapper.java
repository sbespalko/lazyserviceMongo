package com.sbt.lazyservice.server.service.mapper;

import com.sbt.lazyservice.server.domain.*;
import com.sbt.lazyservice.server.service.dto.ProductDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Product} and its DTO {@link ProductDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ProductMapper extends EntityMapper<ProductDTO, Product> {

    @Mapping(source = "client.id", target = "clientId")
    ProductDTO toDto(Product product);

    @Mapping(source = "clientId", target = "client")
    @Mapping(target = "histories", ignore = true)
    @Mapping(target = "removeHistory", ignore = true)
    Product toEntity(ProductDTO productDTO);

    default Product fromId(String id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }
}
