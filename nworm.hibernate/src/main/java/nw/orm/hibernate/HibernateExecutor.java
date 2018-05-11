package nw.orm.hibernate;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import nw.orm.core.query.QueryModifier;
import nw.orm.dao.QueryExecutor;

public interface HibernateExecutor extends QueryExecutor {

	<T> T find(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria);

	<T> List<T> list(Class<T> returnClazz, QueryModifier qm, Criterion ... criteria);
	
	<T> List<T> getListByExample(QueryModifier qm, Example example);
	
}
