package com.sbt.lazyservice.server.service.mapper;

import com.sbt.lazyservice.server.domain.*;
import com.sbt.lazyservice.server.service.dto.HistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link History} and its DTO {@link HistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface HistoryMapper extends EntityMapper<HistoryDTO, History> {

    @Mapping(source = "client.id", target = "clientId")
    HistoryDTO toDto(History history);

    @Mapping(source = "clientId", target = "client")
    History toEntity(HistoryDTO historyDTO);

    default History fromId(String id) {
        if (id == null) {
            return null;
        }
        History history = new History();
        history.setId(id);
        return history;
    }
}
