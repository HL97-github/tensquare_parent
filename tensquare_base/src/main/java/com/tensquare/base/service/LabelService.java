package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部记录
     * @return
     */
    public List<Label> findAll(){
       return labelDao.findAll();
    }

    /**
     * 根据ID查询实体
     * @param id
     * @return
     */
    public Label findById(String id){
       return labelDao.findById(id).get();
    }

    /**
     *  增加
     * @param label
     */
    public void add(Label label){
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /**
     * 修改
     * @param label
     */
    public void update(Label label){
        labelDao.save(label);
    }


    /**
     * 删除
     * @param id
     */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }


    /**
     * 条件查询（不分页）
     * @param searchMap
     * @return
     */
    public List<Label> findSearch(Map searchMap){
        Specification specification=createSpecification(searchMap);
        return labelDao.findAll(specification);
    }

    /**
     * 条件查询（分页）
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    public Page<Label> findSearch(Map searchMap,int page,int size){
        Specification specification=createSpecification(searchMap);
        PageRequest pageRequest=PageRequest.of(page-1,size);
        return  labelDao.findAll(specification,pageRequest);
    }

    private Specification createSpecification(Map searchMap) {

        return new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder cb) {

                List<Predicate> predicateList=new ArrayList();
                if(searchMap!=null ){
                    if(searchMap.get("labelname")!=null && !"".equals(searchMap.get("labelname"))  ){
                        predicateList.add(  cb.like(  root.get("labelname").as(String.class), "%"+ searchMap.get("labelname")+"%"   ) );
                    }
                    if(searchMap.get("state")!=null && !"".equals(searchMap.get("state"))  ){
                        predicateList.add(  cb.equal(  root.get("state").as(String.class), searchMap.get("state")   ) );
                    }
                    if(searchMap.get("recommend")!=null && !"".equals(searchMap.get("recommend"))  ){
                        predicateList.add(  cb.equal(  root.get("recommend").as(String.class), searchMap.get("recommend")   ) );
                    }
                }
                return cb.and( predicateList.toArray( new Predicate[ predicateList.size() ] ) );
            }
        };

    }

    /**
     * 有效标签列表
     * @return
     */
    public List<Label> list(){
        return labelDao.findByState("1");
    }

    /**
     * 推荐标签列表
     * @return
     */
    public List<Label> toplist(){
        return labelDao.findByStateAndRecommend("1","1");
    }

}
