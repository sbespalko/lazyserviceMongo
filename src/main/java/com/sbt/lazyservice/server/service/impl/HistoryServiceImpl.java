package com.sbt.lazyservice.server.service.impl;

import com.sbt.lazyservice.server.service.HistoryService;
import com.sbt.lazyservice.server.domain.History;
import com.sbt.lazyservice.server.repository.HistoryRepository;
import com.sbt.lazyservice.server.service.dto.HistoryDTO;
import com.sbt.lazyservice.server.service.mapper.HistoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link History}.
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    private final Logger log = LoggerFactory.getLogger(HistoryServiceImpl.class);

    private final HistoryRepository historyRepository;

    private final HistoryMapper historyMapper;

    public HistoryServiceImpl(HistoryRepository historyRepository, HistoryMapper historyMapper) {
        this.historyRepository = historyRepository;
        this.historyMapper = historyMapper;
    }

    /**
     * Save a history.
     *
     * @param historyDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public HistoryDTO save(HistoryDTO historyDTO) {
        log.debug("Request to save History : {}", historyDTO);
        History history = historyMapper.toEntity(historyDTO);
        history = historyRepository.save(history);
        return historyMapper.toDto(history);
    }

    /**
     * Get all the histories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<HistoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Histories");
        return historyRepository.findAll(pageable)
            .map(historyMapper::toDto);
    }


    /**
     * Get one history by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<HistoryDTO> findOne(String id) {
        log.debug("Request to get History : {}", id);
        return historyRepository.findById(id)
            .map(historyMapper::toDto);
    }

    /**
     * Delete the history by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete History : {}", id);
        historyRepository.deleteById(id);
    }
}
