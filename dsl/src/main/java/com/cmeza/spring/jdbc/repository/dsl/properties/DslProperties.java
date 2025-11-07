package com.cmeza.spring.jdbc.repository.dsl.properties;

import com.cmeza.spring.jdbc.repository.support.properties.methods.*;
import lombok.Data;

@Data
public class DslProperties {

    //Operations
    private JdbcCallProperties call;
    private JdbcExecuteProperties execute;
    private JdbcFunctionProperties function;
    private JdbcInsertProperties insert;
    private JdbcPaginationProperties pagination;
    private JdbcProcedureProperties procedure;
    private JdbcQueryProperties query;
    private JdbcRawPaginationProperties rawPagination;
    private JdbcRawQueryProperties rawQuery;
    private JdbcRawUpdateProperties rawUpdate;
    private JdbcUpdateProperties update;

}
