package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Company;
import org.zerock.b01.dto.CompanyDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.CompanyRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class CompanyServiceImpl implements CompanyService{

    private final ModelMapper modelMapper;

    private final CompanyRepository companyRepository;

    @Override
    public Long register(CompanyDTO companyDTO) {

        Company company = modelMapper.map(companyDTO, Company.class);

        Long bno = companyRepository.save(company).getBno();

        return bno;
    }

    @Override
    public CompanyDTO readOne(Long bno) {

        Optional<Company> result = companyRepository.findById(bno);

        Company company = result.orElseThrow(() -> new NoSuchElementException());

        CompanyDTO companyDTO = modelMapper.map(company, CompanyDTO.class);

        return companyDTO;
    }

    @Override
    public void modify(CompanyDTO companyDTO) {

        Optional<Company> result = companyRepository.findById(companyDTO.getBno());

        Company company = result.orElseThrow(() -> new NoSuchElementException());

        company.change(companyDTO.getTitle(), companyDTO.getContent());

        companyRepository.save(company);

    }

    @Override
    public void remove(Long bno) {

        companyRepository.deleteById(bno);

    }


    @Override
    public PageResponseDTO<CompanyDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        Pageable pageable = pageRequestDTO.getPageable("bno");

        Page<Company> result = companyRepository.searchAll(types, keyword, pageable);

        List<CompanyDTO> dtoList = result.getContent().stream()
                .map(company -> modelMapper.map(company,CompanyDTO.class)).collect(Collectors.toList());


        return PageResponseDTO.<CompanyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();

    }


}
