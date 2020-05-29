package com.loststars.tccbootx.user.dao;

import com.loststars.tccbootx.user.dataobject.UserDO;
import com.loststars.tccbootx.user.dataobject.UserDOExample;

import java.math.BigDecimal;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserDOMapper {
    long countByExample(UserDOExample example);

    int deleteByExample(UserDOExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    List<UserDO> selectByExample(UserDOExample example);

    UserDO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByExample(@Param("record") UserDO record, @Param("example") UserDOExample example);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    int reduceWallet(@Param("id") Integer id, @Param("walletAmount") BigDecimal walletAmount);

    int addWallet(@Param("id") Integer id, @Param("walletAmount") BigDecimal walletAmount);

    int reduceRedPacket(@Param("id") Integer id, @Param("redPacketAmount") BigDecimal redPacketAmount);

    int addRedPacket(@Param("id") Integer id, @Param("redPacketAmount") BigDecimal redPacketAmount);
}