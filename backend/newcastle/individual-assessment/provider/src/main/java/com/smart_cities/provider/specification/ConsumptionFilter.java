package com.smart_cities.provider.specification;

import com.smart_cities.provider.model.Consumption;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConsumptionFilter implements Specification<Consumption> {
    private LocalDateTime consumptionPeriodStart;
    private LocalDateTime consumptionPeriodEnd;

    public LocalDateTime getConsumptionPeriodStart() {
        return this.consumptionPeriodStart;
    }

    public void setConsumptionPeriodStart(LocalDateTime consumptionPeriodStart) {
        this.consumptionPeriodStart = consumptionPeriodStart;
    }

    public LocalDateTime getConsumptionPeriodEnd() {
        return this.consumptionPeriodEnd;
    }

    public void setConsumptionPeriodEnd(LocalDateTime consumptionPeriodEnd) {
        this.consumptionPeriodEnd = consumptionPeriodEnd;
    }

    /**
     * Responsible for applying filters to findAll method.
     *
     * 1. Applies consumptionPeriodStart filter.
     * 2. Applies consumptionPeriodEnd filter.
     *
     * @param root  The root of the query.
     * @param query The query.
     * @param cb    The criteria builder.
     */
    @Override
    public Predicate toPredicate(Root<Consumption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        ArrayList<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(this.getConsumptionPeriodStart())) {
            predicates.add(cb.greaterThan(root.get("generatedAt"), this.getConsumptionPeriodStart()));
        }

        if (!ObjectUtils.isEmpty(this.getConsumptionPeriodEnd())) {
            predicates.add(cb.lessThan(root.get("generatedAt"), this.getConsumptionPeriodEnd()));
        }

        return predicates.size() <= 0 ? null : cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
