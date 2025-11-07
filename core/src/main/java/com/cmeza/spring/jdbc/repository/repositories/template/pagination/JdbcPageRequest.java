package com.cmeza.spring.jdbc.repository.repositories.template.pagination;

import com.cmeza.spring.jdbc.repository.utils.JdbcPaginationUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class JdbcPageRequest implements Serializable {
    private static final long serialVersionUID = -648275131076429524L;
    
    private final int page;
    private final int size;
    private final long offset;

    private JdbcPageRequest(int page, int size, long offset) {
        this.page = page;
        this.size = size;
        this.offset = offset;
    }

    public static JdbcPageRequest ofPage(int page, int size) {
        if (page < 1) {
            throw new IllegalArgumentException("Page index must not be less than zero");
        } else if (size < 1) {
            throw new IllegalArgumentException("Page size must not be less than one");
        }
        return new JdbcPageRequest(page, size, (long) (page - 1) * (long) size);
    }

    public static JdbcPageRequest ofBounds(long offset, int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("Page limit must not be less than one");
        } else if (offset < 0) {
            throw new IllegalArgumentException("Page offset must not be less than one");
        } else if (offset != 0 && offset != limit && !JdbcPaginationUtils.offsetValidate(offset, limit)) {
            throw new IllegalArgumentException("Page offset " + offset + " needs to be a multiple of " + limit);
        }
        int page = 1;
        if (offset > 0) {
            BigDecimal bigDecimal = BigDecimal.valueOf(offset / limit);
            page = bigDecimal.setScale(0, RoundingMode.CEILING).intValue() + 1;
        }
        return new JdbcPageRequest(page, limit, offset);
    }

    public int getPageNumber() {
        return page;
    }

    public int getPageSize() {
        return size;
    }

    public long getOffset() {
        return offset;
    }


}
