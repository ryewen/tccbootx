package com.loststars.tccbootx.item.dao;

import com.loststars.tccbootx.item.dataobject.ItemDO;
import com.loststars.tccbootx.item.dataobject.ItemDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemDOMapper {
    long countByExample(ItemDOExample example);

    int deleteByExample(ItemDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemDO record);

    int insertSelective(ItemDO record);

    List<ItemDO> selectByExample(ItemDOExample example);

    ItemDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    int updateByExample(@Param("record") ItemDO record, @Param("example") ItemDOExample example);

    int updateByPrimaryKeySelective(ItemDO record);

    int updateByPrimaryKey(ItemDO record);

    int reduceStock(@Param("id") Integer id, @Param("amount") Integer amount);

    int addStock(@Param("id") Integer id, @Param("amount") Integer amount);
}