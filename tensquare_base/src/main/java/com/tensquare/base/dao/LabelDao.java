package com.tensquare.base.dao;

import com.tensquare.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 标签数据访问接口
 */
public interface LabelDao extends JpaRepository<Label,String> , JpaSpecificationExecutor<Label> {


    /**
     * 根据状态查询标签列表
     * @param state
     * @return
     */
    public List<Label> findByState(String state);

    /**
     *  根据状态与推荐状态查询标签列表
     * @param state
     * @param recommend
     * @return
     */
    public List<Label> findByStateAndRecommend(String state,String recommend);


}
