package com.example.demo.login.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * @author work
 *
 */

@Aspect
@Component
public class ErrorAspect {

	/**
	 * @param ex
	 */

	@AfterThrowing(value="execution(* *..*..*(..))"
			+ "&&(bean(*Controller) || bean(*Service) || bean(*Repository))", throwing="ex")
	public void throwingNull(DataAccessException ex) {

		System.out.println("==================================================");
		System.out.println("DataAccessExceptinが発生しました。：" + ex);
		System.out.println("==================================================");

	}
}
