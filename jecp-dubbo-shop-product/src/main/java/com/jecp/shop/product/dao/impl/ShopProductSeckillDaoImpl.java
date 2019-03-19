package com.jecp.shop.product.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.jecp.base.dao.impl.BaseDaoImpl;
import com.jecp.shop.product.dao.ShopProductSeckillDao;
import com.jecp.shop.product.dto.SeckillDetailDTO;
import com.jecp.shop.product.model.ShopProductSeckill;

@Repository
public class ShopProductSeckillDaoImpl extends BaseDaoImpl<ShopProductSeckill> implements ShopProductSeckillDao {
	private static final Logger log = LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

	@Override
	public List<SeckillDetailDTO> list() {
		StringBuilder sql = new StringBuilder();
		sql.append("select d1.category_id \"categoryId\",d1.title \"title\",d1.subtitle \"subtitle\",d1.picture_url \"pictureUrl\",s.seckill_id \"seckillId\",");
		sql.append("s.product_id \"productId\",s.seckill_price \"seckillPrice\",s.seckill_stock \"seckillStock\",s.seller_id \"sellerId\" ");
		sql.append(",s.start_time \"startTime\",s.end_time  \"endTime\",s.create_time \"createTime\",s.code_strategy \"codeStrategy\" from Shop_Product_Seckill s ");
		sql.append("left join (select * from Shop_Product p1) d1 on d1.product_id=s.product_id order by s.end_time desc");
		Query query = getSession().createNativeQuery(sql.toString());
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SeckillDetailDTO.class));
		List<SeckillDetailDTO> list = query.getResultList();
		return list;
	}
	@Override
	public SeckillDetailDTO getSecKillDetailDTO(String seckillId) {
		StringBuilder sql = new StringBuilder();
		sql.append("select d1.category_id \"categoryId\",d1.title \"title\",d1.subtitle \"subtitle\",d1.picture_url \"pictureUrl\",d1.seller_id \"sellerId\" ");
		sql.append(",s.seckill_id \"seckillId\",s.product_id \"productId\",s.seckill_price \"seckillPrice\",s.seckill_stock \"seckillStock\" ");
		sql.append(",s.start_time \"startTime\",s.end_time  \"endTime\",s.create_time \"createTime\",s.code_strategy \"codeStrategy\"  from Shop_Product_Seckill s ");
		sql.append("left join (select * from Shop_Product p1) d1 on d1.product_id=s.product_id where s.seckill_id=:seckillId");
		Query query = getSession().createNativeQuery(sql.toString());
		query.setParameter("seckillId", seckillId);
		query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.aliasToBean(SeckillDetailDTO.class));
		return (SeckillDetailDTO) query.uniqueResult();
	}
	@Override
	public int reduceStock(String seckillId, Integer productCount) {
		Query query = getSession().createQuery("update ShopProductSeckill set seckillStock=seckillStock-:productCount where seckillId=:seckillId and seckillStock>=:productCount");
		query.setParameter("seckillId", seckillId);
		query.setParameter("productCount", productCount);
		return query.executeUpdate();
	}

}
