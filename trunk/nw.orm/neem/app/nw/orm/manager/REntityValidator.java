package nw.orm.manager;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;


/**
 *
 * @author Ogwara O. Rowland (r.ogwara@nimworks.com)
 * @version 0.1
 * @since 11th May, 2013
 *
 */
public class REntityValidator {

	private HibernateUtil hUtil;

	private ValidatorFactory vf;
	private Validator val;

	public REntityValidator(HibernateUtil util) {
		this.hUtil = util;
	}

	public Set<ConstraintViolation<Object>> validateEntity(Object obj) {
		vf = hUtil.getValidatorFactory();
		val = vf.getValidator();
		return val.validate(obj);
	}

}
