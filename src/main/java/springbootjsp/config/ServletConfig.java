package springbootjsp.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import javax.servlet.ServletRegistration;

import org.apache.tomcat.util.descriptor.web.ServletDef;
import org.apache.tomcat.util.descriptor.web.WebXml;
import org.apache.tomcat.util.descriptor.web.WebXmlParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.xml.sax.InputSource;

import lombok.extern.slf4j.Slf4j;

@ConditionalOnProperty("jsp.pre-compile.enabled")
@Slf4j
@Configuration
public class ServletConfig {
	@Value("classpath:/web.xml")
	private Resource precompiledJspWebXml;

	@Bean
	public ServletContextInitializer registerPreCompiledJsps() {
		return servletContext -> {
			WebXmlParser parser = new WebXmlParser(false, false, true);

			try (InputStream is = precompiledJspWebXml.getInputStream()) {
				BufferedReader r = new BufferedReader(new InputStreamReader(is));

				String l;
				while ((l = r.readLine()) != null) {
					System.out.println(l);
				}
			} catch (IOException e) {
				throw new RuntimeException("Error registering precompiled JSPs", e);
			}
			try (InputStream is = precompiledJspWebXml.getInputStream()) {
				WebXml webXml = new WebXml();
				boolean success = parser.parseWebXml(new InputSource(is), webXml, false);
				if (!success) {
					throw new RuntimeException("Error parsing Web XML " + is);
				}
				for (ServletDef def : webXml.getServlets().values()) {
					log.info("Registering precompiled JSP: {} -> {}", def.getServletName(), def.getServletClass());
					ServletRegistration.Dynamic reg = servletContext.addServlet(def.getServletName(), def.getServletClass());
					reg.setLoadOnStartup(99);
				}
				for (Map.Entry<String, String> mapping : webXml.getServletMappings().entrySet()) {
					log.info("Mapping servlet: {} -> {}", mapping.getValue(), mapping.getKey());
					servletContext.getServletRegistration(mapping.getValue()).addMapping(mapping.getKey());
					servletContext.getServletRegistration(mapping.getValue()).getMappings().remove(mapping.getKey());
				}
				for (Map.Entry<String, String> mapping : webXml.getServletMappings().entrySet()) {
					log.info("Mapping servlet: {} -> {}", mapping.getValue(), mapping.getKey());
					servletContext.getServletRegistration(mapping.getValue()).addMapping(mapping.getKey());
				}
			} catch (IOException e) {
				throw new RuntimeException("Error registering precompiled JSPs", e);
			}
		};
	}
}
