package com.loststars.tccbootx.user.dao;

import com.loststars.tccbootx.user.dataobject.RedPacketLogDO;
import com.loststars.tccbootx.user.dataobject.RedPacketLogDOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RedPacketLogDOMapper {
    long countByExample(RedPacketLogDOExample example);

    int deleteByExample(RedPacketLogDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RedPacketLogDO record);

    int insertSelective(RedPacketLogDO record);

    List<RedPacketLogDO> selectByExample(RedPacketLogDOExample example);

    RedPacketLogDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RedPacketLogDO record, @Param("example") RedPacketLogDOExample example);

    int updateByExample(@Param("record") RedPacketLogDO record, @Param("example") RedPacketLogDOExample example);

    int updateByPrimaryKeySelective(RedPacketLogDO record);

    int updateByPrimaryKey(RedPacketLogDO record);

    int updateStatus(@Param("id") Integer id, @Param("startStatus") String startStatus, @Param("endStatus") String endStatus);
}