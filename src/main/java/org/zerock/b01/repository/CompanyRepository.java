package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.Company;
import org.zerock.b01.repository.search.CompanySearch;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanySearch {


}
