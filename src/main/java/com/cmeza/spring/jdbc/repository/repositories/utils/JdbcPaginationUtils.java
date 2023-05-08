package com.cmeza.spring.jdbc.repository.repositories.utils;

import lombok.experimental.UtilityClass;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.statement.select.*;

@UtilityClass
public final class JdbcPaginationUtils {

    public boolean isSimpleCountSql(PlainSelect select) {
        if (select.getGroupBy() != null) {
            return false;
        }

        if (select.getDistinct() != null) {
            return false;
        }

        for (SelectItem item : select.getSelectItems()) {
            if (item.toString().contains("?")) {
                return false;
            }
            if (item instanceof SelectExpressionItem) {
                if (((SelectExpressionItem) item).getExpression() instanceof Function) {
                    return false;
                }
            }
        }
        return true;
    }

    public void cleanSelect(SelectBody sb) {
        if (sb instanceof PlainSelect) {
            ((PlainSelect) sb).setOrderByElements(null);
        } else if (sb instanceof WithItem) {
            WithItem wi = (WithItem) sb;
            if (wi.getSubSelect() != null) {
                cleanSelect(wi.getSubSelect().getSelectBody());
            }
        } else {
            SetOperationList sol = (SetOperationList) sb;
            if (sol.getSelects() != null) {
                sol.getSelects().forEach(JdbcPaginationUtils::cleanSelect);
            }
            sol.setOrderByElements(null);
        }
    }

    public boolean offsetValidate(long offset, int limit) {
        if (offset > limit) {
            return offset % limit == 0;
        } else {
            return limit % offset == 0;
        }
    }

}
