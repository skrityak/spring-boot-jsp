package springbootjsp.controller;

import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Setter;
import springbootjsp.model.TTasks;

@Setter
@ConfigurationProperties("taskinitializer")
@Component
public class TaskInitializer implements InitializingBean {
	private int numberOfTasks = 500;
	@Autowired
	private ITTaskJpaCrud crudJpa;

	@Override
	public void afterPropertiesSet() {
		for (int i = 1; i <= numberOfTasks; i++) {
			crudJpa.save(new TTasks("Task_" + i, "T1Descriptor" + i, new Date(), i % 2 == 0));
		}
	}
}
