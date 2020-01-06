package com.definesys.readword.dao;

import com.definesys.mpaas.common.exception.MpaasRuntimeException;
import com.definesys.mpaas.common.http.Response;
import com.definesys.mpaas.log.SWordLogger;
import com.definesys.mpaas.query.db.PageQueryResult;
import com.definesys.mpaas.query.MpaasQueryFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.definesys.readword.pojo.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * @Copyright: Shanghai Definesys Company.All rights reserved.
 * @Description:
 * @author: fei
 * @since: 2019-08-20
 * @history: 1.2019-08-20 created by fei
 */
@Component
public class TestReadWordDAO {

    @Autowired
    private MpaasQueryFactory sw;

    @Autowired
    private SWordLogger logger;

    public List<TestReadWord> queryTestReadWord() {
        List<TestReadWord> table = sw.buildQuery()
                .doQuery(TestReadWord.class);
        return table;
    }


    @Transactional
    public String addTestReadWord(TestReadWord item) {

            sw.buildQuery().doInsert(item);

        return "success";
    }


}