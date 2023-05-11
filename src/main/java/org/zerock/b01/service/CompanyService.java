package org.zerock.b01.service;

import org.zerock.b01.dto.CompanyDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

public interface CompanyService {

    Long register(CompanyDTO companyDTO);

    CompanyDTO readOne(Long bno);

    void modify(CompanyDTO companyDTO);

    void remove(Long bno);

    PageResponseDTO<CompanyDTO> list(PageRequestDTO pageRequestDTO);

}
