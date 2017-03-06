package com.shdic.szhg.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.shdic.szhg.util.ObjectCreator;
/**
 * 用Hibernate进行基础数据库操作
 * @author zangql
 *
 */
public class BaseDao {
	public static Logger logger = Logger.getLogger(BaseDao.class.getName());

	/**
	 * 根据条件保存数据库中的记录
	 * 
	 * @param entityname
	 *            实体类名
	 * @param values
	 *            需要保存的数据
	 * @throws Exception
	 */
	public void save(Session session, String entityname, Map values)
			throws Exception {
		logger.info("保存实体对象" + entityname);
		logger.info(values);
		try {
			// 根据实体类名将一条记录转换为实体对象
			Object bean = ObjectCreator.createSpecialObjectBean(entityname,
					values);
			
			// 根据数据源名保存实体对象
			session.save(bean);
		
		} catch (Exception e) {
			throw new Exception("保存数据错误");
		}
	}

	/**
	 * 根据条件更新数据库中的记录
	 * 
	 * @param session
	 *            hibernate sessionfactory
	 * @param entityname
	 *            实体类名
	 * @param values
	 *            需要更新的数据
	 * @param conditions
	 *            更新条件
	 * @throws Exception
	 */
	public void update(Session session, String entityname, Map values,
			Map conditions) throws Exception {
		logger.info("更新实体对象" + entityname);
		logger.info(values);
		logger.info(conditions);

		// 查询数据库获得实体对象
		List beans = this.listBeans(session, entityname, conditions);
		// 更新所有的实体对象
		for (int i = 0; i < beans.size(); i++) {
			Object bean = beans.get(i);
			// 将需要更新的数据更新到实体对象
			bean = ObjectCreator.createSpecialObjectBean(bean, values);
			// 根据数据源名更新实体对象
			session.update(bean);
		}
	}

	/**
	 * 根据条件删除数据库中的记录
	 * 
	 * @param session
	 *            hibernate sessionfactory
	 * @param entityname
	 *            实体类名
	 * @param conditions
	 *            删除条件
	 * @throws Exception
	 */
	public void delete(Session session, String entityname, Map conditions)
			throws Exception {
		logger.info("删除实体对象" + entityname);
		logger.info(conditions);

		// 查询数据库获得实体对象
		List beans = this.listBeans(session, entityname, conditions);
		// 删除所有的实体对象
		for (int i = 0; i < beans.size(); i++) {
			Object bean = beans.get(i);
			// 根据数据源名删除实体对象
			session.delete(bean);
		}
	}

	/**
	 * 根据查询条件从数据库中获得一条记录
	 * 
	 * @param session
	 *            hibernate sessionfactory
	 * @param entityname
	 *            实体类名
	 * @param conditions
	 *            查询条件
	 * @return 返回的一条记录
	 * @throws Exception
	 */
	public Map load(Session session, String entityname, Map conditions)
			throws Exception {
		if (conditions == null || conditions.size() == 0) {
			throw new Exception("查询指定的实体对象必须指定查询条件");
		}

		// 查询数据库获得实体对象
		List beans = this.listBeans(session, entityname, conditions);

		// 返回第一条记录
		if (beans.size() == 0) {
			return null;
		} else {
			// 将第一个实体对象转换为记录返回
			return toRow(beans.get(0));
		}
	}

	public List list(Session session, String sql) throws Exception {
		List list = new ArrayList();
		try {
			Query query = session.createQuery(sql);
			logger.info("查询sql：" + query);

			List beans = query.list();
			logger.info("查询结果：" + list);
			if (beans != null)
				list = toRows(beans);
		} catch (Exception e) {
			logger.error("执行查询语句" + sql + "出错：" + e.getMessage(), e);
			throw new Exception("执行查询语句" + sql + "出错：" + e.getMessage());
		}
		return list;
	}

	/**
	 * 根据查询条件从数据库中获得多条记录
	 * 
	 * @param session
	 *            hibernate sessionfactory
	 * @param entityname
	 *            实体类名
	 * @param conditions
	 *            查询条件
	 * @return 返回的多条记录
	 * @throws Exception
	 */
	public List list(Session session, String entityname, Map conditions)
			throws Exception {
		logger.info("查询实体对象" + entityname);
		logger.info(conditions);

		// 查询数据库获得实体对象
		List beans = this.listBeans(session, entityname, conditions);

		// 将所有实体对象转换为记录返回
		return toRows(beans);
	}

	/**
	 * 根据查询条件从数据库中获得多个实体对象
	 * 
	 * @param session
	 *            hibernate sessionfactory
	 * @param entityname
	 *            实体类名
	 * @param conditions
	 *            查询条件
	 * @return 返回的多条记录
	 * @throws Exception
	 */
	private List listBeans(Session session, String entityname, Map conditions)
			throws Exception {

		// 根据实体类的属性转换查询条件的字段
		Map cond = ObjectCreator.FileNameBean(entityname, conditions);

		// 将查询条件拼接为查询语句
		Object[] values = new Object[cond.size()];
		String sql = "from " + entityname + " as t";
		int i = 0;
		for (Iterator it = cond.keySet().iterator(); it.hasNext();) {
			String field = (String) it.next();
			if (i == 0) {
				sql += " where t." + field + " = ?";
			} else {
				sql += " and t." + field + " = ?";
			}

			values[i] = cond.get(field);

			i++;
		}
		Query query = session.createQuery(sql);
		for (int j = 0; j < values.length; j++) {
			query.setString(j, values[j].toString());
		}
		logger.info("查询sql：" + query);
		logger.info("查询条件:" + cond);

		List list = query.list();
		logger.info("查询结果：" + list);
		return list;
	}

	/**
	 * 将多个实体对象转换为多条记录
	 * 
	 * @param beans
	 *            多个实体对象
	 * @return 多条记录
	 * @throws Exception
	 */
	private List toRows(List beans) throws Exception {
		List ret = new ArrayList();

		for (int i = 0; i < beans.size(); i++) {
			Object bean = beans.get(i);

			Map row = new HashMap();
			// 将一个实体对象转换为记录
			row = ObjectCreator.createSpecialMap(bean, row);
			ret.add(row);
		}

		return ret;
	}

	/**
	 * 将一个实体对象转换为一条记录
	 * 
	 * @param bean
	 *            一个实体对象
	 * @return 一条记录
	 * @throws Exception
	 */
	private Map toRow(Object bean) throws Exception {
		Map row = new HashMap();
		return ObjectCreator.createSpecialMap(bean, row);
	}

	/**
	 * 根据实体类名、查询条件、关系运算符生成查询语句
	 * 
	 * @param entityname
	 *            实体类名
	 * @param conditions
	 *            查询条件
	 * @param operators
	 *            查询条件关系运算符
	 * @return 查询语句
	 */
	private String buildSQL(String entityname, Map conditions, List operators) {
		String sql = "from " + entityname + " as t";
		int i = 0;
		for (Iterator it = conditions.keySet().iterator(); it.hasNext();) {
			String operator = (String) operators.get(i);
			String field = (String) it.next();
			if (i == 0) {
				sql += " where t." + field + " " + operator + " ?";
			} else {
				sql += " and t." + field + " " + operator + " ?";
			}

			i++;
		}

		return sql;
	}

}
