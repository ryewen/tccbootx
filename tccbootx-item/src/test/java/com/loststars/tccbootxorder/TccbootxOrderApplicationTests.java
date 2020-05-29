package com.loststars.tccbootxorder;

import com.loststars.tccbootx.item.ItemApplication;
import com.loststars.tccbootx.item.dao.ItemDOMapper;
import com.loststars.tccbootx.item.dataobject.ItemDO;
import com.loststars.tccbootx.item.dataobject.ItemDOExample;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItemApplication.class)
class TccbootxOrderApplicationTests {

	@Autowired
	private ItemDOMapper itemDOMapper;

    @Test
	void test() {
//		ItemDO itemDO = new ItemDO();
//		itemDO.setId(1);
//		itemDO.setName("iPhone 4s");
//		itemDO.setStock(1222);
//		itemDO.setPrice(new BigDecimal(3444));
//		System.out.println(itemDOMapper.insertSelective(itemDO));
		ItemDOExample example = new ItemDOExample();
		example.createCriteria().andIdEqualTo(1);
		itemDOMapper.selectByExample(example).forEach((itemDO) -> {
			System.out.println(itemDO.getId());
		});
    }

}
