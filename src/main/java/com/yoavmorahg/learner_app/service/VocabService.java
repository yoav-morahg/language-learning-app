package com.yoavmorahg.learner_app.service;

import com.yoavmorahg.learner_app.Exception.InvalidDataException;
import com.yoavmorahg.learner_app.Exception.ResourceNotFoundException;
import com.yoavmorahg.learner_app.Exception.VocabItemNotFoundException;
import com.yoavmorahg.learner_app.entity.VocabCollection;
import com.yoavmorahg.learner_app.entity.VocabItem;
import com.yoavmorahg.learner_app.entity.VocabItemDto;
import com.yoavmorahg.learner_app.repository.VocabCollectionRepository;
import com.yoavmorahg.learner_app.repository.VocabCollectionRepositoryCustom;
import com.yoavmorahg.learner_app.repository.VocabItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VocabService {

    private final VocabItemRepository vocabItemRepository;
    private final VocabCollectionRepository vocabCollectionRepository;
    private final VocabCollectionRepositoryCustom vocabCollectionCustomRepository;


    @PersistenceContext
    private EntityManager entityManager;

    public VocabService(VocabItemRepository vocabItemRepository,
                        VocabCollectionRepository vocabCollectionRepository,
                        VocabCollectionRepositoryCustom vocabCollectionCustomRepository) {
        this.vocabItemRepository = vocabItemRepository;
        this.vocabCollectionRepository = vocabCollectionRepository;
        this.vocabCollectionCustomRepository = vocabCollectionCustomRepository;
    }

    public List<VocabItemDto> list() {
        List<VocabItem> vocabItems = vocabItemRepository.findAll();
        List<VocabItemDto> dtos = vocabItems.stream()
                .map(item -> {
                    try {
                        return vocabItemToDto(item);
                    } catch (VocabItemNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
        //return (List<VocabItem>)dtos;
        return dtos;
    }


    public VocabCollection getCollectionByName(String name) throws ResourceNotFoundException {
        Optional<VocabCollection> collection =  vocabCollectionRepository.findByName(name);
        if (collection.isPresent()) {
            return collection.get();
        }
        throw new ResourceNotFoundException("Unable to retrieve collection with name '"
                + name + "'");
    }

    public VocabItemDto getVocabItem(String term) throws VocabItemNotFoundException {

        Optional<VocabItem> vocabItem = vocabItemRepository.findBySideA(term);
        if (vocabItem.isPresent()) {
            return vocabItemToDto(vocabItem.get());
        }

        throw new VocabItemNotFoundException("Vocab term '" + term + "' not found.");

    }
/*
    public VocabItemDto getRandomVocabTerm() {
        Long qty = vocabItemRepository.count();
        int idx = (int)(Math.random() * qty);

        List<VocabItem> vocabItems = vocabItemRepository.findAll();
        VocabItem vi = vocabItems.get(qty.intValue() - 1);
        if (vi != null) {
            VocabItemDto dto = new VocabItemDto();
            dto.setId(vi.getId());
            dto.setSideA(vi.getSideA());
            dto.setSideB(vi.getSideB());
            if (vi.getAudioData() != null) {
                dto.setAudioDataId(vi.getAudioData().getId());
            }
            return dto;
        }

        return null;
    }*/



    public VocabItemDto getRandomVocabTerm() {
        Long qty = vocabItemRepository.count();
        int idx = (int)(Math.random() * qty);
        Page<VocabItem> vocabItemPage = vocabItemRepository.findAll(PageRequest.of(idx, 1));
        VocabItem vi = null;
        if (vocabItemPage.hasContent()) {
            vi = vocabItemPage.getContent().get(0);
            if (vi != null) {
                VocabItemDto dto = new VocabItemDto();
                dto.setId(vi.getId());
                dto.setSideA(vi.getSideA());
                dto.setSideB(vi.getSideB());
                if (vi.getAudioData() != null) {
                    dto.setAudioDataId(vi.getAudioData().getId());
                }
                return dto;
            }
        }

        return null;

    }

    private VocabItemDto vocabItemToDto(VocabItem vocabItem) throws VocabItemNotFoundException {
        VocabItemDto vocabItemDto;
        if (vocabItem != null) {
            vocabItemDto = new VocabItemDto();
            vocabItemDto.setId(vocabItem.getId());
            vocabItemDto.setSideA(vocabItem.getSideA());
            vocabItemDto.setSideB(vocabItem.getSideB());
            if (vocabItem.getAudioData() != null) {
                vocabItemDto.setAudioDataId(vocabItem.getAudioData().getId());
            }
            return vocabItemDto;
        }

        throw new VocabItemNotFoundException();
    }


    public VocabCollection createCollection(String name) throws InvalidDataException {
         if (name != null) {
             VocabCollection newCollection = new VocabCollection();
             newCollection.setName(name);
             return vocabCollectionRepository.save(newCollection);
         }
         throw new InvalidDataException("Parameter name is missing.");
    }

    public List<VocabCollection> listCollections() {
        return vocabCollectionRepository.findAll(Sort.by("name"));
    }

    @Transactional
    public List<VocabCollection> listCollections(boolean loadItems) {
        if (!loadItems) {
            return listCollections();
        }
        else {
            List<VocabCollection> collections = listCollections();
            for (int i = 0; i < collections.size(); i++) {
                Optional<VocabCollection> curCollection = vocabCollectionRepository.findById(
                        collections.get(i).getId());
                if (curCollection.isPresent()) {
                    System.out.println(curCollection.get().getVocabItems().size());
                }
            }
            return collections;
        }
    }
}
