package com.loststars.tccbootx.item.dao;

import com.loststars.tccbootx.item.dataobject.ItemLogDO;
import com.loststars.tccbootx.item.dataobject.ItemLogDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemLogDOMapper {
    long countByExample(ItemLogDOExample example);

    int deleteByExample(ItemLogDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemLogDO record);

    int insertSelective(ItemLogDO record);

    List<ItemLogDO> selectByExample(ItemLogDOExample example);

    ItemLogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ItemLogDO record, @Param("example") ItemLogDOExample example);

    int updateByExample(@Param("record") ItemLogDO record, @Param("example") ItemLogDOExample example);

    int updateByPrimaryKeySelective(ItemLogDO record);

    int updateByPrimaryKey(ItemLogDO record);

    int updateStatus(@Param("id") Integer id, @Param("startStatus") String startStatus, @Param("endStatus") String endStatus);
}