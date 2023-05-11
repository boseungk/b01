package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Company;
import org.zerock.b01.domain.QCompany;

import java.util.List;

public class CompanySearchImpl extends QuerydslRepositorySupport implements CompanySearch {

    public CompanySearchImpl(){
        super(Company.class);
    }

    @Override
    public Page<Company> searchAll(String[] types, String keyword, Pageable pageable) {

        QCompany company = QCompany.company;
        JPQLQuery<Company> query = from(company);

        if( (types != null && types.length > 0) && keyword != null ){

            BooleanBuilder booleanBuilder = new BooleanBuilder(); // (

            for(String type: types){

                switch (type){
                    case "t":
                        booleanBuilder.or(company.title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(company.content.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(company.writer.contains(keyword));
                        break;
                }
            }
            query.where(booleanBuilder);
        }

        query.where(company.bno.gt(0L));


        this.getQuerydsl().applyPagination(pageable, query);

        List<Company> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);

    }


}
