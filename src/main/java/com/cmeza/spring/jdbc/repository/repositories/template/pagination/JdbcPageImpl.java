package com.cmeza.spring.jdbc.repository.repositories.template.pagination;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class JdbcPageImpl<T> implements JdbcPage<T>, Serializable {

    private static final long serialVersionUID = 4151413836228834454L;

    private final List<T> content;
    private final JdbcPageRequest pageRequest;
    private final long total;

    public JdbcPageImpl(long total, JdbcPageRequest pageRequest) {
        this(Collections.emptyList(), total, pageRequest);
    }

    public JdbcPageImpl(List<T> content, long total, JdbcPageRequest pageRequest) {
        this.content = content;
        this.pageRequest = pageRequest;
        this.total = total;
    }

    @Override
    public int getCurrentPage() {
        return pageRequest.getPageNumber();
    }

    @Override
    public int getTotalPages() {
        if (this.pageRequest.getPageSize() <= 1) {
            return 1;
        }
        BigDecimal bigDecimal = BigDecimal.valueOf(this.total / (this.pageRequest.getPageSize() * 1.0));
        return bigDecimal.setScale( 0 , RoundingMode.CEILING ).intValue();
    }

    @Override
    public long getTotalElements() {
        return total;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean isFirst() {
        return this.pageRequest.getPageNumber() <= 1;
    }

    @Override
    public boolean isLast() {
        return !this.hasNext();
    }

    public boolean hasNext() {
        return this.pageRequest.getPageNumber() < this.getTotalPages();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JdbcPageImpl<?> jdbcPage = (JdbcPageImpl<?>) o;
        return total == jdbcPage.total && Objects.equals(content, jdbcPage.content) && Objects.equals(pageRequest, jdbcPage.pageRequest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, pageRequest, total);
    }

    @Override
    public String toString() {
        return "JdbcPageImpl{" +
                "totalPages=" + getTotalPages() +
                ", currentPage=" + getCurrentPage() +
                ", totalElements=" + getTotalElements() +
                ", isFirst=" + isFirst() +
                ", isLast=" + isLast() +
                '}';
    }
}
